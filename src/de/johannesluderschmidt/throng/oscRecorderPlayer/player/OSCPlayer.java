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

import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


import com.illposed.osc.OSCListener;

import de.johannesluderschmidt.throng.oscRecorderPlayer.recorder.OSCRecordedPacket;

public class OSCPlayer {
	
	public static final int POOL_SIZE = 3;
	
	private ArrayList<OSCRecordedPacket> recordedPackets;
	private OSCListener listener;
	private ScheduledThreadPoolExecutor executor;
	
	private long trackDuration;
	private boolean playing;
	
	public OSCPlayer(OSCListener listener){
		this.listener = listener;
	}
	
	public void play(){
		executor = new ScheduledThreadPoolExecutor(POOL_SIZE);
		for(OSCRecordedPacket bundle:recordedPackets){
			OSCPlayableBundle playableBundle = new OSCPlayableBundle(bundle.getOscBundle(), listener);
			executor.schedule(playableBundle, bundle.getExecutionTime(), TimeUnit.MILLISECONDS);
			trackDuration = bundle.getExecutionTime();
		}
		playing = true;
	}
	
	public long getTrackDuration() {
		return trackDuration;
	}

	public void stop(){
		executor.shutdownNow();
		playing = false;
	}
	public ArrayList<OSCRecordedPacket> getRecordedPackets() {
		return recordedPackets;
	}
	
	public void setRecordedPackets(ArrayList<OSCRecordedPacket> recordedPackets) {
		this.recordedPackets = recordedPackets;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		if(playing){
			play();
		}else{
			stop();
		}
	}
	
}
