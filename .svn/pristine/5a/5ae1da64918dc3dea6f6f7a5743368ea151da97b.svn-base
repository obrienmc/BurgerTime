/**
 * 
 */
package burgertime;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * This class is abstract for the Hero and Guards
 * 
 * @author xiaox. trowbrct. obrienm.
 */
public abstract class AbstractCharacter implements Temporal, Drawable {
	protected final Point2D STARTPOINT;
	protected Point2D currentPosition;
	protected ArrayList<Rectangle2D> pathList;
	protected double width;
	protected double height;
	protected boolean isPaused;
	protected boolean isOnPath;
	protected Score score;

	/**
	 * sets the position and size of the character.
	 * 
	 * @param start
	 * @param characterWidth
	 * @param characterHeight
	 * @param pathList
	 */
	AbstractCharacter(Point2D start, double characterWidth,
			double characterHeight, ArrayList<Rectangle2D> pathList,Score score) {
		this.pathList = pathList;
		this.STARTPOINT = start;
		this.currentPosition = this.STARTPOINT;
		this.width = characterWidth;
		this.height = characterHeight;
		this.score=score;
	}

	// -------------------------------------------------------------------------
	// Temporal interface

	@Override
	public void timePassed() {
		this.updatePosition(0, 0);
	}

	@Override
	public void die() {
		// add code to make character die
	}

	@Override
	public boolean getIsPaused() {
		// not yet implemented
		return false;
	}

	@Override
	public void setIsPaused(boolean isPaused) {
		// not yet implemented
	}

	/**
	 * updates the position of the character, moving them up, down, left, or
	 * right.
	 * 
	 * @param x
	 * @param y
	 */
	public abstract void updatePosition(double x, double y);

	/**
	 * Checks the position of the character returns true if it is on path,
	 * making it able to update it's position returns false, disallowing the
	 * character to move to the new position
	 * 
	 * @param pointList
	 * @return isOnPath
	 */
	public abstract boolean isOnPath(Point2D[] pointList);
	
	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param score
	 */
	public void addScore(int score){
		this.score.addScore(score);
	}
	
	public abstract BufferedImage getImage();

	
}
