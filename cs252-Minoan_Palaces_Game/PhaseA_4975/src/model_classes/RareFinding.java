package model_classes;
import java.io.File;

/**
 * Pubblic class "RareFinding"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class RareFinding extends Finding{
	/**
	 * The owner of this finding
	 */
	private Player owner;
	
	/**
	 * The points this finding gives to the player
	 */
	private int points;
	
	/**
	 * Constructor. It sets the points, the owner, the state, the image and the position of each Rare Finding
	 * @param newPoints The points of the rare finding
	 * @param newOwner The owner of this finding
	 * @param newDestroyed The state of the finding
	 * @param newImage The image of the finding
	 * @param newFp The position of the finding
	 */
	public RareFinding(int newPoints, Player newOwner, boolean newDestroyed, File newImage, FindingPosition newFp){super(newDestroyed, newImage, newFp);}
	
	/**
	 * Returns the contents of the finding
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Return a formatted String
	 * @return formatted String
	 */
	public String toString() {return null;}
	
	/**
	 * Returns the points of this finding
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the points of the finding
	 * @return The points of the finding
	 */
	public int getPoints() {return points;}
	
	/**
	 * Returns the owner of this finding
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the owner of this finding
	 * @return The the owner of this finding
	 */
	public Player getOwner() {return owner;}
	
	/**
	 * Sets a new owner for this Rare finding
	 * <br><b>Pre Conditions : </b> Checks if this finding has already an owner instead of null
	 * <br><b>Post Conditions : </b> Sets a new owner for this Rare finding
	 * @param newOwner The new owner for this Rare finding
	 */
	public void setOwner(Player newOwner) {}
}
