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
package de.johannesluderschmidt.throng.proxy;

public class Config {
	private String receiver;
	private String receiverPort;
	private Boolean useOffsets;
	
	private Float offsetXLeft;
	private Float offsetXRight;
	private Float offsetYTop;
	private Float offsetYBottom;
	private Long timeout;
	private boolean flipXAndY;
	
	private static Config inst;
	
	private Config(){
		
	}
	public static Config getInstance(){
		if(inst == null){
			inst = new Config();
		}
		return inst;
	}
	
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	public String getReceiverPort() {
		return receiverPort;
	}
	public void setReceiverPort(String receiverPort) {
		this.receiverPort = receiverPort;
	}
	public Float getOffsetXLeft() {
		return offsetXLeft;
	}
	public void setOffsetXLeft(Float offsetXTop) {
		this.offsetXLeft = offsetXTop;
	}
	public Float getOffsetXRight() {
		return offsetXRight;
	}
	public void setOffsetXRight(Float offsetXBottom) {
		this.offsetXRight = offsetXBottom;
	}
	public Float getOffsetYTop() {
		return offsetYTop;
	}
	public void setOffsetYTop(Float offsetYTop) {
		this.offsetYTop = offsetYTop;
	}
	public Float getOffsetYBottom() {
		return offsetYBottom;
	}
	public void setOffsetYBottom(Float offsetYBottom) {
		this.offsetYBottom = offsetYBottom;
	}
	public boolean isFlipXAndY() {
		return flipXAndY;
	}
	public void setFlipXAndY(boolean flipXAndY) {
		this.flipXAndY = flipXAndY;
	}
	public Long getTimeout() {
		return timeout;
	}
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}
	public Boolean getUseOffsets() {
		return useOffsets;
	}
	public void setUseOffsets(Boolean useOffset) {
		this.useOffsets = useOffset;
	}
	
}
