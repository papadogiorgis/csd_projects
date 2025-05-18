package model_classes;
import java.io.File;

/**
 * Pubblic class "SimplePosition"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class SimplePosition extends Position{
	/**
	 * Constructor. It initializes the assets of a position
	 * @param sc The score of the position
	 * @param pl The path of the position
	 * @param chkp True if the position is on or after the checkpoint, False if position is placed before the checkpoint
	 * @param img The image of the position
	 */
	public SimplePosition(int sc, Path pl, boolean chkp, File img){super(sc,pl,chkp,img);}
	
	/**
	 * Returns the contents of the position
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Return a formatted String
	 * @return formatted String
	 */
	public String toString() {return null;}
}