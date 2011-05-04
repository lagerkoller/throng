/*
 	This file is part of Throng - Tuio multiplexeR that crOps and Globalizes.
	For more information see http://code.google.com/p/throng
	
	Copyright (c) 2010-2011 Johannes Luderschmidt <ich@johannesluderschidt.de>

    Throng is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Throng is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Throng. If not, see <http://www.gnu.org/licenses/>.
 */
package de.johannesluderschmidt.throng.proxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import com.illposed.osc.OSCBundle;
import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPacket;

import de.johannesluderschmidt.throng.util.Constants;

public class ThrongMultiplexer {
	private static ThrongMultiplexer inst;
	
	private HashMap<String, Vector<Integer>> touchAliveIdsToClientAndPort;
	private HashMap<String, Vector<Integer>> blobAliveIdsToClientAndPort;
	private HashMap<String, Vector<Integer>> fiducialAliveIdsToClientAndPort;
	private HashMap<String, Integer> idToClientAndPort;
	private ArrayList<OSCListener> listenersBeforeMultiplexing;
    private ArrayList<OSCListener> listenersAfterMultiplexing;
	private int clientId;
	private int fseq;
	
	private boolean multiplexerEnabled;
    private boolean individualizeIdRanges;
    private boolean manageSrcMsg;
	
	private ThrongMultiplexer(){
		touchAliveIdsToClientAndPort = new HashMap<String, Vector<Integer>>();
		blobAliveIdsToClientAndPort = new HashMap<String, Vector<Integer>>();
		fiducialAliveIdsToClientAndPort = new HashMap<String, Vector<Integer>>();
		idToClientAndPort = new HashMap<String, Integer>();
		listenersBeforeMultiplexing = new ArrayList<OSCListener>();
    	listenersAfterMultiplexing = new ArrayList<OSCListener>();
		fseq = 0;
		clientId = 0;
		multiplexerEnabled = false;
		individualizeIdRanges = false;
		manageSrcMsg = false;
	}
	
	public static ThrongMultiplexer getInstance(){
		if(inst == null){
			inst = new ThrongMultiplexer();
		}
		return inst;
	}
	
	public OSCBundle multiplexTuioBundle(OSCBundle bundle) throws Exception{
		for(OSCListener listener:listenersBeforeMultiplexing){
			listener.acceptBundle(new Date(), bundle);
		}
		
		OSCBundle newBundle=null;
		if(multiplexerEnabled){
			String key = ""+bundle.getIpAddress().toString()+bundle.getPortOut();
			
			if(!idToClientAndPort.containsKey(key)){
				idToClientAndPort.put(key, clientId);
				clientId = clientId+1;
			}
			
			String profileName = getName(bundle); 
			if(profileName.equals(Constants.TWOD_CUR)){
				touchAliveIdsToClientAndPort.put(key, getAliveIds(bundle));
				newBundle = packNewPacketTouch(bundle);
			}else if(profileName.equals(Constants.TWOD_OBJ)){
				fiducialAliveIdsToClientAndPort.put(key, getAliveIds(bundle));
				newBundle = packNewPacketFiducial(bundle);
			}else if(profileName.equals(Constants.TWOD_BLB)){
				blobAliveIdsToClientAndPort.put(key, getAliveIds(bundle));
				newBundle = packNewPacketBlob(bundle);
			}
			else{
				//do nothing
				newBundle = bundle;
			}
		}else{
			newBundle = bundle;
		}
		
		for(OSCListener listener:listenersAfterMultiplexing){
			listener.acceptBundle(new Date(), newBundle);
		}
		
		return newBundle;
	}
	public String getName(OSCBundle bundle){
		String name = "";
		for(OSCPacket packet : bundle.getPackets()){
			OSCMessage message = (OSCMessage)packet;
			if(message.getAddress().equals(Constants.TWOD_CUR)){
				name = Constants.TWOD_CUR;
				break;
			}
			if(message.getAddress().equals(Constants.TWOD_OBJ)){
				name = Constants.TWOD_OBJ;
				break;
			}
			if(message.getAddress().equals(Constants.TWOD_BLB)){
				name = Constants.TWOD_BLB;
				break;
			}
		}
		return name;
	}
	
	private Vector<Integer> getAliveIds(OSCBundle bundle){
		Vector<Integer> aliveIds = new Vector<Integer>();
		for(OSCPacket packet : bundle.getPackets()){
			OSCMessage message = (OSCMessage)packet;
			if(message.getArguments()[0].equals("alive")){
				for(int i = 1; i < message.getArguments().length; i++){
					aliveIds.add((Integer)message.getArguments()[i]);
				}
				break;
			}
		}
		return aliveIds;
	}
	private Vector<Integer> allAliveIds(HashMap<String, Vector<Integer>> clientPortToAliveIds, int offset, boolean individualize){
		Vector<Integer> aliveIds = new Vector<Integer>();
		for(String hostKey : clientPortToAliveIds.keySet()){
			Vector<Integer> ids = clientPortToAliveIds.get(hostKey);
			for(Integer id:ids){
				if(individualize){
					aliveIds.add(getIndividualizedId(id, idToClientAndPort.get(hostKey)));
				}else{
					aliveIds.add(id);
				}
			}
		}
		return aliveIds;
	}
	
