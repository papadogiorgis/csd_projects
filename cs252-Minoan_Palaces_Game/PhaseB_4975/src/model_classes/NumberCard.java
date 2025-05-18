package model_classes;

import javax.swing.*;

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
	public NumberCard(Player newOwner, ImageIcon newImage, int newNoC, int newPlace){
		super(newOwner, newImage, newPlace);
		this.NoC = newNoC;
	}
	
	/**
	 * Returns the number of the card
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the number of the card
	 * @return The number of the card
	 */
	public int getNoC() {return NoC;}
}