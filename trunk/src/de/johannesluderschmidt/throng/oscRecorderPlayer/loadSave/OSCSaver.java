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

import de.johannesluderschmidt.throng.oscRecorderPlayer.recorder.OSCRecordedPacket;

public class OSCSaver {
	public void save(File outputFile, ArrayList<OSCRecordedPacket> recordedPackets){
		try {  
			BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));  
			out.write(getSaveString(recordedPackets));  
			out.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getSaveString(ArrayList<OSCRecordedPacket> recordedPackets){
		String outputString = "";
		for(OSCRecordedPacket recordedPacket:recordedPackets){
			outputString = outputString + "\n";
			outputString = outputString + recordedPacket.getExecutionTime() + "\n";
			outputString = outputString + recordedPacket.getOscBundle().getIpAddress().getHostAddress() + "\n";
			outputString = outputString + recordedPacket.getOscBundle().getPortOut() + "\n";
			outputString = outputString + getOSCBundleSaveString(recordedPacket.getOscBundle());
		}
		return outputString; 
	}
	
	public String getOSCBundleSaveString(OSCBundle bundle){
		String saveString = "";
		int i = 1;
		for(OSCPacket packet:bundle.getPackets()){
			OSCMessage message = (OSCMessage) packet;
			saveString = saveString + message.getAddress();
			for(Object o:message.getArguments()){
				if(o instanceof String){
					saveString = saveString + " s" + o;
				}else if(o instanceof Integer){
					saveString = saveString + " i" + o;
				}else if(o instanceof Float){
					saveString = saveString + " f" + o;
				}else if(o == null){
					saveString = saveString + " n" + o;
				}else if(o instanceof Date){
					Date date = (Date) o;
					saveString = saveString + " t" + date.getTime();
				}
			}
			saveString = saveString + "\n";
			i = i+1;
		}
		
		return saveString;
	}
	
	
}
