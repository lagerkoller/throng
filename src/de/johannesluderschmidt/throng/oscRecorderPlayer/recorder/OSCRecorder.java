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
package de.johannesluderschmidt.throng.oscRecorderPlayer.recorder;

import java.util.ArrayList;
import java.util.Date;

import com.illposed.osc.OSCBundle;
import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;

public class OSCRecorder implements OSCListener{
	
	private ArrayList<OSCRecordedPacket> recordedPackets;
	private Date startTime;
	
	private long sizeInByte;
	private long duration;
	private boolean recording;
	
	public OSCRecorder(){
		recordedPackets = new ArrayList<OSCRecordedPacket>();
		sizeInByte = 0;
	}
	
	public void addPacket(OSCBundle bundle){
		recordedPackets.add(new OSCRecordedPacket(bundle, ((new Date()).getTime()) - startTime.getTime()));
		sizeInByte = sizeInByte + bundle.getByteArray().length;
	}
	public void reset(){
		recordedPackets.clear();
	}
	
	public boolean isRecording() {
		return recording;
	}

	public void setRecording(boolean recording) {
		this.recording = recording;
		if(recording){
			reset();
			startTime = new Date();
		}else{
			duration = (new Date()).getTime() - startTime.getTime();
		}
	}
	
	public long getSizeInByte(){
		return sizeInByte;
	}
	
	public ArrayList<OSCRecordedPacket> getRecordedPackets() {
		return recordedPackets;
	}

	public void setRecordedPackets(ArrayList<OSCRecordedPacket> recordedPackets) {
		this.recordedPackets = recordedPackets;
		duration = recordedPackets.get(recordedPackets.size()-1).getExecutionTime();
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getSaveString(){
		String outputString = "";
		for(OSCRecordedPacket recPack:recordedPackets){
			outputString = outputString + recPack.toString();
		}
		return outputString; 
	}

	public void acceptMessage(Date time, OSCMessage message) {
		//do nothing
	}

	public void acceptBundle(Date time, OSCBundle bundle) {
		if(recording){
			addPacket(bundle);
		}
	}
	

}
