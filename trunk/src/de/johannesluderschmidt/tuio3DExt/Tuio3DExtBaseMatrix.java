package de.johannesluderschmidt.tuio3DExt;

public class Tuio3DExtBaseMatrix {
	private Tuio3DExtVector3D axis1;
	private Tuio3DExtVector3D axis2;
	private Tuio3DExtVector3D axis3;
	private Tuio3DExtVector3D centroid;
	
	public Tuio3DExtBaseMatrix(){}
	
	public Tuio3DExtBaseMatrix(Tuio3DExtVector3D axis1, Tuio3DExtVector3D axis2, Tuio3DExtVector3D axis3, Tuio3DExtVector3D centroid){
		this.axis1 = axis1;
		this.axis2 = axis2;
		this.axis3 = axis3;
		this.centroid = centroid;
	}

	public Tuio3DExtVector3D getAxis1() {
		return axis1;
	}

	public void setAxis1(Tuio3DExtVector3D axis1) {
		this.axis1 = axis1;
	}

	public Tuio3DExtVector3D getAxis2() {
		return axis2;
	}

	public void setAxis2(Tuio3DExtVector3D axis2) {
		this.axis2 = axis2;
	}

	public Tuio3DExtVector3D getAxis3() {
		return axis3;
	}

	public void setAxis3(Tuio3DExtVector3D axis3) {
		this.axis3 = axis3;
	}

	public Tuio3DExtVector3D getCentroid() {
		return centroid;
	}

	public void setCentroid(Tuio3DExtVector3D centroid) {
		this.centroid = centroid;
	}
}
