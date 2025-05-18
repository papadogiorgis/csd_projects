package model_classes;

import javax.swing.*;

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
	 * A boolean that shows if this fresco has been found by Player1
	 */
	private boolean foundby1;
	
	/**
	 * A boolean that shows if this fresco has been found by Player2
	 */
	private boolean foundby2;
	
	/**
	 * Constructor. It sets the points, the state, the image and the position of each fresco
	 * @param newPoints The points of the fresco
	 * @param newImage The image of the finding
	 */
	public FrescoFinding(int newPoints, ImageIcon newImage){
		super(newImage);
		this.points = newPoints;
		this.foundby1 = false;
		this.foundby2 = false;
	}
	
	/**
	 * Sets the boolean that shows if this fresco has been found by Player1 as TRUE
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Sets the that shows if this fresco has been found by Player1 as TRUE
	 */
	public void set_foundby1() {
		this.foundby1 = true;
	}
	
	/**
	 * Sets the boolean that shows if this fresco has been found by Player2 as TRUE
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Sets the that shows if this fresco has been found by Player1 as TRUE
	 */
	public void set_foundby2() {
		this.foundby2 = true;
	}
	
	/**
	 * Returns the boolean that shows if this fresco has been found by Player1
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the boolean that shows if this fresco has been found by Player1
	 * @return The boolean that shows if this fresco has been found by Player1
	 */
	public boolean get_foundby1() {
		return this.foundby1;
	}
	
	/**
	 * Returns the boolean that shows if this fresco has been found by Player2
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the boolean that shows if this fresco has been found by Player2
	 * @return The boolean that shows if this fresco has been found by Player2
	 */
	public boolean get_foundby2() {
		return this.foundby2;
	}
	
	/**
	 * Returns the points of this fresco
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the points of the finding
	 * @return The points of the finding
	 */
	public int getPoints() {return points;}
}
