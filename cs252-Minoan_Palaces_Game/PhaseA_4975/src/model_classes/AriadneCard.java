package model_classes;
import java.io.File;

/**
 * Pubblic class "AriadneCard"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class AriadneCard extends SpecialCard{
	/**
	 * Constructor. It sets the owner, image, number and place of the card.
	 * @param newOwner The Player that has the card.
	 * @param newImage The image of the card
	 * @param newPlace The path that this card can be used.
	 */
	public AriadneCard(Player newOwner, File newImage, Path newPlace){super(newOwner, newImage, newPlace);}
	
	/**
	 * Overrides the string with the contents of the card
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Return a formatted String
	 * @return formatted String
	 */
	public String toString() {return null;}
	
	/**
	 * Checks if this card can be played and moves the pawn
	 * <br><b>Pre Conditions : </b> Checks if this card can be played
	 * <br><b>Post Conditions : </b> Moves the Pawn 2 steps ahead, makes the card inactive, removes it from the Hand, gets in UsedDeck
	 * @param p The Pawn of the Owner of the card
	 * @param ud The Deck with the cards that have been played
	 */
	public void useCard(Pawn p, UsedDeck ud) {}
}