package model_classes;

import javax.swing.*;

/**
 * Pubblic abstract class "Position"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public abstract class Position {
	/**
	 * The score of the position
	 */
	private int position_score;
	
	/**
	 * True if the position is on or after the checkpoint, False if position is placed before the checkpoint
	 */
	private boolean is_checkpoint;
	
	/**
	 * The image of the position
	 */
	private ImageIcon position_image;
	
	/**
	 * Constructor. It initializes the assets of a position
	 * @param sc The score of the position
	 * @param chkp True if the position is on or after the checkpoint, False if position is placed before the checkpoint
	 * @param img The image of the position
	 */
	public Position(int sc, boolean chkp, ImageIcon img){
		this.position_score = sc;
		this.is_checkpoint = chkp;
		this.position_image = img;
	}
	
	/**
	 * Returns the position's score
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns position's score
	 * @return position_score
	 */
	public int get_position_score() {return position_score;}
	
	/**
	 * Returns the position's checkpoint state
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns position's checkpoint state
	 * @return is_checkpoint
	 */
	public boolean get_is_checkpoint() {return is_checkpoint;}
	
	/**
	 * Returns the position's image
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns position's image
	 * @return position_image
	 */
	public ImageIcon get_position_image() {return position_image;}
}