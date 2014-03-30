package fr.ups.l3info.l3info_catchgamedatastructure;

import java.util.List;
import java.util.Random;

import android.graphics.Rect;
import fr.ups.l3info.l3info_catchgameactivity.CatchGameActivity;
import fr.ups.l3info.l3info_catchgameactivity.CatchGameView;
import fr.ups.l3info.l3info_catchgameactivity.TimerGame;
import fr.ups.l3info.utils.ITimer;
import fr.ups.l3info.utils.Parameters;

/**
 * Gestion du jeu 
 *
 */
public class Game {
	private static Game _instance;

	private int fruitFallDelay;
	private int gameOver;
	private int appearSpeed;
	private int level;
	private TimerGame _timer;
	
	public FruitBasket fruitBasket;
	public int fruitLimit;//fruits can fall without being picked up
	public int fruitSize;
	public int simultFruitNumber;
	
	private boolean isStarted;
	private boolean isPaused;
	
	private Game() {
		majParameters();
		isStarted = false;
		isPaused = false;
		fruitBasket = new FruitBasket();
		_timer = new TimerGame();
	}
	
	/**
	 * 
	 * @return L'instance de Jeu
	 */
	public static Game getInstance() {
		if(_instance == null) {
			_instance = new Game();
		} 
		
		return _instance;
	}

	/**
	 * Démarre le jeu
	 * @param t Timer avec fonction de callback
	 */
	public void start(ITimer t) {
		if(!isPaused) {
			majParameters();
			isStarted = true;
		}
		isPaused = false;
		_timer.start(t, fruitFallDelay);
	}
	
	/** 
	 * Termine le jeu
	 */
	public void finish() {
		_timer.stop();
		isStarted = false;
		isPaused = false;
		Parameters.getInstance().put("bestScore", fruitBasket.getBestScore());
		Parameters.getInstance().commit();
		_instance = null;
	}
	
	/**
	 * Est-ce qu'on a perdu ?
	 * @return Vrai si le jeu est perdu
	 */
	public boolean lose() {
		System.out.println(gameOver + " "+fruitLimit);
		return gameOver >= fruitLimit;
	}
	
	/**
	 * Met à jour les paramètres
	 */
	public void majParameters() {
		fruitFallDelay = (100-Parameters.getInstance().get("fruitSpeed", 50))-10;
		int param = Parameters.getInstance().get("fruitSize", 1);
		
		fruitSize = Parameters.getInstance().get("fruitSize", 1);
		appearSpeed = Parameters.getInstance().get("fruitNumbers", 30);
		fruitLimit = Parameters.getInstance().get("fruitLimit", 3);
		fruitFallDelay = (fruitFallDelay > 0) ? fruitFallDelay : 1;
		level = 1;
		gameOver = 0; 
	}

	/**
	 * Perte d'un fruit :(
	 */
	public void losingFruit() {
		++gameOver;
	}
	
	/**
	 * Retourne la taille du fruit
	 * @return La taille
	 */
	public int getFruitSize() {
		return fruitSize;
	}

	/** 
	 * Accélère la tombée des fruits si besoin
	 */
	public void quickly(ITimer t) {
		if (_timer.getTimerCount() % 200 == 0) {
			_timer.stop();
			_timer.initTimerCount();
			fruitFallDelay -= 5 ;
			fruitFallDelay = (fruitFallDelay >= 10) ? fruitFallDelay : 10;
			++level;
			_timer = new TimerGame();
			_timer.start(t, fruitFallDelay);
		}
	}
	
	/**
	 * Retourne le niveau de difficulté
	 * @return Le niveau
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Ajoute une pomme si besoin
	 * @param fallingDownFruitsList La liste des pommes
	 */
	public void addApple(List<Rect> fallingDownFruitsList) {
		if (_timer.getTimerCount() % appearSpeed == 0) {
			Random rand = new Random();
			int coord1 = rand.nextInt(CatchGameView.GAME_FIELD);
			fallingDownFruitsList.add(new Rect(0, coord1, fruitSize*CatchGameView.FRUIT_RADIUS*2, 
													coord1+fruitSize*CatchGameView.FRUIT_RADIUS*2));							
		}	
	}
	
	/**
	 * Retourne le nombre de vies restantes
	 * @return le nombre de vie
	 */
	public int getNbRestantLives() {
		return fruitLimit-gameOver;
	}

	public boolean isStarted() {
		return isStarted;
	}

	public void pause() {
		isPaused = true;
		_timer.cancel();
		_timer = new TimerGame();
	}
}
