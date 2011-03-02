package manualView;

import java.awt.Event;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import throngModel.Host;
import util.Constants;


import flosc.Debug;

public class ThrongSlider extends JPanel {
	protected JSlider slider;
	protected float lastSliderValue;
	
	protected GridBagLayout gridbagSlider;
	protected GridBagConstraints cSlider;
	
	protected JLabel sliderLabel;
	protected JLabel sliderValueLabel;
	
	protected ThrongSlider previousSlider;
	protected ThrongSlider nextSlider;
	
	protected Vector<Host> hosts;
	protected int index;
	
	protected String type;

	public ThrongSlider(String type, Vector<Host> hosts, int index){
		this.type = type;
		this.hosts = hosts;
		this.index = index;
		
		gridbagSlider= new GridBagLayout();
		cSlider = new GridBagConstraints();
		setLayout(gridbagSlider);
	}

	public JSlider getSlider() {
		return slider;
	}

	public void setSlider(JSlider slider) {
		if(this.slider != null){
			remove(this.slider);
		}
		this.slider = slider;
		this.slider.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
//				sliderHandler(e);
			}
		});
		this.slider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				sliderHandler(e);
			}
		});
		add(this.slider);
	}
	protected void sliderHandler(ChangeEvent e){
		
	}
	
	public JLabel getSliderLabel() {
		return sliderLabel;
	}

	public void setSliderLabel(JLabel sliderLabel) {
		if(this.sliderLabel != null){
			remove(this.sliderLabel);
		}
		this.sliderLabel = sliderLabel;
		add(this.sliderLabel);
	}

	public JLabel getSliderValueLabel() {
		return sliderValueLabel;
	}

	public void setSliderValueLabel(JLabel sliderValueLabel) {
		if(this.sliderValueLabel != null){
			remove(this.sliderValueLabel);
		}
		this.sliderValueLabel = sliderValueLabel;
		add(this.sliderValueLabel);
		if(this.slider != null){
			float sliderValue = ((float)this.slider.getValue())/100;
			String output = ""+sliderValue;
			if(output.length() == 3){
				output = output + "0";
			}
			this.sliderValueLabel.setText(output);
		}
	}
	
	
	
	public ThrongSlider getPreviousSlider() {
		return previousSlider;
	}

	public void setPreviousSlider(ThrongSlider previousSlider) {
		this.previousSlider = previousSlider;
	}

	public ThrongSlider getNextSlider() {
		return nextSlider;
	}

	public void setNextSlider(ThrongSlider nextSlider) {
		this.nextSlider = nextSlider;
	}
	public int getSumNextValues(String type){
		int nextValueSum = 0;
		ThrongSlider nextSliderTemp = nextSlider;
		while(nextSliderTemp != null){
			if((nextSliderTemp.getType().equals(type)) || (nextSliderTemp.getType().equals(Constants.BOTH))){
				nextValueSum = nextValueSum + nextSliderTemp.getSlider().getValue();
			}
			nextSliderTemp = nextSliderTemp.getNextSlider();
		}
		
		return nextValueSum;
	}
	
	public int getSumPrevValues(String type){
		int prevValueSum = 0;
		ThrongSlider prevSliderTemp = previousSlider;
		while(prevSliderTemp != null){
			if((prevSliderTemp.getType().equals(type)) || (prevSliderTemp.getType().equals(Constants.BOTH))){
				prevValueSum = prevValueSum + prevSliderTemp.getSlider().getValue();
			}
			prevSliderTemp = prevSliderTemp.getPreviousSlider();
		}
		
		return prevValueSum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
