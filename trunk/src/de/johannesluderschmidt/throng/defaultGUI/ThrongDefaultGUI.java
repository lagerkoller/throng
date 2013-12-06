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
package de.johannesluderschmidt.throng.defaultGUI;


import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;



import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.muchsoft.util.Sys;
import com.muchsoft.util.mac.Java14Adapter;

import de.johannesluderschmidt.simpleDebug.Debug;
import de.johannesluderschmidt.throng.proxy.ThrongMultiplexer;
import de.johannesluderschmidt.throng.proxy.ThrongProxy;
import de.johannesluderschmidt.throng.proxy.Tuio1_1Terminator;
import de.johannesluderschmidt.throng.sharedGUIResources.mac.ThrongMacAboutHandler;
import de.johannesluderschmidt.throng.sharedGUIResources.mac.ThrongMacApplicationHandler;
import de.johannesluderschmidt.throng.util.Constants;
import de.johannesluderschmidt.tuio3DExt.Tuio3DExtTerminator;

public class ThrongDefaultGUI extends JFrame{
	private JTextField outgoingPortTextField;
	private JTextField incomingPortTextField;
	private JButton startStopButton;
	private JCheckBox chckbxAliveMessages;
	private JCheckBox chckbxIndividualizeIds; 
	private JCheckBox chckbxManageSourceMsg;
	
	private boolean proxyStarted = false;
	
	public ThrongDefaultGUI(int inboundPort, int outboundPort) throws HeadlessException {
		super();
		
		setCorrectLookAndFeel();
		
		setResizable(false);
		
		
		setTitle("Throng "+Constants.THRONG_VERSION);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("118px"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("150px"),},
			new RowSpec[] {
				RowSpec.decode("28px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel myIpAddressLabel = new JLabel("My IP Address:");
		getContentPane().add(myIpAddressLabel, "1, 1, right, center");
		
		JLabel ipText = new JLabel("");
		try {
			ipText.setText(InetAddress.getLocalHost().getHostAddress());
	    }catch (UnknownHostException e) {
	    	e.printStackTrace();
	    }
		getContentPane().add(ipText, "3, 1, left, center");
		
		JLabel incomingPortLabel = new JLabel("Inbound port:");
		getContentPane().add(incomingPortLabel, "1, 3, right, center");
		
		incomingPortTextField = new JTextField();
		incomingPortTextField.setText(""+inboundPort);
		incomingPortTextField.setColumns(10);
		getContentPane().add(incomingPortTextField, "3, 3, left, center");
		
		JLabel outgoingPortLabel = new JLabel("Outbound port:");
		getContentPane().add(outgoingPortLabel, "1, 5, right, center");
		
		outgoingPortTextField = new JTextField();
		outgoingPortTextField.setText(""+outboundPort);
		outgoingPortTextField.setColumns(10);
		getContentPane().add(outgoingPortTextField, "3, 5, left, center");
		
		startStopButton = new JButton("Start Throng");
		startStopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startOrStopProxy();
			}
		});
		getContentPane().add(startStopButton, "3, 7, right, top");

		//sets initial focus on startStopButton
		this.addWindowFocusListener(new WindowAdapter() {
		    public void windowGainedFocus(WindowEvent e) {
		    	startStopButton.requestFocusInWindow();
		    }
		});
		
