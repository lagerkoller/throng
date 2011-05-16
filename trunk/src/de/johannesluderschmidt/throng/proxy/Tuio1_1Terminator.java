package de.johannesluderschmidt.throng.proxy;

import java.util.Date;

import com.illposed.osc.OSCBundle;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPacket;
import com.illposed.osc.OSCPortOut;

import de.johannesluderschmidt.simpleDebug.Debug;

public class Tuio1_1Terminator implements IThrongProxyTerminator {

	public void sendEmptyBundlesWithSource(SourceIpPortBean bean) {
		Object[] aliveArguments = {"alive"};
    	Object[] fseqArguments = {"fseq",new Integer(ThrongMultiplexer.getInstance().getIncrementedFseq())};
		
    	OSCPacket[] terminating2DcurBundleArguments;
    	OSCPacket[] terminating2DobjBundleArguments;
    	
		Object[] sourceArguments;
		sourceArguments = new Object[2];
    	
		sourceArguments[0] = "source";
		sourceArguments[1] = bean.getSource();
		terminating2DcurBundleArguments = new OSCPacket[3];
		terminating2DobjBundleArguments = new OSCPacket[3];
		
		terminating2DcurBundleArguments[0] = new OSCMessage("/tuio/2Dcur",sourceArguments);
		terminating2DcurBundleArguments[1] = new OSCMessage("/tuio/2Dcur",aliveArguments);
    	terminating2DcurBundleArguments[2] = new OSCMessage("/tuio/2Dcur",fseqArguments);
    	
    	terminating2DobjBundleArguments[0] = new OSCMessage("/tuio/2Dobj",sourceArguments);
    	terminating2DobjBundleArguments[1] = new OSCMessage("/tuio/2Dobj",aliveArguments);
    	terminating2DobjBundleArguments[2] = new OSCMessage("/tuio/2Dobj",fseqArguments);
			
	    	
    	OSCBundle terminating2DcurBundle = new OSCBundle(terminating2DcurBundleArguments);
    	OSCBundle terminating2DobjBundle = new OSCBundle(terminating2DobjBundleArguments);
    	
		terminating2DcurBundle.setIpAddress(bean.getIpAddress());
    	terminating2DcurBundle.setPortOut(bean.getPort());
    	
    	terminating2DobjBundle.setIpAddress(bean.getIpAddress());
    	terminating2DobjBundle.setPortOut(bean.getPort());
    	
    	try{
	    	ThrongProxy.getInstance().acceptBundle(new Date(), terminating2DcurBundle);
	    	ThrongProxy.getInstance().acceptBundle(new Date(), terminating2DobjBundle);
    	}catch(Exception e){
    		Debug.writeException("Could not multiplex and send terminating bundles", this, e);
    	}
	}

	public void sendEmptyBundlesWithoutSource(OSCPortOut oscPortOut) {
		Object[] aliveArguments = {"alive"};
    	Object[] fseqArguments = {"fseq",new Integer(ThrongMultiplexer.getInstance().getIncrementedFseq())};
		
    	OSCPacket[] terminating2DcurBundleArguments;
    	OSCPacket[] terminating2DobjBundleArguments;
    	
    	terminating2DcurBundleArguments = new OSCPacket[2];
    	terminating2DobjBundleArguments = new OSCPacket[2];
    	
    	terminating2DcurBundleArguments[0] = new OSCMessage("/tuio/2Dcur",aliveArguments);
    	terminating2DcurBundleArguments[1] = new OSCMessage("/tuio/2Dcur",fseqArguments);
    	
    	terminating2DobjBundleArguments[0] = new OSCMessage("/tuio/2Dobj",aliveArguments);
    	terminating2DobjBundleArguments[1] = new OSCMessage("/tuio/2Dobj",fseqArguments);
    	
    	OSCBundle terminating2DcurBundle = new OSCBundle(terminating2DcurBundleArguments);
    	OSCBundle terminating2DobjBundle = new OSCBundle(terminating2DobjBundleArguments);
    	
    	try{
    		//send directly via socket and not via multiplexer
    		oscPortOut.send(terminating2DcurBundle);
    		oscPortOut.send(terminating2DobjBundle);
    	}catch(Exception e){
    		Debug.writeException("Could not send empty termination bundles", this, e);
    	}
	}

}
