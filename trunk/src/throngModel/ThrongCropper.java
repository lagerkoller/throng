package throngModel;


import java.util.Vector;

import flosc.OscMessage;
import flosc.OscPacket;

import util.Config;
import util.Constants;

public class ThrongCropper{
	private static ThrongCropper inst;
	
	private Vector<Host> hosts;
	private Vector<Host> touchHosts;
	private Vector<Host> fiducialHosts;
	
	private ThrongCropper(){
	}
	
	public static ThrongCropper getInstance(){
		if(inst == null){
			inst = new ThrongCropper();
		}
		return inst;
	}
	public OscPacket globalizeTUIO(OscPacket packet, int inputPort) {
//    	try{
    		String address = packet.getAddress().toString().substring(1,packet.getAddress().toString().length());
//    		String port = 
    		for(OscMessage message : packet.getMessages()){
    			if(message.getArguments().get(0).equals("set") && message.getName().equals(Constants.TWOD_CUR)){
    				message.setArguments(globalizeTouch(message.getArguments(), address, inputPort));
    			}
    			if(message.getArguments().get(0).equals("set") && message.getName().equals(Constants.TWOD_OBJ)){
    				message.setArguments(globalizeFidu(message.getArguments(), address, inputPort));
    			}
    		}
//    	}catch(Exception e){
//    		Debug.writeActivity("Exception in MultifloscGlobalizer.globalizeTUIO(): "+ e.getMessage());
//    	}
    	
    	return packet;
    }
	
	private Vector<Object> globalizeTouch(Vector<Object> payloadVector, String address, int inputPort){
		/*
		Integer id = (Integer) payloadVector.get(1);
		Float xpos = (Float) payloadVector.get(2);
		Float ypos = (Float) payloadVector.get(3);
		Float xspeed = (Float) payloadVector.get(4);
		Float yspeed = (Float) payloadVector.get(5);
		Float maccel = (Float) payloadVector.get(6);
		*/
		
		int i = 0;
		float width = 0;
		int xPosIndex = 2;
		int yPosIndex = 3;
		
		if(Config.getInstance().getUseOffsets()){
			width = Config.getInstance().getOffsetXLeft();
		}
		
		for(Host host : touchHosts){
			if(address.equals(host.getIp()) && host.getPort() == inputPort){
				
				if(host.getFlipXAndY()){
					Float xValueTemp = (Float)payloadVector.get(xPosIndex);
					payloadVector.set(xPosIndex, (Float)payloadVector.get(yPosIndex));
					payloadVector.set(yPosIndex, xValueTemp);
				}
				
				if(Config.getInstance().getUseOffsets()){
					float finalWidth = width + ((Float)payloadVector.get(xPosIndex)) * host.getWidth();
					payloadVector.set(xPosIndex, finalWidth);
				}
				break;
			}else{
				if(Config.getInstance().getUseOffsets()){
					width = width+host.getWidth();
				}
			}
			i = i+1;
		}
		
		return payloadVector;
	}
	
	private Vector<Object> globalizeFidu(Vector<Object> payloadVector, String address, int inputPort){
		/*
		Integer s_id = (Integer) payloadVector.get(1);
		Integer f_id = (Integer) payloadVector.get(2);
		Float xpos = (Float) payloadVector.get(3);
		Float ypos = (Float) payloadVector.get(4);
		Float angle = (Float) payloadVector.get(5);
		Float xspeed = (Float) payloadVector.get(6);
		Float yspeed = (Float) payloadVector.get(7);
		Float rspeed = (Float) payloadVector.get(8);
		Float maccel = (Float) payloadVector.get(9);
		Float raccel = (Float) payloadVector.get(10);
		*/
		
		int i = 1;
		float width = 0;
		if(!Config.getInstance().isFlipXAndY()){
			width = Config.getInstance().getOffsetXLeft();
		}else{
			width = Config.getInstance().getOffsetYTop();
		}
		
		for(Host host : fiducialHosts){
			if(address.equals(host.getIp()) && host.getPort() == inputPort){
				int xPosIndex = 3;
				int yPosIndex = 4;
				if(host.getFlipXAndY()){
					Float xValueTemp = (Float)payloadVector.get(xPosIndex);
					payloadVector.set(xPosIndex, (Float)payloadVector.get(yPosIndex));
					payloadVector.set(yPosIndex, xValueTemp);
				}
				if(host.getFlipRotation()){
					payloadVector.set(5, -((Float)payloadVector.get(5)));
				}
				
				if(Config.getInstance().getUseOffsets()){
					if(((Float)payloadVector.get(yPosIndex)) > host.getCropStartY() && ((Float)payloadVector.get(yPosIndex)) < host.getCropEndY()){
						float finalWidth = width + ((Float)payloadVector.get(xPosIndex)) * host.getWidth();
						payloadVector.set(xPosIndex, finalWidth);
					}else{
						payloadVector = null;
					}
				}
				
				break;
			}else{
				width = width+host.getWidth();
			}
			i = i+1;
		}
		
		
		return payloadVector;
	}
	
	public Vector<Host> getHosts() {
		return hosts;
	}
	public void setHosts(Vector<Host> hosts) {
		touchHosts = new Vector<Host>();
		fiducialHosts = new Vector<Host>();
		for(Host host : hosts){
			if(host.getType().equals(Constants.TOUCH)){
				touchHosts.add(host);
			}
			if(host.getType().equals(Constants.FIDUCIAL)){
				fiducialHosts.add(host);
			}
			if(host.getType().equals(Constants.BOTH)){
				fiducialHosts.add(host);
				touchHosts.add(host);
			}
		}
		this.hosts = hosts;
	}
}
