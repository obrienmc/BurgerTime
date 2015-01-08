package burgertime;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * @author trowbrct. xiaox. obrienm.
 */
public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// creates the main frame which displays the start button and
		// information
		final JFrame mainFrame = new JFrame();
		mainFrame.setSize(700, 550);
		mainFrame.setTitle("Game Menu");
		mainFrame.setLocationRelativeTo(null);

		// creates the start button
		JButton newGameButton = new JButton("Start New Game");
		mainFrame.getContentPane().setBackground(Color.BLACK);
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.BLACK);
		newGameButton.setPreferredSize(new Dimension(180, 30));
		newGameButton.setFont(new Font("Dialog", Font.ITALIC, 17));
		panel1.add(newGameButton);
		mainFrame.add(panel1, BorderLayout.SOUTH);
		
		JPanel stuff = new JPanel();
		stuff.setBackground(Color.BLACK);
		Icon icon = new ImageIcon("burger.gif");
		JLabel label = new JLabel(icon);
		stuff.add(label, BorderLayout.NORTH);
		
		Icon icon2 = new ImageIcon("time.png");
		JLabel title = new JLabel(icon2);
		mainFrame.add(title, BorderLayout.NORTH);
		mainFrame.add(stuff, BorderLayout.CENTER);
		
		newGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setVisible(false);
				Game gameWindow = null;
				try {
					gameWindow = new Game();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				gameWindow.gameFrame();
			}

		});
	

		// displays the information
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 2));
		JLabel label1 = new JLabel();
		JLabel label2 = new JLabel();
		JLabel label3 = new JLabel();
		JLabel label4 = new JLabel();
		JLabel label5 = new JLabel();
		JLabel label6 = new JLabel();
		label1.setText("Information");
		label1.setSize(30, 35);
		label1.setFont(new Font("Dialog", Font.BOLD, 20));
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label2.setText("    Move the hero with arrow keys;  \n");
		label3.setText("    Press 'Space Bar' to spray pepper straight ahead;  \n");
		label4.setText("    Press 'U' to up the level;  \n");
		label5.setText("    Press 'D' to down the level;  \n");
		label6.setText("    Press 'P' to pause/resume the game.  \n");
		panel.add(label1);
		panel.add(label2);
		panel.add(label3);
		panel.add(label4);
		panel.add(label5);
		panel.add(label6);

//		mainFrame.add(panel, BorderLayout.SOUTH);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

}
