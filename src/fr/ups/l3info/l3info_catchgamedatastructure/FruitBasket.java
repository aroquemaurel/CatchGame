package fr.ups.l3info.l3info_catchgamedatastructure;

import android.graphics.Point;

/* 
 * Data structure for fruit basket
 * To be completed to implement your own version of the game
 */
public class FruitBasket {
	private static Point locationInScreen; //Where the basket is located
	private int fruits; // How big is the basket
	
	public FruitBasket(Point location){
		locationInScreen = location;
		fruits = 0;
	}
	
	public void setLocation(Point p){
		locationInScreen = p;
	}
	
	public Point getLocationInScreen() {
		return locationInScreen;
	}
	
	public void addFruits() {
		fruits++;
	}
	
	public int getFruits() {
		return fruits;
	}
}
