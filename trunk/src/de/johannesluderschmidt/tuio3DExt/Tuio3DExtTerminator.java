package de.johannesluderschmidt.tuio3DExt;

import java.util.Date;

import com.illposed.osc.OSCBundle;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPacket;
import com.illposed.osc.OSCPortOut;

import de.johannesluderschmidt.simpleDebug.Debug;
import de.johannesluderschmidt.throng.proxy.IThrongProxyTerminator;
import de.johannesluderschmidt.throng.proxy.SourceIpPortBean;
import de.johannesluderschmidt.throng.proxy.ThrongMultiplexer;
import de.johannesluderschmidt.throng.proxy.ThrongProxy;

public class Tuio3DExtTerminator implements IThrongProxyTerminator{

	public void sendEmptyBundlesWithSource(SourceIpPortBean bean) {
		Object[] aliveArguments = {"alive"};
    	Object[] fseqArguments = {"fseq",new Integer(ThrongMultiplexer.getInstance().getIncrementedFseq())};
		
    	OSCPacket[] terminating_3DextBundleArguments;
    	
		Object[] sourceArguments;
		sourceArguments = new Object[2];
    	
		sourceArguments[0] = "source";
		sourceArguments[1] = bean.getSource();
		terminating_3DextBundleArguments = new OSCPacket[3];
		
    	terminating_3DextBundleArguments[0] = new OSCMessage("/tuio/_3Dext",sourceArguments);
    	terminating_3DextBundleArguments[1] = new OSCMessage("/tuio/_3Dext",aliveArguments);
    	terminating_3DextBundleArguments[2] = new OSCMessage("/tuio/_3Dext",fseqArguments);
    	
	    	
    	OSCBundle terminating_3DextBundle = new OSCBundle(terminating_3DextBundleArguments);
    	
    	terminating_3DextBundle.setIpAddress(bean.getIpAddress());
    	terminating_3DextBundle.setPortOut(bean.getPort());
    	
    	try{
	    	ThrongProxy.getInstance().acceptBundle(new Date(), terminating_3DextBundle);
    	}catch(Exception e){
    		Debug.writeException("Could not multiplex and send terminating bundles", this, e);
    	}		
	}

	public void sendEmptyBundlesWithoutSource(OSCPortOut oscPortOut) {
		Object[] aliveArguments = {"alive"};
    	Object[] fseqArguments = {"fseq",new Integer(ThrongMultiplexer.getInstance().getIncrementedFseq())};
		
    	OSCPacket[] terminating_3DextBundleArguments;
    	
    	terminating_3DextBundleArguments = new OSCPacket[2];
    	
    	terminating_3DextBundleArguments[0] = new OSCMessage("/tuio/_3Dext",aliveArguments);
    	terminating_3DextBundleArguments[1] = new OSCMessage("/tuio/_3Dext",fseqArguments);
    	
    	OSCBundle terminating_3DextBundle = new OSCBundle(terminating_3DextBundleArguments);
    	
    	try{
    		//send directly via socket and not via multiplexer
    		oscPortOut.send(terminating_3DextBundle);
    	}catch(Exception e){
    		Debug.writeException("Could not send empty termination bundles", this, e);
    	}		
	}

}
