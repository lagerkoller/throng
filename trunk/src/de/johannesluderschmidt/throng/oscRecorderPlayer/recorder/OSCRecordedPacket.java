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

import com.illposed.osc.OSCBundle;

public class OSCRecordedPacket {
	private OSCBundle oscBundle;
	private long executionTime;
	
	public OSCRecordedPacket(OSCBundle oscBundle, long executionTime){
		this.executionTime = executionTime; 
		setOscBundle(oscBundle);
	}
	
	public OSCBundle getOscBundle() {
		return oscBundle;
	}
	
	public void setOscBundle(OSCBundle oscBundle) {
		this.oscBundle = oscBundle;
//		executionTime = timestamp.getTime() - startTime.getTime();
	}
	
	public long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	@Override
	public String toString() {
		String recordString = "";
		recordString += recordString + "\n";
		recordString = recordString + executionTime + "\n";
		recordString = recordString + oscBundle.getIpAddress().getHostAddress() + "\n";
		recordString = recordString + oscBundle.getPortOut() + "\n";
		recordString = recordString + oscBundle.toString(); 
		return recordString;
	}
	
	
}
