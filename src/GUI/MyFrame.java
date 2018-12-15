package GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import File_format.Csv2Game;
import File_format.Path2KML;
import GIS.packmanModel.Fruit;
import GIS.packmanModel.Game;
import GIS.packmanModel.Packman;
import GIS.packmanModel.PatchA;
import GIS.packmanModel.PatchPackman;
import GIS.packmanModel.PatchPoint;
import Geom.Point3D;
import algorithms.ShortestPathAlgo;


/**
 * 
 * @author aviv vexler
 *
 */
public class MyFrame extends JFrame {
	private Image img1;

	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem itemLoadCSV;
	private JMenuItem itemSaveKML;
	private JMenu menuRun;
	private JMenuItem itemSort;
	private JMenuItem itemSimulate;
	private JMenuItem itemSimulateWithSoundAndAnimation;
	private JMenuItem itemStop;
	private JMenu menuInsert;
	private JMenuItem itemPackmen;
	private JMenuItem itemFrut;
	private JMenuItem[] itemAll;

	private Game game;
	private PatchA patch;//array index refer to Packman ID.arrayList sorted by time
	private Map map = new Map(); 
	private JFileChooser fc = new JFileChooser();
	private String fileDictory = "default.kml";
	private Runnable simulateThread;
	
	enum SelectedObject{non,packmen,fruit};
	private SelectedObject selectedObject = SelectedObject.non;
	
	//run automatic game_1543684662657.csv to test fester my code/
	private static boolean CHEAT_1543693911932CSV = true;
	private static boolean CHEAT_TEST_ALL_CSV = false;

