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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.illposed.osc.OSCBundle;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPacket;

import de.johannesluderschmidt.simpleDebug.Debug;
import de.johannesluderschmidt.throng.oscRecorderPlayer.recorder.OSCRecordedPacket;

public class OSCSaver {
	public void save(File outputFile, ArrayList<OSCRecordedPacket> recordedPackets){
		try {  
			BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));  
			writeSaveString(out, recordedPackets);  
			out.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeSaveString(BufferedWriter out, ArrayList<OSCRecordedPacket> recordedPackets) throws IOException{
		long start = System.currentTimeMillis();
		for(OSCRecordedPacket recordedPacket:recordedPackets){
			out.write("\n");
			out.write(recordedPacket.getExecutionTime() + "\n");
			out.write(recordedPacket.getOscBundle().getIpAddress().getHostAddress() + "\n");
			out.write(recordedPacket.getOscBundle().getPortOut() + "\n");
			writeOSCBundleSaveString(out, recordedPacket.getOscBundle());
		}
		Debug.writeActivity("Built save string in "+ (System.currentTimeMillis() - start) + "ms", this);
	}
	
	public void writeOSCBundleSaveString(BufferedWriter out, OSCBundle bundle) throws IOException{
		int i = 1;
		for(OSCPacket packet:bundle.getPackets()){
			OSCMessage message = (OSCMessage) packet;
			out.write(message.getAddress());
			for(Object o:message.getArguments()){
				if(o instanceof String){
					out.write(" s" + o);
				}else if(o instanceof Integer){
					out.write(" i" + o);
				}else if(o instanceof Float){
					out.write(" f" + o);
				}else if(o == null){
					out.write(" n" + o);
				}else if(o instanceof Date){
					Date date = (Date) o;
					out.write(" t" + date.getTime());
				}
			}
			out.write("\n");
			i = i+1;
		}
	}
	
	
}
