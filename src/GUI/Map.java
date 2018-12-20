package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import GIS.packmanModel.Fruit;
import GIS.packmanModel.Game;
import GIS.packmanModel.Packman;
import GIS.packmanModel.Patch;
import GIS.packmanModel.PatchPackman;
import Geom.Point3D;

/**
 * 
 * shoe the game {@link GIS.packmanModel.Game} data structure and 
 * patch {@link GIS.packmanModel.Patch} data structure on screen.
 * Method setPatchAndGame(Game,Patch) must celled before repaint.
 * @author Aviv Vexler
 */
public class Map extends JPanel{

	/** version 1 maybe forever*/
	private static final long serialVersionUID = 1L;

	// polar bound points.
	public static final double TOP_LEFT_X = 35.2025;
	public static final double TOP_LEFT_Y = 32.1057;
	public static final double BOTTOM_RIGHT_X = 35.2124; 
	public static final double BOTTOM_RIGHT_Y = 32.1019;

	//image detail
	public static final int IMAGE_WIDTH = 1433;
	public static final int IMAGE_HEIGHT = 642;

	//varible
	private Game game;
	private Patch patch;
	private BufferedImage imgMap;
	private BufferedImage imgPackman;

	/**
	 * load image to draw.
	 * @throws IOException - {@link IOException}.
	 */
	public Map() throws IOException{
		//image		
		File imgMapFile = new File("Ariel1.png");
		imgMap = ImageIO.read(imgMapFile);

		File imgPackmanFile = new File("Packman.png");
		imgPackman= ImageIO.read(imgPackmanFile);
	}

	/**@param game - game data structure to draw.
	 * @param patch - patch data structure to draw.*/
	public void setGameAndPatch(Game game,Patch patch) {
		this.game = game;
		this.patch = patch;
	}

	/**Calls the UI delegate's paint method, if the UI delegate is non-null.
	 * @param g - the Graphics object to protect
	 */
	@Override
	protected void paintComponent(Graphics g) {		
		int width = getWidth();
		int Height = getHeight();

		//map
		
		g.drawImage(imgMap, 0, 0,width,Height, null);				

		//game and patch
		paintPatch(g, patch,width,Height);
		paintGame(g, game,width,Height);
	}

	/**
	 * convert polar point to point on the image.
	 * @param point - point in polar coordinate.
	 * @return - point on image.
	 */
	private Point3D polarPointToImage(Point3D point) {
		//x
		double dx = (point.y() - TOP_LEFT_X)/(BOTTOM_RIGHT_X - TOP_LEFT_X);
		double x = dx*IMAGE_WIDTH;

		//y
		double dy = (point.x() - BOTTOM_RIGHT_Y)/(TOP_LEFT_Y - BOTTOM_RIGHT_Y);
		dy = 1 - dy;
		double y = dy*IMAGE_HEIGHT;

		//return a-v-i-v-v-e-x-l-e-r
		return new Point3D(x,y,point.z());
	}

	/**
	 * scale point to window size.
	 * 
	 * @param point - point in image location.
	 * @param wWidth - window width
	 * @param wHeight - window height
	 * @return point on window location.
	 */
	private Point3D scalePoint(Point3D point,int wWidth,int wHeight) {
		double dx = point.x() / IMAGE_WIDTH;
		double x = dx*wWidth;

		double dy = point.y() / IMAGE_HEIGHT;
		double y = dy*wHeight;

		return new Point3D(x,y,0);
	}

	/**
	 * draw game {@link GIS.packmanModel.Game} data structure on screen
	 * @param g - Graphics from JFrame
	 * @param game - game {@link GIS.packmanModel.Game} 
	 * @param wWidth - window width.
	 * @param wHeight - window height.
	 */
	private void paintGame(Graphics g,Game game,int wWidth,int wHeight){

		if(game == null)
			return;

		//fruit
		Iterator<Fruit> fruitIterator = game.iteratorFruit();
		while(fruitIterator.hasNext()) {
			//get point ans scale
			Fruit fruit= fruitIterator.next();
			Point3D point = polarPointToImage(fruit.location);
			point = scalePoint(point,wWidth,wHeight);
			int radius = 25;

			//draw (maybe later is be image).
			g.setColor(Color.GREEN);
			g.drawOval(((int)point.x() - radius/2), (int)(point.y() - radius/2), radius, radius);
			g.fillOval((int)point.x() - radius/2, (int)point.y() - radius/2, radius, radius);

		}

		//packman
		Iterator<Packman> packmanIterator = game.iteratorPackmen();
		while(packmanIterator.hasNext()) {
			//get point ans scale
			Packman packman = packmanIterator.next();
			Point3D point = polarPointToImage(packman.location);
			point = scalePoint(point,wWidth,wHeight);
			int radius = packman.radius*50;


			//draw (maybe later is be image).
			double x = point.x() - radius/2 ;
			double y = point.y() - radius/2;
			Graphics2D g2d=(Graphics2D)g;		
			AffineTransform at = AffineTransform.getTranslateInstance(x, y);
			at.rotate(Math.toRadians(packman.angle),radius/2,radius/2);
			g2d.drawImage(imgPackman, at, null);
			
			//Circle
			//g.setColor(Color.yellow);
			//g.drawOval((int)(point.x() - radius/2 ), (int)(point.y() - radius/2), radius, radius);
			//g.fillOval((int)(point.x() - radius/2) , (int)(point.y() - radius/2), radius, radius);

		}

	}


	/**
	 * draw patch {@link GIS.packmanModel.Patch} data structure on screen.
	 * @param g - Graphics from JFrame
	 * @param patchs - patch {@link GIS.packmanModel.Patch} 
	 * @param wWidth - window width.
	 * @param wHeight - window height.
	 */
	private void paintPatch(Graphics g,Patch patchs,int wWidth,int wHeight){				
		if(patchs == null)
			return;

		//paint		

		for(int pi = 0;pi < patchs.getNumPackmens();pi++) {//packmenIndex

			PatchPackman patchPackman =  patchs.getPatchPackman(pi);
			//color
			g.setColor( getColor(pi) );

			for(int pointI=1; pointI < patchPackman.getSize();pointI++) {
				//get a point and b point.
				Point3D  aPoint= patchPackman.get(pointI-1).getLocation();
				aPoint = polarPointToImage(aPoint);
				aPoint = scalePoint(aPoint, wWidth, wHeight);

				Point3D  bPoint= patchPackman.get(pointI).getLocation();
				bPoint = polarPointToImage(bPoint);
				bPoint = scalePoint(bPoint, wWidth, wHeight);

				//draw line a point to b point.
				g.drawLine((int)aPoint.x(), (int)aPoint.y(),(int) bPoint.x(),(int) bPoint.y());

			}


		}
	}

	/**
	 * 
	 * @param i - index
	 * @return unique color.if not have more ten 6 packman.
	 */
	private Color getColor(int i) {

		switch (i % 6) {
		case 0:
			return Color.BLUE;
		case 1:
			return Color.RED;
		case 2:
			return Color.CYAN;
		case 3:
			return Color.GRAY;
		case 4:
			return Color.ORANGE;
		case 5:
			return Color.PINK;
		default:
			return Color.BLACK;
		}

	}	
}
