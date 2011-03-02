package throngModel;

public class ThrongClient {
	private String ip;
	private Integer port;
	private Float offsetX;
	private Float offsetY;
	private Float width;
	private Float height;
	private Float overlapXLeft;
	private Float overlapXRight;
	private Float cropStartY;
	private Float cropEndY;
	private String type;
	private Boolean flipRotation;
	private Boolean flipXAndY;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Float getOverlapXLeft() {
		return overlapXLeft;
	}
	public void setOverlapXLeft(Float overlapXLeft) {
		this.overlapXLeft = overlapXLeft;
	}
	public Float getOverlapXRight() {
		return overlapXRight;
	}
	public void setOverlapXRight(Float overlapXRight) {
		this.overlapXRight = overlapXRight;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Float getOffsetX() {
		return offsetX;
	}
	public void setOffsetX(Float pos) {
		offsetX = pos;
	}
	public Float getOffsetY() {
		return offsetY;
	}
	public void setOffsetY(Float pos) {
		offsetY = pos;
	}
	public Float getWidth() {
		return width;
	}
	public void setWidth(Float width) {
		this.width = width;
	}
	public Float getHeight() {
		return height;
	}
	public void setHeight(Float height) {
		this.height = height;
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
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public Boolean getFlipXAndY() {
		return flipXAndY;
	}
	public void setFlipXAndY(Boolean flipXAndY) {
		this.flipXAndY = flipXAndY;
	}
}
