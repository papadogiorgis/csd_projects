package model_classes;
import java.io.File;

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
	private File Image;
	
	/**
	 * The path that this card can be used
	 */
	private Path place;
	
	/**
	 * Constructor. It sets the owner, image, number and place of the card.
	 * @param newOwner The Player that has the card.
	 * @param newImage The image of the card
	 * @param newPlace The path that this card can be used.
	 */
	public Card(Player newOwner, File newImage, Path newPlace){}
	
	/**
	 * Returns the contents of the card
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Return a formatted String
	 * @return formatted String
	 */
	public String toString() {return null;}
	
	/**
	 * Sets a new owner of this card
	 * <br><b>Pre Conditions : </b> Checks if the card is active
	 * <br><b>Post Conditions : </b> Changes the owner of the card from null to newOwner
	 * @param newOwner The player that owns this card
	 */
	public void setOwner(Player newOwner) {}
	
	/**
	 * Returns the owner of the card
	 * <br><b>Pre Conditions : </b> Card has an owner
	 * <br><b>Post Conditions : </b> Returns Owner
	 * @return Owner
	 */
	public Player getOwner() {return owner;}
	
	/**
	 * Changes the state of the card. True if its active, False if it has been played before
	 * <br><b>Pre Conditions : </b> Checks if it has been played before
	 * <br><b>Post Conditions : </b> Changes the protected IsActive
	 * @param newIsActive A boolean variable for the new state of the card
	 */
	public void setIsActive(boolean newIsActive) {}
	
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
	public Path getPlace() {return place;}
	
	/**
	 * Returns the image of the card
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Gives the image of the card
	 * @return The image of the card
	 */
	public File getImage() {return Image;}
	
	/**
	 * Checks if this card can be played and moves the pawn
	 * <br><b>Pre Conditions : </b> Checks if this card can be played
	 * <br><b>Post Conditions : </b> Moves the Pawn to the correct position, makes the card inactive, removes it from the Hand, gets in UsedDeck
	 * @param p The Pawn of the Owner of the card
	 * @param ud The Deck with the cards that have been played
	 */
	public void useCard(Pawn p, UsedDeck ud) {}
}
