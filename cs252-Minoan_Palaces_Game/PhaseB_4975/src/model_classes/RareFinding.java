package model_classes;

import javax.swing.*;

/**
 * Pubblic class "RareFinding"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class RareFinding extends Finding{
	/**
	 * The owner of this finding
	 */
	private Player owner;
	
	/**
	 * The points this finding gives to the player
	 */
	private int points;
	
	/**
	 * Constructor. It sets the points, the owner, the state, the image and the position of each Rare Finding
	 * @param newPoints The points of the rare finding
	 * @param newImage The image of the finding
	 */
	public RareFinding(int newPoints, ImageIcon newImage){
		super(newImage);
		this.owner = null;
		this.points = newPoints;
	}
	
	/**
	 * Returns the points of this finding
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the points of the finding
	 * @return The points of the finding
	 */
	public int getPoints() {return points;}
	
	/**
	 * Returns the owner of this finding
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the owner of this finding
	 * @return The the owner of this finding
	 */
	public Player getOwner() {return owner;}
	
	/**
	 * Sets a new owner for this Rare finding
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Sets a new owner for this Rare finding
	 * @param newOwner The new owner for this Rare finding
	 */
	public void setOwner(Player newOwner) {this.owner=newOwner;}
}
