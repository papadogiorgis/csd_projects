package model_classes;
import java.io.File;

/**
 * Pubblic class "MinotaurCard"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class MinotaurCard extends SpecialCard{
	/**
	 * Constructor. It sets the owner, image, number and place of the card.
	 * @param newOwner The Player that has the card.
	 * @param newImage The image of the card
	 * @param newPlace The path that this card can be used.
	 */
	public MinotaurCard(Player newOwner, File newImage, Path newPlace){super(newOwner, newImage, newPlace);}
	
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
	 * <br><b>Post Conditions : </b> Moves an enemy's pawn 2 steps back(if its not Theseus), makes the card inactive, removes it from the Hand, gets in UsedDeck
	 * @param enemypawn The enemy's pawn that must move back
	 * @param ud The Deck with the cards that have been played
	 */
	public void useCard(Pawn enemypawn, UsedDeck ud) {}
}