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

import java.util.Vector;

import javax.swing.event.ChangeEvent;

import de.johannesluderschmidt.throng.proxy.Host;

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
