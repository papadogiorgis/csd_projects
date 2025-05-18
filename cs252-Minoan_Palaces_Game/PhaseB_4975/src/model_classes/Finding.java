package model_classes;

import javax.swing.*;

/**
 * Pubblic abstract class "Finding"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public abstract class Finding {
	/**
	 * If this has been destroyed by Theseus then this boolean is True, else is False
	 */
	private boolean IsDestroyed;
	
	/**
	 * The image of the Finding
	 */
	private ImageIcon Image;
	
	/**
	 * Constructor. It sets the state and the image of each Finding
	 * @param newImage The image of the finding
	 */
	public Finding(ImageIcon newImage){
		this.IsDestroyed = false;
		this.Image = newImage;
	}
	
	/**
	 * Changes the state of the finding.
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Changes the protected IsDestroyed
	 * @param newIsDestroyed A boolean variable for the new state of the finding
	 */
	public void setIsDestroyed(boolean newIsDestroyed) {this.IsDestroyed=newIsDestroyed;}
	
	/**
	 * Returns the state of the finding
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the state of the finding
	 * @return The state of the finding
	 */
	public boolean getIsDestroyed() {return IsDestroyed;}
	
	/**
	 * Returns the image of the finding
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Gives the image of the finding
	 * @return The image of the finding
	 */
	public ImageIcon getImage() {return Image;}
}
