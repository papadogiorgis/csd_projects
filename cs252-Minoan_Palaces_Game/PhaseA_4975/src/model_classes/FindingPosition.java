package model_classes;
import java.io.File;

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
	 * <br><b>Pre Conditions : </b> Check if there is any finding in this position
	 * <br><b>Post Conditions : </b> Returns the finding in this position
	 * @return finding_in_position
	 */
	public Finding get_finding_in_position() {return finding_in_position;}
	
	/**
	 * A player grabs the finding from this position
	 * <br><b>Pre Conditions : </b> Check if there is any finding in this position and if this finding can be grabbed(if it is a fresco or not)
	 * <br><b>Post Conditions : </b> Returns the finding in this position
	 * @return finding_in_position
	 */
	public Finding grab_finding_in_position() {return finding_in_position;}
	
	/**
	 * Constructor. It initializes the assets of a position
	 * @param sc The score of the position
	 * @param pl The path of the position
	 * @param chkp True if the position is on or after the checkpoint, False if position is placed before the checkpoint
	 * @param img The image of the position
	 * @param f The finding that is places in this position
	 */
	public FindingPosition(int sc, Path pl, boolean chkp, File img, Finding f){super(sc,pl,chkp,img);}
	
	/**
	 * Returns the contents of the position
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Return a formatted String
	 * @return formatted String
	 */
	public String toString() {return null;}
}
