package model_classes;
import java.util.ArrayList;

/**
 * Pubblic class "Hand"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class Hand {
	/**
	 * The ArrayList with the 8 cards of the Hand
	 */
	private ArrayList<Card> H;
	
	/**
	 * Constructor. It initializes the Hand with 8 cards from the Deck
	 */
	public Hand() {}
	
	/**
	 * Add a card in the hand
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> The drawn card is added to the Hand
	 * @param c The card that will be added in the Hand
	 */
	public void addInHand(Card c) {}
	
	/**
	 * If possible, remove a card from the Hand
	 * <br><b>Pre Conditions : </b> Checks if this card exists in the Hand
	 * <br><b>Post Conditions : </b> Removes the card from the Hand
	 * @param rc The card that must be removed
	 * @return True if the card has been removed, False if the Hand is empty
	 */
	public boolean removeFromHand(Card rc) {return false;}
	
	/**
	 * Returns the contents of the hand
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Return a formatted String
	 * @return formatted String
	 */
	public String toString() {return null;}
}