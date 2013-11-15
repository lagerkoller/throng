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
package de.johannesluderschmidt.throng.applications;
import java.awt.EventQueue;

import com.muchsoft.util.Sys;

import de.johannesluderschmidt.simpleDebug.Debug;
import de.johannesluderschmidt.throng.oscRecorderPlayer.ui.ThrongOSCDeckGUI;
import de.johannesluderschmidt.throng.util.ArgsParser;


public class ThrongOSCDeckApplication {

	private static ArgsParser parser;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(Sys.isMacOSX()){
			System.setProperty("apple.awt.graphics.EnableQ2DX", "true");
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "OSCRecorderPlayer");
		}else{
			
		}
		
		parser = new ArgsParser(args);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ThrongOSCDeckGUI oscDeck = new ThrongOSCDeckGUI(parser.getInboundPort(), parser.getOutboundIp(), parser.getOutboundPort());
					if(parser.isAutoStart()){
						oscDeck.startProxy();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		try{
			Debug.enableDebug(parser.isDebug());
		}catch(ArrayIndexOutOfBoundsException e){}
	
		if(parser.isShowWarning()){
			System.out.println("Usage: -autoStart true|false -inboundPort port -outboundIp ip -outboundPort port -debug true|false");
		}
	}

}
