package fr.ups.l3info.l3info_catchgamedatastructure;

import fr.ups.l3info.utils.Parameters;



/* 
 * Data structure for fruit basket
 * To be completed to implement your own version of the game
 */
public class FruitBasket {
	private int score;
	private int bestScore;
	private int nbFruits;
	
	public FruitBasket(){
		score = 0;
		nbFruits = 0;
		bestScore = Parameters.getInstance().get("bestScore", 0);
	}
	
	public void addFruit(int level) {
		score += level;
		++nbFruits;
		if(score > bestScore) {
			bestScore = score;
		}
	}
	
	public int getScore() {
		return score;
	}
	
	public int getNbFruits() {
		return nbFruits;
	}

	public int getBestScore() {
		return bestScore;
	}
}
