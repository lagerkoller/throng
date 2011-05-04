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
package de.johannesluderschmidt.throng.oscRecorderPlayer.ui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.NumberFormat;
import java.util.Date;

import javax.swing.JComponent;

public class TimeLabel extends JComponent implements Runnable{
	private String labelString;
	private Thread updateTimeThread;
	private ITimeLabelCallback callback;
	
	private long startTime;
	private long duration;
	private boolean isRunning;
	
	public TimeLabel(){
		this.setPreferredSize(new Dimension(300, 25));
		reset();
	}
	
	public TimeLabel(ITimeLabelCallback callback){
		this();
		this.callback = callback;
	}
	public void run() {
		try {
			while(isRunning){
				Date current = new Date();
				long currentTime = current.getTime();
				long elapsedTime = currentTime - startTime;
				labelString = calculateTimeString(elapsedTime);
				repaint();
				Thread.sleep(75);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	public void startCounting(){
		isRunning = true;
		updateTimeThread = new Thread(this);
		updateTimeThread.start();
	}
	
	public void stopCounting(){
		isRunning = false;
	}
	
	public void reset(){
		setStartTime((new Date()).getTime());
		setLabelString("00:00:00");
		duration = Long.MAX_VALUE;
	}
	
	public String calculateTimeString(long elapsedTime){
		if(elapsedTime > duration){
			elapsedTime = duration;
			stopCounting();
			if(callback != null){
				callback.countingStopped();
			}
		}
		long ms = elapsedTime%1000;
		ms = ms/10;
		long seconds = (elapsedTime/1000);
		long minutes = (seconds/60)%99;
		seconds = seconds%60;
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(2);
		nf.setMaximumIntegerDigits(2);
		
		return nf.format(minutes)+":"+nf.format(seconds)+":"+nf.format(ms);
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	
		g2.setColor(Color.black);
		g2.drawString(labelString, 0, g2.getFontMetrics().getHeight());
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
		setLabelString(calculateTimeString(duration));
	}

	public String getLabelString() {
		return labelString;
	}

	public void setLabelString(String labelString) {
		this.labelString = labelString;
		repaint();
	}

}
