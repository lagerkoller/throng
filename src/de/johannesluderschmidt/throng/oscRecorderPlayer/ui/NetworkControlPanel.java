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
package de.johannesluderschmidt.throng.oscRecorderPlayer.ui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import de.johannesluderschmidt.simpleDebug.Debug;
import de.johannesluderschmidt.throng.oscRecorderPlayer.IOSCProxy;
import de.johannesluderschmidt.throng.util.Constants;

public class NetworkControlPanel extends JPanel {
	private final String START_LABEL = "Start Proxy";
	private final String STOP_LABEL = "Stop Proxy";
	
	private JTextField inboundPortTextField;
	private JTextField outboundIPTextField;
	private JTextField outboundPortTextField;
	private JLabel lblIpAddress;
	private JButton btnStartProxy;
	private IOSCProxy deck;
	
	private boolean proxyStarted;
	
	public NetworkControlPanel(IOSCProxy deck){
		this.deck = deck;
		
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("54dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("54dlu"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,}));
		
		JLabel lblNetworkOptions = new JLabel("Network Options");
		add(lblNetworkOptions, "1, 1");
		
		
		JLabel lblMyIpAddress = new JLabel("Inbound IP");
		add(lblMyIpAddress, "1, 3, right, default");
		
		lblIpAddress = new JLabel("IPAddress");
		add(lblIpAddress, "3, 3");
		
		try {
			lblIpAddress.setText(InetAddress.getLocalHost().getHostAddress());
	    }catch (UnknownHostException e) {
	    	e.printStackTrace();
	    }
		
		JLabel lblInboundPort = new JLabel("Inbound Port");
		add(lblInboundPort, "1, 5, right, default");
		
		inboundPortTextField = new JTextField();
		inboundPortTextField.setText(""+Constants.DEFAULT_INPUT_PORT);
		add(inboundPortTextField, "3, 5, fill, default");
		inboundPortTextField.setColumns(10);
		
		JLabel lblOutboundIP = new JLabel("Outbound IP");
		add(lblOutboundIP, "1, 7, right, default");
		
		outboundIPTextField = new JTextField();
		outboundIPTextField.setText(Constants.DEFAULT_IP);
		add(outboundIPTextField, "3, 7, fill, default");
		outboundIPTextField.setColumns(10);
		
		JLabel lblOutboundPort = new JLabel("Outbound Port");
		add(lblOutboundPort, "1, 9, right, default");
		
		outboundPortTextField = new JTextField();
		outboundPortTextField.setText(""+Constants.DEFAULT_OUTPUT_PORT);
		add(outboundPortTextField, "3, 9, fill, default");
		outboundPortTextField.setColumns(10);
		
		btnStartProxy = new JButton(START_LABEL);
		btnStartProxy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startStopButtonAction();
			}
		});
		add(btnStartProxy, "3, 11");
		
		//sets initial focus on btnStartProxy
		this.addFocusListener(new FocusAdapter() {
		    public void focusGained(FocusEvent e) {
		    	btnStartProxy.requestFocusInWindow();
		    }
		});
	}
	private void startStopButtonAction(){
		if(btnStartProxy.getText().equals(START_LABEL)){
			startProxy();
		}else{
			stopProxy();
		}
		
	}
	
	private void startProxy(){
		try{
			int inboundPort = Integer.parseInt(inboundPortTextField.getText());
			InetAddress ipAddress = InetAddress.getByName(outboundIPTextField.getText());
			int outboundPort = Integer.parseInt(outboundPortTextField.getText());
			deck.startOrStopProxy(true, inboundPort, ipAddress, outboundPort);
		}catch (UnknownHostException e) {
			Debug.writeException("Could not resolve the IP address \""+outboundIPTextField.getText()+"\" from the text field", this, e);
		}catch(NumberFormatException e){
			Debug.writeException("There is no number in the port text fields: ", this, e);
		}
	}
	
	private void stopProxy(){
		deck.startOrStopProxy(false, -1, null, -1);
	}
	public void setFocusOnStartButton(){
		btnStartProxy.requestFocusInWindow();
	}
	
	public boolean isProxyStarted() {
		return proxyStarted;
	}
	public void setProxyStarted(boolean proxyStarted) {
		this.proxyStarted = proxyStarted;
		if(proxyStarted){
			btnStartProxy.setText(STOP_LABEL);
		}else{
			btnStartProxy.setText(START_LABEL);
		}
	}
	public void enableStartProxyButton(boolean enable){
		btnStartProxy.setEnabled(enable);
	}
	
}
