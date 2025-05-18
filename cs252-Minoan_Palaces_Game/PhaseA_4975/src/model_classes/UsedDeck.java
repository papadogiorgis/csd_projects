package model_classes;
import java.util.Stack;

/**
 * Pubblic class "UsedDeck"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class UsedDeck {
	/**
	 * A stack of Cards to keep used cards
	 */
	private Stack<Card> UD;
	
	/**
	 * Constructor.
	 */
	public UsedDeck() {}
	
	/**
	 * This card is put in the stack with the used cards
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> The card uc is pushed in UsedDeck
	 * @param The card that has been used and is placed in the UsedDeck stack
	 */
	public void pushInUsed(Card uc) {}
	
	/**
	 * Gives the stack of the Used Deck
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the stack of the Used Deck
	 * @return The Used Deck
	 */
	public Stack<Card> getUsedDeck(){return UD;}
	
	/**
	 * Returns the contents of the Used deck
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Return a formatted String
	 * @return formatted String
	 */
	public String toString() {return null;}
}