package burgertime;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * TODO Put here a description of what this class does.
 *
 * @author xiaox.
 *         Created Feb 4, 2014.
 */
public class Burger implements Temporal{

	public Rectangle2D[][] burger=new Rectangle2D[4][5];
	private int[][] burgerStatus=new int[4][5];
	private Color[] colorList={Color.ORANGE,Color.GREEN,Color.RED,Color.ORANGE};
	private ArrayList<Rectangle2D> position;
	private ArrayList<Rectangle2D> bars;
	//private int numberDepressed;
	//private boolean isDepressed;
	private boolean[] isPlated={false,false,false,false};
	private boolean[] isMoving={false,false,false,false};
	private int itemsPlated;
	private boolean isPaused;
	public Thread fallThread;
	private boolean[] hitBurger={false,false,false,false};
	private Score score;
	private BufferedImage[][] image=new BufferedImage[4][5];
	
	/**
	 * TODO Put here a description of what this constructor does.
	 * @param burger 
	 * @param bars 
	 *
	 */
	public Burger(ArrayList<Rectangle2D> burger, ArrayList<Rectangle2D> bars,Score score){
		this.bars=bars;
		this.fallThread = new Thread();
		this.position=burger; //record the burger's current position 
		this.isPaused=false;
		for (int i=0;i<this.burger.length;i++){
			//get the Rectangle's left-corner point;
			double x=this.position.get(i).getX();
			double y=this.position.get(i).getY();
			//get the Rectangle's width and height and divide their width into 5 pieces
			double width=(this.position.get(i).getWidth())/5;
			double height=this.position.get(i).getHeight();
			//initialize small rectangles
			for (int j=0;j<this.burger[i].length;j++){
				this.burger[i][j]=new Rectangle2D.Double(x+width*j,y,width,height);
				this.burgerStatus[i][j]=0;
			}
		}
		this.score=score;
	}
	/* (non-Javadoc)
	 * @see burgettime.Temporal#timePassed()
	 */
	@Override
	public void timePassed() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see burgettime.Temporal#die()
	 */
	@Override
	public void die() {
		// Do nothing, Burger will not die
	}

	/* (non-Javadoc)
	 * @see burgettime.Temporal#setIsPaused(boolean)
	 */
	@Override
	public void setIsPaused(boolean isPaused) {
		// TODO Auto-generated method stub
		this.isPaused = isPaused;

	}

