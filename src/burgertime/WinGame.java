/**
 * 
 */
package burgertime;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * TODO Put here a description of what this class does.
 *
 * @author obrienm, trowbrct, xiaox
 *         Created Feb 16, 2014.
 */
public class WinGame extends JFrame {
	private final int WIDTH = 400;
	private final int HEIGHT = 500;
	private Score score;
	
	public WinGame(Score score){
		this.setSize(WIDTH, HEIGHT);
		this.score=score;
		
		JPanel panel2 = new JPanel();
		JLabel gameOver = new JLabel();
		gameOver.setText("YOU WIN!");
		gameOver.setFont(new Font("Dialog",Font.BOLD,30));
		gameOver.setSize(400, 200);
		panel2.add(gameOver);
		this.add(panel2, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		JButton playAgain = new JButton("Play Again!");
		playAgain.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Game game = null;
				try {
					game = new Game();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				game.gameFrame();
			}

		});	
		panel.add(playAgain);
		this.add(panel, BorderLayout.SOUTH);
		
//		JPanel highScorePanel = new JPanel();
//		panel.setLayout(new GridLayout(5, 2));
//		JLabel label1 = new JLabel(this.score.getHighScore(0)+" ");
//		JLabel label2 = new JLabel(this.score.getHighScore(1)+" ");
//		JLabel label3 = new JLabel(this.score.getHighScore(2)+" ");
//		JLabel label4 = new JLabel(this.score.getHighScore(3)+" ");
//		JLabel label5 = new JLabel(this.score.getHighScore(4)+" ");
//		label1.setSize(30, 35);
//		label1.setFont(new Font("Dialog", Font.BOLD, 20));
//		label1.setHorizontalAlignment(SwingConstants.CENTER);
//		highScorePanel.add(label1);
//		highScorePanel.add(label2);
//		highScorePanel.add(label3);
//		highScorePanel.add(label4);
//		highScorePanel.add(label5);
//		if (this.score.getScore()>this.score.getHighScore(4)){
//			this.score.recordHighScore("a",this.score.getScore());
//			System.out.println("true");
//		}

		this.setVisible(true);
	}
}
