package model_classes;

import java.util.*;

/**
 * Pubblic class "Hand"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class Hand {
	/**
	 * The ArrayList with the 8 cards of the Hand
	 */
	private ArrayList<Card> hand;
	
	/**
	 * Constructor. It initializes the Hand with 8 cards from the Deck
	 */
	public Hand(Deck D) {
		hand = new ArrayList<>();
		int i;
		for(i=0;i<8;i++) {
			hand.add(D.drawFromDeck());
		}
	}
	
	/**
	 * Replaces a card with a new one in deck.
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> The drawn card is added to the Hand in i-th place of the array.
	 * @param c The card that will be added in the Hand.
	 * @param i The index for hand's array.
	 */
	public void addInHand(Card c, int i) {
		hand.set(i,c);
	}
	
	/**
	 * If possible, remove a card from the Hand
	 * <br><b>Pre Conditions : </b> Checks if this card exists in the Hand
	 * <br><b>Post Conditions : </b> Removes the card from the Hand
	 * @param rc The card that must be removed
	 * @return rbool True if the card has been removed, False if the Hand is empty
	 */
	public boolean removeFromHand(Card rc) {
		boolean rbool = false;
		int i;
		for(i=0;i<8;i++) {
			if(rc == hand.get(i)) {
				rbool = true;
				hand.remove(i);
			}
		}
		return rbool;
	}
	
	/**
	 * Returns the n-th card of the hand's array of cards.
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the n-th card of the hand's array of cards.
	 * @param n The hand's index.
	 * @return c The n-th card of the hand's array of cards.
	 */
	public Card get_cardfromhand(int n) {
		return hand.get(n);
	}
	
	/**
	 * Returns the hand's array of cards.
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the hand's array of cards.
	 * @return hand The hand's array of cards.
	 */
	public ArrayList<Card> get_hand_list(){return this.hand;}
}