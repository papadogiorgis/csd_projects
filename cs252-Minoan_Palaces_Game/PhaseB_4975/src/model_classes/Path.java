package model_classes;

import java.util.*;
import javax.swing.*;

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
	 * This table stores the 5 findings that are hidden in each path.
	 */
	private Finding[] finding_path = new Finding[5];
	
	/**
	 * Constructor. It sets the palace int, initializes each position and stores it in the table
	 * @param anakt The int that indicates the palace that this path is leading to
	 * @param finding_stack This stack stores randomly 10 zeros and 6 different integers. Each zero represents a figurine and each int is a fresco. This constructor pops an int and initializes the finding in each FindingPosition.
	 * @param frfin An ArrayList with all the frescoes.
	 * @param rarefin An ArrayList with all the Rare Findings.
	 */
	public Path(int anakt, Stack<Integer> finding_stack, ArrayList<FrescoFinding> frfin, ArrayList<RareFinding> rarefin) {
		this.anaktoro = anakt;
		this.theseis = new Position[9];
		String pal;
		RareFinding raref;
    	if(this.anaktoro==1) {
    		pal = "knossos";
    		raref = rarefin.get(0);
    	}else if(this.anaktoro==2) {
    		pal = "malia";
    		raref = rarefin.get(1);
    	}else if(this.anaktoro==3) {
    		pal = "phaistos";
    		raref = rarefin.get(2);
    	}else {
    		pal = "zakros";
    		raref = rarefin.get(3);
    	}
    	
    	Random rand = new Random();
        int randomInt = rand.nextInt(4);
        int popped;
        for(int i=0;i<4;i++) {
        	if(i==randomInt) {
        		finding_path[i] = raref;
        	}else {
        		popped = finding_stack.pop();
        		if(popped == 0) {
        			finding_path[i] = new SnakeGoddessFinding(new ImageIcon("resources/images/findings/snakes.jpg"));
        		}else if(popped == 1){
        			finding_path[i] = frfin.get(0);
        		}else if(popped == 2){
        			finding_path[i] = frfin.get(1);
        		}else if(popped == 3){
        			finding_path[i] = frfin.get(2);
        		}else if(popped == 4){
        			finding_path[i] = frfin.get(3);
        		}else if(popped == 5){
        			finding_path[i] = frfin.get(4);
        		}else if(popped == 6){
        			finding_path[i] = frfin.get(5);
        		}
        	}
        }
    	
    	ImageIcon originalIcon = new ImageIcon("resources/images/paths/"+pal+".jpg");
    	theseis[0] = new SimplePosition(-20,false,originalIcon);
    	
    	originalIcon = new ImageIcon("resources/images/paths/"+pal+"2.jpg");
    	theseis[1] = new FindingPosition(-15,false,originalIcon,finding_path[0]);
    	
    	originalIcon = new ImageIcon("resources/images/paths/"+pal+".jpg");
    	theseis[2] = new SimplePosition(-10,false,originalIcon);
    	
    	originalIcon = new ImageIcon("resources/images/paths/"+pal+"2.jpg");
    	theseis[3] = new FindingPosition(5,false,originalIcon,finding_path[1]);
    	
    	originalIcon = new ImageIcon("resources/images/paths/"+pal+".jpg");
    	theseis[4] = new SimplePosition(10,false,originalIcon);
    	
    	originalIcon = new ImageIcon("resources/images/paths/"+pal+"2.jpg");
    	theseis[5] = new FindingPosition(15,false,originalIcon,finding_path[2]);
    	
    	originalIcon = new ImageIcon("resources/images/paths/"+pal+".jpg");
    	theseis[6] = new SimplePosition(30,true,originalIcon);
    	
    	originalIcon = new ImageIcon("resources/images/paths/"+pal+"2.jpg");
    	theseis[7] = new FindingPosition(35,true,originalIcon,finding_path[3]);
    	
    	originalIcon = new ImageIcon("resources/images/paths/"+pal+"Palace.jpg");
    	theseis[8] = new FindingPosition(50,true,originalIcon,finding_path[4]);
	}
	
	/**
	 * Returns the palace int
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the palace int
	 * @return anaktoro The palace int
	 */
	public int get_palace_int() {return anaktoro;}
	
	/**
	 * Returns the path's position table
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Returns the path's position table
	 * @return theseis The path's position table
	 */
	public Position[] get_path_positions() {return theseis;}
}