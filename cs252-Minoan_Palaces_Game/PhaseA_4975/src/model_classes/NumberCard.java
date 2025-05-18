package model_classes;
import java.io.File;

/**
 * Pubblic class "NumberCard"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class NumberCard extends Card{
	/**
	 * The number of the card
	 */
	private int NoC;
	
	/**
	 * Constructor. It sets the owner, image, number and place of the card.
	 * @param newOwner The Player that has the card.
	 * @param newImage The image of the card
	 * @param newNoC   The number of the card
	 * @param newPlace The path that this card can be used.
	 */
	public NumberCard(Player newOwner, File newImage, int newNoC, Path newPlace){super(newOwner, newImage, newPlace);}
	
	/**
	 * Overrides the string with the contents of the card
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Return a formatted String
	 * @return formatted String
	 */
	public String toString() {return null;}
	
	/**
	 * Returns the number of the card
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the number of the card
	 * @return The number of the card
	 */
	public int getNoC() {return NoC;}
	
	/**
	 * Checks if this card can be played and moves the pawn
	 * <br><b>Pre Conditions : </b> Checks if this card can be played
	 * <br><b>Post Conditions : </b> Moves the Pawn to the next position, makes the card inactive, removes it from the Hand, gets in UsedDeck
	 * @param p The Pawn of the Owner of the card
	 * @param ud The Deck with the cards that have been played
	 */
	public void useCard(Pawn p, UsedDeck ud) {}
}