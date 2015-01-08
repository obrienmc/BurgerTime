/**
 * 
 */
package burgertime;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Displays a help frame when user clicks the help button on the game frame.
 * 
 * @author xiaox. trowbrct. obrienm.
 */
public class HelpFrame extends JFrame {
	private final int WIDTH1 = 350;
	private final int HEIGHT1 = 350;
	public DrawableComponent draw;

	/**
	 * Creates the help frame, and panel for the back button
	 * 
	 */
	public HelpFrame(DrawableComponent draw) {
		this.draw = draw;
		this.setTitle("Help?");
		this.setSize(this.WIDTH1, this.HEIGHT1);

		JPanel panel1 = new JPanel();
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				HelpFrame.this.draw.setIsPaused(false);
			}

		});
		panel1.add(back);
		this.add(panel1, BorderLayout.NORTH);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 1));
		JLabel label1 = new JLabel();
		JLabel label2 = new JLabel();
		JLabel label3 = new JLabel();
		JLabel label4 = new JLabel();
		JLabel label5 = new JLabel();
		JLabel label6 = new JLabel();
		label1.setText("Information");
		label1.setFont(new Font("Dialog", Font.BOLD, 17));
		label2.setText("        Move the hero with arrow keys  \n");
		label3.setText("        Press 'Space Bar' to spray pepper  \n");
		label4.setText("        Press 'U' to up the level  \n");
		label5.setText("        Press 'D' to down the level  \n");
		label6.setText("        Press 'P' to pause/resume the game  \n");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label1);
		panel.add(label2);
		panel.add(label3);
		panel.add(label4);
		panel.add(label5);
		panel.add(label6);
		this.add(panel, BorderLayout.CENTER);
		this.setVisible(true);
	}

}
