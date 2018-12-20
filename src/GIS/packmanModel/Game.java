package GIS.packmanModel;

import java.util.ArrayList;
import java.util.Iterator;

import Geom.Point3D;
/**
 * store packmans and fruits
 * @author Aviv Vexler
 *
 */
public class Game {
	private ArrayList<Packman> packmens;
	private ArrayList<Fruit> fruits;
	
	public Game() {
		packmens = new ArrayList<Packman>();
		fruits = new ArrayList<Fruit>();
	}
	
	/**
	 * make new packman with: angle=0,speed=1,radius=1, id=lest packmen + 1.  
	 * @param location - location to new packman.
	 */
	public void addDefultPackmen(Point3D location) {
		//find id.
		int id = 0;
		if(packmens.size() > 0) {
			Packman packmen = packmens.get(packmens.size() - 1);//lest packmen
			id = packmen.id + 1;
		}
		
		//make the new packmen and add to list.
		Packman packmen = new Packman(id, location, 1, 1);
		packmens.add(packmen);
		////a-v-i-v-v-e-x-l-e-r.
	}
	
	/**@param packmen - packmen to add.*/
	public void addPackman(Packman packmen) {
		packmens.add(packmen);
	}
	
	/**
	 * add fruit with weight=1 and id=lest fruit + 1.
	 * @param location - loaction to new fruit.
	 */
	public void addDefultFruit(Point3D location) {
		//find id
		int id = 0;
		if(fruits.size() > 0) {
			Fruit fruit = fruits.get( fruits.size() - 1 );
			id = fruit.id + 1;
		}
		
		//make new fruit
		Fruit fruit = new Fruit(id, location, 1);
		fruits.add(fruit);
	}
	
	/** @param fruit - fruit to add.*/
	public void addFruit(Fruit fruit) {
		fruits.add(fruit);
	}
	
	/**@return Iterator to packmans.*/
	public Iterator<Packman> iteratorPackmen(){
		return packmens.iterator();
	}
	
	/** @param index - index to packmans.
	 * @return - Packman.*/
	public Packman getPackmen(int index) {
		return packmens.get(index);
	}
	
	/**@return Iterator to Fruit.*/
	public Iterator<Fruit> iteratorFruit(){
		return fruits.iterator();
	}
	
	/** @return size packman*/
	public int countPackmens() {
		return packmens.size();
	}
	
	/*** @return ArrayList fruit with another Pointer but same object. */
	public ArrayList<Fruit> CopyPointerFruit(){
		ArrayList<Fruit> arrayList = new ArrayList<Fruit>();
		arrayList.addAll(fruits);
		return arrayList;
	}

	@Override
	public String toString() {
		return "Game [packmens=" + packmens + ", fruits=" + fruits + "]";
	}
	
	
	
}
