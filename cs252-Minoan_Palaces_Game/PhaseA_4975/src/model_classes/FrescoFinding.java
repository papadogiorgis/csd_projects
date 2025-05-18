package model_classes;
import java.io.File;

/**
 * Pubblic class "FrescoFinding"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class FrescoFinding extends Finding{
	/**
	 * The points that a pic from this fresco gives to each player
	 */
	private int points;
	
	/**
	 * Constructor. It sets the points, the state, the image and the position of each fresco
	 * @param newPoints The points of the fresco
	 * @param newDestroyed The state of the finding
	 * @param newImage The image of the finding
	 * @param newFp The position of the finding
	 */
	public FrescoFinding(int newPoints, boolean newDestroyed, File newImage, FindingPosition newFp){super(newDestroyed, newImage, newFp);}
	
	/**
	 * Returns the contents of the finding
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Return a formatted String
	 * @return formatted String
	 */
	public String toString() {return null;}
	
	/**
	 * Returns the points of this fresco
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the points of the finding
	 * @return The points of the finding
	 */
	public int getPoints() {return points;}
}
