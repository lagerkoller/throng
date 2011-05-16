package de.johannesluderschmidt.tuio3DExt;

import org.tuio.java.TuioPoint;
import org.tuio.java.TuioTime;

public class Tuio3DExtremity extends TuioPoint {
	protected long sessionId;
	protected long personId;
	
	protected Tuio3DExtBaseMatrix baseMatrix;

	protected String src;
	protected long fseq;
	
	public Tuio3DExtremity(){}
	public Tuio3DExtremity(TuioTime ttime, long sessionId, long personId, Tuio3DExtBaseMatrix baseMatrix, String src, long fseq) {
		super(ttime,baseMatrix.getCentroid().getX(), baseMatrix.getCentroid().getY());
		this.sessionId = sessionId;
		this.personId = personId;
		
		this.baseMatrix = baseMatrix;
		
		this.src = src;
		this.fseq = fseq;
	}
	public void update(Tuio3DExtBaseMatrix baseMatrix, long fseq){
		this.baseMatrix = baseMatrix;
		this.fseq = fseq;
	}
	public long getSessionId() {
		return sessionId;
	}

	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}
	
	public Tuio3DExtBaseMatrix getBaseMatrix() {
		return baseMatrix;
	}

	public void setBaseMatrix(Tuio3DExtBaseMatrix baseMatrix) {
		this.baseMatrix = baseMatrix;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public long getFseq() {
		return fseq;
	}

	public void setFseq(long fseq) {
		this.fseq = fseq;
	}
	@Override
	public String toString() {
		return "Tuio3DExtremity [sessionId=" + sessionId + ", personId="
				+ personId + ", baseMatrix=" + baseMatrix + ", src=" + src
				+ ", fseq=" + fseq + ", currentTime=" + currentTime + "]";
	}
	
	
}
