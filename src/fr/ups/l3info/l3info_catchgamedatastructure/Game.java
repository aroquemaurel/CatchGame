package fr.ups.l3info.l3info_catchgamedatastructure;

import fr.ups.l3info.utils.Parameters;

public class Game {
	private int fruitFallDelay;
	private int fruitLimit;//fruits can fall without being picked up
	private int gameOver;
	private FruitBasket fruitBasket;
	private int appearSpeed;
	
	public Game() {
		majParameters();
		fruitLimit = 1;
		gameOver = 0;
		fruitBasket = new FruitBasket();
	}
	public void start() {
		gameOver = 0; 
	}
	
	public void stop() {
		
	}

	public int getFruitFallDelay() {
		return fruitFallDelay;
	}

	public void setFruitFallDelay(int fruitFallDelay) {
		this.fruitFallDelay = fruitFallDelay;
	}

	public int getFruitLimit() {
		return fruitLimit;
	}

	public void setFruitLimit(int fruitLimit) {
		this.fruitLimit = fruitLimit;
	}

	public int getGameOver() {
		return gameOver;
	}
	
	public FruitBasket getFruitBasket() {
		return fruitBasket;
	}

	public void setGameOver(int gameOver) {
		this.gameOver = gameOver;
	}
	
	public boolean lose() {
		return gameOver >= fruitLimit;
	}
	
	public void majParameters() {
		fruitFallDelay = (100-Parameters.getInstance().get("fruitSpeed", 50))-20;
		fruitFallDelay = (fruitFallDelay > 0) ? fruitFallDelay : 1;
		appearSpeed = 15;
	}

	public void losingFruit() {
		++gameOver;
	}
	
	public int getAppearSpeed() {
		return appearSpeed;
	}
}
