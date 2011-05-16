package de.johannesluderschmidt.tuio3DExt;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.tuio.java.TuioTime;

import com.illposed.osc.OSCBundle;
import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;

public class Tuio3DExtClient implements OSCListener {
	private final String TUIO_3D_EXT = "/tuio/_3Dext";
	
	private Hashtable<Long,Tuio3DExtremity> extremityList;
	private Hashtable<Long,Tuio3DExtremity> frameExtremityList;
	private Vector<Long> added;
	private Vector<Long> updated;
	private Vector<Long> removed;
	private Vector<ITuio3DExtremityListener> listenerList;
	
	private TuioTime currentTime;
	private String src;
	private long currentFrame;
	
	public Tuio3DExtClient(){
		currentFrame = 0;
		extremityList = new Hashtable<Long,Tuio3DExtremity>();
		listenerList = new Vector<ITuio3DExtremityListener>();
		added = new Vector<Long>();
		updated = new Vector<Long>();
		removed = new Vector<Long>();
	}
	
	public void acceptMessage(Date time, OSCMessage message) {
		if(currentTime == null){
			currentTime = new TuioTime();
			currentTime.reset();
		}
		
		Object[] args = message.getArguments();
		String command = (String)args[0];
		String address = message.getAddress();
		
		if (address.equals(TUIO_3D_EXT)) {

			if (command.equals("source")) {
				this.src = (String)args[1];
			}else if(command.equals("alive")){
				frameExtremityList = new Hashtable<Long,Tuio3DExtremity>();
				for (int i=1;i<args.length;i++) {
					long sessionId = ((Integer)args[i]).longValue();
					
					if(extremityList.contains(sessionId)){
						frameExtremityList.put(sessionId, extremityList.get(sessionId));
						updated.add(sessionId);
					}else{
//						frameExtremityList.put(sessionId, new Tuio3DExtremity());
						added.add(sessionId);
					}
				}
				
			}else if (command.equals("set")) {
				Tuio3DExtVector3D axis1 = new Tuio3DExtVector3D();
				Tuio3DExtVector3D axis2 = new Tuio3DExtVector3D();
				Tuio3DExtVector3D axis3 = new Tuio3DExtVector3D();
				Tuio3DExtVector3D centroid = new Tuio3DExtVector3D();

				int index = 1;

				long sessionId = ((Integer)args[index++]).longValue();
				long personId = ((Integer)args[index++]).longValue();
				axis1.setX(((Float)args[index++]).floatValue());
				axis1.setY(((Float)args[index++]).floatValue());
				axis1.setZ(((Float)args[index++]).floatValue());
				axis2.setX(((Float)args[index++]).floatValue());
				axis2.setY(((Float)args[index++]).floatValue());
				axis2.setZ(((Float)args[index++]).floatValue());
				axis3.setX(((Float)args[index++]).floatValue());
				axis3.setY(((Float)args[index++]).floatValue());
				axis3.setZ(((Float)args[index++]).floatValue());
				centroid.setX(((Float)args[index++]).floatValue());
				centroid.setY(((Float)args[index++]).floatValue());
				centroid.setZ(((Float)args[index++]).floatValue());
				Tuio3DExtBaseMatrix baseMatrix = new Tuio3DExtBaseMatrix(axis1, axis2, axis3, centroid);
				
				if (!frameExtremityList.contains(sessionId)) {
					Tuio3DExtremity ext = new Tuio3DExtremity(currentTime, sessionId, personId, baseMatrix, src, currentFrame);
					frameExtremityList.put(sessionId, ext);
				}else{
					frameExtremityList.get(sessionId).update(baseMatrix, currentFrame);
				}
			}else if (command.equals("fseq")) {
				long fseq = ((Integer)args[1]).longValue();
				boolean lateFrame = false;
				
				if (fseq>0) {
					if (fseq>currentFrame) currentTime = TuioTime.getSessionTime();
					if ((fseq>=currentFrame) || ((currentFrame-fseq)>100)) currentFrame = fseq;
					else lateFrame = true;
				} else if (TuioTime.getSessionTime().subtract(currentTime).getTotalMilliseconds()>100) {
					currentTime = TuioTime.getSessionTime();
				}
				if(!lateFrame){
					//set fseq values in the current exts
					Enumeration<Tuio3DExtremity> extremitiesEnum = frameExtremityList.elements();
					while(extremitiesEnum.hasMoreElements()) {
						Tuio3DExtremity extremity = extremitiesEnum.nextElement();
						extremity.setFseq(currentFrame);
					}
					
					//figure out removed exts
					for(Long sessionIdTemp:extremityList.keySet()){
						if(!frameExtremityList.contains(sessionIdTemp)){
//							Debug.writeActivity("1. Removing sessionId: "+sessionIdTemp+", ext: "+extremityList.get(sessionIdTemp), this);
							removed.add(sessionIdTemp);
						}
					}
					
					//notify callback objects
					callAddMethods();
					callUpdateMethods();
					callRemoveMethods();
					
					//set current list
					extremityList = frameExtremityList;
				}
				
				added.clear();
				updated.clear();
				removed.clear();
				this.src = null;
			}
		}
	}
	
	private void callAddMethods(){
		for(Long sessionId:added){
			for (int i=0;i<listenerList.size();i++) {
				ITuio3DExtremityListener listener = (ITuio3DExtremityListener)listenerList.elementAt(i);
				if (listener!=null) listener.addTuio3DExtremity(frameExtremityList.get(sessionId));
			}
		}
	}
	private void callUpdateMethods(){
		for(Long sessionId:updated){
			for (int i=0;i<listenerList.size();i++) {
				ITuio3DExtremityListener listener = (ITuio3DExtremityListener)listenerList.elementAt(i);
				if (listener!=null) listener.updateTuio3DExtremity(frameExtremityList.get(sessionId));
			}
		}
	}
	private void callRemoveMethods(){
		for(Long sessionId:removed){
			for (int i=0;i<listenerList.size();i++) {
				ITuio3DExtremityListener listener = (ITuio3DExtremityListener)listenerList.elementAt(i);
//				Debug.writeActivity("2. Removing sessionId: "+sessionId+", ext: "+extremityList.get(sessionId), this);
				if (listener!=null) listener.removeTuio3DExtremity(extremityList.get(sessionId));
			}
		}
	}

	public void acceptBundle(Date time, OSCBundle bundle) {}
	
	/**
	 * Adds the provided TuioListener to the list of registered TUIO event listeners
	 *
	 * @param  listener  the TuioListener to add
	 */
	public void addTuioListener(ITuio3DExtremityListener listener) {
		listenerList.addElement(listener);
	}
	
	/**
	 * Removes the provided TuioListener from the list of registered TUIO event listeners
	 *
	 * @param  listener  the TuioListener to remove
	 */
	public void removeTuioListener(ITuio3DExtremityListener listener) {	
		listenerList.removeElement(listener);
	}
}
