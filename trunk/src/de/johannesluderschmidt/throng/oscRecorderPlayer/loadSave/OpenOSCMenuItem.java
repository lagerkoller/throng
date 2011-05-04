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

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import de.johannesluderschmidt.simpleDebug.Debug;
import de.johannesluderschmidt.throng.oscRecorderPlayer.recorder.OSCRecordedPacket;

/**
 * 
 */

/**
 * @author johannes
 *
 */
public class OpenOSCMenuItem extends JMenuItem implements ActionListener{
	IOSCLoaderSaverInterface loaderSaverInterface;
	
	public OpenOSCMenuItem(IOSCLoaderSaverInterface loaderSaverInterface){
		super("Open...");
		addActionListener(this);
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		this.loaderSaverInterface = loaderSaverInterface;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser chooser = new JFileChooser();
		  chooser.addChoosableFileFilter(new FileFilter() {
		    public boolean accept(File f) {
		      if (f.isDirectory()) return true;
		      return f.getName().toLowerCase().endsWith(".osc");
		    }
		    public String getDescription () { return "osc Files"; }  
		  });
		  chooser.setMultiSelectionEnabled(false);
		  if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
			  File selectedFile = chooser.getSelectedFile();
			  String fileName = selectedFile.toString(); 

			  if(fileName.toLowerCase().endsWith(".osc")){
				  Debug.writeActivity("Load file: "+fileName, this);
				  ArrayList<OSCRecordedPacket> loadedPackets = (new OSCLoader()).load(selectedFile); 
				  if(loadedPackets != null){
					  loaderSaverInterface.setRecordedPackets(loadedPackets);
				  }
			  }else{
				  Debug.writeActivity("I cannot load "+fileName+". I can only load files of type '.osc'.", this);  
			  }
		  }
	}
	
	
}
