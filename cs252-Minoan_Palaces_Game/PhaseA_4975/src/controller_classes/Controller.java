package controller_classes;
import java.util.ArrayList;
import model_classes.*;
import view_classes.*;

/**
 * Pubblic class "Controller"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class Controller {
	/**
	 * An int that keeps track of the player's turn. turn=1 for player1 and turn=2 for player2 
	 */
	private int turn;
	
	/**
	 * First player of the game
	 */
	private Player player1;
	
	/**
	 * Second player of the game
	 */
	private Player player2;
	
	/**
	 * Game's board
	 */
	private Board gameBoard;
	
	/**
	 * Game's window
	 */
	private GraphicUI gameWindow;
	
	/**
	 * Controller's constructor. It creates new players, game board and game graphic UI
	 */
	public Controller(){}
	
	/**
	 * Changes the players' turn. If turn=1 then it changes its value to 2, and the opposite
	 * <br><b>Pre Conditions : </b> Checks if the previous player has finished playing
	 * <br><b>Post Conditions : </b> Changes player's turn
	 */
	public void yourTurn() {}
	
	/**
	 * Returns the value of int turn, that keeps track of player's turn
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns player's turn
	 * @return turn
	 */
	public int getTurn() {return this.turn;}
	
	/**
	 * Initializes a player of the game
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Initializes a player of the game
	 * @param p The player that will be initialized
	 */
	public void initializePlayer(Player p) {}
	
	/**
	 * Returns the player that is currently playing
	 * <br><b>Pre Conditions : </b> Checks player's turn
	 * <br><b>Post Conditions : </b> Returns the player that is currently playing
	 * @return player
	 */
	public Player getPlayerPlaying() {return null;}
	
	/**
	 * Initializes the game's board
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Initializes the game's board
	 * @param b The game's board
	 */
	public void initializeGameBoard(Board b) {}
	
	/**
	 * Returns the game's board
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the game's board
	 * @param gameBoard The game's board
	 */
	public Board getGameBoard() {return null;}
	
	/**
	 * Initializes the game's window
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Initializes the game's window
	 * @param gw The game's window
	 */
	public void initializeGameWindow(GraphicUI gw) {}
	
	/**
	 * Updates the game's window
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Updates the game's window
	 * @param gw The game's window
	 */
	public void updateGameWindow(GraphicUI gw) {}
	
	/**
	 * Returns the game's window
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the game's window
	 * @param gameWindow The game's window
	 */
	public GraphicUI getGameWindow() {return null;}
	
	/**
	 * Main checks at the end of each round if the game has finished
	 * <br><b>Pre Conditions : </b> 4 pawns are on checkpoint or deck is empty
	 * <br><b>Post Conditions : </b> Determines if the game has finished
	 * @param b The game's board
	 * @return fin A boolean that determines if the game has finished
	 */
	public boolean isGameFinished(Board b) {return false;}
	
	/**
	 * Finds the winner of the game
	 * <br><b>Pre Conditions : </b> isGameFinished function returned true in main
	 * <br><b>Post Conditions : </b> Returns the winner of the game based on game's rules
	 * @return p The winner of the game
	 */
	public Player findWinner() {return null;}
	
	/**
	 * Announces the winner of the game
	 * <br><b>Pre Conditions : </b> isGameFinished function returned true in main and findWinner returned the winner of the game
	 * <br><b>Post Conditions : </b> Announces the winner of the game
	 * @param p The winner of the game
	 */
	public void announceWinner(Player p) {}
	
	/**
	 * Game's main function. There the game's loop will take place
	 * @param lala Arguments' table
	 */
	public static void main(String[] lala) {}
	
	/**
	 * Returns the contents of the controller
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Return a formatted String
	 * @return formatted String
	 */
	public String toString() {return null;}
}