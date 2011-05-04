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

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class SaveOSCMenuItem extends JMenuItem implements ActionListener{
	IOSCLoaderSaverInterface loaderSaverInterface;
	
	public SaveOSCMenuItem(IOSCLoaderSaverInterface loaderSaverInterface){
		super("Save...");
		this.loaderSaverInterface = loaderSaverInterface;
		addActionListener(this);
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	}
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
		
		fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
		    public boolean accept(File f) {
		      if (f.isDirectory()) return true;
		      return f.getName().toLowerCase().endsWith(".osc");
		    }
		    public String getDescription () { return "osc Files"; }  
		  });
		
		int returnVal = fileChooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION){
			File outputFile = fileChooser.getSelectedFile();
			String fileName = outputFile.toString(); 

			//file extension is not .osc -> make it .osc
			if(!fileName.toLowerCase().endsWith(".osc")){
				fileName = fileName+".osc";
				outputFile = new File(fileName);
			}
			
			new OSCSaver().save(outputFile, loaderSaverInterface.getRecordedPackets());
		}
	}
}
