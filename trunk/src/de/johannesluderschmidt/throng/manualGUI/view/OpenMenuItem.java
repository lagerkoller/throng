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
package de.johannesluderschmidt.throng.manualGUI.view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

/**
 * 
 */

/**
 * @author johannes
 *
 */
public class OpenMenuItem extends JMenuItem implements ActionListener{
	ThrongManualGUI app;
	
	public OpenMenuItem(ThrongManualGUI app){
		super("Open...");
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		this.app = app;
	}
	public void actionPerformed(ActionEvent arg0) {
		
//		JFileChooser chooser = new JFileChooser(Constants.START_PATH);
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
		  chooser.addChoosableFileFilter(new FileFilter() {
		    public boolean accept(File f) {
		      if (f.isDirectory()) return true;
		      return f.getName().toLowerCase().endsWith(".xml");
		    }
		    public String getDescription () { return "XMLs"; }  
		  });
		  chooser.setMultiSelectionEnabled(false);
		  if (chooser.showOpenDialog(this) == 
		                                JFileChooser.APPROVE_OPTION){
			  File selectedFile = chooser.getSelectedFile();
			  String filePath = selectedFile.toString(); 
			  app.loadFile(filePath);
		  }
	}
}
