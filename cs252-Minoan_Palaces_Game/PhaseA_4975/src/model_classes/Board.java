package model_classes;

/**
 * Pubblic class "Board"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class Board {
	/**
	 * The board's deck of unused cards
	 */
	private Deck trapoula;
	
	/**
	 * A table with the 4 paths
	 */
	private Path[] monopatia;
	
	/**
	 * Constructor. It initializes the assets of the board.
	 */
	public Board() {}
	
	/**
	 * Returns the contents of the board
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Return a formatted String
	 * @return formatted String
	 */
	public String toString() {return null;}
	
	/**
	 * Returns the board's deck of unused cards
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the board's deck of unused cards
	 * @return trapoula The board's deck of unused cards
	 */
	public Deck get_board_deck() {return trapoula;}
	
	/**
	 * Returns the board's table with the four paths
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the board's table with the four paths
	 * @return monopatia The board's table with the four paths
	 */
	public Path[] get_board_path() {return monopatia;}
}