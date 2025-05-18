package model_classes;
import java.io.File;

/**
 * Pubblic class "SnakeGoddessFinding"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class SnakeGoddessFinding extends Finding{
	/**
	 * The owner of this finding
	 */
	private Player owner;
	
	/**
	 * Constructor. It sets the owner, the state, the image and the position of each Snake Goddess Finding
	 * @param newOwner The owner of this finding
	 * @param newDestroyed The state of the finding
	 * @param newImage The image of the finding
	 * @param newFp The position of the finding
	 */
	public SnakeGoddessFinding(Player newOwner, boolean newDestroyed, File newImage, FindingPosition newFp){super(newDestroyed, newImage, newFp);}
	
	/**
	 * Returns the contents of the finding
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Return a formatted String
	 * @return formatted String
	 */
	public String toString() {return null;}

	/**
	 * Returns the owner of this finding
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the owner of this finding
	 * @return The the owner of this finding
	 */
	public Player getOwner() {return owner;}
	
	/**
	 * Sets a new owner for this Snake Goddess finding
	 * <br><b>Pre Conditions : </b> Checks if this finding has already an owner instead of null
	 * <br><b>Post Conditions : </b> Sets a new owner for this Snake Goddess finding
	 * @param newOwner The new owner for this Snake Goddess finding
	 */
	public void setOwner(Player newOwner) {}
}
