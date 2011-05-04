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
package de.johannesluderschmidt.throng.sharedGUIResources.mac;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.EventObject;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



import com.muchsoft.util.mac.Java14Handler;

import de.johannesluderschmidt.throng.util.Constants;


public class ThrongMacAboutHandler extends JOptionPane implements Java14Handler {
	private JFrame owner;
	private JLabel aboutTextPane;
	private JLabel lbljohannesLuderschmidt;
	private JPanel aboutPanel;
	
	public ThrongMacAboutHandler(JFrame owner) {
//		super(owner, true);
//		setResizable(false);
		setBounds(100, 100, 260, 165);
//		getContentPane().setLayout(null);
		
		aboutTextPane = new JLabel();
		aboutTextPane.setBounds(6, 6, 255, 100);
		aboutTextPane.setText("<html><center><strong>Throng</strong> "+Constants.THRONG_VERSION+"</center><br/>"+Constants.FULL_NAME+"<br/><br/>For help look in<html>");
//		getContentPane().add(aboutTextPane);
		
		lbljohannesLuderschmidt = new JLabel("<html><a href=''>"+Constants.HOMEPAGE_TEXT+"</a></html>");
		lbljohannesLuderschmidt.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lbljohannesLuderschmidt.addMouseListener(new MouseListener(){
		      public void mouseClicked(MouseEvent e) {
		        java.net.URI uri = null;
				try {
					uri = new java.net.URI(Constants.HOMEPAGE_LINK);
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        if(java.awt.Desktop.isDesktopSupported()){
		        	try {
		        		if(uri != null){
		        			java.awt.Desktop.getDesktop().browse( uri );
	        			}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
		      }
		      //other MouseListener methods 

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
			}

		    });
		lbljohannesLuderschmidt.setBounds(6, 93, 248, 40);
		
		aboutPanel = new JPanel();
		aboutPanel.add(aboutTextPane);
		aboutPanel.add(lbljohannesLuderschmidt);		
//		getContentPane().add(lbljohannesLuderschmidt);
		
	}

	public void handleAbout(EventObject arg0) {
//		this.setVisible(true);
		showMessageDialog(owner, aboutPanel,"",INFORMATION_MESSAGE);
		
	}

	public void handleOpenApplication(EventObject arg0) {
	}

	public void handleOpenFile(EventObject arg0, String arg1) {
	}

	public void handlePrefs(EventObject arg0) {
	}

	public void handlePrintFile(EventObject arg0, String arg1) {
	}

	public void handleQuit(EventObject arg0) {
	}

	public void handleReOpenApplication(EventObject arg0) {
	}
}
