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

import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class HostRow extends JPanel {
	private JLabel oscHostLabel;
	private JLabel oscHost;
	private JLabel oscHostPortLabel;
	private JLabel oscHostPort;
	private ThrongSlider widthSlider;
	private ThrongSlider overlapXRightSlider;
	private ThrongSlider overlapXLeftSlider;
	private JLabel typeLabel;
	
	private RemoveHostJButton removeIPButton;
	
	private GridBagLayout gridbagHost;
//	private GridBagConstraints cHost;
	
	public HostRow(){
		gridbagHost= new GridBagLayout();
//		cHost = new GridBagConstraints();
		setLayout(gridbagHost);
	}
	
	public JLabel getOscHostPortLabel() {
		return oscHostPortLabel;
	}

	public void setOscHostPortLabel(JLabel oscHostPortLabel) {
		if(this.oscHostPortLabel != null){
			remove(this.oscHostPortLabel);
		}
		this.oscHostPortLabel = oscHostPortLabel;
		add(this.oscHostPortLabel);
	}

	public JLabel getOscHostPort() {
		return oscHostPort;
	}



	public void setOscHostPort(JLabel oscHostPort) {
		if(this.oscHostPort != null){
			remove(this.oscHostPort);
		}
		this.oscHostPort = oscHostPort;
		add(this.oscHostPort);
	}



	public ThrongSlider getWidthSlider() {
		return widthSlider;
	}

	public void setWidthSlider(ThrongSlider widthSlider) {
		if(this.widthSlider != null){
			remove(widthSlider);
		}
		this.widthSlider = widthSlider;
		add(widthSlider);
	}

	public ThrongSlider getOverlapXRightSlider() {
		return overlapXRightSlider;
	}

	public void setOverlapXRightSlider(ThrongSlider overlapXRightSlider) {
		this.overlapXRightSlider = overlapXRightSlider;
	}

	public ThrongSlider getOverlapXLeftSlider() {
		return overlapXLeftSlider;
	}

	public void setOverlapXLeftSlider(ThrongSlider overlapXLeftSlider) {
		this.overlapXLeftSlider = overlapXLeftSlider;
	}

	public JLabel getOscHostLabel() {
		return oscHostLabel;
	}
	public void setOscHostLabel(JLabel oscHostLabel) {
		if(this.oscHostLabel != null){
			remove(this.oscHostLabel);
		}
		this.oscHostLabel = oscHostLabel;
		add(this.oscHostLabel);
	}
	public JLabel getOscHost() {
		return oscHost;
	}
	public void setOscHost(JLabel oscHost) {
		if(this.oscHost != null){
			remove(this.oscHost);
		}
		this.oscHost = oscHost;
		add(this.oscHost);
	}
	public JLabel getTypeLabel() {
		return typeLabel;
	}

	public void setTypeLabel(JLabel typeLabel) {
		if(this.typeLabel != null){
			remove(this.typeLabel);
		}
		this.typeLabel = typeLabel;
		add(this.typeLabel);
	}
	public RemoveHostJButton getRemoveIPButton() {
		return removeIPButton;
	}
	public void setRemoveIPButton(RemoveHostJButton removeIPButton) {
		if(this.removeIPButton != null){
			remove(this.removeIPButton);
		}
		this.removeIPButton = removeIPButton;
		
//		cHost.gridwidth = GridBagConstraints.REMAINDER; //end row
//		gridbagHost.setConstraints(this, cHost);
		
		add(this.removeIPButton);
	}
}
