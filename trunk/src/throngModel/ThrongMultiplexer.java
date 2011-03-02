package throngModel;

import java.util.HashMap;
import java.util.Vector;

import util.Constants;
import flosc.Debug;
import flosc.OscMessage;
import flosc.OscPacket;
import flosc.OscServer;

public class ThrongMultiplexer {
	private static ThrongMultiplexer inst;
	
	private OscServer oscServer;
	private HashMap<String, Vector<Integer>> touchAliveIdsToClientAndPort;
	private HashMap<String, Vector<Integer>> blobAliveIdsToClientAndPort;
	private HashMap<String, Vector<Integer>> fiducialAliveIdsToClientAndPort;
	private HashMap<String, Integer> idToClientAndPort;
	private int clientId;
	private int fseq;
	
	private ThrongMultiplexer(){
		touchAliveIdsToClientAndPort = new HashMap<String, Vector<Integer>>();
		blobAliveIdsToClientAndPort = new HashMap<String, Vector<Integer>>();
		fiducialAliveIdsToClientAndPort = new HashMap<String, Vector<Integer>>();
		idToClientAndPort = new HashMap<String, Integer>();
		fseq = 0;
		clientId = 0;
	}
	
	public static ThrongMultiplexer getInstance(){
		if(inst == null){
			inst = new ThrongMultiplexer();
		}
		return inst;
	}
	
	public OscPacket multiplexPacket(OscPacket packet, boolean manageAliveIds, boolean individualize, boolean manageSrcMsg) throws Exception{
		String key = ""+packet.getAddress()+packet.getPort();
		OscPacket newPacket=null;
		
		if(!idToClientAndPort.containsKey(key)){
			idToClientAndPort.put(key, clientId);
			clientId = clientId+1;
		}
		
		if(manageAliveIds){
			String profileName = getName(packet); 
			if(profileName.equals(Constants.TWOD_CUR)){
				touchAliveIdsToClientAndPort.put(key, getAliveIds(packet));
				newPacket = packNewPacketTouch(packet, individualize, manageSrcMsg);
			}else if(profileName.equals(Constants.TWOD_OBJ)){
				fiducialAliveIdsToClientAndPort.put(key, getAliveIds(packet));
				newPacket = packNewPacketFiducial(packet, individualize, manageSrcMsg);
			}else if(profileName.equals(Constants.TWOD_BLB)){
				blobAliveIdsToClientAndPort.put(key, getAliveIds(packet));
				newPacket = packNewPacketBlob(packet, individualize, manageSrcMsg);
			}
			else{
				throw new Exception("I cannot handle Tuio packets of this profile name: "+profileName);
			}
		}else{
			newPacket = packet;
		}
		
		return newPacket;
	}
	
	public String getName(OscPacket packet){
		String name = "";
		for(OscMessage message : packet.getMessages()){
			if(message.getName().equals(Constants.TWOD_CUR)){
				name = Constants.TWOD_CUR;
				break;
			}
			if(message.getName().equals(Constants.TWOD_OBJ)){
				name = Constants.TWOD_OBJ;
				break;
			}
			if(message.getName().equals(Constants.TWOD_BLB)){
				name = Constants.TWOD_BLB;
				break;
			}
		}
		return name;
	}
	
	private Vector<Integer> getAliveIds(OscPacket packet){
		Vector<Integer> aliveIds = new Vector<Integer>();
		for(OscMessage message : packet.getMessages()){
			if(message.getArguments().get(0).equals("alive")){
				for(int i = 1; i < message.getArguments().size(); i++){
					aliveIds.add((Integer)message.getArguments().get(i));
				}
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
	
	public OscPacket packNewPacketTouch(OscPacket receivedPacket, boolean individualize, boolean manageSrcMsg){
		return packNewPacket(receivedPacket, touchAliveIdsToClientAndPort, Constants.TWOD_CUR, individualize, manageSrcMsg);
	}
	public OscPacket packNewPacketBlob(OscPacket receivedPacket, boolean individualize, boolean manageSrcMsg){
		return packNewPacket(receivedPacket, blobAliveIdsToClientAndPort, Constants.TWOD_BLB, individualize, manageSrcMsg);
	}
	public OscPacket packNewPacketFiducial(OscPacket receivedPacket, boolean individualize, boolean manageSrcMsg){
		return packNewPacket(receivedPacket, fiducialAliveIdsToClientAndPort, Constants.TWOD_OBJ, individualize, manageSrcMsg);
	}
	
	public OscPacket packNewPacket(OscPacket receivedPacket,HashMap<String, Vector<Integer>> clientPortAliveIds, String messageType, boolean individualize, boolean manageSrcMsg){
		int clientId = idToClientAndPort.get(""+receivedPacket.getAddress()+receivedPacket.getPort());
		String ipPort = ""+receivedPacket.getAddress();
		ipPort = ipPort.substring(1)+":"+receivedPacket.getPort();
		
		OscPacket newPacket = new OscPacket();
		newPacket.setAddress(receivedPacket.getAddress());
		newPacket.setPort(receivedPacket.getPort());
		
		OscMessage sourceMessage = null;
		OscMessage aliveMessage = null;
		OscMessage fseqMessage = null;
		Vector<OscMessage> setMessages = new Vector<OscMessage>();
		
		for(OscMessage message : receivedPacket.getMessages()){
			if(message.getArguments().get(0).equals("source")){
				sourceMessage = message;
				if(manageSrcMsg){
					String sourceValue = (String)message.getArguments().elementAt(1);
					int adSignPos = sourceValue.indexOf("@");
					if(adSignPos > -1){
						sourceValue = sourceValue.substring(0,adSignPos+1)+ipPort;
					}else{
						sourceValue = sourceValue+"@"+ipPort;
					}
					message.getArguments().set(1, sourceValue);
				}
			}
			if(message.getArguments().get(0).equals("set")){
				if(!individualize){
					setMessages.add(message);
				}else{
					//1 is session id
					Integer aliveId = (Integer)message.getArguments().elementAt(1);
//					message.getArguments().set(1,aliveId+clientId*Constants.TUIO_SESSION_ID_RANGE);
					message.getArguments().set(1,getIndividualizedId(aliveId, clientId));
					setMessages.add(message);
				}
			}
		}
		
		aliveMessage = new OscMessage(messageType);
		aliveMessage.addArg('s',"alive");
		for(Integer aliveId:allAliveIds(clientPortAliveIds,clientId, individualize)){
			aliveMessage.addArg('i',aliveId);
		}
		
		fseqMessage = new OscMessage(messageType);
		fseqMessage.addArg('s',"fseq");
		fseqMessage.addArg('i',new Integer(fseq));
		
		//add source manually if there is none
		if(sourceMessage == null && manageSrcMsg){
			sourceMessage = new OscMessage(messageType);
			sourceMessage.addArg('s', "source");
			sourceMessage.addArg('s', "generic@"+ipPort);
		}
		if(sourceMessage != null){
			newPacket.addMessage(sourceMessage);
		}
		newPacket.addMessage(aliveMessage);
		for(OscMessage message:setMessages){
			newPacket.addMessage(message);
		}
		newPacket.addMessage(fseqMessage);
		
		fseq = fseq+1;
		return newPacket;
	}
	
	public OscServer getOscServer() {
		return oscServer;
	}

	public void setOscServer(OscServer oscServer) {
		this.oscServer = oscServer;
	}
}