		chckbxAliveMessages = new JCheckBox("Multiplex Alive Ids");
		chckbxAliveMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxAliveMessages.isSelected()){
					chckbxIndividualizeIds.setEnabled(true);
					chckbxManageSourceMsg.setEnabled(true);
				}else{
					chckbxIndividualizeIds.setEnabled(false);
					chckbxManageSourceMsg.setEnabled(false);
				}
				ThrongMultiplexer.getInstance().setMultiplexerEnabled(chckbxAliveMessages.isSelected());
				ThrongProxy.getInstance().resetTuioClientMessages();
			}
		});
		chckbxAliveMessages.setSelected(true);
		chckbxAliveMessages.setToolTipText("<html>If checked, Throng will multiplex Tuio alive ids in a way that all ids "+
				"of alive touches and fiducials will be included in each alive message.</html>");
		getContentPane().add(chckbxAliveMessages, "3, 9");
		
		chckbxIndividualizeIds = new JCheckBox("Use Alive Id Range");
		chckbxIndividualizeIds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ThrongMultiplexer.getInstance().setIndividualizeIdRanges(chckbxIndividualizeIds.isSelected());
				ThrongProxy.getInstance().resetTuioClientMessages();
			}
		});
		chckbxIndividualizeIds.setSelected(true);
		chckbxIndividualizeIds.setToolTipText("If checked, Throng will put alive ids of each client into a distinct range of ids in order to prevent overlapping of alive ids of different Tuio producers.");
		getContentPane().add(chckbxIndividualizeIds, "3, 11");
		
		chckbxManageSourceMsg = new JCheckBox("Manage Src. Msg.");
		chckbxManageSourceMsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ThrongMultiplexer.getInstance().setManageSrcMsg(chckbxManageSourceMsg.isSelected());
				ThrongProxy.getInstance().resetTuioClientMessages();
			}
		});
		chckbxManageSourceMsg.setToolTipText("If \"Manage Src. Msg.\" is selected, the source Ip address and the source port of the Tuio packet will be added after the @ to allow for discerning in the Tuio client application. If no source message is included, it will be created.");
		chckbxManageSourceMsg.setSelected(true);
		getContentPane().add(chckbxManageSourceMsg, "3, 13");
		
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void startProxy(){
		if(!proxyStarted){
			startOrStopProxy();
		}
	}
	
	public void stopProxy(){
		if(proxyStarted){
			startOrStopProxy();
		}
	}
	
	private void startOrStopProxy(){
		if(!proxyStarted){
			try{
				int[] oscInputPorts = new int[1];
				oscInputPorts[0] = Integer.parseInt(incomingPortTextField.getText());
				ThrongProxy.getInstance().setInboundPorts(oscInputPorts);
				ThrongProxy.getInstance().setOutboundConnection(InetAddress.getByName(Constants.DEFAULT_IP), Integer.parseInt(outgoingPortTextField.getText()));
				ThrongMultiplexer.getInstance().setIndividualizeIdRanges(chckbxIndividualizeIds.isSelected());
				ThrongMultiplexer.getInstance().setMultiplexerEnabled(chckbxAliveMessages.isSelected());
				ThrongMultiplexer.getInstance().setManageSrcMsg(chckbxManageSourceMsg.isSelected());
				ThrongProxy.getInstance().startProxy();
				ThrongProxy.getInstance().addTerminator(new Tuio1_1Terminator());
				ThrongProxy.getInstance().addTerminator(new Tuio3DExtTerminator());
				startStopButton.setText("Stop Throng");
				proxyStarted = true;
			}catch(UnknownHostException e){
				Debug.writeException("Could not resolve localhost: "+e.getMessage(),this, e);
			}catch(Exception ex){
				Debug.writeException("Error when trying to start TuioProxy: "+ex.getMessage(),this, ex);
			}
		}else{
			try{
				ThrongProxy.getInstance().stopProxy();
				startStopButton.setText("Start Throng");
				proxyStarted = false;
			}catch(Exception ex){
				Debug.writeException("Error when stopping gateway from GUI: "+ex.getMessage(),this, ex);
			}
		}
	}
	/**
	    * Mac Os X and Windows systems get different menubars to use systemwide
	    * preferences like mnemonics and labels for default functions like open 
	    * 
	    * @return
	    */
	   private void setCorrectLookAndFeel(){
		   if(Sys.isMacOSX()){
			   Java14Adapter.registerJava14Handler(new ThrongMacAboutHandler(this));
			   Java14Adapter.registerJava14Handler(new ThrongMacApplicationHandler());
			   setBounds(100, 100, 290, 275);
		   }else{
			   setBounds(100, 100, 290, 290);
		   }
		}
	   
	   public void minimize(){
			this.setState(Frame.ICONIFIED);
		}
		
		public void maximize(){
			this.setState(Frame.NORMAL);
		}
	   
}
