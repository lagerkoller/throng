package manualView;

import java.util.Vector;

import javax.swing.event.ChangeEvent;

import throngModel.Host;


import flosc.Debug;

public class ThrongWidthSlider extends ThrongSlider {
	
	public ThrongWidthSlider(String type, Vector<Host> hosts, int index){
		super(type, hosts, index);
	}
	
	protected void sliderHandler(ChangeEvent e){
		if(previousSlider == null && nextSlider == null){
			if(this.sliderValueLabel != null){
				float sliderValue = ((float)this.slider.getValue())/100;
				String output = ""+sliderValue;
				if(output.length() == 3){
					output = output + "0";
				}
				this.sliderValueLabel.setText(output);
				lastSliderValue = sliderValue;
			}
		}else{
			//first (but not the only slider)
			if(previousSlider == null && nextSlider != null){
				if(getSumNextValues(type) + this.slider.getValue() <= 100){
					if(this.sliderValueLabel != null){
						float sliderValue = ((float)this.slider.getValue())/100;
						hosts.get(index).setWidth(sliderValue);
						String output = ""+sliderValue;
						if(output.length() == 3){
							output = output + "0";
						}
						this.sliderValueLabel.setText(output);
					}
				}else{
					this.slider.setValue((int)100-getSumNextValues(type));
					float sliderValue = ((float)this.slider.getValue())/100;
					String output = ""+sliderValue;
					if(output.length() == 3){
						output = output + "0";
					}
					this.sliderValueLabel.setText(output);
				}
			}

			//one of the middle sliders
			if(previousSlider != null && nextSlider != null){
				if(getSumPrevValues(type) + getSumNextValues(type) + this.slider.getValue() <= 100){
					if(this.sliderValueLabel != null){
						float sliderValue = ((float)this.slider.getValue())/100;
						hosts.get(index).setWidth(sliderValue);
						String output = ""+sliderValue;
						if(output.length() == 3){
							output = output + "0";
						}
						this.sliderValueLabel.setText(output);
					}
				}else{
					this.slider.setValue((int)100-(getSumPrevValues(type) + getSumNextValues(type)));
					float sliderValue = ((float)this.slider.getValue())/100;
					String output = ""+sliderValue;
					if(output.length() == 3){
						output = output + "0";
					}
					this.sliderValueLabel.setText(output);
				}
			}
			
			//last (but not the only slider)
			if(previousSlider != null && nextSlider == null){
				if(getSumPrevValues(type) + this.slider.getValue() <= 100){
					if(this.sliderValueLabel != null){
						float sliderValue = ((float)this.slider.getValue())/100;
						hosts.get(index).setWidth(sliderValue);
						String output = ""+sliderValue;
						if(output.length() == 3){
							output = output + "0";
						}
						this.sliderValueLabel.setText(output);
					}
				}else{
					this.slider.setValue((int)100-getSumPrevValues(type));
					float sliderValue = ((float)this.slider.getValue())/100;
					String output = ""+sliderValue;
					if(output.length() == 3){
						output = output + "0";
					}
					this.sliderValueLabel.setText(output);
				}
			}
			
		}
		
	}
}
