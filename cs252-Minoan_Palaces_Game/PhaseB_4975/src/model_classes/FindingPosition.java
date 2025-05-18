package model_classes;

import javax.swing.*;

/**
 * Pubblic class "FindingPosition"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class FindingPosition extends Position{
	/**
	 * The state of the finding in this position
	 */
	private boolean in_place;
	
	/**
	 * The finding in this position
	 */
	private Finding finding_in_position;
	
	/**
	 * Returns the finding's state in this position
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns finding's state in this position
	 * @return in_place
	 */
	public boolean get_in_place() {return in_place;}
	
	/**
	 * Returns the finding in this position
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the finding in this position
	 * @return finding_in_position
	 */
	public Finding get_finding_in_position() {return finding_in_position;}
	
	/**
	 * A player grabs the finding from this position
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the finding in this position, sets the finding in this position as null and boolean in_place as false.
	 * @return finding_in_position
	 */
	public Finding grab_finding_in_position() {
		Finding temp = this.finding_in_position;
		this.finding_in_position = null;
		this.in_place = false;
		return temp;
	}
	
	/**
	 * Constructor. It initializes the assets of a position
	 * @param sc The score of the position
	 * @param chkp True if the position is on or after the checkpoint, False if position is placed before the checkpoint
	 * @param img The image of the position
	 * @param f The finding that is places in this position
	 */
	public FindingPosition(int sc, boolean chkp, ImageIcon img, Finding f){
		super(sc,chkp,img);
		this.finding_in_position = f;
		this.in_place = true;
	}
}
