package model_classes;
import java.util.ArrayList;
import java.io.File;

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
	 * The path of the position
	 */
	private Path position_path;
	
	/**
	 * A list with all the pawns that are on this position
	 */
	private ArrayList<Pawn> pawns_on_pos;
	
	/**
	 * True if the position is on or after the checkpoint, False if position is placed before the checkpoint
	 */
	private boolean is_checkpoint;
	
	/**
	 * The image of the position
	 */
	private File position_image;
	
	/**
	 * Constructor. It initializes the assets of a position
	 * @param sc The score of the position
	 * @param pl The path of the position
	 * @param chkp True if the position is on or after the checkpoint, False if position is placed before the checkpoint
	 * @param img The image of the position
	 */
	public Position(int sc, Path pl, boolean chkp, File img){}
	
	/**
	 * Returns the position's score
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns position's score
	 * @return position_score
	 */
	public int get_position_score() {return position_score;}
	
	/**
	 * Returns the position's path
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns position's path
	 * @return position_path
	 */
	public Path get_position_path() {return position_path;}
	
	/**
	 * Adds the pawn p in the list of pawns that are placed on top of this position
	 * <br><b>Pre Conditions : </b> Checks if this pawn is already on the position
	 * <br><b>Post Conditions : </b> Adds the pawn p in the list of pawns that are placed on top of this position
	 * @param p The pawn
	 */
	public void add_pawn_on_pos(Pawn p) {}
	
	/**
	 * Removes the pawn p from the list of pawns that are placed on top of this position
	 * <br><b>Pre Conditions : </b> Checks if this pawn is in the list
	 * <br><b>Post Conditions : </b> Removes the pawn p from the list of pawns that are placed on top of this position
	 * @param p The pawn
	 */
	public void remove_pawn_from_pos(Pawn p) {}
	
	/**
	 * Returns the position's list of pawns
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns position's list of pawns
	 * @return pawns_on_pos
	 */
	public ArrayList<Pawn> get_pawn_on_pos(){return pawns_on_pos;}
	
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
	public File get_position_image() {return position_image;}
	
	/**
	 * Returns the contents of the position
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Return a formatted String
	 * @return formatted String
	 */
	public String toString() {return null;}
}