package manualView;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

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
	private GridBagConstraints cHost;
	
	public HostRow(){
		gridbagHost= new GridBagLayout();
		cHost = new GridBagConstraints();
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
