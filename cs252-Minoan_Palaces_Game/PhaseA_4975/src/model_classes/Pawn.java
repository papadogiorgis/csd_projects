package model_classes;
import java.io.File;

/**
 * Pubblic class "Pawn"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class Pawn{
	/**
	 * The owner of the pawn
	 */
	private Player pawn_owner;
	
	/**
	 * True if the pawn is Theseus, False if it is just an archaeologist
	 */
	private boolean is_theseus;
	
	/**
	 * The position that this pawn is placed on
	 */
	private Position pawn_position;
	
	/**
	 * The image of the pawn
	 */
	private File pawn_image;
	
	/**
	 * Constructor. It sets the owner and the image of the pawn. It also sets if it is Theseus or not
	 * @param newOwner The owner of the pawn
	 * @param theseus_or_not True if it is Theseus, False if not
	 * @param newImage The image of the pawn
	 */
	public Pawn(Player newOwner, boolean theseus_or_not, File newImage) {}
	
	/**
	 * Returns the contents of the pawn
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Return a formatted String
	 * @return formatted String
	 */
	public String toString() {return null;}
	
	/**
	 * Sets a new position for this pawn
	 * <br><b>Pre Conditions : </b> Checks if the pawn can move there
	 * <br><b>Post Conditions : </b> Changes the pawn's position to newPosition
	 * @param newPosition The pawn's new position
	 */
	public void set_pawn_position(Position newPosition) {}
	
	/**
	 * Returns the owner of the pawn
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns pawn_owner
	 * @return pawn_owner
	 */
	public Player getOwner() {return pawn_owner;}
	
	/**
	 * Returns True if this pawn is Theseus, False if not
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns is_theseus
	 * @return is_theseus
	 */
	public boolean get_is_theseus() {return is_theseus;}
	
	/**
	 * Returns the pawn's position
	 * <br><b>Pre Conditions : </b> Checks if the pawn has started exploring a path
	 * <br><b>Post Conditions : </b> Returns pawn_position
	 * @return pawn_position
	 */
	public Position get_pawn_position() {return pawn_position;}
	
	/**
	 * Returns the pawn's image
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns pawn_image
	 * @return pawn_image
	 */
	public File get_pawn_image() {return pawn_image;}
}
