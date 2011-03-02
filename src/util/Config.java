package util;

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
