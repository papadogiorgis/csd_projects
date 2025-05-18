package model_classes;

/**
 * Pubblic class "Path"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class Path {
	/**
	 * This int indicates for which palace does this path refer to.
	 * <br>1=Knossos, 2=Malia, 3=Phaistos, 4=Zakros
	 */
	private int anaktoro;
	
	/**
	 * This tables stores the 9 positions of each path
	 */
	private Position[] theseis;
	
	/**
	 * Constructor. It sets the palace int, initializes each position and stores it in the table
	 * @param anakt The int that indicates the palace that this path is leading to
	 */
	public Path(int anakt) {}
	
	/**
	 * Returns the contents of the path
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Return a formatted String
	 * @return formatted String
	 */
	public String toString() {return null;}
	
	/**
	 * Returns the palace int
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the palace int
	 * @return anaktoro The palace int
	 */
	public int get_palace_int() {return anaktoro;}
	
	/**
	 * Stores position newPosition in the position table
	 * <br><b>Pre Conditions : </b> Checks if this position is already been set
	 * <br><b>Post Conditions : </b> Sets position newPosition in the position table
	 * @param newPosition The position that will be stored in the position table
	 */
	public void set_position_in_path(Position newPosition) {}
	
	/**
	 * Returns the path's position table
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the path's position table
	 * @return theseis The path's position table
	 */
	public Position[] get_path_positions() {return theseis;}
}