package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;
import File_format.Csv2Game;
import File_format.Path2KML;
import GIS.packmanModel.Game;
import GIS.packmanModel.Packman;
import GIS.packmanModel.Patch;
import GIS.packmanModel.PatchPackman;
import GIS.packmanModel.PatchPoint;
import Geom.Point3D;
import algorithms.ShortestPathAlgo;


/**
 * @author aviv vexler
 * show menu,map and control the game.<br>
 * <h1>File</h1><br>
 * Load csv - load csv file to game<br>
 * Test multi csv - test the sort algorithm in all CSV in the sDirectory.<br>
 * Save kml - save to patch to KML that you can see in google earth.have also time line.<br> 
 * <h1>Run</h1><br>
 * Sort - calculate the patch<br>
 * Simulate - run animation that show the patch in real time.<br>
 * Stop - Stop the animation if you not have patience to wait till the end.<br>
 * <h1>Insert</h1><br>
 * Packmen - after you press is click on the screen and the packman show.<br>
 * Fruit - after you press is click on the screen and the fruit show.<br>
 */
public class MyFrame extends JFrame {

	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem itemLoadCSV;
	private JMenuItem itemLoadMultCSV;
	private JMenuItem itemSaveKML;
	private JMenu menuRun;
	private JMenuItem itemSort;
	private JMenuItem itemSimulate;	
	private JMenuItem itemStop;
	private JMenu menuInsert;
	private JMenuItem itemPackmen;
	private JMenuItem itemFrut;
	private JMenuItem[] itemAll;

	private Game game;
	private Patch patch;
	private Map map = new Map(); 
	private JFileChooser fc = new JFileChooser();
	private FileNameExtensionFilter filterCSV;
	private FileNameExtensionFilter filterKML;
	private String fileName = "default.kml";
	private Simulate simulate;

	enum SelectedObject{non,packmen,fruit};
	private SelectedObject selectedObject = SelectedObject.non;

	//run automatic game_1543684662657.csv to test faster my code/
	private static boolean CHEAT_1543693911932CSV = false;