	MyFrame(int w,int h) throws IOException{
		//image
		img1 = Toolkit.getDefaultToolkit().getImage("Ariel1.png");

		//file open 
		fc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				csvFileOpen();
			}
			
		});
		//menu
		menuBar = new JMenuBar();	
		
		
		//menu - file
		menuFile = new JMenu("file");
		itemLoadCSV = new JMenuItem("load CSV");		
		menuFile.add(itemLoadCSV);
		itemLoadCSV.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				itemLoadCSV();

			}
		});
		itemSaveKML = new JMenuItem("save kml");
		menuFile.add(itemSaveKML);
		itemSaveKML.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				itemSaveKML();

			}
			

		});
		menuBar.add(menuFile);

		//menu run
		menuRun = new JMenu("run");
		itemSort = new JMenuItem("sort");
		itemSort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				itemSort();

			}
		});
		itemSimulate = new JMenuItem("simulate");
		itemSimulate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				itemSimulte();

			}
		});
		itemSimulateWithSoundAndAnimation = new JMenuItem("simulate with sound and animation");
		itemSimulateWithSoundAndAnimation.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				itemSimulteWithSoundAndAnimation();

			}
		});
		itemStop = new JMenuItem("stop");
		itemStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				itemStop();
				
			}

			
		});
		itemStop.setEnabled(false);
		menuRun.add(itemSort);
		menuRun.add(itemSimulate);
		menuRun.add(itemSimulateWithSoundAndAnimation);
		menuRun.add(itemStop);
		menuBar.add(menuRun);

		//menu insert
		menuInsert = new JMenu("insert");
		itemPackmen = new JMenuItem("packmen");
		itemPackmen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				itemPackmen();

			}
		});
		itemFrut = new JMenuItem("frut");
		itemFrut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				itemFrut();

			}
		});
		menuInsert.add(itemPackmen);
		menuInsert.add(itemFrut);
		menuBar.add(menuInsert);

		//insert all item
		JMenuItem itemAll[] = {itemLoadCSV,itemSaveKML,itemSort,itemSimulate,itemSimulateWithSoundAndAnimation,itemPackmen,itemFrut}; 
		this.itemAll = itemAll;
		
		//gui
		setJMenuBar(menuBar);

		setSize(w,h); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		//Mouse
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				click(e.getX(),e.getY());
				
			}
			
		});
		
		//cheat
		if(CHEAT_1543693911932CSV) {
			File file = new File("data/game_1543693911932.csv");
			fileDictory = file.getPath();
			game = Csv2Game.loadGame(file);						
			itemSort();			
			//itemSaveKML();
		}
		
		if(CHEAT_TEST_ALL_CSV) {
			File file = new File("data/game_1543684662657.csv");
			fileDictory = file.getPath();
			game = Csv2Game.loadGame(file);						
			itemSort();	
			
			file = new File("data/game_1543685769754.csv");
			fileDictory = file.getPath();
			game = Csv2Game.loadGame(file);						
			itemSort();	
			
			file = new File("data/game_1543693822377.csv");
			fileDictory = file.getPath();
			game = Csv2Game.loadGame(file);						
			itemSort();	
			
			file = new File("data/game_1543693911932.csv");
			fileDictory = file.getPath();
			game = Csv2Game.loadGame(file);						
			itemSort();	
			
			file = new File("data/game_1543693911932_a.csv");
			fileDictory = file.getPath();
			game = Csv2Game.loadGame(file);						
			itemSort();	
			
			file = new File("data/game_1543693911932_b.csv");
			fileDictory = file.getPath();
			game = Csv2Game.loadGame(file);						
			itemSort();	

		}
	}

	public void paint(Graphics g){
		super.paint(g);

		int width = getWidth();
		int Height = getHeight();

		//map
		g.drawImage(img1, 0, 0,width,Height, null);

		//game and patch
		map.paintGame(g, game,width,Height);
		map.paintPatch(g, patch,width,Height);

		//
		menuBar.repaint();
		//finalize
		g.finalize();


	}
	
	private void click(int x, int y) {
		//non don't do nafing
		if(selectedObject == SelectedObject.non)
			return;
		
		//make sure game is not null
		if(game == null)
			game = new Game();
		
		//convert click point to polar point.
		Point3D point = new Point3D((double)x, (double)y,0);
		point = scaleToImg(point);
		point = scaleToPolar(point);
		
		//add packmen or fruit.
		switch (selectedObject) {
		case packmen:
			game.addDefultPackmen(point);
			break;

		default:
			game.addDefultFruit(point);
			break;
		}
		 
		//setting
		selectedObject = SelectedObject.non;
		repaint();
		
	}
	
	private Point3D scaleToImg(Point3D p) {
		double dx = p.x() / getWidth();
		dx = dx*Map.IMAGE_WIDTH;
		
		double dy = p.y() / getHeight();
		dy = dy*Map.IMAGE_HEIGHT;
		return new Point3D(dx,dy,0);
	}
	
	private Point3D scaleToPolar(Point3D p) {
		double dx = p.x() / Map.IMAGE_WIDTH;
		dx = dx*(Map.BOTTOM_RIGHT_X - Map.TOP_LEFT_X) + Map.TOP_LEFT_X;
		
		double dy = p.y() / Map.IMAGE_HEIGHT;
		dy = 1 - dy;
		dy = dy*(Map.TOP_LEFT_Y - Map.BOTTOM_RIGHT_Y) + Map.BOTTOM_RIGHT_Y;
		return new Point3D(dy,dx,0);
	}
	

	private void csvFileOpen() {
		File file = fc.getSelectedFile();
		fileDictory = file.getPath();
		game = Csv2Game.loadGame(file);
		repaint();
		
	}
	
	private void itemLoadCSV() {
		fc.showOpenDialog(this);
		//a>v>i>v>v>e>x>l>e>r.
	}

	private void itemSaveKML() {
		Path2KML.save(patch,fileDictory);
	}

	private void itemStop() {
		// TODO Auto-generated method stub
		
	}
	
	private void itemSort() {
		ShortestPathAlgo algo = new ShortestPathAlgo(game);
		patch = algo.getPatch();
		System.out.println(fileDictory + " run in: " + algo.getTimeToComplete() + " ms");
		
		repaint();
	}

	private void itemSimulte() {		
		startSimulte();
	}

	

	private void itemSimulteWithSoundAndAnimation() {

	}

	private void itemPackmen() {
		selectedObject = SelectedObject.packmen;
		repaint();
	}

	private void itemFrut() {
		selectedObject = SelectedObject.fruit;
		repaint();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws IOException {
		new MyFrame((int)(1433/1.5),(int)(642/1.5));
	}

	private void startSimulte() {
		//disable all item without stop item.
		for(JMenuItem item:itemAll) {
			item.setEnabled(false);
		}
		
		itemStop.setEnabled(true);
		
		//start thread
		simulateThread = new Simulte();
		Thread thread = new Thread(simulateThread);
		thread.setName("simulate packman move");
		thread.start();
	}
	

	/*
	 * make simple game and patch before i write the algorithm.
	 */
	@SuppressWarnings("unused") 
	private void makeSimpleGameAndPatch() {
		//game
		Point3D loacationPackmen1 = new Point3D(32.10233979215339,35.20739592361731,10);//kikar
		Point3D loacationPackmen2 = new Point3D(32.10511220722532,35.20836131097678,0);//score
		Point3D loacationFruit1 = new Point3D(32.10462702,35.20573393,10);

		Packman packman1 = new Packman(0,loacationPackmen1 , 0, 1, 1);
		Packman packman2 = new Packman(1,loacationPackmen2 , 0, 1, 1);
		Fruit fruit1 = new Fruit(2,loacationFruit1, 10);


		game = new Game();
		game.addPackman(packman1);
		game.addPackman(packman2);
		game.addFruit(fruit1);

		//patch
		Point3D loacation0_0 = new Point3D(32.10233979215339,35.20739592361731,0);
		Point3D loacation0_1 = new Point3D(32.10511220722532,35.20836131097678,0);
		Point3D loacation0_2 = new Point3D(32.1046270,35.20573393,0);

		Point3D loacation1_0 = new Point3D(32.1047,35.2103,0);
		Point3D loacation1_1 = new Point3D(32.1057,35.2113,0);
/*
		Patch patch0_0 = new Patch(1544183030947l, loacation0_0);
		Patch patch0_1 = new Patch(1544183030957l, loacation0_1);
		Patch patch0_2 = new Patch(1544183030967l, loacation0_2);
		Patch patch1_0 = new Patch(1544183030956l, loacation1_0);
		Patch patch1_1 = new Patch(1544183030966l, loacation1_1);

		this.patch = new ArrayList<ArrayList<Patch>>();
		this.patch.add( new ArrayList<Patch>());
		this.patch.get(0).add(patch0_0);
		this.patch.get(0).add(patch0_1);
		this.patch.get(0).add(patch0_2);
		this.patch.add( new ArrayList<Patch>());
		this.patch.get(1).add(patch1_0);
		this.patch.get(1).add(patch1_1);*/
	}
	
	class Simulte implements Runnable{

		@Override
		public void run() {
			int time = 100;
			boolean allPackmanStop = true;
			System.out.println("sim "+time+" ms");
			
			for(int pi=0;pi < patch.getNumPackmens();pi++) {
				PatchPackman patchPackman = patch.getPatchPackman(pi);
				//pakmen need be between point a to point b.
				//find point a and point b.
				PatchPoint ap = null;//a point
				PatchPoint bp = null;//b point
				
				for(int i=0;i<patchPackman.patchPoints.size();i++) {
					PatchPoint patchPoint = patchPackman.patchPoints.get(i);
					
					if(time > patchPoint.getTime())
						ap = patchPoint;
					
					if(patchPoint.getTime() >= time) {
						bp = patchPoint;
						break;
					}
				}
				
				//insert ap the point to show
				if(bp == null) {
					System.out.println("packman stop");
				}else{
					//find time
					allPackmanStop = false;
				}
				
				//show ap
				Packman packmen = game.getPackmen(pi);
				packmen.location = ap.getLocation();
				
				System.out.println("packman: "+pi+" ap: "+ap+" bp: "+bp);
			}
			
			
			
			//stop
			if(allPackmanStop)
				System.out.println("all packman stop");
			repaint();
			
		}
		
	}
	
	
}