	/* (non-Javadoc)
	 * @see burgettime.Temporal#getIsPaused()
	 */
	@Override
	public boolean getIsPaused() {
		// TODO Auto-generated method stub
		return this.isPaused;
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param index
	 * @return
	 */
	public boolean getIsMoving(int index){
		return this.isMoving[index];
	}
	/**
	 * TODO Put here a description of what this method does.
	 * @param x 
	 * @param y
	 */
	
	public void depressSection(double x, double y){
		for (int i=0;i<this.burger.length;i++){
			for (int j=0;j<this.burger[0].length;j++){
				if (x>=this.burger[i][j].getX() && x<=this.burger[i][j].getX()+this.burger[i][j].getWidth()
					&& y>=this.burger[i][j].getY()-4 && y<=this.burger[i][j].getY()+this.burger[i][j].getHeight()
					&& this.burgerStatus[i][j]!=2){
					this.burger[i][j].setRect(this.burger[i][j].getX(),this.burger[i][j].getY()+(2-this.burgerStatus[i][j])*3,
							this.burger[i][j].getWidth(),this.burger[i][j].getHeight());
					if (j!=0 && this.burgerStatus[i][j-1]!=2){
						this.burger[i][j-1].setRect(this.burger[i][j-1].getX(),this.burger[i][j-1].getY()+(1-this.burgerStatus[i][j-1])*3,
								this.burger[i][j-1].getWidth(),this.burger[i][j-1].getHeight());
						this.burgerStatus[i][j-1]=1;
					}
					if (j!=this.burger[0].length-1 && this.burgerStatus[i][j+1]!=2){
						this.burger[i][j+1].setRect(this.burger[i][j+1].getX(),this.burger[i][j+1].getY()+(1-this.burgerStatus[i][j+1])*3,
								this.burger[i][j+1].getWidth(),this.burger[i][j+1].getHeight());
						this.burgerStatus[i][j+1]=1;
					}
					this.burgerStatus[i][j]=2;
				}
			}
		}
		for (int i=0;i<this.burger.length;i++){
			int count=0;
			for (int j=0;j<this.burger[i].length;j++){
				if (this.burgerStatus[i][j]==2){
					count++;
				}
			}
			if (count==5){
				this.fall(i);
			}
		}
	}
	
	/**
	 * TODO Put here a description of what this method does.
	 * @param level 
	 *
	 */
	public void fall(int level){
		double bars_Ycoordinate = 0;
		double burger_Ycoordinate=this.burger[level][0].getY();
		
		for (Rectangle2D bars: this.bars){
			if (burger_Ycoordinate+this.burger[level][0].getHeight()<bars.getY()){
				bars_Ycoordinate=bars.getY();
				break;
			}
		}
		
		for (int i=0;i<this.burger[0].length;i++){
			this.burgerStatus[level][i]=0;
		}
		
		if (bars_Ycoordinate==this.bars.get(this.bars.size()-1).getY()){
			this.isPlated[level]=true;
			this.itemsPlated++;
			if (this.itemsPlated==4){
				this.score.addScore(100);
			}
			bars_Ycoordinate=bars_Ycoordinate-this.burger[level][0].getHeight()*this.itemsPlated;
		}
		
		if (level<this.burger.length-1 && this.burger[level+1][0].getY()>=bars_Ycoordinate-8 
				&& this.burger[level+1][0].getY()<=bars_Ycoordinate+8
				){
			this.hitBurger[level]=true;
		}
			
		ArrayList<Runnable> fall = new ArrayList<Runnable>();
		fall.add(new Fall(bars_Ycoordinate,burger_Ycoordinate,level,this.burger,this.hitBurger[level])); 
		this.fallThread = new Thread(fall.get(fall.size()-1));
		this.fallThread.start();
		
		this.position.get(level).setRect(this.burger[level][0].getX(),bars_Ycoordinate,
				this.burger[level][0].getWidth()*5,this.burger[level][0].getHeight());
		this.addScore(50);
	}
	
	
	private class Fall implements Runnable{
		private double bars_Ycoordinate,burger_Ycoordinate;
		private int level;
		private Rectangle2D[][] burger1;	
		private boolean hitBurger;
		
		public Fall(double bars_Ycoordinate,double burger_Ycoordinate,int level,Rectangle2D[][] burger1,boolean hitBurger){
			this.bars_Ycoordinate=bars_Ycoordinate;
			this.burger_Ycoordinate=burger_Ycoordinate;
			this.level=level;
			this.burger1=burger1;
			this.hitBurger=hitBurger;
		}
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run(){
			try{
				Burger.this.isMoving[this.level]=true;
				while (this.bars_Ycoordinate>=this.burger_Ycoordinate){
					for (int i=0;i<this.burger1[0].length;i++){
						this.burger1[this.level][i].setRect(this.burger1[this.level][i].getX(),(int) this.burger_Ycoordinate,
								this.burger1[this.level][i].getWidth(),this.burger1[this.level][i].getHeight());
					}
					this.burger_Ycoordinate+=0.2;
					Thread.sleep(1);
					if (this.hitBurger && Burger.this.isPlated[this.level+1]==false
							&& this.burger_Ycoordinate>=this.burger1[this.level+1][0].getY()-this.burger1[this.level][0].getHeight()-1){
						this.hitBurger=false;
						Burger.this.score.addScore(25);
						fall(this.level+1);
					}
				}
				Burger.this.isMoving[this.level]=false;
				for (int i=0;i<this.burger1[0].length;i++){
					Burger.this.burgerStatus[this.level][i]=0;
				}
			}catch (InterruptedException exception){
				exception.printStackTrace();
			}
		}
	}
	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @return
	 */
	@SuppressWarnings("javadoc")
	public boolean isPlated(){
		if (this.itemsPlated==4){
			return true;
		}
		return false;
	}
	
	
	/**
	 * TODO Put here a description of what this method does.
	 * @return
	 */
	
	@SuppressWarnings("javadoc")
	public BufferedImage[][] getColor() {
		return this.image;
	}
	
	/**
	 * TODO Put here a description of what this method does.
	 *
	 */
	public void resetPosition(){
		
		for (int i=0;i<this.burger.length;i++){
			//get the Rectangle's left-corner point;
			double x=this.position.get(i).getX();
			double y=this.position.get(i).getY();
			//get the Rectangle's width and height and divide their width into 5 pieces
			double width=(this.position.get(i).getWidth())/5;
			double height=this.position.get(i).getHeight();
			//initialize small rectangles
			for (int j=0;j<this.burger[i].length;j++){
				this.burger[i][j]=new Rectangle2D.Double(x+width*j,y,width,height);
				this.burgerStatus[i][j]=0;
			}
		}
	}
	/**
	 * TODO Put here a description of what this method does.
	 * @return 
	 *
	 */
	@SuppressWarnings("javadoc")
	public Rectangle2D[][] getShape() {
		return this.burger;
	}
	
	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param score
	 */
	public void addScore(int score){
		this.score.addScore(score);
	}
	
	/**
	 * TODO Put here a description of what this method does.
	 *
	 */
	public void readImage(){
		for (int i=0;i<this.burger.length;i++){
			for (int j=0;j<this.burger[0].length;j++){
				try {
					this.image[i][j]=ImageIO.read(new File("image/"+(i+1)+"."+(j+1)+".png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
