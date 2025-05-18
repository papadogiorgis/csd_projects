package model_classes;
import java.util.ArrayList;

/**
 * Pubblic class "Player"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class Player {
	/**
	 * The player's score.
	 */
	private int player_score;
	
	/**
	 * The list with the player's rare findings.
	 */
	private ArrayList<RareFinding> rfindings_list;
	
	/**
	 * A table with the player's pawns.
	 */
	private Pawn[] player_pawns;
	
	/**
	 * A table with the player's pictures of frescos.
	 */
	private ArrayList<FrescoFinding> player_pics_list;
	
	/**
	 * A counter of the Snake Goddess figurines that the player has acquired.
	 */
	private int player_snakegoddess_count;
	
	/**
	 * A counter of how many findings can this player's Theseus pawn destroy.
	 */
	private int destruction_counter;
	
	/**
	 * Constructor. It initializes every asset of the player
	 */
	public Player(){
		this.player_score = 0;
		this.rfindings_list = new ArrayList<>();
		this.player_pics_list = new ArrayList<>();
		this.player_snakegoddess_count =0;
		player_pawns = new Pawn[4];
		for(int i=0;i<3;i++) {
			player_pawns[i] = new Pawn(this , false);
		}
		player_pawns[3] = new Pawn(this, true);
		this.destruction_counter = 3;
	}
	
	/**
	 * Returns the player's destruction_counter.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Returns the player's destruction_counter.
	 * @return destruction_counter The player's destruction_counter.
	 */
	public int get_destruction_counter() {return this.destruction_counter;}
	
	/**
	 * Updates the player's destruction_counter.
	 * <br><b>Pre Conditions : </b> Checks if the counter has a positive value.
	 * <br><b>Post Conditions : </b> Subtracts 1 from the player's destruction_counter.
	 */
	public void update_destruction_counter() {
		if(this.destruction_counter>0) {
			int temp = this.destruction_counter;
			temp--;
			this.destruction_counter = temp;
		}
	}
	
	/**
	 * Uodates the player's score.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Adds the parameter newScore to the existing score.
	 * @param newScore The value that will be added to the player's score.
	 */
	public void update_player_score(int newScore) {
		int temp = this.player_score;
		temp += newScore;
		this.player_score = temp;
	}
	
	/**
	 * Sets a new score for this player.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Changes the score of this player.
	 * @param newScore The new value of the player's score.
	 */
	public void set_player_score(int newScore) {
		this.player_score = newScore;
	}
	
	/**
	 * Returns the player's score.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Returns Player's score.
	 * @return player_score
	 */
	public int get_player_score() {return player_score;}
	
	/**
	 * Returns the player's list of rare findings.
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns Player's list of rare findings.
	 * @return list_findings The player's list of rare findings.
	 */
	public ArrayList<RareFinding> get_list_findings(){return rfindings_list;}
	
	/**
	 * Returns the player's table of pawns.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Returns Player's table of pawns.
	 * @return player_pawns Rhe player's table of pawns.
	 */
	public Pawn[] get_player_pawns() {return player_pawns;}
	
	/**
	 * Returns the player's table of pics.
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns Player's table of pics.
	 * @return player_pics The player's table of pics.
	 */
	public ArrayList<FrescoFinding> get_player_pics() {return this.player_pics_list;}
	
	/**
	 * Adds a new fresco in the ArrayList with the player's pics of frescoes.
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Adds a new fresco in the ArrayList with the player's pics of frescoes.
	 * @param f The new fresco.
	 */
	public void add_fresco(FrescoFinding f) {
		player_pics_list.add(f);
	}
	
	/**
	 * Adds a new rare finding in the ArrayList with the player's rare findings.
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Adds a new rare finding in the ArrayList with the player's rare findings.
	 * @param f The new rare finding.
	 */
	public void add_rare(RareFinding f) {
		rfindings_list.add(f);
	}
	
	/**
	 * Updates the player's player_snakegoddess_count.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Adds one in the counter of the Snake Goddess figurines.
	 */
	public void update_player_snakegoddess_count() {
		int temp = this.player_snakegoddess_count;
		temp++;
		this.player_snakegoddess_count = temp;
	}
	
	/**
	 * Returns the Snake Goddess figurines' counter.
	 * <br><b>Pre Conditions : </b> None.
	 * <br><b>Post Conditions : </b> Returns the Snake Goddess figurines' counter.
	 * @return player_snakegoddess_count
	 */
	public int get_player_snakegoddess_count() {return player_snakegoddess_count;}
}