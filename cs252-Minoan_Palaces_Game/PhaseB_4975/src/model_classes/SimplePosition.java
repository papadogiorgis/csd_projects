package model_classes;

import javax.swing.*;

/**
 * Pubblic class "SimplePosition"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class SimplePosition extends Position{
	/**
	 * Constructor. It initializes the assets of a position
	 * @param sc The score of the position
	 * @param chkp True if the position is on or after the checkpoint, False if position is placed before the checkpoint
	 * @param img The image of the position
	 */
	public SimplePosition(int sc, boolean chkp, ImageIcon img){super(sc,chkp,img);}
}