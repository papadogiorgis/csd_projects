package model_classes;
import java.util.ArrayList;

/**
 * Pubblic class "Player"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class Player {
	/**
	 * The player's score
	 */
	private int player_score;
	
	/**
	 * The list with the player's findings
	 */
	private ArrayList<Finding> list_findings;
	
	/**
	 * Player's hand of cards
	 */
	private Hand player_hand;
	
	/**
	 * A list with the last cards played
	 */
	private ArrayList<Card> last_cards_played;
	
	/**
	 * A table with the player's pawns
	 */
	private Pawn[] player_pawns;
	
	/**
	 * A table with the player's pictures of frescos
	 */
	private FrescoFinding[] player_pics;
	
	/**
	 * A counter of the Snake Goddess findings that the player has acquired
	 */
	private int player_snakegoddess_count;
	
	/**
	 * Constructor. It initializes every asset of the player
	 */
	public Player(){}
	
	/**
	 * Sets a new score for this player
	 * <br><b>Pre Conditions : </b> Checks if the score can be updated
	 * <br><b>Post Conditions : </b> Changes the score of this player
	 * @param newScore The new value of the player's score
	 */
	public void set_player_score(int newScore) {}
	
	/**
	 * Returns the player's score
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns Player's score
	 * @return player_score
	 */
	public int get_player_score() {return player_score;}
	
	/**
	 * Adds a new finding in the list of the player's findings
	 * <br><b>Pre Conditions : </b> Checks if this finding is already in the list
	 * <br><b>Post Conditions : </b> Adds the finding in the list
	 * @param newFinding The new finding that is added in the list
	 */
	public void add_new_finding(Finding newFinding) {}
	
	/**
	 * Returns the player's list of findings
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns Player's list of findings
	 * @return list_findings
	 */
	public ArrayList<Finding> get_list_findings(){return list_findings;}
	
	/**
	 * Player draws a card from Deck
	 * <br><b>Pre Conditions : </b> Checks if a card can be drawn from deck
	 * <br><b>Post Conditions : </b> Calls function drawFromDeck() from Deck and adds this card in Hand with addInHand() function
	 */
	public void player_draws_cards() {}
	
	/**
	 * Gives the player's hand of cards
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the array of the current hand of the player
	 * @return player_hand The player's hand
	 */
	public Hand getHand() {return player_hand;}
	
	/**
	 * Adds a new card in the list of the player's last played cards
	 * <br><b>Pre Conditions : </b> Checks if this card is already in the list
	 * <br><b>Post Conditions : </b> Adds the card in the list
	 * @param c The new card that is added in the list
	 */
	public void update_last_cards_played(Card c) {}
	
	/**
	 * Returns the player's list of last played cards
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns Player's list of last played cards
	 * @return last_cards_played
	 */
	public ArrayList<Card> get_last_cards_played(){return last_cards_played;}
	
	/**
	 * Returns the player's table of pawns
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns Player's table of pawns
	 * @return player_pawns
	 */
	public Pawn[] get_player_pawns() {return player_pawns;}
	
	/**
	 * Updates the table with the player's pictures with a new pic
	 * <br><b>Pre Conditions : </b> Checks if the fresco has already been found
	 * <br><b>Post Conditions : </b> Updates the pic in the player's table of pics from "unfound" to "found" state
	 * @param fresco_pic
	 */
	public void update_player_pics(FrescoFinding fresco_pic) {}
	
	/**
	 * Returns the player's table of pics
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns Player's table of pics
	 * @return player_pics
	 */
	public FrescoFinding[] get_player_pics() {return player_pics;}
	
	/**
	 * Adds one in the counter of the Snake Goddess findings
	 * <br><b>Pre Conditions : </b> Checks if the player_snakegoddess_count has a valid value
	 * <br><b>Post Conditions : </b> Adds one in the counter of the Snake Goddess findings
	 */
	public void update_player_snakegoddess_count() {}
	
	/**
	 * Returns the Snake Goddess findings' counter
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the Snake Goddess findings' counter
	 * @return player_snakegoddess_count
	 */
	public int get_player_snakegoddess_count() {return player_snakegoddess_count;}
	
	/**
	 * Returns the contents of the player
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Return a formatted String
	 * @return formatted String
	 */
	public String toString() {return null;}
}