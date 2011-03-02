package throngModel;

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
