package fr.ups.l3info.l3info_catchgamedatastructure;

import java.util.ArrayList;
import java.util.List;


/* 
 * Data structure for fruit basket
 * To be completed to implement your own version of the game
 */
public class FruitBasket {
	private List<Fruit> fruits; // How big is the basket
	
	public FruitBasket(){
		fruits = new ArrayList<Fruit>();
	}
	
	public void addFruit() {
		fruits.add(new Fruit());
	}
	
	public int getNbFruits() {
		return fruits.size();
	}
}