	/**
	 * configured all the buttons and gui setting.
	 *  all the  buttons organize in method itemXXX.
	 * @param w - Width screen.
	 * @param h - Height screen.
	 * @throws IOException
	 */
	public MyFrame(int w,int h) throws IOException{
		//file open 
		filterCSV = new FileNameExtensionFilter("csv onely","csv");	
		filterKML = new FileNameExtensionFilter("kml onely","kml");	
		fc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fileOpen();
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
		itemLoadMultCSV = new JMenuItem("test multi CSV");
		menuFile.add(itemLoadMultCSV);
		itemLoadMultCSV.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				itemLoadMultCSV();			
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
		JMenuItem itemAll[] = {itemLoadCSV,itemSaveKML,itemSort,itemSimulate,itemPackmen,itemFrut,itemLoadMultCSV}; 
		this.itemAll = itemAll;

		//gui
		setJMenuBar(menuBar);
		add(map);

		setSize(w,h); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		//Mouse
		map.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				click(e.getX(),e.getY());
			}

		});

		//cheat
		if(CHEAT_1543693911932CSV) {
			File file = new File("data/game_1543693911932_a.csv");
			fileOpenCSV(file);
			itemSort();			
			//itemSaveKML();
		}

	}	

	/** update the map in new patch and game and repaint*/
	@Override
	public void repaint() {
		map.setGameAndPatch(game, patch);
		super.repaint();
	}

	/**
	 * called when user click on the screen.
	 * Add packman, fruit or do not do nothing if user not select something to add.
	 * @param x - x axis in mouse.
	 * @param y - y axis in mouse.
	 */
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

	/**
	 * scale x,y point on screen to map Ariel image size.
	 * @param p - point that user click on the screen
	 * @return point in image
	 */
	private Point3D scaleToImg(Point3D p) {
		double dx = p.x() / map.getWidth();
		dx = dx*Map.IMAGE_WIDTH;

		double dy = p.y() / map.getHeight();
		dy = dy*Map.IMAGE_HEIGHT;
		return new Point3D(dx,dy,0);
	}

	/**
	 * scale x,y point on image to polar gps locatin.
	 * @param p - point that user click on the screen
	 * @return point in image
	 */
	private Point3D scaleToPolar(Point3D p) {
		double dx = p.x() / Map.IMAGE_WIDTH;
		dx = dx*(Map.BOTTOM_RIGHT_X - Map.TOP_LEFT_X) + Map.TOP_LEFT_X;

		double dy = p.y() / Map.IMAGE_HEIGHT;
		dy = 1 - dy;
		dy = dy*(Map.TOP_LEFT_Y - Map.BOTTOM_RIGHT_Y) + Map.BOTTOM_RIGHT_Y;
		return new Point3D(dy,dx,0);
	}

	/**
	 * Celled when user open file.
	 * Filter the option:
	 * user cancel,user selected one CSV file,user save kml.
	 * And called the right method.
	 */
	private void fileOpen() {
		File file = fc.getSelectedFile();
		if(file == null)//user cancel
			return;
		if(file.isDirectory()) {//user selected multi CSV files.
			fileOpenMultCSV(file);
		}else if(file.isFile()) {//user selected one CSV file.or KML file.
			
			if(file.getName().endsWith(".csv"))
				fileOpenCSV(file);
			else if(file.getName().endsWith(".kml")) {
				Path2KML.save(patch,file.getPath());
			}
			
		}else if(patch != null) {//user save kml.	
			String pathFile = file.getPath();
			if(!pathFile.endsWith(".kml"))
				pathFile = file.getPath() + ".kml";

			Path2KML.save(patch,pathFile);			
		}


	}

	/**
	 * User want open CSV.
	 * @param file - CSV file to open.
	 */
	private void fileOpenCSV(File file) {
		fileName = file.getName();
		game = Csv2Game.loadGame(file);
		patch = null;
		repaint();	
	}

	/**
	 * user want test multi CSV files.
	 * @param dictory - dictory to folder white some CSV files.
	 */
	private void fileOpenMultCSV(File dictory) {
		for(File file:dictory.listFiles())
			if(file.getName().endsWith(".csv")) {
				fileOpenCSV(file);
				itemSort();
			}
	}

	/** called when user press "Load CSV" button. */
	private void itemLoadCSV() {
		fc.setFileFilter(filterCSV);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.showOpenDialog(this);		
		//a>v>i>v>v>e>x>l>e>r.
	}

	/** called when user press "Load Mult CSV" button. */
	private void itemLoadMultCSV() {
		fc.setFileFilter(filterCSV);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.showOpenDialog(this);	
	}

	/** called when user press "Save KML" button. */
	private void itemSaveKML() {
		fc.setFileFilter(filterKML);
		fc.showSaveDialog(this);		
	}

	/** called when user press "Stop" button. */
	private void itemStop() {
		simulate.run = false;
	}

	/** called when user press "Sort" button. */
	private void itemSort() {
		ShortestPathAlgo algo = new ShortestPathAlgo(game);
		patch = algo.getPatch();
		System.out.println(fileName + " run in: " + algo.getTimeToComplete() + " ms");

		repaint();
	}

	/** called when user press "Simulte" button. */
	private void itemSimulte() {		
		startSimulte();
	}

	/** called when user press "Packmen" button. */
	private void itemPackmen() {
		selectedObject = SelectedObject.packmen;
		repaint();
	}

	/** called when user press "Fru" button. */
	private void itemFrut() {
		selectedObject = SelectedObject.fruit;
		repaint();
	}

	/** Version 1 maybe forever. */
	private static final long serialVersionUID = 1L;

	/**
	 * start the animation
	 */
	private void startSimulte() {
		
		if(patch == null)
			return;
		
		//disable all item without stop item.
		for(JMenuItem item:itemAll) {
			item.setEnabled(false);
		}

		itemStop.setEnabled(true);

		//start thread
		simulate = new Simulate();
		Thread thread = new Thread(simulate);
		thread.setName("simulate packman move");
		thread.start();
	}

	/** Open the software.
	 * @param args - not use
	 * @throws IOException - {@link IOException}
	 */
	public static void main(String[] args) throws IOException {
		new MyFrame((int)(1433/1.5),(int)(642/1.5));
	}	

	/**
	 * 
	 * @author Aviv Vexler
	 * Responsible for simulate animation.
	 * run on different thread.
	 */
	class Simulate implements Runnable{
		/** user can change it to false if is press the stop button.*/
		boolean run = true;

		/**
		 * Algorithm that calculate the location for every packman with regard to time
		 * while all the packman not go to end(or the user stop the animation),
		 * i add more time and sleep the thread for 15 ms'*/
		@Override
		public void run() {
			int time = 1;
			boolean allPackmanStop = false;

			try {

				while(!allPackmanStop && run) {
					allPackmanStop = true;

					//move the packman.
					for(int pi=0;pi < patch.getNumPackmens();pi++) {
						PatchPackman patchPackman = patch.getPatchPackman(pi);
						//pakmen need be between point a to point b.
						//find point a and point b.
						PatchPoint ap = null;//a point
						PatchPoint bp = null;//b point

						for(int i=0;i<patchPackman.getSize();i++) {
							PatchPoint patchPoint = patchPackman.get(i);

							if(time > patchPoint.getTime())
								ap = patchPoint;

							if(patchPoint.getTime() >= time) {
								bp = patchPoint;
								break;
							}
						}

						//insert point in current time to packman location.
						Packman packman = game.getPackmen(pi);	

						if(bp == null) {//the packman finish the patch.
							if(ap != null)
								packman.location = ap.getLocation();
						}else{
							//find point in current time
							double x,y;

							double ax = ap.getLocation().x();
							double ay = ap.getLocation().y();
							double bx = bp.getLocation().x();
							double by = bp.getLocation().y();

							double dt = (double)(time - ap.getTime())/(bp.getTime() - ap.getTime());
							//x in current time.
							if(ax < bx)
								x = (bx - ax)*dt+ax;
							else
								x = (ax - bx)*(1 - dt)+bx;
							//y in current time.
							if(ay < by)
								y=(by-ay)*dt+ay;
							else
								y=(ay-by)*(1-dt)+by;


							//loaction
							packman.location = new Point3D(x,y,0);							
							allPackmanStop = false;
						}

					}

					//sleep
					try {
						Thread.sleep(15);
						repaint();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					time = time + 1;
				}


			}catch (Exception e) {
				e.printStackTrace();
			}

			//stop
			stopSimulte();
		}

		/**unlock all the button that i don't want the user press in the animation time.*/
		private void stopSimulte() {
			//enable all item without stop item.
			for(JMenuItem item:itemAll) {
				item.setEnabled(true);
			}

			itemStop.setEnabled(false);	

		}

	}


}
