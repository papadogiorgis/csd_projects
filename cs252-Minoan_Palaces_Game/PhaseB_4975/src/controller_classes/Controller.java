package controller_classes;

import java.util.ArrayList;
import java.util.Random;

import model_classes.*;

/**
 * Pubblic class "Controller"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class Controller {
	/**
	 * An int that keeps track of the player's turn. turn=1 for player1 and turn=2 for player2.
	 */
	private int turn;
	
	/**
	 * First player of the game.
	 */
	private Player player1;
	
	/**
	 * Second player of the game.
	 */
	private Player player2;
	
	/**
	 * Game's board.
	 */
	private Board gameBoard;
	
	/**
	 * Counter for the cards left.
	 */
	private int available_cards;

	/**
	 * This integer is initialized as zero when the game starts. When a minotaur card is played on an enemy Theseus
	 * then replay gets value of 1 and the player that used the minotaur card plays again.
	 */
	private int replay;
	
	/**
	 * Counter for the nu,ber of pawns that are on checkpoints.
	 */
	private int pawns_on_chkp;
	
	/**
	 * Controller's constructor. It creates new players, game board and game graphic UI
	 */
	public Controller(){
		Random rand = new Random();
		this.replay = 0;
        this.turn = rand.nextInt(2) + 1;
		this.gameBoard = new Board();
		player1 = new Player();
		player2 = new Player();
		available_cards = 84;
		pawns_on_chkp = 0;
	}
	
	/**
	 * Returns the Player1.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Returns the Player1.
	 * @return player1
	 */
	public Player get_p1() {return this.player1;}
	
	/**
	 * Returns the Player2.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Returns the Player2.
	 * @return player2
	 */
	public Player get_p2() {return this.player2;}
	
	/**
	 * Counter pawns_on_chkp is increased by one.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Counter pawns_on_chkp is increased by one.
	 */
	public void new_pawn_on_chkp() {this.pawns_on_chkp++;}
	
	/**
	 * Returns the counter pawns_on_chkp.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Returns the counter pawns_on_chkp.
	 * @return pawns_on_chkp
	 */
	public int get_pawns_on_chkp() {return this.pawns_on_chkp;}
	
	/**
	 * Changes the players' turn. If turn=1 then it changes its value to 2, and the opposite
	 * <br><b>Pre Conditions : </b> Checks if the previous player has finished playing(if int replay==0)
	 * <br><b>Post Conditions : </b> Changes player's turn
	 */
	public void yourTurn() {
		if(replay==0) {
			if(this.turn == 1) {
				this.turn = 2;
			}else {
				this.turn = 1;
			}
		}else {
			replay--;
		}
	}
	
	/**
	 * Checks if the minotaur card can be played and what the penalty will be for the enemy pawn.
	 * <br><b>Pre Conditions : </b> Checks if the minotaur card can be played(if enemy pawn is at least on second position and before the checkpoint)
	 * <br><b>Post Conditions : </b> Returns the number of positions the enemy pawn must go back.
	 * @param player The number of the player that called this function.
	 * @param anak The number of the palace path.
	 * @return penalty
	 */
	public int check_minotaur(int player, int anak) {
		if(player==1) {
			Pawn[] pawns = player2.get_player_pawns();
			for(int i=0;i<3;i++) {
				if((pawns[i].get_pawn_palace()==anak)&&(pawns[i].get_pawn_position()==1)) {
					pawns[i].change_undercover();
					return 1;
				}else if((pawns[i].get_pawn_palace()==anak)&&(pawns[i].get_pawn_position()>1)&&(pawns[i].get_pawn_position()<6)) {
					pawns[i].change_undercover();
					return 2;
				}
			}
			if(pawns[3].get_pawn_palace()==anak) {
				pawns[3].change_undercover();
				replay++;
				return 0;
			}
		}else {
			Pawn[] pawns = player1.get_player_pawns();
			for(int i=0;i<3;i++) {
				if((pawns[i].get_pawn_palace()==anak)&&(pawns[i].get_pawn_position()==1)) {
					pawns[i].change_undercover();
					return 1;
				}else if((pawns[i].get_pawn_palace()==anak)&&(pawns[i].get_pawn_position()>1)&&(pawns[i].get_pawn_position()<6)) {
					pawns[i].change_undercover();
					return 2;
				}
			}
			if(pawns[3].get_pawn_palace()==anak) {
				pawns[3].change_undercover();
				replay++;
				return 0;
			}
		}
		return -1;
	}
	
	/**
	 * Finds the enemy pawn(the int index) that is placed on path "anak".
	 * <br><b>Pre Conditions : </b> The enemy player has at least one pawn on path "anak".
	 * <br><b>Post Conditions : </b> Returns the int index of the pawn that is placed on path "anak".
	 * @param player The number of the player that called this function.
	 * @param anak The number of the palace path.
	 * @return pawn_index
	 */
	public int find_enemy(int player, int anak) {
		Pawn[] pawns = new Pawn[4];
		if(player==1) {
			pawns = player2.get_player_pawns();
			
		}else {
			pawns = player1.get_player_pawns();
		}
		for(int i=0;i<3;i++) {
			if((pawns[i].get_pawn_palace()==anak)&&(pawns[i].get_pawn_position()>=1)&&(pawns[i].get_pawn_position()<6)) {
				return i;
			}
		}
		if(pawns[3].get_pawn_palace()==anak) {
			return 3;
		}
		return -1;
	}
	
	/**
	 * Finds the index of the card that has been played by this player.
	 * <br><b>Pre Conditions : </b> There is a card in player's hand with the boolean is_active==false.
	 * <br><b>Post Conditions : </b> Returns the index of the card that has been played by this player.
	 * @param player The number of the player that called this function.
	 * @return n The card's index in hand's ArrayList of cards.
	 */
	public int find_lost_card(int player) {
		int n=-1;
		if(player==1) {
			Hand h = gameBoard.get_p1hand();
			ArrayList<Card> list = h.get_hand_list();
			for(Card c: list) {
				n++;
				if(c.getIsActive()==false) {
					return n;
				}
			}
		}else {
			Hand h = gameBoard.get_p2hand();
			ArrayList<Card> list = h.get_hand_list();
			for(Card c: list) {
				n++;
				if(c.getIsActive()==false) {
					return n;
				}
			}
		}
		return n;
	}
	
	/**
	 * Draws a new card from deck and places it in position "cardIndex" of player's hand.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Draws a new card from deck and places it in position "cardIndex" of player's hand.
	 * @param cardIndex The card's index in hand's ArrayList of cards.
	 * @return c The drawn card.
	 */
	public Card newCardInHand(int cardIndex) {
		Hand h;
		if(turn==1) {
			h = gameBoard.get_p1hand();
		}else {
			h = gameBoard.get_p2hand();
		}
		Deck d = gameBoard.get_board_deck();
		Card c = d.drawFromDeck();
		h.addInHand(c, cardIndex);
		yourTurn();
		available_cards--;
		return c;
	}
	
	/**
	 * Returns the counter of the available cards.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Returns the counter of the available cards.
	 * @return available_cards The counter of the available cards.
	 */
	public int get_avlblcards() {return this.available_cards;}
	
	/**
	 * Returns the value of int turn, that keeps track of player's turn.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Returns player's turn.
	 * @return turn
	 */
	public int getTurn() {return this.turn;}
	
	/**
	 * Returns the game's board.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Returns the game's board.
	 * @param gameBoard The game's board.
	 */
	public Board getGameBoard() {return this.gameBoard;}
}