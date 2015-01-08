/**
 * 
 */
package burgertime;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Game class opens the game frame, adds the drawable component, action control,
 * and help menu to the frame.
 * 
 * @author xiaox. trowbrct. obrienm. Created Feb 1, 2014.
 */
public class Game {
	private final int WIDTH = 683;
	private final int HEIGHT = 650;
	public DrawableComponent draw;
	public JFrame frame;
	public Score score=new Score();
	@SuppressWarnings("unused")
	private ActionControl keyHandler;

	/**
	 * Creates an instance of the Drawable Component
	 * @throws IOException 
	 * 
	 */
	public Game() throws IOException {
		this.draw = new DrawableComponent(this,this.score);
		this.frame = new JFrame();
//		this.frame.setLocationRelativeTo(null);
		
//		Icon cloud = new ImageIcon("clouds.png");
//		JLabel label = new JLabel(cloud);
//		
//		this.frame.setContentPane(label);
	}
	
	/**
	 * TODO Put here a description of what this method does.
	 *
	 */
	public void gameOver(boolean status){
		this.frame.setVisible(false);
		@SuppressWarnings("unused")
		JFrame gameOver = new GameOver(this.score, status);
		gameOver.setSize(356,400);
	}
	
	/**
	 * TODO Put here a description of what this method does.
	 *
	 */
	public void gameWon(){
		this.frame.setVisible(false);
		@SuppressWarnings("unused")
		JFrame WinGame = new WinGame(this.score);
	}

	/**
	 * Creates the game frame, adds component, help button, and action control
	 * for keyboard handling
	 * 
	 */
	public void gameFrame() {
		JPanel actionPanel = new JPanel();
		this.keyHandler = new ActionControl(actionPanel, this.draw);
		this.frame.add(actionPanel);
		this.frame.setTitle("BurgerTime Game");
		this.frame.setSize(this.WIDTH, this.HEIGHT);
		this.frame.getContentPane().setBackground(Color.BLACK);
		
		JButton help = new JButton("Help?");
		help.setFocusable(false);
		JPanel helpPanel = new JPanel();
		help.setPreferredSize(new Dimension(80, 30));
		help.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Game.this.draw.setIsPaused(true);
				@SuppressWarnings("unused")
				JFrame helpFrame = new HelpFrame(Game.this.draw);
				
			}

		});
		helpPanel.add(help);
		helpPanel.add(this.score.getLabel());
		helpPanel.add(this.score.getHighScoreLabel());
		this.frame.add(helpPanel, BorderLayout.SOUTH);
		this.frame.getContentPane().add(this.draw);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
	}
}