	private Integer getIndividualizedId(Integer id, Integer clientId){
		//every Tuio source has its own range of alive ids. Tuio sources
		//are numbered in order of their first use. clientId contains this
		//number. Thus, clientId*Constants.TUIO_SESSION_ID_RANGE is the start
		//of the range to which the actual id is being added. If the id is 
		//bigger than the range, the numbering starts again from the beginning 
		//of the range.
		Integer individualizedAliveId = id%Constants.TUIO_SESSION_ID_RANGE+clientId*Constants.TUIO_SESSION_ID_RANGE;
		
		return individualizedAliveId;
	}
	
	public OSCBundle packNewPacketTouch(OSCBundle receivedBundle){
		return packNewPacket(receivedBundle, touchAliveIdsToClientAndPort, Constants.TWOD_CUR);
	}
	public OSCBundle packNewPacketBlob(OSCBundle receivedBundle){
		return packNewPacket(receivedBundle, blobAliveIdsToClientAndPort, Constants.TWOD_BLB);
	}
	public OSCBundle packNewPacketFiducial(OSCBundle receivedBundle){
		return packNewPacket(receivedBundle, fiducialAliveIdsToClientAndPort, Constants.TWOD_OBJ);
	}
	
	public OSCBundle packNewPacket(OSCBundle receivedBundle,HashMap<String, Vector<Integer>> clientPortAliveIds, String messageType){
		int clientId = idToClientAndPort.get(""+receivedBundle.getIpAddress().toString()+receivedBundle.getPortOut());
		String ipPort = ""+receivedBundle.getIpAddress().toString();
		ipPort = ipPort.substring(1)+":"+receivedBundle.getPortOut();
		
		OSCBundle newBundle = new OSCBundle();
		newBundle.setIpAddress(receivedBundle.getIpAddress());
		newBundle.setPortOut(receivedBundle.getPortOut());
		
		OSCMessage sourceMessage = null;
		OSCMessage aliveMessage = null;
		OSCMessage fseqMessage = null;
		Vector<OSCMessage> setMessages = new Vector<OSCMessage>();
		
		for(OSCPacket packet : receivedBundle.getPackets()){
			OSCMessage message = (OSCMessage)packet;
			if(message.getArguments()[0].equals("source")){
				sourceMessage = message;
				if(manageSrcMsg){
					String sourceValue = (String)message.getArguments()[1];
					int adSignPos = sourceValue.indexOf("@");
					if(adSignPos > -1){
						sourceValue = sourceValue.substring(0,adSignPos+1)+ipPort;
					}else{
						sourceValue = sourceValue+"@"+ipPort;
					}
					Object[] newArguments = new Object[message.getArguments().length];
					for(int i=0; i<message.getArguments().length;i++){
						if(i!=1){
							newArguments[i] = message.getArguments()[i];
						}else{
							newArguments[i] = sourceValue;
						}
					}
					sourceMessage = new OSCMessage(message.getAddress(), newArguments);
				}
			}
			if(message.getArguments()[0].equals("set")){
				if(!individualizeIdRanges){
					setMessages.add(message);
				}else{
					//index 1 is session id
					Integer aliveId = (Integer)message.getArguments()[1];
					Object[] newArguments = new Object[message.getArguments().length];
					for(int i=0; i<message.getArguments().length;i++){
						if(i!=1){
							newArguments[i] = message.getArguments()[i];
						}else{
							newArguments[i] = getIndividualizedId(aliveId, clientId);
						}
					}
					setMessages.add(new OSCMessage(message.getAddress(), newArguments));
				}
			}
		}
		
		aliveMessage = new OSCMessage(messageType);
		aliveMessage.addArgument("alive");
		for(Integer aliveId:allAliveIds(clientPortAliveIds,clientId, individualizeIdRanges)){
			aliveMessage.addArgument(aliveId);
		}
		
		fseqMessage = new OSCMessage(messageType);
		fseqMessage.addArgument("fseq");
		fseqMessage.addArgument(new Integer(fseq));
		
		//add source manually if there is none
		if(sourceMessage == null && manageSrcMsg){
			sourceMessage = new OSCMessage(messageType);
			sourceMessage.addArgument("source");
			sourceMessage.addArgument("generic@"+ipPort);
		}
		if(sourceMessage != null){
			newBundle.addPacket(sourceMessage);
		}
		newBundle.addPacket(aliveMessage);
		for(OSCMessage message:setMessages){
			newBundle.addPacket(message);
		}
		newBundle.addPacket(fseqMessage);
		
		fseq = fseq+1;
		return newBundle;
	}
	public int getIncrementedFseq(){
		return fseq++;
	}
	public void addListenerBeforeMultiplexing(OSCListener listener){
		listenersBeforeMultiplexing.add(listener);
	}
	public void addListenerAfterMultiplexing(OSCListener listener){
		listenersAfterMultiplexing.add(listener);
	}
	
	public boolean isMultiplexerEnabled() {
		return multiplexerEnabled;
	}

	public void setMultiplexerEnabled(boolean multiplexerEnabled) {
		this.multiplexerEnabled = multiplexerEnabled;
	}

	public boolean isIndividualizeIdRanges() {
		return individualizeIdRanges;
	}

	public void setIndividualizeIdRanges(boolean individualize) {
		this.individualizeIdRanges = individualize;
	}
	public boolean isManageSrcMsg() {
		return manageSrcMsg;
	}

	public void setManageSrcMsg(boolean manageSrcMsg) {
		this.manageSrcMsg = manageSrcMsg;
	}
}
