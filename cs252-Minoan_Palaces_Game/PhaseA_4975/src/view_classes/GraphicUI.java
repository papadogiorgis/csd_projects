package view_classes;
import javax.swing.*;
import java.awt.event.*;

/**
 * Pubblic class "GraphicUI"
 * @author Georgios Papadakis csd4975@csd.uoc.gr
 */
public class GraphicUI extends JFrame  {
	/**
	 * Players' fields
	 */
	private JDesktopPane player1_field;
    private JDesktopPane player2_field;
    
    /**
     * Button to view frescos window
     */
    private JButton MyFrescos;
    
    /**
     * Button to draw card
     */
    private JButton DeckButton;
    
    /**
     * Buttons of the 16 cards
     */
    private JButton[] buttons = new JButton[16];
    
    /**
     * Constructor
     */
    public GraphicUI() {}
    
    /**
     * Initializes components and buttons
     */
    public void initComponents() {}

	/**
	 * Private class "PlayListener"
	 * It is used to make actions after a card button has been pushed
	 */
	private class PlayListener implements ActionListener 	{
		public void actionPerformed(ActionEvent e) {}
	}
}