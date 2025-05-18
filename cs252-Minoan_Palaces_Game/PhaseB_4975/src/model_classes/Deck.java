package model_classes;

import java.util.*;
import javax.swing.ImageIcon;

/**
 * Pubblic class "Deck"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class Deck {
		/**
		 * The stack with the 100 cards of the game
		 */
		private Stack<Card> deck;
		
		/**
		 * Constructor. It initializes every card and places it in the stack
		 */
		public Deck() {
			deck = new Stack<>();
			int i,j;
			ArrayList<Card> list = new ArrayList<>();
			ImageIcon originalIcon;
			String pal = "";
			for(j=1;j<=4;j++) {
		    	if(j==1) {
		    		pal = "knossos";
		    	}else if(j==2) {
		    		pal = "malia";
		    	}else if(j==3) {
		    		pal = "phaistos";
		    	}else {
		    		pal = "zakros";
		    	}
				for(i=1;i<=10;i++) {
					originalIcon = new ImageIcon("resources/images/cards/"+pal+Integer.toString(i)+".jpg");
					list.add(new NumberCard(null, originalIcon, i, j));
					list.add(new NumberCard(null, originalIcon, i, j));
				}
				for(i=0;i<3;i++) {
					originalIcon = new ImageIcon("resources/images/cards/"+pal+"Ari.jpg");
					list.add(new AriadneCard(null, originalIcon, j));
				}
				for(i=0;i<2;i++) {
					originalIcon = new ImageIcon("resources/images/cards/"+pal+"Min.jpg");
					list.add(new MinotaurCard(null, originalIcon, j));
				}
			}
			Collections.shuffle(list);
			for (Card c : list) {
	        	deck.push(c);
	        }
		}
		
		/**
		 * Gives the Deck.
		 * <br><b>Pre Conditions : </b> None
		 * <br><b>Post Conditions : </b> Returns the Deck.
		 * @return deck The Deck.
		 */
		public Stack<Card> getDeck(){return deck;}
		
		/**
		 * Draw a card from the Deck.
		 * <br><b>Pre Conditions : </b> None.
		 * <br><b>Post Conditions : </b> Removes the card from the Hand.
		 * @return c The card on top of the stack of cards in Deck.
		 */
		public Card drawFromDeck() {return deck.pop();}
}