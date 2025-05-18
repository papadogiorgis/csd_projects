package model_classes;

import javax.swing.*;

/**
 * Pubblic abstract class "Card"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public abstract class Card {
	/**
	 * The owner of this card. It can be either "Player" or NULL if its not used yet.
	 */
	private Player owner;
	
	/**
	 * If this Card has been played before then the boolean IsActive is FALSE, else it is TRUE.
	 */
	private boolean IsActive;
	
	/**
	 * The image of the Card.
	 */
	private ImageIcon Image;
	
	/**
	 * The path that this card can be used
	 */
	private int place;
	
	/**
	 * Constructor. It sets the owner, image and place of the card.
	 * @param newOwner The Player that has the card.
	 * @param newImage The image of the card
	 * @param newPlace The path that this card can be used.
	 */
	public Card(Player newOwner, ImageIcon newImage, int newPlace){
		this.owner = newOwner;
		this.IsActive = true;
		this.Image = newImage;
		this.place = newPlace;
	}
	
	/**
	 * Sets a new owner of this card
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Changes the owner of the card from null to newOwner
	 * @param newOwner The player that owns this card
	 */
	public void setOwner(Player newOwner) {this.owner=newOwner;}
	
	/**
	 * Returns the owner of the card
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns Owner
	 * @return Owner
	 */
	public Player getOwner() {return owner;}
	
	/**
	 * Changes the state of the card. True if its active, False if it has been played before
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Changes the protected IsActive
	 * @param newIsActive A boolean variable for the new state of the card
	 */
	public void setIsActive(boolean newIsActive) {this.IsActive=newIsActive;}
	
	/**
	 * Returns the state of the card
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the state of the card
	 * @return The state of the card
	 */
	public boolean getIsActive() {return IsActive;}
	
	/**
	 * Returns the path of the card
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the path of the card
	 * @return The path of the card
	 */
	public int getPlace() {return place;}
	
	/**
	 * Returns the image of the card
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Gives the image of the card
	 * @return The image of the card
	 */
	public ImageIcon getImage() {return Image;}
}
