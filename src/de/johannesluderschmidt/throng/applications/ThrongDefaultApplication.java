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
import de.johannesluderschmidt.throng.defaultGUI.ThrongDefaultGUI;


public class ThrongDefaultApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(Sys.isMacOSX()){
			System.setProperty("apple.awt.graphics.EnableQ2DX", "true");
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Throng");
		}else{
			
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ThrongDefaultGUI frame = new ThrongDefaultGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		int i=0;
		boolean correctParameterFound=false;
		for(String arg:args){
			if(arg.equals("-debug")){
				correctParameterFound = true;
				try{
					Debug.enableDebug(Boolean.parseBoolean(args[i+1]));
				}catch(ArrayIndexOutOfBoundsException e){}
			}
			i = i+1;
		}
		if(args.length>0 && !correctParameterFound){
			System.out.println("Usage: -debug true|false.");
		}
		

	}

}
