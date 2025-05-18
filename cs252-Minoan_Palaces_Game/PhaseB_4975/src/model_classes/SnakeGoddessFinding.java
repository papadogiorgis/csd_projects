package model_classes;

import javax.swing.*;

/**
 * Pubblic class "SnakeGoddessFinding"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class SnakeGoddessFinding extends Finding{
	/**
	 * The owner of this finding
	 */
	private Player owner;
	
	/**
	 * Constructor. It sets the owner, the state, the image and the position of each Snake Goddess Finding
	 * @param newImage The image of the finding
	 */
	public SnakeGoddessFinding(ImageIcon newImage){
		super(newImage);
		this.owner = null;
	}

	/**
	 * Returns the owner of this finding
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the owner of this finding
	 * @return The the owner of this finding
	 */
	public Player getOwner() {return owner;}
	
	/**
	 * Sets a new owner for this Snake Goddess finding
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Sets a new owner for this Snake Goddess finding
	 * @param newOwner The new owner for this Snake Goddess finding
	 */
	public void setOwner(Player newOwner) {this.owner=newOwner;}
}
