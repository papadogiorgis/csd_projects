package model_classes;
import java.io.File;

/**
 * Pubblic abstract class "Finding"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public abstract class Finding {
	/**
	 * If this has been destroyed by Theseus then this boolean is True, else is False
	 */
	private boolean IsDestroyed;
	
	/**
	 * The image of the Finding
	 */
	private File Image;
	
	/**
	 * The position that this finding is placed
	 */
	private FindingPosition fp;
	
	/**
	 * Constructor. It sets the owner, the state, the image and the position of each Finding
	 * @param newDestroyed The state of the finding
	 * @param newImage The image of the finding
	 * @param newFp The position of the finding
	 */
	public Finding(boolean newDestroyed, File newImage, FindingPosition newFp){}
	
	/**
	 * Returns the contents of the finding
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Return a formatted String
	 * @return formatted String
	 */
	public String toString() {return null;}
	
	/**
	 * Changes the state of the finding.
	 * <br><b>Pre Conditions : </b> Checks if the finding has been destroyed by Theseus
	 * <br><b>Post Conditions : </b> Changes the protected IsDestroyed
	 * @param newIsDestroyed A boolean variable for the new state of the finding
	 */
	public void setIsDestroyed(boolean newIsDestroyed) {}
	
	/**
	 * Returns the state of the finding
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the state of the finding
	 * @return The state of the finding
	 */
	public boolean getIsDestroyed() {return IsDestroyed;}
	
	/**
	 * Returns the image of the finding
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Gives the image of the finding
	 * @return The image of the finding
	 */
	public File getImage() {return Image;}
	
	/**
	 * Returns the position of the finding
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the position of the finding
	 * @return The position of the finding
	 */
	public FindingPosition getFP() {return fp;}
}
