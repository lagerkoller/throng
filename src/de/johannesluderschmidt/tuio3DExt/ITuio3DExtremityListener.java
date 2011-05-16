package de.johannesluderschmidt.tuio3DExt;


public interface ITuio3DExtremityListener {
	/**
	 * This callback method is invoked by the TuioClient when a new TuioObject is added to the session.   
	 *
	 * @param  ext  the TuioObject reference associated to the addTuioObject event
	 */
	public void addTuio3DExtremity(Tuio3DExtremity ext);

	/**
	 * This callback method is invoked by the TuioClient when an existing TuioObject is updated during the session.   
	 *
	 * @param  ext  the TuioObject reference associated to the updateTuioObject event
	 */
	public void updateTuio3DExtremity(Tuio3DExtremity ext);

	/**
	 * This callback method is invoked by the TuioClient when an existing TuioObject is removed from the session.   
	 *
	 * @param  ext  the TuioObject reference associated to the removeTuioObject event
	 */
	public void removeTuio3DExtremity(Tuio3DExtremity ext);
}
