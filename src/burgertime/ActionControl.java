package burgertime;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * 
 */

/**
 * This class handles the keyboard input for the BurgerTime game
 * 
 * @author xiaox. trowbrct. obrienm.
 */
public class ActionControl {
	private JPanel panel;
	private boolean isPaused;

	/**
	 * binds all of the keys to handle input
	 * 
	 * @param panel
	 * @param draw
	 */
	public ActionControl(JPanel panel, DrawableComponent draw) {
		this.panel = panel;
		this.isPaused = false;
		this.bindKeys(draw);

	}

	/**
	 * binds the keys and their actions
	 * 
	 * @param draw
	 */
	public void bindKeys(DrawableComponent draw) {
		// must be final for inner class usage
		final DrawableComponent drawClass = draw;

		// right arrow key
		this.panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("RIGHT"), "right");
		this.panel.getActionMap().put("right", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ActionControl.this.isPaused) {
					drawClass.hero.updatePosition(1, 0);
				}
			}

		});

		// left arrow key
		this.panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("LEFT"), "left");
		this.panel.getActionMap().put("left", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ActionControl.this.isPaused) {
					drawClass.hero.updatePosition(-1, 0);
				}
			}

		});

		// up key
		this.panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("UP"), "up");
		this.panel.getActionMap().put("up", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ActionControl.this.isPaused) {
					drawClass.hero.updatePosition(0, -1);
				}
			}

		});

		// down key
		this.panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("DOWN"), "down");
		this.panel.getActionMap().put("down", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ActionControl.this.isPaused) {
					drawClass.hero.updatePosition(0, 1);
				}
			}

		});

		// p key
		this.panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("P"), "pause");
		this.panel.getActionMap().put("pause", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (ActionControl.this.isPaused) {
					ActionControl.this.isPaused = false;
				} else {
					ActionControl.this.isPaused = true;
				}
				drawClass.setIsPaused(ActionControl.this.isPaused);
			}

		});

		// u key
		this.panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("U"), "upLevel");
		this.panel.getActionMap().put("upLevel", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ActionControl.this.isPaused) {
					drawClass.level.upLevel();
				}
			}

		});

		// d key
		this.panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("D"), "downLevel");
		this.panel.getActionMap().put("downLevel", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ActionControl.this.isPaused) {
					drawClass.level.downLevel();
				}
			}

		});

		// space bar
		this.panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("SPACE"), "spray pepper");
		this.panel.getActionMap().put("spray pepper", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ActionControl.this.isPaused) {
					if (drawClass.hero.pepperSprays > 0) {
						drawClass.hero.pepperDeployed = true;
					}
					drawClass.hero.pepperSprays--;
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							drawClass.hero.pepperDeployed = false;
						}
					}, 3000);

				}
			}
		});

		// cheat for no restrictions
		this.panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK
						| InputEvent.SHIFT_MASK), "cheat");
		this.panel.getActionMap().put("cheat", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (drawClass.hero.cheat == true) {
					drawClass.hero.cheat = false;
					drawClass.hero.VELOCITY = 4;
				} else {
					drawClass.hero.cheat = true;
					drawClass.hero.VELOCITY = 8;

					try {
						URL url = new URL(
								"http://www.midishrine.com/midipp/nes/Super_Mario_Bros/smb1-star.mid");
						Clip clip = AudioSystem.getClip();
						AudioInputStream ais = AudioSystem
								.getAudioInputStream(url);
						clip.open(ais);
						clip.start();
				
					} catch (Exception e) {
						//
					}

				}
			}

		});

	}

}
