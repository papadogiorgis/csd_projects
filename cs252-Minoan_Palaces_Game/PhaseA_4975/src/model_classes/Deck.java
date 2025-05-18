package model_classes;
import java.util.Stack;

/**
 * Pubblic class "Deck"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class Deck {
		/**
		 * The stack with the 100 cards of the game
		 */
		private Stack<Card> D;
		
		/**
		 * Constructor. It initializes every card and places it in the stack
		 */
		public Deck() {}
		
		/**
		 * Gives the Deck
		 * <br><b>Pre Conditions : </b> None
		 * <br><b>Post Conditions : </b> Returns the Deck
		 * @return The Deck
		 */
		public Stack<Card> getDeck(){return D;}
		
		/**
		 * If possible, draw a card from the Deck
		 * <br><b>Pre Conditions : </b> Checks if this card exists in the Hand
		 * <br><b>Post Conditions : </b> Removes the card from the Hand
		 * @return The card on top of the stack of cards in Deck
		 */
		public Card drawFromDeck() {return null;}
		
		/**
		 * Returns the contents of the deck
		 * <br><b>Pre Conditions : </b> None
		 * <br><b>Post Conditions : </b> Return a formatted String
		 * @return formatted String
		 */
		public String toString() {return null;}
}