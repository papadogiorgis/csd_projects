package view_classes;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;
import javax.swing.border.*;
import controller_classes.*;
import model_classes.*;
import java.awt.image.*;
import javafx.application.Platform;

/**
 * Pubblic class "GraphicUI"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class GraphicUI{
	private static JLayeredPane player1_field;
    private static JLayeredPane player2_field;
    private static JLayeredPane tablo;
    private JButton myfrescos_button1, myfrescos_button2;
    private JLabel[] paths = new JLabel[36];
    private static JLabel[] rarefindings_labels = new JLabel[8];
    private static JLabel[] cardhistory1_labels = new JLabel[4];
    private static JLabel[] cardhistory2_labels = new JLabel[4];
    private static JButton[] card_buttons = new JButton[16];
    private static Controller game;
    private Board gameboard;
    private static Path[] monopatia;
    private static Position[] theseis;
    private JLabel score_label;
    private JLabel chkp_label;
    private static JButton drawnewcard_button;
    private static JLabel availablecards_label;
    private static int avlbl_ = 84;
    private static int chkpt_ = 0;
    private static int turn_;
    private JLabel availablepawns_label1, availablepawns_label2;
    private static JLabel snakefig_label1, snakefig_label2;
    private static JLabel myscore_label1, myscore_label2;
    private static Hand p1_hand;
	private static Hand p2_hand;
    private static int selectedPawn;
    private static Player p1,p2;
	private static JLabel[] p1_pawns, p2_pawns;
	private static JFrame window;
	private static boolean ready_to_draw_card=false;
	private static boolean can_select_card=true;
	private static ArrayList<RareFinding> all_rare_findings;
	private MusicPlayer musicManager;
    int i,j;
    
    /**
     * Constructor.
     */
    public GraphicUI() {
    	game = new Controller();
    	gameboard = game.getGameBoard();
    	monopatia = gameboard.get_board_path();
        turn_ = game.getTurn();
        p1 = game.get_p1();
        p2 = game.get_p2();
    	
        window = new JFrame("Lost Cities | Minoan Civilization");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(1000,800);
        window.setLayout(new BorderLayout());
        window.setResizable(false);
        
        player1_field =  new JLayeredPane();
        player2_field =  new JLayeredPane();
        all_rare_findings= new ArrayList<>();
        
        musicManager = new MusicPlayer();
        Platform.startup(() -> {});
        if(turn_ == 1) {
        	musicManager.playMusic("resources/music/Player1.wav");
        }else {
        	musicManager.playMusic("resources/music/Player2.wav");
        }
        
        initComponents();
    }
    
    /**
     * Initializes components and buttons.
	 * <br><b>Pre Conditions : </b> None
	 * <br><b>Post Conditions : </b> Initializes components and buttons.
     */
    public void initComponents() {
    	
    	player1_field.setBackground(Color.WHITE);
        player1_field.setOpaque(true);
        player1_field.setPreferredSize(new Dimension(1000, 200));
        player1_field.setBorder(new LineBorder(Color.RED, 5));
        window.add(player1_field, BorderLayout.NORTH);
        
        player2_field.setBackground(Color.WHITE);
        player2_field.setOpaque(true);
        player2_field.setPreferredSize(new Dimension(1000, 200));
        player2_field.setBorder(new LineBorder(Color.GREEN, 5));
        window.add(player2_field, BorderLayout.SOUTH);
        
        tablo = new JLayeredPane() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image (replace "background.jpg" with your image path)
                ImageIcon backgroundIcon = new ImageIcon("resources/images/background.jpg");
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        tablo.setPreferredSize(new Dimension(1000, 400));
        window.add(tablo, BorderLayout.CENTER);
        
        for(j=0;j<4;j++) {
        	theseis = monopatia[j].get_path_positions();
            for(i=0;i<9;i++) {
            	if((i==1)||(i==3)||(i==5)||(i==7)) {
            		ImageIcon originalIcon = theseis[i].get_position_image();
            		Image img = originalIcon.getImage().getScaledInstance(75,66, Image.SCALE_SMOOTH);
            		ImageIcon resizedIcon = new ImageIcon(img);
                    paths[i + (j*9)] = new JLabel(resizedIcon);
                    paths[i + (j*9)].setBounds(240 + (i*78), 36 + (j*82), 75,66);
            	}else if(i==8) {
            		ImageIcon originalIcon = theseis[i].get_position_image();
            		Image img = originalIcon.getImage().getScaledInstance(108,76, Image.SCALE_SMOOTH);
            		ImageIcon resizedIcon = new ImageIcon(img);
            		paths[i + (j*9)] = new JLabel(resizedIcon);
            		paths[i + (j*9)].setBounds(240 + (i*78), 32 + (j*82), 108,76);
            	}else {
            		ImageIcon originalIcon = theseis[i].get_position_image();
            		Image img = originalIcon.getImage().getScaledInstance(75,66, Image.SCALE_SMOOTH);
            		ImageIcon resizedIcon = new ImageIcon(img);
            		paths[i + (j*9)] = new JLabel(resizedIcon);
            		paths[i + (j*9)].setBounds(240 + (i*78), 36 + (j*82), 75,66);
            	}
                tablo.add(paths[i + (j*9)], JLayeredPane.DEFAULT_LAYER);
            }
        }
        
        score_label = new JLabel("-20 points           -15 points         -10 points          5 points           10 points          15 points           30 points            35 points            50 points", JLabel.CENTER);
        score_label.setFont(new Font("Arial", Font.BOLD, 10));
        score_label.setForeground(Color.BLACK);
        score_label.setOpaque(false);
        score_label.setBounds(240, -10, 700, 50);
        tablo.add(score_label);
        
        chkp_label = new JLabel("Check Point!", JLabel.CENTER);
        chkp_label.setFont(new Font("Arial", Font.BOLD, 10));
        chkp_label.setForeground(Color.BLACK);
        chkp_label.setOpaque(false);
        chkp_label.setBounds(395, 0, 700, 50);
        tablo.add(chkp_label);
        
        ImageIcon imageIcon = new ImageIcon("resources/images/cards/backCard.jpg");
        Image img = imageIcon.getImage().getScaledInstance(73, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        drawnewcard_button = new JButton(scaledIcon);
        drawnewcard_button.setPreferredSize(new Dimension(73, 100));
        drawnewcard_button.setBounds(30, 150, 73, 100);
        drawnewcard_button.setBorder(BorderFactory.createEmptyBorder());
        drawnewcard_button.setOpaque(false);
        tablo.add(drawnewcard_button);
        drawnewcard_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ready_to_draw_card) {
                	if(!is_game_over()) {
                        System.out.println("drawnewcard_button was clicked!");
                        int temp_int = game.find_lost_card(turn_);
                        Card c = game.newCardInHand(temp_int);
                        updatehand(c,turn_, temp_int);
                        update_myscore_label(turn_);
                        turn_ = game.getTurn();
                        if(turn_ == 1) {
                        	musicManager.playMusic("resources/music/Player1.wav");
                        }else {
                        	musicManager.playMusic("resources/music/Player2.wav");
                        }
                        avlbl_ = game.get_avlblcards();
                        chkpt_ = game.get_pawns_on_chkp();
                        update_availablecards_label();
                        ready_to_draw_card = false;
                        can_select_card=true;
                	}else {
                		announce_winner();
                	}
                }
            }
        });
        
        availablecards_label = new JLabel("<html>Available Cards: "+avlbl_+"<br>Check Points: "+chkpt_+"<br>Turn: Player "+turn_+"</html>", JLabel.CENTER);
        availablecards_label.setFont(new Font("Arial", Font.BOLD, 12));
        availablecards_label.setForeground(Color.BLACK);
        availablecards_label.setBackground(Color.WHITE);
        availablecards_label.setOpaque(true);
        Border blackBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        availablecards_label.setBorder(blackBorder);
        availablecards_label.setBounds(30, 260, 140, 65);
        tablo.add(availablecards_label);
        
        availablepawns_label1 = new JLabel("Player 1 - Available Pawns: 3 Archaeologists and 1 Theseus", JLabel.CENTER);
        availablepawns_label1.setFont(new Font("Arial", Font.BOLD, 12));
        availablepawns_label1.setForeground(Color.BLACK);
        availablepawns_label1.setBackground(Color.WHITE);
        availablepawns_label1.setOpaque(false);
        availablepawns_label1.setBounds(20, 170, 350, 30);
        player1_field.add(availablepawns_label1);
        
        availablepawns_label2 = new JLabel("Player 2 - Available Pawns: 3 Archaeologists and 1 Theseus", JLabel.CENTER);
        availablepawns_label2.setFont(new Font("Arial", Font.BOLD, 12));
        availablepawns_label2.setForeground(Color.BLACK);
        availablepawns_label2.setBackground(Color.WHITE);
        availablepawns_label2.setOpaque(false);
        availablepawns_label2.setBounds(20, 170, 350, 30);
        player2_field.add(availablepawns_label2);
        
        snakefig_label1 = new JLabel("<html>Snake Goddess<br>figurines: 0</html>", JLabel.CENTER);
        snakefig_label1.setFont(new Font("Arial", Font.BOLD, 12));
        snakefig_label1.setForeground(Color.BLACK);
        snakefig_label1.setBackground(Color.WHITE);
        snakefig_label1.setOpaque(false);
        snakefig_label1.setBounds(850, 150, 100, 60);
        player1_field.add(snakefig_label1);
        imageIcon = new ImageIcon("resources/images/findings/snakes.jpg");
        img = imageIcon.getImage().getScaledInstance(28, 31, Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(img);
        JLabel snakefig_pic = new JLabel(scaledIcon);
        snakefig_pic.setPreferredSize(new Dimension(28, 31));
        snakefig_pic.setBounds(950, 160, 28, 31);
        snakefig_pic.setBorder(BorderFactory.createEmptyBorder());
        snakefig_pic.setOpaque(false);
        player1_field.add(snakefig_pic);
        
        snakefig_label2 = new JLabel("<html>Snake Goddess<br>figurines: 0</html>", JLabel.CENTER);
        snakefig_label2.setFont(new Font("Arial", Font.BOLD, 12));
        snakefig_label2.setForeground(Color.BLACK);
        snakefig_label2.setBackground(Color.WHITE);
        snakefig_label2.setOpaque(false);
        snakefig_label2.setBounds(850, 150, 100, 60);
        player2_field.add(snakefig_label2);
        imageIcon = new ImageIcon("resources/images/findings/snakes.jpg");
        img = imageIcon.getImage().getScaledInstance(28, 31, Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(img);
        JLabel snakefig_pic2 = new JLabel(scaledIcon);
        snakefig_pic2.setPreferredSize(new Dimension(28, 31));
        snakefig_pic2.setBounds(950, 160, 28, 31);
        snakefig_pic2.setBorder(BorderFactory.createEmptyBorder());
        snakefig_pic2.setOpaque(false);
        player2_field.add(snakefig_pic2);
        
        myscore_label1 = new JLabel("Score: 0 points", JLabel.CENTER);
        myscore_label1.setFont(new Font("Arial", Font.BOLD, 12));
        myscore_label1.setForeground(Color.BLACK);
        myscore_label1.setBackground(Color.WHITE);
        myscore_label1.setOpaque(false);
        myscore_label1.setBounds(835, 5, 150, 30);
        player1_field.add(myscore_label1);
        
        myscore_label2 = new JLabel("Score: 0 points", JLabel.CENTER);
        myscore_label2.setFont(new Font("Arial", Font.BOLD, 12));
        myscore_label2.setForeground(Color.BLACK);
        myscore_label2.setBackground(Color.WHITE);
        myscore_label2.setOpaque(false);
        myscore_label2.setBounds(835, 5, 150, 30);
        player2_field.add(myscore_label2);
        
        ArrayList<FrescoFinding> frf = gameboard.get_frfin();

        myfrescos_button1 = new JButton("My Frescoes");
        myfrescos_button1.setBounds(870,80, 110, 30);
        player1_field.add(myfrescos_button1);
        showmyfrescos1_actionlistener(myfrescos_button1, frf);
        
        myfrescos_button2 = new JButton("My Frescoes");
        myfrescos_button2.setBounds(870,80, 110, 30);
        player2_field.add(myfrescos_button2);
        showmyfrescos2_actionlistener(myfrescos_button2, frf);
        
        all_rare_findings = gameboard.get_rarefin();
        rarefindings_function(player1_field, 0, 1);
        rarefindings_function(player2_field, 0, 2);
        
        for(i=0;i<4;i++) {
        	String pal = "";
        	Color col = Color.BLACK;
        	if(i==0) {
        		pal = "Knossos";
        		col = Color.RED;
        	}else if(i==1) {
        		pal = "Malia";
        		col = Color.YELLOW;
        	}else if(i==2) {
        		pal = "Phaistos";
        		col = Color.GRAY;
        	}else if(i==3) {
        		pal = "Zakros";
        		col = Color.BLUE;
        	}
        	cardhistory1_labels[i] = new JLabel(pal, JLabel.CENTER);
        	cardhistory1_labels[i].setFont(new Font("Arial", Font.BOLD, 12));
        	cardhistory1_labels[i].setForeground(Color.BLACK);
        	cardhistory1_labels[i].setBackground(Color.WHITE);
			cardhistory1_labels[i].setHorizontalAlignment(SwingConstants.CENTER);
			cardhistory1_labels[i].setVerticalAlignment(SwingConstants.CENTER);
        	cardhistory1_labels[i].setOpaque(false);
        	cardhistory1_labels[i].setBorder(BorderFactory.createLineBorder(col, 2));
        	cardhistory1_labels[i].setBounds(582 + (i*71), 40, 69, 100);
            player1_field.add(cardhistory1_labels[i]);
            
            cardhistory2_labels[i] = new JLabel(pal, JLabel.CENTER);
            cardhistory2_labels[i].setFont(new Font("Arial", Font.BOLD, 12));
            cardhistory2_labels[i].setForeground(Color.BLACK);
            cardhistory2_labels[i].setBackground(Color.WHITE);
			cardhistory2_labels[i].setHorizontalAlignment(SwingConstants.CENTER);
			cardhistory2_labels[i].setVerticalAlignment(SwingConstants.CENTER);
            cardhistory2_labels[i].setOpaque(false);
            cardhistory2_labels[i].setBorder(BorderFactory.createLineBorder(col, 2));
            cardhistory2_labels[i].setBounds(582 + (i*71), 40, 69, 100);
            player2_field.add(cardhistory2_labels[i]);
        }
        
        p1_hand = gameboard.get_p1hand();
        p2_hand = gameboard.get_p2hand();
        
        Card tempcard;
        for(i=0;i<8;i++) {
        	tempcard = p1_hand.get_cardfromhand(i);
        	imageIcon = tempcard.getImage();
        	img = imageIcon.getImage().getScaledInstance(69, 100, Image.SCALE_SMOOTH);
        	scaledIcon = new ImageIcon(img);
        	card_buttons[i] = new JButton(scaledIcon);
        	card_buttons[i].setPreferredSize(new Dimension(69, 100));
        	card_buttons[i].setBounds(5 + (i*72), 40, 69, 100);
        	card_buttons[i].setBorder(BorderFactory.createEmptyBorder());
        	card_buttons[i].setOpaque(false);
        	card_buttons[i].setActionCommand(Integer.toString(i));
        	card_buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	String actionCommand = e.getActionCommand();
                    int passedInt = Integer.parseInt(actionCommand);
                    showChoiceDialog(passedInt);
                }
            });
        	player1_field.add(card_buttons[i]);
        }
        for(i=0;i<8;i++) {
        	tempcard = p2_hand.get_cardfromhand(i);
        	imageIcon = tempcard.getImage();
        	img = imageIcon.getImage().getScaledInstance(69, 100, Image.SCALE_SMOOTH);
        	scaledIcon = new ImageIcon(img);
        	card_buttons[i+8] = new JButton(scaledIcon);
        	card_buttons[i+8].setPreferredSize(new Dimension(69, 100));
        	card_buttons[i+8].setBounds(5 + (i*72), 40, 69, 100);
        	card_buttons[i+8].setBorder(BorderFactory.createEmptyBorder());
        	card_buttons[i+8].setOpaque(false);
        	card_buttons[i+8].setActionCommand(Integer.toString(i+8));
        	card_buttons[i+8].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	String actionCommand = e.getActionCommand();
                    int passedInt = Integer.parseInt(actionCommand);
                    showChoiceDialog(passedInt);
                }
            });
        	player2_field.add(card_buttons[i+8]);
        }
        
        p1_pawns = new JLabel[4];
        p2_pawns = new JLabel[4];
        
    	window.setVisible(true);
    }
    
    /**
     * Function that makes the player choose wether to take a snake goddess figurine or ignore it via a new dialog.
	 * <br><b>Pre Conditions : </b> If the player wants to take the figurine, then this function checks if the figurine has been destroyed.
	 * <br><b>Post Conditions : </b> Player takes/ignores the figurine.
     * @param player Player's id.
     * @param p Pawn's index.
     * @param pos Position's index.
     * @param anak Path's index.
     */
    private static void show_snakegoddess_dialog(int player, int p, int pos,int anak) {
    	JDialog dialog = new JDialog(window, "Make a choice", true);
    	JLabel messagelabel = new JLabel("Do you want to take the Snake Goddess figurine?");
    	dialog.setLocationRelativeTo(window);
    	dialog.add(messagelabel);
        dialog.setSize(300, 100);
        dialog.setLayout(new FlowLayout());
    	JButton acceptButton = new JButton("Yes");
        JButton declineButton = new JButton("Ignore");

        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                theseis = monopatia[anak-1].get_path_positions();
            	FindingPosition fipo = (FindingPosition) theseis[pos];
                SnakeGoddessFinding snfi = (SnakeGoddessFinding) fipo.grab_finding_in_position();
                if((player==1)&&(snfi.getIsDestroyed()==false)) {
        			snfi.setOwner(p1);
        			p1.update_player_snakegoddess_count();
        			snakefig_label1.setText("<html>Snake Goddess<br>figurines: "+p1.get_player_snakegoddess_count()+"</html>");
        			player1_field.revalidate();
        			player1_field.repaint();
        		}else if((player==2)&&(snfi.getIsDestroyed()==false)) {
        			snfi.setOwner(p2);
        			p2.update_player_snakegoddess_count();
        			snakefig_label2.setText("<html>Snake Goddess<br>figurines: "+p2.get_player_snakegoddess_count()+"</html>");
        			player2_field.revalidate();
        			player2_field.repaint();
        		}
    			reveal_pawn(player,p,anak,0);
            }
        });

        declineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                }
            }
        );

        dialog.add(acceptButton);
        dialog.add(declineButton);

        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        dialog.setVisible(true);
        
    	window.setVisible(true);
    }

    /**
     * Function that makes the player choose wether to take a picture of a fresco or ignore it via a new dialog.
	 * <br><b>Pre Conditions : </b> If the player wants to take a picture of a fresco, then this function checks if the fresco has been destroyed.
	 * <br><b>Post Conditions : </b> Player takes/ignores a picture of a fresco.
     * @param player Player's id.
     * @param p Pawn's index.
     * @param pos Position's index.
     * @param anak Path's index.
     */
    private static void show_frefo_dialog(int player, int p, int pos,int anak) {
    	JDialog dialog = new JDialog(window, "Make a choice", true);
    	JLabel messagelabel = new JLabel("Do you want to take a picture of this Fresco?");
    	dialog.setLocationRelativeTo(window);
    	dialog.add(messagelabel);
        dialog.setSize(300, 100);
        dialog.setLayout(new FlowLayout());
    	JButton acceptButton = new JButton("Yes");
        JButton declineButton = new JButton("Ignore");

        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                theseis = monopatia[anak-1].get_path_positions();
            	FindingPosition fipo = (FindingPosition) theseis[pos];
                FrescoFinding ffi = (FrescoFinding) fipo.get_finding_in_position();
                if((player==1)&&(ffi.getIsDestroyed()==false)&&(ffi.get_foundby1()==false)) {
        			ffi.set_foundby1();
        			p1.add_fresco(ffi);
        			update_myscore_label(player);
        			player1_field.revalidate();
        			player1_field.repaint();
        		}else if((player==2)&&(ffi.getIsDestroyed()==false)&&(ffi.get_foundby2()==false)) {
        			ffi.set_foundby2();
        			p2.add_fresco(ffi);
        			update_myscore_label(player);
        			player2_field.revalidate();
        			player2_field.repaint();
        		}
    			reveal_pawn(player,p,anak,0);
            }
        });

        declineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                }
            }
        );

        dialog.add(acceptButton);
        dialog.add(declineButton);

        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        dialog.setVisible(true);
        
    	window.setVisible(true);
    }
    
    /**
     * Function that makes the player choose wether to take a rare finding or ignore it via a new dialog.
	 * <br><b>Pre Conditions : </b> If the player wants to take the rare finding, then this function checks if the rare finding has been destroyed.
	 * <br><b>Post Conditions : </b> Player takes/ignores the rare finding.
     * @param player Player's id.
     * @param p Pawn's index.
     * @param pos Position's index.
     * @param anak Path's index.
     */
    private static void show_raref_dialog(int player, int p, int pos,int anak) {
    	JDialog dialog = new JDialog(window, "Make a choice", true);
    	JLabel messagelabel = new JLabel("Do you want to take the Rare Finding of this path?");
    	dialog.setLocationRelativeTo(window);
    	dialog.add(messagelabel);
        dialog.setSize(300, 100);
        dialog.setLayout(new FlowLayout());
    	JButton acceptButton = new JButton("Yes");
        JButton declineButton = new JButton("Ignore");

        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                theseis = monopatia[anak-1].get_path_positions();
            	FindingPosition fipo = (FindingPosition) theseis[pos];
                RareFinding rfi = (RareFinding) fipo.grab_finding_in_position();
        		if((player==1)&&(rfi.getIsDestroyed()==false)) {
        			rfi.setOwner(p1);
        			p1.add_rare(rfi);
        			rarefindings_function(player1_field, anak, player);
        			update_myscore_label(player);
        			player1_field.revalidate();
        			player1_field.repaint();
        		}else if((player==2)&&(rfi.getIsDestroyed()==false)) {
        			rfi.setOwner(p2);
        			p2.add_rare(rfi);
        			rarefindings_function(player2_field, anak, player);
        			update_myscore_label(player);
        			player2_field.revalidate();
        			player2_field.repaint();
        		}
    			reveal_pawn(player,p,anak,0);
            }
        });

        declineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                }
            }
        );

        dialog.add(acceptButton);
        dialog.add(declineButton);

        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        dialog.setVisible(true);
        
    	window.setVisible(true);
    }
    
    /**
     * Function that makes the player choose wether to destroy a finding or ignore it via a new dialog.
	 * <br><b>Pre Conditions : </b> If the player wants to destroy a finding, then this function checks if the finding has already been destroyed.
	 * <br><b>Post Conditions : </b> Player destroys/ignores the finding.
     * @param player Player's id.
     * @param p Pawn's index.
     * @param pos Position's index.
     * @param anak Path's index.
     */
    private static void show_destruction_dialog(int player, int p, int pos,int anak) {
    	JDialog dialog = new JDialog(window, "Make a choice", true);
    	JLabel messagelabel = new JLabel("Do you want to destroy the finding in this position?");
    	dialog.setLocationRelativeTo(window);
    	dialog.add(messagelabel);
        dialog.setSize(300, 100);
        dialog.setLayout(new FlowLayout());
    	JButton acceptButton = new JButton("Yes");
        JButton declineButton = new JButton("Ignore");

        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                theseis = monopatia[anak-1].get_path_positions();
            	FindingPosition fipo = (FindingPosition) theseis[pos];
                Finding fi =fipo.grab_finding_in_position();
        		if((player==1)&&(p1.get_destruction_counter()>0)&&(fi.getIsDestroyed()==false)) {
        			fi.setIsDestroyed(true);
        			p1.update_destruction_counter();
        		}else if((player==2)&&(p2.get_destruction_counter()>0)&&(fi.getIsDestroyed()==false)) {
        			fi.setIsDestroyed(true);
        			p2.update_destruction_counter();
        		}
    			reveal_pawn(player,p,anak,0);
            }
        });

        declineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                }
            }
        );

        dialog.add(acceptButton);
        dialog.add(declineButton);

        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        dialog.setVisible(true);
        
    	window.setVisible(true);
    }
    
    /**
     * Function that makes the player choose whether to play or remove a card from hand via a new dialog.
	 * <br><b>Pre Conditions : </b> It's the right player's turn to choose a card.
	 * <br><b>Post Conditions : </b> Player plays/disposes a card.
     * @param passedInt The card's index.
     */
    private static void showChoiceDialog(int passedInt) {
    	JDialog dialog = new JDialog(window, "Choose Option", true);
    	dialog.setLocationRelativeTo(window);
        dialog.setSize(300, 100);
        dialog.setLayout(new FlowLayout());
    	JButton acceptButton = new JButton("Play Card");
        JButton declineButton = new JButton("Remove Card from Hand");

        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();

                int cardIndex = passedInt;
                if(can_select_card) {
	                if((cardIndex<=7)&&(turn_ == 1)) {
	                	Card c = p1_hand.get_cardfromhand(cardIndex);
	                	selectedPawn = -1;
	                	while((selectedPawn==-1)&&(!(c instanceof MinotaurCard))) {
	                		openPawnSelectionDialog();
	                	}
	                	System.out.println("SELECTED PAWN:"+selectedPawn);
	                	if(c instanceof NumberCard) {
	                		NumberCard nc = (NumberCard) c;
	                		System.out.println("Number Card of player1. Number="+nc.getNoC()+" Place="+nc.getPlace());
	                		if(movepawn(1, selectedPawn, nc.getPlace(), nc.getNoC())){
	                			updatecardhistory(nc, nc.getPlace(), 1);
	                        	removepicfromhand(cardIndex);
	                		}
	                	}else if(c instanceof AriadneCard) {
	                		AriadneCard ac = (AriadneCard) c;
	                		System.out.println("Ariadne Card of player1. Place="+ac.getPlace());
	                		if(movepawn(1, selectedPawn, ac.getPlace(), -1)){
	                			updatecardhistory(ac, ac.getPlace(), 1);
	                        	removepicfromhand(cardIndex);
	                		}
	                	}else if(c instanceof MinotaurCard) {
	                		MinotaurCard mc = (MinotaurCard) c;
	                		System.out.println("Minotaur Card of player1. Place="+mc.getPlace());
	                		int penalty = game.check_minotaur(turn_, mc.getPlace());
	                		int pawntoreveal = game.find_enemy(turn_, mc.getPlace());
	                		if(penalty>=0) {
	                			reveal_pawn(2, pawntoreveal, mc.getPlace(), penalty);
	                			updatecardhistory(mc, mc.getPlace(), 1);
	                        	removepicfromhand(cardIndex);
	                		}
	                	}
	                }else if((cardIndex>7)&&(turn_ == 2)) {
	                	Card c = p2_hand.get_cardfromhand(cardIndex-8);
	                	selectedPawn = -1;
	                	while((selectedPawn==-1)&&(!(c instanceof MinotaurCard))) {
	                		openPawnSelectionDialog();
	                	}
	                	System.out.println("SELECTED PAWN:"+selectedPawn);
	                	if(c instanceof NumberCard) {
	                		NumberCard nc = (NumberCard) c;
	                		System.out.println("Number Card of player2. Number="+nc.getNoC()+" Place="+nc.getPlace());
	                		if(movepawn(2, selectedPawn, nc.getPlace(), nc.getNoC())) {
	                			updatecardhistory(nc, nc.getPlace(), 2);
	                        	removepicfromhand(cardIndex);
	                		}
	                	}else if(c instanceof AriadneCard) {
	                		AriadneCard ac = (AriadneCard) c;
	                		System.out.println("Ariadne Card of player2. Place="+ac.getPlace());
	                		if(movepawn(2, selectedPawn, ac.getPlace(), -1)){
	                			updatecardhistory(ac, ac.getPlace(), 2);
	                        	removepicfromhand(cardIndex);
	                		}
	                	}else if(c instanceof MinotaurCard) {
	                		MinotaurCard mc = (MinotaurCard) c;
	                		System.out.println("Minotaur Card of player2. Place="+mc.getPlace());
	                		int penalty = game.check_minotaur(turn_, mc.getPlace());
	                		int pawntoreveal = game.find_enemy(turn_, mc.getPlace());
	                		if(penalty>=0) {
	                			reveal_pawn(1, pawntoreveal, mc.getPlace(), penalty);
	                			updatecardhistory(mc, mc.getPlace(), 2);
	                        	removepicfromhand(cardIndex);
	                		}
	                	}
	                }
                }
            }
        });

        declineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();

                int cardIndex = passedInt;
                if(can_select_card) {
                	if((cardIndex<=7)&&(turn_ == 1)) {
                    	Card c = p1_hand.get_cardfromhand(cardIndex);
                    	if(c instanceof NumberCard) {
                    		NumberCard nc = (NumberCard) c;
                    		System.out.println("Number Card of player1. Number="+nc.getNoC()+" Place="+nc.getPlace());
                    		nc.setIsActive(false);
                    		removepicfromhand(cardIndex);
                    	}else if(c instanceof AriadneCard) {
                    		AriadneCard ac = (AriadneCard) c;
                    		System.out.println("Ariadne Card of player1. Place="+ac.getPlace());
                    		ac.setIsActive(false);
                    		removepicfromhand(cardIndex);
                    	}else if(c instanceof MinotaurCard) {
                    		MinotaurCard mc = (MinotaurCard) c;
                    		System.out.println("Minotaur Card of player1. Place="+mc.getPlace());
                    		mc.setIsActive(false);
                    		removepicfromhand(cardIndex);
                    	}
                    }else if((cardIndex>7)&&(turn_ == 2)) {
                    	Card c = p2_hand.get_cardfromhand((cardIndex-8));
                    	if(c instanceof NumberCard) {
                    		NumberCard nc = (NumberCard) c;
                    		System.out.println("Number Card of player2. Number="+nc.getNoC()+" Place="+nc.getPlace());
                    		nc.setIsActive(false);
                    		removepicfromhand(cardIndex);
                    	}else if(c instanceof AriadneCard) {
                    		AriadneCard ac = (AriadneCard) c;
                    		System.out.println("Ariadne Card of player2. Place="+ac.getPlace());
                    		ac.setIsActive(false);
                    		removepicfromhand(cardIndex);
                    	}else if(c instanceof MinotaurCard) {
                    		MinotaurCard mc = (MinotaurCard) c;
                    		System.out.println("Minotaur Card of player2. Place="+mc.getPlace());
                    		mc.setIsActive(false);
                    		removepicfromhand(cardIndex);
                    	}
                    }
                }
            }
        });

        dialog.add(acceptButton);
        dialog.add(declineButton);

        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        dialog.setVisible(true);
    }
    
    /**
     * Function that reveals player's pawn and moves it back "penalty" positions.
     * @param player Player's id.
     * @param p Pawn's index.
     * @param anak Path's index.
     * @param penalty The amount of positions the pawn must go back.
     */
    public static void reveal_pawn(int player, int p, int anak, int penalty) {
    	String hero="";
    	if(p==3) {
    		hero="theseus";
    	}else {
    		hero="arch";
    	}
    	if(player==2) {
    		Pawn[] pawns = p2.get_player_pawns();
    		Pawn pawntoreveal = pawns[p];
    		int pos = pawntoreveal.get_pawn_position();
    		pos = pos - penalty;
			theseis = monopatia[anak-1].get_path_positions();
			Position posit = theseis[pos];
			pawntoreveal.set_pawn_pos(posit);
			pawntoreveal.set_pawn_position(pos);
    		tablo.remove(p2_pawns[p]);
			theseis = monopatia[anak-1].get_path_positions();
			ImageIcon originalIcon = new ImageIcon("resources/images/pionia/"+hero+".jpg");
    		Image img = originalIcon.getImage().getScaledInstance(30,50, Image.SCALE_SMOOTH);
    		ImageIcon resizedIcon = new ImageIcon(img);
            p2_pawns[p] = new JLabel(resizedIcon);
            p2_pawns[p].setForeground(Color.BLACK);
            p2_pawns[p].setBackground(Color.WHITE);
            p2_pawns[p].setOpaque(false);
            p2_pawns[p].setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
            p2_pawns[p].setBounds(280 + (pos*78), 44 + ((anak-1)*82), 30,50);
            tablo.add(p2_pawns[p], JLayeredPane.PALETTE_LAYER);
    	}else {
    		Pawn[] pawns = p1.get_player_pawns();
    		Pawn pawntoreveal = pawns[p];
    		int pos = pawntoreveal.get_pawn_position();
    		pos = pos - penalty;
			theseis = monopatia[anak-1].get_path_positions();
			Position posit = theseis[pos];
			pawntoreveal.set_pawn_pos(posit);
			pawntoreveal.set_pawn_position(pos);
    		tablo.remove(p1_pawns[p]);
			theseis = monopatia[anak-1].get_path_positions();
			ImageIcon originalIcon = new ImageIcon("resources/images/pionia/"+hero+".jpg");
    		Image img = originalIcon.getImage().getScaledInstance(30,50, Image.SCALE_SMOOTH);
    		ImageIcon resizedIcon = new ImageIcon(img);
            p1_pawns[p] = new JLabel(resizedIcon);
            p1_pawns[p].setForeground(Color.BLACK);
            p1_pawns[p].setBackground(Color.WHITE);
            p1_pawns[p].setOpaque(false);
            p1_pawns[p].setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            p1_pawns[p].setBounds(247 + (pos*78), 44 + ((anak-1)*82), 30,50);
            tablo.add(p1_pawns[p], JLayeredPane.PALETTE_LAYER);
    	}
    	update_myscore_label(player);
        tablo.revalidate();
        tablo.repaint();
    	window.setVisible(true);
    }
    
    /**
     * Function that is used to move the pawn on the board.
     * @param player Player's id.
     * @param p Pawn's index.
     * @param anak Path's index.
     * @param noc The value of the card. If it's ariadne card then noc==-1.
     * @return bool True for success, False for fail.
     */
    public static boolean movepawn(int player, int p, int anak, int noc) {
    	if(player == 1) {
    		Pawn[] pawntable = p1.get_player_pawns();
    		Pawn pawntomove = pawntable[p];
    		if((pawntomove.get_minvalue() == -1)&&(noc!=-1)) {
    			pawntomove.set_minvalue(noc);
    			pawntomove.set_pawn_palace(anak);
    			pawntomove.set_pawn_position(0);
    			theseis = monopatia[anak-1].get_path_positions();
    			Position pos = theseis[0];
    			pawntomove.set_pawn_pos(pos);
    			ImageIcon originalIcon = new ImageIcon("resources/images/pionia/question.jpg");
        		Image img = originalIcon.getImage().getScaledInstance(30,50, Image.SCALE_SMOOTH);
        		ImageIcon resizedIcon = new ImageIcon(img);
                p1_pawns[p] = new JLabel(resizedIcon);
                p1_pawns[p].setForeground(Color.BLACK);
                p1_pawns[p].setBackground(Color.WHITE);
                p1_pawns[p].setOpaque(false);
                p1_pawns[p].setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                p1_pawns[p].setBounds(247, 44 + ((anak-1)*82), 30,50);
                tablo.add(p1_pawns[p], JLayeredPane.PALETTE_LAYER);
    		}else if((noc >= pawntomove.get_minvalue())&&(pawntomove.get_pawn_palace()==anak)&&(noc!=-1)&&(pawntomove.get_pawn_position()<=7)) {
    			pawntomove.set_minvalue(noc);
    			int pos = pawntomove.get_pawn_position();
    			pos++;
    			if(pos==6) {
    				game.new_pawn_on_chkp();
    			}
    			theseis = monopatia[anak-1].get_path_positions();
    			Position posit = theseis[pos];
    			pawntomove.set_pawn_pos(posit);
    			pawntomove.set_pawn_position(pos);
                p1_pawns[p].setBounds(247 + (pos*78), 44 + ((anak-1)*82), 30,50);
                tablo.revalidate();
                tablo.repaint();
                if((pos==1)||(pos==3)||(pos==5)||(pos==7)||(pos==8)) {
                	stepped_on_finding(player,p,pos,anak);
                }
    		}else if((pawntomove.get_minvalue() != -1)&&(noc==-1)&&(pawntomove.get_pawn_palace()==anak)&&(pawntomove.get_pawn_position()<=6)) {
    			int pos = pawntomove.get_pawn_position();
    			pos++;
    			if((pos==1)||(pos==3)||(pos==5)||(pos==7)||(pos==8)) {
                	stepped_on_finding(player,p,pos,anak);
                }
    			if(pos==6) {
    				game.new_pawn_on_chkp();
    			}
    			pos++;
    			if((pos==1)||(pos==3)||(pos==5)||(pos==7)||(pos==8)) {
                	stepped_on_finding(player,p,pos,anak);
                }
    			if(pos==6) {
    				game.new_pawn_on_chkp();
    			}
    			theseis = monopatia[anak-1].get_path_positions();
    			Position posit = theseis[pos];
    			pawntomove.set_pawn_pos(posit);
    			pawntomove.set_pawn_position(pos);
                p1_pawns[p].setBounds(247 + (pos*78), 44 + ((anak-1)*82), 30,50);
                tablo.revalidate();
                tablo.repaint();
    		}else if((pawntomove.get_minvalue() != -1)&&(noc==-1)&&(pawntomove.get_pawn_palace()==anak)&&(pawntomove.get_pawn_position()==7)) {
    			int pos = pawntomove.get_pawn_position();
    			pos++;
    			if((pos==1)||(pos==3)||(pos==5)||(pos==7)||(pos==8)) {
                	stepped_on_finding(player,p,pos,anak);
                }
    			if(pos==6) {
    				game.new_pawn_on_chkp();
    			}
    			theseis = monopatia[anak-1].get_path_positions();
    			Position posit = theseis[pos];
    			pawntomove.set_pawn_pos(posit);
    			pawntomove.set_pawn_position(pos);
                p1_pawns[p].setBounds(247 + (pos*78), 44 + ((anak-1)*82), 30,50);
                tablo.revalidate();
                tablo.repaint();
    		}else {
    			return false;
    		}
    	}else {
    		Pawn[] pawntable = p2.get_player_pawns();
    		Pawn pawntomove = pawntable[p];
    		if((pawntomove.get_minvalue() == -1)&&(noc!=-1)) {
    			pawntomove.set_minvalue(noc);
    			pawntomove.set_pawn_palace(anak);
    			pawntomove.set_pawn_position(0);
    			theseis = monopatia[anak-1].get_path_positions();
    			Position pos = theseis[0];
    			pawntomove.set_pawn_pos(pos);
    			ImageIcon originalIcon = new ImageIcon("resources/images/pionia/question.jpg");
        		Image img = originalIcon.getImage().getScaledInstance(30,50, Image.SCALE_SMOOTH);
        		ImageIcon resizedIcon = new ImageIcon(img);
                p2_pawns[p] = new JLabel(resizedIcon);
                p2_pawns[p].setForeground(Color.BLACK);
                p2_pawns[p].setBackground(Color.WHITE);
                p2_pawns[p].setOpaque(false);
                p2_pawns[p].setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
                p2_pawns[p].setBounds(280, 44 + ((anak-1)*82), 30,50);
                tablo.add(p2_pawns[p], JLayeredPane.PALETTE_LAYER);
    		}else if((noc >= pawntomove.get_minvalue())&&(pawntomove.get_pawn_palace()==anak)&&(noc!=-1)&&(pawntomove.get_pawn_position()<=7)) {
    			pawntomove.set_minvalue(noc);
    			int pos = pawntomove.get_pawn_position();
    			pos ++;
    			if(pos==6) {
    				game.new_pawn_on_chkp();
    			}
    			theseis = monopatia[anak-1].get_path_positions();
    			Position posit = theseis[pos];
    			pawntomove.set_pawn_pos(posit);
    			pawntomove.set_pawn_position(pos);
                p2_pawns[p].setBounds(280 + (pos*78), 44 + ((anak-1)*82), 30,50);
                tablo.revalidate();
                tablo.repaint();
                if((pos==1)||(pos==3)||(pos==5)||(pos==7)||(pos==8)) {
                	stepped_on_finding(player,p,pos,anak);
                }
    		}else if((pawntomove.get_minvalue() != -1)&&(noc==-1)&&(pawntomove.get_pawn_palace()==anak)&&(pawntomove.get_pawn_position()<=6)) {
    			int pos = pawntomove.get_pawn_position();
    			pos++;
    			if((pos==1)||(pos==3)||(pos==5)||(pos==7)||(pos==8)) {
                	stepped_on_finding(player,p,pos,anak);
                }
    			if(pos==6) {
    				game.new_pawn_on_chkp();
    			}
    			pos++;
    			if((pos==1)||(pos==3)||(pos==5)||(pos==7)||(pos==8)) {
                	stepped_on_finding(player,p,pos,anak);
                }
    			if(pos==6) {
    				game.new_pawn_on_chkp();
    			}
    			theseis = monopatia[anak-1].get_path_positions();
    			Position posit = theseis[pos];
    			pawntomove.set_pawn_pos(posit);
    			pawntomove.set_pawn_position(pos);
                p2_pawns[p].setBounds(280 + (pos*78), 44 + ((anak-1)*82), 30,50);
                tablo.revalidate();
                tablo.repaint();
    		}else if((pawntomove.get_minvalue() != -1)&&(noc==-1)&&(pawntomove.get_pawn_palace()==anak)&&(pawntomove.get_pawn_position()==7)) {
    			int pos = pawntomove.get_pawn_position();
    			pos++;
    			if((pos==1)||(pos==3)||(pos==5)||(pos==7)||(pos==8)) {
                	stepped_on_finding(player,p,pos,anak);
                }
    			if(pos==6) {
    				game.new_pawn_on_chkp();
    			}
    			theseis = monopatia[anak-1].get_path_positions();
    			Position posit = theseis[pos];
    			pawntomove.set_pawn_pos(posit);
    			pawntomove.set_pawn_position(pos);
                p2_pawns[p].setBounds(280 + (pos*78), 44 + ((anak-1)*82), 30,50);
                tablo.revalidate();
                tablo.repaint();
    		}else {
    			return false;
    		}
    	}
    	window.setVisible(true);
    	return true;
    }
    
    /**
     * Finds the class of the finding that the pawn has stepped on and calls the proper show_..._dialog function
     * @param player Player's id.
     * @param p Pawn's index.
     * @param pos Position's index.
     * @param anak Path's index.
     */
    public static void stepped_on_finding(int player, int p, int pos,int anak){
    	theseis = monopatia[anak-1].get_path_positions();
    	FindingPosition fipo = (FindingPosition) theseis[pos];
    	Finding fi = fipo.get_finding_in_position();
    	if(p!=3) {
        	if((fi instanceof SnakeGoddessFinding)&&(fipo.get_in_place()==true)) {
        		show_snakegoddess_dialog(player, p, pos, anak);
        	}else if((fi instanceof FrescoFinding)&&(fipo.get_in_place()==true)) {
        		show_frefo_dialog(player, p, pos, anak);
        	}else if((fi instanceof RareFinding)&&(fipo.get_in_place()==true)) {
        		show_raref_dialog(player, p, pos, anak);
        	}
    	}else {
    		if(fipo.get_in_place()==true) {
        		show_destruction_dialog(player, p, pos, anak);
        	}
    	}
    }
    
    /**
     * Function that updates last played cards jlabels.
     * @param c The card that has been played.
     * @param n The Path's index.
     * @param player The player's id.
     */
    public static void updatecardhistory(Card c, int n, int player) {
    	n--;
    	Color col = Color.BLACK;
    	if(n==0) {
    		col = Color.RED;
    	}else if(n==1) {
    		col = Color.YELLOW;
    	}else if(n==2) {
    		col = Color.GRAY;
    	}else if(n==3) {
    		col = Color.BLUE;
    	}
    	c.setIsActive(false);
		ImageIcon originalIcon = c.getImage();
		Image img = originalIcon.getImage().getScaledInstance(60,91, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(img);
		if(player==1) {
			player1_field.remove(cardhistory1_labels[n]);
			cardhistory1_labels[n] = new JLabel(resizedIcon);
			cardhistory1_labels[n].setForeground(Color.BLACK);
			cardhistory1_labels[n].setBackground(Color.WHITE);
			cardhistory1_labels[n].setHorizontalAlignment(SwingConstants.CENTER);
			cardhistory1_labels[n].setVerticalAlignment(SwingConstants.CENTER);
        	cardhistory1_labels[n].setBorder(BorderFactory.createLineBorder(col, 2));
			cardhistory1_labels[n].setOpaque(false);
			cardhistory1_labels[n].setBounds(582 + (n*71), 40, 69, 100);
			player1_field.add(cardhistory1_labels[n]);
		}else {
			player2_field.remove(cardhistory2_labels[n]);
			cardhistory2_labels[n] = new JLabel(resizedIcon);
			cardhistory2_labels[n].setForeground(Color.BLACK);
			cardhistory2_labels[n].setBackground(Color.WHITE);
			cardhistory2_labels[n].setHorizontalAlignment(SwingConstants.CENTER);
			cardhistory2_labels[n].setVerticalAlignment(SwingConstants.CENTER);
        	cardhistory2_labels[n].setBorder(BorderFactory.createLineBorder(col, 2));
			cardhistory2_labels[n].setOpaque(false);
			cardhistory2_labels[n].setBounds(582 + (n*71), 40, 69, 100);
			player2_field.add(cardhistory2_labels[n]);
		}
		window.setVisible(true);
    }
    
    /**
     * Function that removes a certain card from player's hand.
     * @param cardIndex The card that must be removed.
     */
    public static void removepicfromhand(int cardIndex) {
    	card_buttons[cardIndex].setIcon(null);
    	card_buttons[cardIndex].setText("DRAW");
    	card_buttons[cardIndex].setBackground(Color.WHITE);
    	card_buttons[cardIndex].setOpaque(true);
    	card_buttons[cardIndex].setForeground(Color.BLACK);
    	ready_to_draw_card = true;
    	can_select_card=false;
    	window.setVisible(true);
    }
    
    /**
     * Function that when called it update the label that shows the current turn, available cards and amount of pawns that are on checkpoints.
     */
    public void update_availablecards_label() {
    	availablecards_label.setText("<html>Available Cards: "+avlbl_+"<br>Check Points: "+chkpt_+"<br>Turn: Player "+turn_+"</html>");
    	window.setVisible(true);
    }
    
    /**
     * Function that updates the score label of each player.
     * @param player The player's id.
     */
    public static void update_myscore_label(int player) {
    	if(player==1) {
    		int sum=0,i;
    		Pawn[] pawns = p1.get_player_pawns();
    		Position temp_pos;
    		for(i=0;i<3;i++) {
    			temp_pos = pawns[i].get_pawn_pos();
    			if(temp_pos!=null) {
    				sum+= temp_pos.get_position_score();
    			}
    		}
    		temp_pos = pawns[3].get_pawn_pos();
			if(temp_pos!=null) {
				sum+= 2 * temp_pos.get_position_score();
			}
    		ArrayList<FrescoFinding> list = p1.get_player_pics();
    		for(FrescoFinding ff: list) {
    			if(ff.get_foundby1()==true) {
    				sum+= ff.getPoints();
    			}
    		}
    		for(RareFinding rf: all_rare_findings) {
    			if(rf.getOwner()==p1) {
        			sum+= rf.getPoints();
    			}
    		}
    		if(p1.get_player_snakegoddess_count()==0) {
    			sum+= 0;
    		}else if(p1.get_player_snakegoddess_count()==1) {
    			sum+= -20;
    		}else if(p1.get_player_snakegoddess_count()==2) {
    			sum+= -15;
    		}else if(p1.get_player_snakegoddess_count()==3) {
    			sum+= 10;
    		}else if(p1.get_player_snakegoddess_count()==4) {
    			sum+= 15;
    		}else if(p1.get_player_snakegoddess_count()==5) {
    			sum+= 30;
    		}else if(p1.get_player_snakegoddess_count()==6) {
    			sum+= 50;
    		}
    		p1.set_player_score(sum);
    		myscore_label1.setText("Score: "+sum+" points");
    	}else {
    		int sum=0,i;
    		Pawn[] pawns = p2.get_player_pawns();
    		Position temp_pos;
    		for(i=0;i<3;i++) {
    			temp_pos = pawns[i].get_pawn_pos();
    			if(temp_pos!=null) {
    				sum+= temp_pos.get_position_score();
    			}
    		}
    		temp_pos = pawns[3].get_pawn_pos();
			if(temp_pos!=null) {
				sum+= 2 * temp_pos.get_position_score();
			}
    		ArrayList<FrescoFinding> list = p2.get_player_pics();
    		for(FrescoFinding ff: list) {
    			if(ff.get_foundby2()==true) {
    				sum+= ff.getPoints();
    			}
    		}
    		for(RareFinding rf: all_rare_findings) {
    			if(rf.getOwner()==p2) {
        			sum+= rf.getPoints();
    			}
    		}
    		if(p2.get_player_snakegoddess_count()==0) {
    			sum+= 0;
    		}else if(p2.get_player_snakegoddess_count()==1) {
    			sum+= -20;
    		}else if(p2.get_player_snakegoddess_count()==2) {
    			sum+= -15;
    		}else if(p2.get_player_snakegoddess_count()==3) {
    			sum+= 10;
    		}else if(p2.get_player_snakegoddess_count()==4) {
    			sum+= 15;
    		}else if(p2.get_player_snakegoddess_count()==5) {
    			sum+= 30;
    		}else if(p2.get_player_snakegoddess_count()==6) {
    			sum+= 50;
    		}
    		p2.set_player_score(sum);
    		myscore_label2.setText("Score: "+sum+" points");
    	}
    	window.setVisible(true);
    }
    
    /**
     * Function that places a new card in hand, after the player has played one.
     * @param c The new card.
     * @param player The player's id.
     * @param temp_int The card_buttons table index.
     */
    public void updatehand(Card c, int player, int temp_int) {
    	if(player==1) {
        	ImageIcon imageIcon = c.getImage();
        	Image img = imageIcon.getImage().getScaledInstance(69, 100, Image.SCALE_SMOOTH);
        	ImageIcon scaledIcon = new ImageIcon(img);
        	player1_field.remove(card_buttons[temp_int]);
        	card_buttons[temp_int] = new JButton(scaledIcon);
        	card_buttons[temp_int].setPreferredSize(new Dimension(69, 100));
        	card_buttons[temp_int].setBounds(5 + (temp_int*72), 40, 69, 100);
        	card_buttons[temp_int].setBorder(BorderFactory.createEmptyBorder());
        	card_buttons[temp_int].setOpaque(false);
        	card_buttons[temp_int].setActionCommand(Integer.toString(temp_int));
        	card_buttons[temp_int].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	String actionCommand = e.getActionCommand();
                    int passedInt = Integer.parseInt(actionCommand);
                    showChoiceDialog(passedInt);
                }
            });
        	player1_field.add(card_buttons[temp_int]);
    	}else {
        	ImageIcon imageIcon = c.getImage();
        	Image img = imageIcon.getImage().getScaledInstance(69, 100, Image.SCALE_SMOOTH);
        	ImageIcon scaledIcon = new ImageIcon(img);
        	player2_field.remove(card_buttons[temp_int+8]);
        	card_buttons[temp_int+8] = new JButton(scaledIcon);
        	card_buttons[temp_int+8].setPreferredSize(new Dimension(69, 100));
        	card_buttons[temp_int+8].setBounds(5 + (temp_int*72), 40, 69, 100);
        	card_buttons[temp_int+8].setBorder(BorderFactory.createEmptyBorder());
        	card_buttons[temp_int+8].setOpaque(false);
        	card_buttons[temp_int+8].setActionCommand(Integer.toString(temp_int+8));
        	card_buttons[temp_int+8].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	String actionCommand = e.getActionCommand();
                    int passedInt = Integer.parseInt(actionCommand);
                    showChoiceDialog(passedInt);
                }
            });
        	player2_field.add(card_buttons[temp_int+8]);
    	}
    	window.setVisible(true);
    }
    
    /**
     * Dialog that makes the player choose which pawn to play.
     */
    public static void openPawnSelectionDialog() {
        // Create a new modal dialog (window)
        JDialog dialog = new JDialog();
        dialog.setTitle("Select a Pawn");
        dialog.setModal(true); // Make the dialog modal (blocks interaction with the main window)
        dialog.setSize(600, 100);
        dialog.setLayout(new FlowLayout());
        dialog.setLocationRelativeTo(window);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        
        JButton[] pawnButtons = new JButton[4];
        for (int i = 0; i < 3; i++) {
            final int pawnIndex = i;
            pawnButtons[i] = new JButton("Archaeologist " + (i + 1));
            pawnButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedPawn = pawnIndex;
                    dialog.dispose();
                }
            });
            dialog.add(pawnButtons[i]);
        }
        final int pawnIndex = 3;
        pawnButtons[3] = new JButton("Theseus");
        pawnButtons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPawn = pawnIndex;
                dialog.dispose();
            }
        });
        dialog.add(pawnButtons[3]);

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    
    /**
     * Function that when firstly called it initializes all rare findings on each player's pane with a gray filter.
     * When called with n!=0 then it updates the pic of the n-th rare finding of each player, removing the gray filter.
     * @param player_field The player's jlayeredpane.
     * @param n If n==0 then it initializes the gray rare findings, if n>0 then it updates n-th rare finding removing the gray filter.
     * @param player The player's id.
     */
    public static void rarefindings_function(JLayeredPane player_field, int n, int player) {
    	int i;
    	ArrayList<RareFinding> rrf =all_rare_findings;
    	if(n==0) {
    		for (i = 0; i < 4; i++) {
    		    RareFinding raref = rrf.get(i);
		        ImageIcon originalIcon = raref.getImage();
	            Image image = originalIcon.getImage();
	            int originalWidth = image.getWidth(null);
	            int originalHeight = image.getHeight(null);
	            if (originalWidth > 0 && originalHeight > 0) {
	                int newWidth = 36;
	                int newHeight = 40;
	                BufferedImage resizedBufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
	                Graphics2D g2d = resizedBufferedImage.createGraphics();
	                g2d.drawImage(image, 0, 0, newWidth, newHeight, null);
	                g2d.dispose();
	                int resizedWidth = resizedBufferedImage.getWidth();
	                int resizedHeight = resizedBufferedImage.getHeight();
	                if (resizedWidth > 0 && resizedHeight > 0) {
	                    BufferedImage grayscaleImage = new BufferedImage(resizedWidth, resizedHeight, BufferedImage.TYPE_BYTE_GRAY);
	                    Graphics2D g2dGray = grayscaleImage.createGraphics();
	                    g2dGray.drawImage(resizedBufferedImage, 0, 0, null);
	                    g2dGray.dispose();
	                    int finalWidth = 36;
	                    int finalHeight = 40;
	                    Image finalResizedImage = grayscaleImage.getScaledInstance(finalWidth, finalHeight, Image.SCALE_SMOOTH);
	                    int finalWidthCheck = finalResizedImage.getWidth(null);
	                    int finalHeightCheck = finalResizedImage.getHeight(null);
	                    if (finalWidthCheck > 0 && finalHeightCheck > 0) {
	                        ImageIcon imageIcon = new ImageIcon(finalResizedImage);
	                        rarefindings_labels[i + ((player - 1) * 4)] = new JLabel(imageIcon);
	                        rarefindings_labels[i + ((player - 1) * 4)].setBounds(595 + (i * 70), 145, finalWidth, finalHeight);
	                        player_field.add(rarefindings_labels[i + ((player - 1) * 4)]);
	                    } else {
	                        System.out.println("Final resized grayscale image has invalid dimensions for index " + i);
	                    }
	                } else {
	                    System.out.println("Resized image has invalid dimensions for index " + i);
	                }
		        } else {
		            System.out.println("Image is null for index " + i);
		        }
    		}
    	}else{
    		player_field.remove(rarefindings_labels[(n-1) + ((player-1)*4)]);
			RareFinding raref = rrf.get(n-1);
			ImageIcon originalIcon = raref.getImage();
			Image img = originalIcon.getImage().getScaledInstance(36,40, Image.SCALE_SMOOTH);
    		ImageIcon resizedIcon = new ImageIcon(img);
    		rarefindings_labels[(n-1) + ((player-1)*4)] = new JLabel(resizedIcon);
    		rarefindings_labels[(n-1) + ((player-1)*4)].setBounds(595 + ((n-1)*70), 145, 36,40);
    		player_field.add(rarefindings_labels[(n-1) + ((player-1)*4)]);
    	}
    }
    
    /**
     * Function that checks if the game is over.
     * @return bool True if the game is over, false if else.
     */
    public static boolean is_game_over() {
    	if((game.get_pawns_on_chkp()>=4)||(game.get_avlblcards() == 0)) {
    		return true;
    	}else {
    		return false;
    	}
    }
    
    /**
     * When called it finds the winner based of the game's rules and announces the winner on a new dialog.
     */
    public static void announce_winner() {
    	String message = "";
    	if(p1.get_player_score() > p2.get_player_score()) {
    		message = "Player1 wins the game!";
    	}else if(p1.get_player_score() < p2.get_player_score()) {
    		message = "Player2 wins the game!";
    	}else if(p1.get_player_score() == p2.get_player_score()) {
    		boolean flag = true;
    		ArrayList<RareFinding> rare1_list = p1.get_list_findings();
    		ArrayList<RareFinding> rare2_list = p2.get_list_findings();
    		if(rare1_list.size() > rare2_list.size()) {
    			message = "Player1 wins the game!";
    			flag = false;
    		}else if(rare1_list.size() < rare2_list.size()) {
    			message = "Player2 wins the game!";
    			flag = false;
    		}
    		ArrayList<FrescoFinding> fresco1_list = p1.get_player_pics();
    		ArrayList<FrescoFinding> fresco2_list = p2.get_player_pics();
    		if((fresco1_list.size() > fresco2_list.size())&&(flag)) {
    			message = "Player1 wins the game!";
    			flag = false;
    		}else if((fresco1_list.size() < fresco2_list.size())&&(flag)) {
    			message = "Player2 wins the game!";
    			flag = false;
    		}
    		int snake1_count = p1.get_player_snakegoddess_count();
    		int snake2_count = p2.get_player_snakegoddess_count();
    		if((snake1_count > snake2_count)&&(flag)) {
    			message = "Player1 wins the game!";
    			flag = false;
    		}else if((snake1_count < snake2_count)&&(flag)) {
    			message = "Player2 wins the game!";
    			flag = false;
    		}
    		if(flag) {
    			message = "It's a tie!";
    		}
    	}
        int option = JOptionPane.showOptionDialog(window, message, "Game Over", 
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
                null, new Object[] { "OK" }, null);
        if (option == 0) {
            System.exit(0);
        }
    }
    
    /**
     * Actionlistener of player's1 button "My Frescoes". It opens a new dialog with all 6 pics of the frescoes.
     * Each fresco the player has not found has a gray filter, else it is shown without the filter.
     * @param button The player's1 "My Frescoes" button.
     * @param frf The ArrayList with all 6 frescoes.
     */
    public static void showmyfrescos1_actionlistener(JButton button, ArrayList<FrescoFinding> frf) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame newWindow = new JFrame("Player 1 - My Frescoes");
                newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                newWindow.setSize(500, 500);
                newWindow.setLayout(new GridLayout(3, 2, 10, 10));
                for (int i=0; i < 6; i++) {
                	FrescoFinding f = frf.get(i);
                	if(f.get_foundby1()) {
                		ImageIcon imageIcon = f.getImage();
                        JLabel label = new JLabel(imageIcon);
                        label.setHorizontalAlignment(JLabel.CENTER);
                        newWindow.add(label);
                	}else {
                		ImageIcon originalIcon = f.getImage();
                		Image image = originalIcon.getImage();
                        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2d = bufferedImage.createGraphics();
                        g2d.drawImage(image, 0, 0, null);
                        g2d.dispose();
                        BufferedImage grayscaleImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
                        Graphics2D g2dGray = grayscaleImage.createGraphics();
                        g2dGray.drawImage(bufferedImage, 0, 0, null);
                        g2dGray.dispose();

                        ImageIcon imageIcon = new ImageIcon(grayscaleImage);
                        JLabel label = new JLabel(imageIcon);
                        label.setHorizontalAlignment(JLabel.CENTER);
                        newWindow.add(label);
                	}
                }
                newWindow.setVisible(true);
            }
        });
    }
    
    /**
     * Actionlistener of player's2 button "My Frescoes". It opens a new dialog with all 6 pics of the frescoes.
     * Each fresco the player has not found has a gray filter, else it is shown without the filter.
     * @param button The player's2 "My Frescoes" button.
     * @param frf The ArrayList with all 6 frescoes.
     */
    public static void showmyfrescos2_actionlistener(JButton button, ArrayList<FrescoFinding> frf) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame newWindow = new JFrame("Player 2 - My Frescoes");
                newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                newWindow.setSize(500, 500);
                newWindow.setLayout(new GridLayout(3, 2, 10, 10));
                for (int i=0; i < 6; i++) {
                	FrescoFinding f = frf.get(i);
                	if(f.get_foundby2()) {
                		ImageIcon imageIcon = f.getImage();
                        JLabel label = new JLabel(imageIcon);
                        label.setHorizontalAlignment(JLabel.CENTER);
                        newWindow.add(label);
                	}else {
                		ImageIcon originalIcon = f.getImage();
                		Image image = originalIcon.getImage();
                        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2d = bufferedImage.createGraphics();
                        g2d.drawImage(image, 0, 0, null);
                        g2d.dispose();
                        BufferedImage grayscaleImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
                        Graphics2D g2dGray = grayscaleImage.createGraphics();
                        g2dGray.drawImage(bufferedImage, 0, 0, null);
                        g2dGray.dispose();

                        ImageIcon imageIcon = new ImageIcon(grayscaleImage);
                        JLabel label = new JLabel(imageIcon);
                        label.setHorizontalAlignment(JLabel.CENTER);
                        newWindow.add(label);
                	}
                }
                newWindow.setVisible(true);
            }
        });
    }
}