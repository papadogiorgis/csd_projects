package model_classes;

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
	 * The position that this pawn is placed on the path as an int.
	 */
	private int pawn_position;
	
	/**
	 * The path that this pawn is placed on as an int. (1=knossos, 2=malia, 3=phaistos, 4=kakros)
	 */
	private int pawn_palace;
	
	/**
	 * The position that this pawn is placed on the path as a Position object.
	 */
	private Position pawn_pos;
	
	/**
	 * The minimum value that the cards should have in order to use that pawn.
	 */
	private int min_value;
	
	/**
	 * Boolean that shows pawn's state. True if the pawn is undercover, False if its not.
	 */
	private boolean is_undercover;
	
	/**
	 * Constructor. It sets the owner of the pawn. It also sets if it is Theseus or not
	 * @param newOwner The owner of the pawn
	 * @param theseus_or_not True if it is Theseus, False if not
	 */
	public Pawn(Player newOwner, boolean theseus_or_not) {
		this.pawn_owner = newOwner;
		this.is_theseus = theseus_or_not;
		this.pawn_position = -1;
		this.pawn_palace = -1;
		this.min_value = -1;
		this.is_undercover = true;
		this.pawn_pos = null;
	}
	
	/**
	 * Sets new minimum value for the pawn.
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Sets new minimum value for the pawn.
	 * @param newMinvalue The new minimum value for the pawn.
	 */
	public void set_minvalue(int newMinvalue) {this.min_value = newMinvalue;}
	
	/**
	 * Returns the minimum value of the pawn.
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the minimum value of the pawn.
	 * @return min_value The minimum value of the pawn.
	 */
	public int get_minvalue() {return this.min_value;}
	
	/**
	 * Changes the pawn's undercover state from true to false.
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Changes the pawn's undercover state from true to false.
	 */
	public void change_undercover() {this.is_undercover = false;}
	
	/**
	 * Returns the pawn's undercover state.
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the pawn's undercover state.
	 * @return is_undercover The pawn's undercover state.
	 */
	public boolean get_isundercover() {return this.is_undercover;}
	
	/**
	 * Sets a new position(int) for this pawn.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Changes the pawn's position to newPosition(int).
	 * @param newPosition The pawn's new position(int).
	 */
	public void set_pawn_position(int newPosition) {
		this.pawn_position = newPosition;
	}
	
	/**
	 * Returns the owner of the pawn.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Returns pawn_owner.
	 * @return pawn_owner The owner of this pawn.
	 */
	public Player getOwner() {return pawn_owner;}
	
	/**
	 * Returns True if this pawn is Theseus, False if not.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Returns is_theseus.
	 * @return is_theseus
	 */
	public boolean get_is_theseus() {return is_theseus;}
	
	/**
	 * Returns the pawn's position(int).
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Returns pawn_position(int).
	 * @return pawn_position The pawn's new position(int).
	 */
	public int get_pawn_position() {return this.pawn_position;}
	
	/**
	 * Sets a new palace int, depending on which path the pawn follows.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Sets a new palace int, depending on which path the pawn follows.
	 * @param newPalace The new palace int, depending on which path the pawn follows.
	 */
	public void set_pawn_palace(int newPalace) {
		this.pawn_palace = newPalace;
	}
	
	/**
	 * Returns the palace int.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Returns the palace int.
	 * @return pawn_palace The palace int.
	 */
	public int get_pawn_palace() {return this.pawn_palace;}
	
	/**
	 * Sets a new position(object Position) for this pawn.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Changes the pawn's position to newPosition(object Position).
	 * @param newPosition The pawn's new position(object Position).
	 */
	public void set_pawn_pos(Position p) {this.pawn_pos = p;}
	
	/**
	 * Returns the pawn's position(object Position).
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Returns pawn_position(object Position).
	 * @return pawn_position The pawn's new position(object Position).
	 */
	public Position get_pawn_pos() {return this.pawn_pos;}
}
