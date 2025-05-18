package model_classes;

import javax.swing.*;

/**
 * Pubblic abstract class "SpecialCard"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public abstract class SpecialCard extends Card{
	/**
	 * Constructor. It sets the owner, image, number and place of the card.
	 * @param newOwner The Player that has the card.
	 * @param newImage The image of the card
	 * @param newPlace The path that this card can be used.
	 */
	public SpecialCard(Player newOwner, ImageIcon newImage, int newPlace){super(newOwner, newImage, newPlace);}
}
