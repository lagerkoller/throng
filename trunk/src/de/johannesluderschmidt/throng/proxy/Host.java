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

public class Host {
	private String ip;
	private Integer port;
	private String type;
	private Float width;
	private Float cropStartY;
	private Float cropEndY;
	private Boolean flipRotation;
	private Boolean flipXAndY;
	
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Float getWidth() {
		return width;
	}
	public void setWidth(Float width) {
		this.width = width;
	}
	public Float getCropStartY() {
		return cropStartY;
	}
	public void setCropStartY(Float cropStartY) {
		this.cropStartY = cropStartY;
	}
	public Float getCropEndY() {
		return cropEndY;
	}
	public void setCropEndY(Float cropEndY) {
		this.cropEndY = cropEndY;
	}
	public Boolean getFlipRotation() {
		return flipRotation;
	}
	public void setFlipRotation(Boolean flipRotation) {
		this.flipRotation = flipRotation;
	}
	public Boolean getFlipXAndY() {
		return flipXAndY;
	}
	public void setFlipXAndY(Boolean flipXAndY) {
		this.flipXAndY = flipXAndY;
	}
}
