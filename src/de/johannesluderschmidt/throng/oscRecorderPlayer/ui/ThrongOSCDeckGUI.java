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


import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import org.tuio.java.TuioTime;

import de.johannesluderschmidt.simpleDebug.Debug;
import de.johannesluderschmidt.throng.oscRecorderPlayer.IOSCProxy;
import de.johannesluderschmidt.throng.oscRecorderPlayer.loadSave.IOSCLoaderSaverInterface;
import de.johannesluderschmidt.throng.oscRecorderPlayer.loadSave.OpenOSCMenuItem;
import de.johannesluderschmidt.throng.oscRecorderPlayer.loadSave.SaveOSCMenuItem;
import de.johannesluderschmidt.throng.oscRecorderPlayer.recorder.OSCRecordedPacket;
import de.johannesluderschmidt.throng.proxy.ThrongMultiplexer;
import de.johannesluderschmidt.throng.proxy.ThrongProxy;
import de.johannesluderschmidt.throng.proxy.Tuio1_1Terminator;
import de.johannesluderschmidt.throng.util.Constants;
import de.johannesluderschmidt.tuio3DExt.Tuio3DExtTerminator;

public class ThrongOSCDeckGUI extends JFrame implements IOSCProxy, IOSCLoaderSaverInterface{
	private DeckControlPanel deckControlGui;
	private NetworkControlPanel networkControlGui;
	private JPanel containerPanel;
	
	private boolean proxyStarted;
	
	public ThrongOSCDeckGUI(int inboundPort, String outboundIp, int outboundPort) {
		super();

		containerPanel = new JPanel();
		
		deckControlGui = new DeckControlPanel(this);
		deckControlGui.setMaximumSize(new Dimension(deckControlGui.getPreferredSize().width, 100));
		networkControlGui = new NetworkControlPanel(this, inboundPort, outboundIp, outboundPort);
		
		TuioTime.initSession();
		ThrongProxy.getInstance().addTerminator(new Tuio1_1Terminator());
		ThrongProxy.getInstance().addTerminator(new Tuio3DExtTerminator());
		
		initProxy();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(new OpenOSCMenuItem(this));
		fileMenu.add(new SaveOSCMenuItem(this));
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
		
		setupWindow();
		
		//sets initial focus on startStopButton
		addWindowFocusListener(new WindowAdapter(){
		    public void windowGainedFocus(WindowEvent e) {
		    	networkControlGui.setFocusOnStartButton();
		    }
		});
	}
	private void setupWindow(){
		setTitle("ThrongOSCDeck "+Constants.THRONG_VERSION);
		setResizable(false);
		
		BoxLayout layout = new BoxLayout(containerPanel, BoxLayout.Y_AXIS);
		
		getContentPane().add(containerPanel);
		containerPanel.setLayout(layout);
		containerPanel.add(deckControlGui);
		containerPanel.add(networkControlGui);
		containerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		pack();
		setVisible(true);
		repaint();
	}
	public void initProxy(){
		try {
			ThrongProxy.getInstance().setOutboundConnection(InetAddress.getByName(Constants.DEFAULT_IP), Constants.DEFAULT_OUTPUT_PORT);
			ThrongMultiplexer.getInstance().setMultiplexerEnabled(false);
			ThrongMultiplexer.getInstance().setIndividualizeIdRanges(false);
			ThrongMultiplexer.getInstance().setManageSrcMsg(false);
		} catch (UnknownHostException e) {
			Debug.writeException("Could not initialize proxy because I could not resolve the localhost address", this, e);
		}
	}
	public void startProxy(){
		networkControlGui.startProxy();
	}
	public void stopProxy(){
		networkControlGui.stopProxy();
	}
	public void setFocusOnStartButton(){
		networkControlGui.setFocusOnStartButton();
	}
	public void setRecordingEnabled(boolean recordingEnabled){
		deckControlGui.setRecordingEnabled(recordingEnabled);
	}
	public void setProxyStarted(boolean proxyStarted){
		networkControlGui.setProxyStarted(proxyStarted);
	}
	public void setRecFileLoaded(ArrayList<OSCRecordedPacket> recordedPackets){
		deckControlGui.setRecordedPackets(recordedPackets);
	}
	public void enableStartProxyButton(boolean enable){
		networkControlGui.enableStartProxyButton(enable);
	}
	public boolean isProxyStarted() {
		return proxyStarted;
	}
	public void startOrStopProxy(boolean start, int inboundPort,
			InetAddress outboundIp, int outboundPort) {

		if(start){
			try{
				if(inboundPort > 0){
					int[] oscInputPorts = new int[1];
					oscInputPorts[0] = inboundPort;
					ThrongProxy.getInstance().setInboundPorts(oscInputPorts);
				}else{
					ThrongProxy.getInstance().setInboundPorts(ThrongProxy.getInstance().getInboundPorts());
				}
				
				if(outboundPort > 0){
					ThrongProxy.getInstance().setOutboundConnection(outboundIp, outboundPort);
				}
				ThrongProxy.getInstance().startProxy();
				deckControlGui.setRecordingEnabled(true);
				networkControlGui.setProxyStarted(true);
				proxyStarted = true;
			}catch(Exception ex){
				Debug.writeException("Error when trying to start TuioProxy: "+ex.getMessage(),this, ex);
			}
		}else{
			try{
				ThrongProxy.getInstance().stopProxy();
				deckControlGui.setRecordingEnabled(false);
				networkControlGui.setProxyStarted(false);
				proxyStarted = false;
			}catch(Exception ex){
				Debug.writeException("Error when stopping gateway from GUI: "+ex.getMessage(),this, ex);
			}
		}
	}
	
	public void resetProxy() {
		ThrongProxy.getInstance().resetConnectedSources();
		ThrongProxy.getInstance().resetTuioClientMessages();
	}
	public void setRecordedPackets(ArrayList<OSCRecordedPacket> recordedPackets) {
		deckControlGui.setRecordedPackets(recordedPackets);
	}

	public ArrayList<OSCRecordedPacket> getRecordedPackets(){
		return deckControlGui.getRecordedPackets();
	}
	
}