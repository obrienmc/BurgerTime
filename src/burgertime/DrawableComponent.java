/**
 * 
 */
package burgertime;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * DrawableComponent class draws everything in the game, handles pausing /
 * resuming
 * 
 * @author xiaox. trowbrct. obrienm.
 */
public class DrawableComponent extends JComponent implements Temporal {
	private static final int FRAMES_PER_SECOND = 30;
	private static final long REPAINT_INTERVAL_MS = 1000 / FRAMES_PER_SECOND;
	
	public boolean displayPowerup;
	public long timePassed;
	private double powerupTime;
	public Ellipse2D powerUp;
	public BufferedImage powerup;
	
	public boolean[] isLevelThreading;
	private boolean leveledUp;
	
	private Rectangle2D heroRect;
	@SuppressWarnings("javadoc")
	public Hero hero;
	public Burger[] burger;
	private Runnable[] guard;
	private ArrayList<Drawable> drawableParts;
	private ArrayList<Rectangle2D> pathList;
	private Score score;
	private boolean isPaused;

	@SuppressWarnings("javadoc")
	public Level level;

	private Thread guardThread1;
	private Thread guardThread2;
	private Thread guardThread3;
	private Thread guardThread4;
	
	public Game game;
	/**
	 * loads the level, starts the threads, initializes all of the variables
	 * @throws IOException 
	 * 
	 */
	public DrawableComponent(Game game,Score score) throws IOException {
		this.isLevelThreading = new boolean[4];
		this.isLevelThreading[0] = true;
		this.isLevelThreading[1] = false;
		this.isLevelThreading[2] = false;
		this.isLevelThreading[3] = false;
		this.score=score;
		this.game = game;
		this.level = new Level(this,this.score);
		this.level.loadLevel();
		
		this.initialize();
		this.startThread();
		this.powerup = ImageIO.read(new File("shroom.png"));
	}

	@SuppressWarnings("deprecation")
	private void startThread() {
		// Creates a separate "thread of execution" to trigger periodic
		// repainting of this component.
		Runnable repainter = new Runnable() {
			@Override
			public void run() {
				// Periodically asks Java to repaint this component
				try {
					while (true) {
						Thread.sleep(REPAINT_INTERVAL_MS);
						repaint();
						timePassed();
					}
				} catch (InterruptedException exception) {
					// Stop when interrupted
				}
			}
		};
		new Thread(repainter).start();

		this.guardThread1.resume();
		this.guardThread2.resume();
		this.guardThread3.resume();
		this.guardThread4.resume();
		for (int i = 0; i < 4; i++) {
			this.burger[i].fallThread.resume();
		}
	}

