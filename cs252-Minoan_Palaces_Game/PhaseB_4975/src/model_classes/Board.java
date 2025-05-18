package model_classes;

import java.util.*;

import javax.swing.ImageIcon;

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
	 * The ArrayList with all the frescoes.
	 */
	private ArrayList<FrescoFinding> frfin = new ArrayList<>();
	
	/**
	 * The ArrayList with all the Rare Findings.
	 */
	private ArrayList<RareFinding> rarefin = new ArrayList<>();
	
	/**
	 * The hand of Player1.
	 */
	private Hand p1_hand;
	
	/**
	 * The hand of Player2.
	 */
	private Hand p2_hand;
	
	/**
	 * Constructor. It initializes the assets of the board.
	 */
	public Board() {
		init_frfin(frfin);
		init_rarefin(rarefin);
		trapoula = new Deck();
		monopatia = new Path[4];
		Stack<Integer> finding_stack = new Stack<>();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(0);
        }
        for (int i = 1; i <= 6; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int number : list) {
        	finding_stack.push(number);
        }
		monopatia[0]= new Path(1, finding_stack, frfin, rarefin);
		monopatia[1]= new Path(2, finding_stack, frfin, rarefin);
		monopatia[2]= new Path(3, finding_stack, frfin, rarefin);
		monopatia[3]= new Path(4, finding_stack, frfin, rarefin);
		
		p1_hand = new Hand(trapoula);
		p2_hand = new Hand(trapoula);
	}
	
	/**
	 * Function init_frfin initializes all 6 frescoes and stores them in the ArrayList frfin.
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Initializes all 6 frescoes and stores them in the ArrayList frfin.
	 * @param frfin The empty ArrayList where the frescoes will be stored.
	 */
	public void init_frfin(ArrayList<FrescoFinding> frfin) {
		FrescoFinding temp = new FrescoFinding(20, new ImageIcon("resources/images/findings/fresco1_20.jpg"));
		frfin.add(temp);
		temp = new FrescoFinding(20, new ImageIcon("resources/images/findings/fresco2_20.jpg"));
		frfin.add(temp);
		temp = new FrescoFinding(15, new ImageIcon("resources/images/findings/fresco3_15.jpg"));
		frfin.add(temp);
		temp = new FrescoFinding(20, new ImageIcon("resources/images/findings/fresco4_20.jpg"));
		frfin.add(temp);
		temp = new FrescoFinding(15, new ImageIcon("resources/images/findings/fresco5_15.jpg"));
		frfin.add(temp);
		temp = new FrescoFinding(15, new ImageIcon("resources/images/findings/fresco6_15.jpg"));
		frfin.add(temp);
	}
	
	/**
	 * Function init_rarefin initializes all 4 rare findings and stores them in the ArrayList rarefin.
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Initializes all 4 rare findings and stores them in the ArrayList rarefin.
	 * @param rarefin The empty ArrayList where the rare findings will be stored.
	 */
	public void init_rarefin(ArrayList<RareFinding> rarefin) {
		RareFinding raref= new RareFinding(25 , new ImageIcon("resources/images/findings/ring.jpg"));
		rarefin.add(raref);
		raref = new RareFinding(25 , new ImageIcon("resources/images/findings/kosmima.jpg"));
		rarefin.add(raref);
		raref = new RareFinding(35 , new ImageIcon("resources/images/findings/diskos.jpg"));
		rarefin.add(raref);
		raref = new RareFinding(25 , new ImageIcon("resources/images/findings/ruto.jpg"));
		rarefin.add(raref);
	}
	
	/**
	 * Returns the board's deck of unused cards
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the board's deck of unused cards
	 * @return trapoula The board's deck of unused cards
	 */
	public Deck get_board_deck() {return trapoula;}
	
	/**
	 * Returns the ArrayList frfin with all 6 frescoes.
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the ArrayList frfin with all 6 frescoes.
	 * @return frfin The ArrayList frfin with all 6 frescoes.
	 */
	public ArrayList<FrescoFinding> get_frfin(){return this.frfin;}
	
	/**
	 * Returns the ArrayList rarefin with all 4 rare findings.
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the ArrayList rarefin with all 4 rare findings.
	 * @return rarefin The ArrayList with all 4 rare findings.
	 */
	public ArrayList<RareFinding> get_rarefin(){return this.rarefin;}
	
	/**
	 * Returns the hand of Player1.
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the hand of Player1.
	 * @return p1_hand The hand of Player1.
	 */
	public Hand get_p1hand() {return this.p1_hand;}
	
	/**
	 * Returns the hand of Player2.
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the hand of Player2.
	 * @return p2_hand The hand of Player2.
	 */
	public Hand get_p2hand() {return this.p2_hand;}
	
	/**
	 * Returns the board's table with the four paths
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the board's table with the four paths
	 * @return monopatia The board's table with the four paths
	 */
	public Path[] get_board_path() {return monopatia;}
}