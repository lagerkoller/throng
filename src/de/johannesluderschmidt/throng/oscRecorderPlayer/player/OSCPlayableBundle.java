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
package de.johannesluderschmidt.throng.oscRecorderPlayer.player;

import java.util.Date;

import com.illposed.osc.OSCBundle;
import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPacket;

public class OSCPlayableBundle implements Runnable {
	private OSCBundle bundle;
	private OSCListener listener;
	
	public OSCPlayableBundle(OSCBundle bundle, OSCListener listener){
		this.bundle = bundle;
		this.listener = listener;
	}
	
	public void run() {
		listener.acceptBundle(new Date(), bundle);
		for(OSCPacket packet:bundle.getPackets()){
			OSCMessage message = (OSCMessage)packet;
			listener.acceptMessage(new Date(), message);
		}
	}
	
	

}
