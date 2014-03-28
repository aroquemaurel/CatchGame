package fr.ups.l3info.l3info_catchgamedatastructure;

import java.util.ArrayList;
import java.util.List;


/* 
 * Data structure for fruit basket
 * To be completed to implement your own version of the game
 */
public class FruitBasket {
	private int nbFruits;
	public FruitBasket(){
		nbFruits = 0;
	}
	
	public void addFruit() {
		++nbFruits;
	}
	
	public int getNbFruits() {
		return nbFruits;
	}
}