	/**
	 * Initialize all of the variables from the level class
	 * 
	 */
	public void initialize() {
		//random number between 10 and 20 for powerup spawn time
		this.powerupTime = 10 + (int)(Math.random() * ((10) + 1));
		this.displayPowerup = false;
		this.timePassed = System.currentTimeMillis();

		if (this.level.currentLevel == 1){
			this.powerUp = new Ellipse2D.Double(330, 200, 20, 20);
		} else if (this.level.currentLevel == 2){
			this.powerUp = new Ellipse2D.Double(315, 200, 20, 20);
		} else if (this.level.currentLevel == 3){
			this.powerUp = new Ellipse2D.Double(315, 180, 20, 20);
		} else {
			this.powerUp = new Ellipse2D.Double(315, 180, 20, 20);
		}
		
		this.leveledUp = false;
		this.isLevelThreading[this.level.currentLevel - 1] = true;
		
		this.pathList = new ArrayList<Rectangle2D>();
		this.pathList = this.level.getPath();
		this.pathList.addAll(this.level.getPlates());

		this.guard = new Guard[4];
		this.guard[0] = this.level.getGuard(0);
		this.guard[1] = this.level.getGuard(1);
		this.guard[2] = this.level.getGuard(2);
		this.guard[3] = this.level.getGuard(3);

		this.burger = new Burger[4];
		this.burger[0] = this.level.getBurger(0);
		this.burger[1] = this.level.getBurger(1);
		this.burger[2] = this.level.getBurger(2);
		this.burger[3] = this.level.getBurger(3);
		for (int i=0;i<this.burger.length;i++){
			this.burger[i].readImage();
		}
		
		this.heroRect = this.level.getHero();
		this.hero = new Hero(new Point2D.Double(this.heroRect.getX(),
				this.heroRect.getY()), this.heroRect.getWidth(),
				this.heroRect.getHeight(), this.pathList, this.burger,
				(Guard[]) this.guard,this.score);
		this.hero.lives = 3;

		this.hero.guards = (Guard[]) this.guard;
		this.hero.burgers = this.burger;

		this.drawableParts = new ArrayList<Drawable>();
		for (int i = 0; i < this.guard.length; i++) {
			this.drawableParts.add((Guard) this.guard[i]);
		}
		this.drawableParts.add(this.hero);

		this.guardThread1 = new Thread(this.guard[0]);
		this.guardThread2 = new Thread(this.guard[1]);
		this.guardThread3 = new Thread(this.guard[2]);
		this.guardThread4 = new Thread(this.guard[3]);

		this.guardThread1.start();
		this.guardThread2.start();
		this.guardThread3.start();
		this.guardThread4.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		for (int sprays = 1; sprays <= this.hero.pepperSprays; sprays++) {
			g2.setColor(new Color(150, 130, 10, 155));
			Ellipse2D.Double spray = new Ellipse2D.Double(35 * sprays + 120,
					505, 30, 30);
			g2.fill(spray);
		}

		for (int lives = 1; lives <= this.hero.lives; lives++) {
			try {
				BufferedImage image=ImageIO.read(new File("image/chef.png"));
				g2.drawImage(image,null,30*lives+10,500);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < this.pathList.size(); i++) {
			this.drawPathway(g2, this.pathList.get(i));
		}

		for (Drawable d : this.drawableParts) {
			this.drawDrawable(g2, d);
		}

		for (int i = 0; i < this.burger.length; i++) {
			for (Burger d : this.burger) {
				this.drawBurger(g2, d);
			}
		}

		if (this.hero.pepperDeployed) {
			g2.setColor(new Color(150, 130, 10, 155));
			g2.fill(this.hero.deployPepperSpray());
		}
		
		if (this.displayPowerup) {
			if (this.isPaused) {
				this.displayPowerup = false;
			} else {
				g2.draw(this.powerUp);
				int x = (int) this.powerUp.getX();
				int y = (int) this.powerUp.getY();
				g2.drawImage(this.powerup, null, x, y);
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						DrawableComponent.this.displayPowerup = false;
						DrawableComponent.this.timePassed = System
								.currentTimeMillis();
					}
				}, 4500);
			}
		}
	}

	/**
	 * This helper method draws the given drawable object on the given graphics
	 * area.
	 * 
	 * @param g2
	 *            the graphics area on which to draw
	 * @param d
	 *            the drawable object
	 */
	private void drawDrawable(Graphics2D g2, Drawable d) {
		Shape shape = d.getShape();
		g2.drawImage(d.getImage(), null,
				(int) ((RectangularShape) shape).getX(),
				(int) ((RectangularShape) shape).getY());
	}

	private void drawPathway(Graphics2D g2, Rectangle2D pathSegment) {
		if (pathSegment.getHeight() > pathSegment.getWidth()) {
			for (int i = 0; i < pathSegment.getHeight(); i += 4) {
				g2.setColor(Color.DARK_GRAY);
				g2.fill(new Rectangle2D.Double(pathSegment.getX(), pathSegment
						.getY() + i, pathSegment.getWidth(), 2));
			}
		} else {
			g2.setColor(Color.DARK_GRAY);
			g2.fill(pathSegment);
		}
	}

	private void drawBurger(Graphics2D g2, Burger burger) {
		Rectangle2D[][] shape = burger.getShape();
		BufferedImage[][] image = burger.getColor();

		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				g2.drawImage(image[i][j], null, (int) shape[i][j].getX(),
						(int) shape[i][j].getY());
			}
		}
	}

	/**
	 * When the hero dies, reset position of the characters and burgers
	 * 
	 */
	public void resetCharacters() {
		this.hero.resetPosition();
		this.timePassed = System.currentTimeMillis();
		for (int i = 0; i < 4; i++) {
			((Guard) this.guard[i]).resetPosition();
			this.burger[i].resetPosition();
		}
	}

	@Override
	public void timePassed() {
		if (System.currentTimeMillis() - this.timePassed >= this.powerupTime * 1000){
			this.displayPowerup = true;
			this.timePassed = System.currentTimeMillis();
		}
		
		if (this.displayPowerup){
			if (this.powerUp.intersects(this.hero.character)){
				this.score.addScore(500);
				this.hero.pepperSprays++;
				this.displayPowerup = false;
			}
		}
		
		if (!this.hero.pepperDeployed) {
			if (this.hero.isDead()) {
				this.resetCharacters();
				this.hero.heroState = false;
			}
			if (this.hero.contactWithGuard(this.hero.getHeroCorners(0, 0))) {
				this.hero.die();

			}
		}
		if (this.hero.lives == 0) {
			this.isLevelThreading[this.level.currentLevel - 1] = false;
			this.game.gameOver(true);
			this.hero.lives = -1;
		}
		

		int plateCount = 0;
		if (!this.leveledUp) {
			for (int i = 0; i < 4; i++) {
				if (this.burger[i].isPlated()) {
					plateCount++;
				}
			}
		}
		if (plateCount == 4) {
			this.leveledUp = true;
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					int level = DrawableComponent.this.level.currentLevel;
					DrawableComponent.this.level.upLevel();
					if (level != DrawableComponent.this.level.currentLevel) {
						DrawableComponent.this.leveledUp = false;
					}
				}
			}, 1500);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.Temporal#die()
	 */
	@Override
	public void die() {
		// System.out.println("BOOM");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.Temporal#setIsPaused(boolean)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void setIsPaused(boolean isPaused) {
		this.isPaused = isPaused;
		for (int i = 0; i < 4; i++) {
			this.burger[i].setIsPaused(this.isPaused);
		}
		if (!this.isPaused) {
			startThread();
		} else {
			this.guardThread1.suspend();
			this.guardThread2.suspend();
			this.guardThread3.suspend();
			this.guardThread4.suspend();
			for (int i = 0; i < 4; i++) {
				this.burger[i].fallThread.suspend();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.Temporal#getIsPaused()
	 */
	@Override
	public boolean getIsPaused() {
		return this.isPaused;
	}

}
