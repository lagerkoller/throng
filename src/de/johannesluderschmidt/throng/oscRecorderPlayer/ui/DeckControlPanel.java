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
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import de.johannesluderschmidt.throng.oscRecorderPlayer.IOSCProxy;
import de.johannesluderschmidt.throng.oscRecorderPlayer.player.OSCPlayer;
import de.johannesluderschmidt.throng.oscRecorderPlayer.recorder.OSCRecordedPacket;
import de.johannesluderschmidt.throng.oscRecorderPlayer.recorder.OSCRecorder;
import de.johannesluderschmidt.throng.oscRecorderPlayer.ui.elements.ITimeLabelCallback;
import de.johannesluderschmidt.throng.oscRecorderPlayer.ui.elements.TimeLabel;
import de.johannesluderschmidt.throng.proxy.ThrongProxy;

public class DeckControlPanel extends JPanel implements ITimeLabelCallback{
	private static final String RECORD_BUTTON_LABEL = "Record";
	private static final String STOP_BUTTON_LABEL = "Stop";
	private static final String PLAY_BUTTON_LABEL = "Play";
	
	private JButton playButton;
	private TimeLabel playTimeLabel;
	private JButton recordButton;
	private TimeLabel recordTimeLabel;	
	private IOSCProxy deck;
	private OSCRecorder recorder;
	private OSCPlayer player;
	
	private boolean reconnect;
	
	public DeckControlPanel(IOSCProxy deck) {
		this.deck = deck;
		
		recorder = new OSCRecorder();
		ThrongProxy.getInstance().addListener(recorder);
		
		player = new OSCPlayer(ThrongProxy.getInstance());
		
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
				FormFactory.UNRELATED_GAP_ROWSPEC,}));
		
		JLabel lblVisOptions = new JLabel("Deck Options");
		add(lblVisOptions, "1, 1");
		
		recordTimeLabel = new TimeLabel();
		add(recordTimeLabel, "1, 3");
		
		recordButton = new JButton(RECORD_BUTTON_LABEL);
		recordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				recordButtonAction();
			}
		});
		recordButton.setEnabled(false);
		add(recordButton, "3, 3");
		
		playTimeLabel = new TimeLabel(this);
		add(playTimeLabel, "1, 5");
		
		playButton = new JButton(PLAY_BUTTON_LABEL);
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				playButtonAction();
			}
		});
		playButton.setEnabled(false);
		add(playButton, "3, 5");
	}
	
	private void recordButtonAction(){
		if(recordButton.getText().equals(RECORD_BUTTON_LABEL)){
			startRecording();
		}else{
			stopRecording();
		}
	}
	
	private void startRecording(){
		if(player.isPlaying()){
			stopPlaying();
			playTimeLabel.reset();
		}
		playButton.setEnabled(false);
		playTimeLabel.reset();
		recorder.setRecording(true);
		recordTimeLabel.reset();
		recordTimeLabel.startCounting();
		recordButton.setText(STOP_BUTTON_LABEL);
	}
	
	private void stopRecording(){
		recorder.setRecording(false);
		playButton.setEnabled(true);
		recordTimeLabel.stopCounting();
		recordTimeLabel.setLabelString(recordTimeLabel.calculateTimeString(recorder.getDuration()));
		recordButton.setText(RECORD_BUTTON_LABEL);
	}
	public ArrayList<OSCRecordedPacket> getRecordedPackets(){
		return recorder.getRecordedPackets();
	}
	public void setRecordedPackets(ArrayList<OSCRecordedPacket> recordedPackets){
		playButton.setEnabled(true);
		playButton.requestFocusInWindow();
		recorder.setRecordedPackets(recordedPackets);
		recordTimeLabel.setDuration(recorder.getDuration());
	}
	private void playButtonAction(){
		if(playButton.getText().equals(PLAY_BUTTON_LABEL)){
			startPlaying();
		}else{
			stopPlaying();
		}
	}
	private void startPlaying(){
		if(deck.isProxyStarted()){
			deck.startOrStopProxy(false, -1, null, -1);
			reconnect = true;
		}
		deck.resetProxy();
		deck.enableStartProxyButton(false);
		player.setRecordedPackets(recorder.getRecordedPackets());
		player.play();
		recordButton.setEnabled(false);
		playTimeLabel.reset();
		playTimeLabel.setDuration(recorder.getDuration());
		playTimeLabel.startCounting();
		playButton.setText(STOP_BUTTON_LABEL);
	}
	private void stopPlaying(){
		deck.resetProxy();
		deck.enableStartProxyButton(true);
		player.stop();
		playTimeLabel.stopCounting();
		playTimeLabel.reset();
		playButton.setText(PLAY_BUTTON_LABEL);
	}
	public void setRecordingEnabled(boolean recordingEnabled){
		recordButton.setEnabled(recordingEnabled);
		if(recorder.isRecording() && !recordingEnabled){
			stopRecording();
		}
	}

	public void countingStopped() {
		if(player.isPlaying()){
			stopPlaying();
		}
		if(reconnect){
			deck.startOrStopProxy(true, -1, null, -1);
			reconnect = false;
		}
	}
	public void setFocusOnPlayButton(){
		playButton.requestFocusInWindow();
	}
	
}
