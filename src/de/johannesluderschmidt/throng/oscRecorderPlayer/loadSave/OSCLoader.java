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
package de.johannesluderschmidt.throng.oscRecorderPlayer.loadSave;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import com.illposed.osc.OSCBundle;
import com.illposed.osc.OSCMessage;

import de.johannesluderschmidt.throng.oscRecorderPlayer.recorder.OSCRecordedPacket;

public class OSCLoader {
	
	public ArrayList<OSCRecordedPacket> load(File inputFile){
		ArrayList<OSCRecordedPacket> recordedPackets = null;
		try{
			recordedPackets = new ArrayList<OSCRecordedPacket>();
			BufferedReader in = new BufferedReader(new FileReader(inputFile));
			
			while(in.ready()){ 
				
				//read all empty lines before/between different bundles until there starts a new one
				boolean bundleStarted = false;
				String line = null;
				while(!bundleStarted){
					line = in.readLine();
					if(line.length()>0){
						bundleStarted = true;
					}
				}
				
				//read execution time in first line of bundle
				long executionTime = Long.parseLong(line);
				InetAddress ipAddress = InetAddress.getByName(in.readLine());
				int portOut = Integer.parseInt(in.readLine());
				
				//read TUIO message lines into OSCBundle
				OSCBundle bundle = readOSCBundle(in);
				bundle.setIpAddress(ipAddress);
				bundle.setPortOut(portOut);
				recordedPackets.add(new OSCRecordedPacket(bundle, executionTime));
			}
		}catch(Exception e){
			e.printStackTrace();
			recordedPackets = null;
		}
		if(recordedPackets != null && recordedPackets.size() == 0){
			recordedPackets = null;
		}
		return recordedPackets;
	}
	
	private OSCMessage readMessage(String line) throws IOException{
		StringTokenizer stok = new StringTokenizer(line);
		String address = stok.nextToken();
		Object [] arguments = new Object[stok.countTokens()];
		
		int i = 0;
		while(stok.hasMoreTokens()){
			String nextToken = stok.nextToken();
			if(nextToken.substring(0, 1).equals("f")){
				arguments[i] = new Float(nextToken.substring(1,nextToken.length()));
			}else if(nextToken.substring(0, 1).equals("i")){
				arguments[i] = new Integer(nextToken.substring(1,nextToken.length()));
			}else if(nextToken.substring(0, 1).equals("s")){
				arguments[i] = nextToken.substring(1,nextToken.length());
			}else if(nextToken.substring(0, 1).equals("n")){
				arguments[i] = null;
			}else if(nextToken.substring(0, 1).equals("t")){
				arguments[i] = new Date(new Long(nextToken.substring(1,nextToken.length())));
			}
			i = i+1;
		}
		
		return new OSCMessage(address, arguments);
	}
	
	private OSCBundle readOSCBundle(BufferedReader in) throws IOException{
		boolean bundleEnded = false;
		ArrayList<OSCMessage> messages = new ArrayList<OSCMessage>();
		while(!bundleEnded){
			if(in.ready()){
				String line = in.readLine();
				if(line.length() != 0){
					messages.add(readMessage(line));
				}else{
					bundleEnded = true;
				}
			}else{
				bundleEnded = true;
			}
		}
		OSCMessage[] type = new OSCMessage[messages.size()];
		return new OSCBundle(messages.toArray(type));
	}
}
