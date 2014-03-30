package fr.ups.l3info.l3info_catchgameactivity;

import java.util.Timer;
import java.util.TimerTask;

import fr.ups.l3info.utils.ITimer;

/**
 * Gestion du timer du jeu
 *
 */
public class TimerGame extends Timer {
	private int _timerCount;
	
	/**
	 * Démarre le timer
	 * @param f Fonction contenant le callback
	 * @param delay Délais
	 */
	public void start(final ITimer f, final int delay) {
		schedule(new TimerTask() {			
			@Override
			public void run() {
				++_timerCount;
				f.timerEventHandler();
			}
			
		}, 0, delay);
	}
	
	/**
	 * Stop le timer
	 */
	public void stop() {
		cancel();
	}

	/**
	 * Renvoit le nombre d'itération du timer
	 * @return
	 */
	public int getTimerCount() {
		return _timerCount;
	}

	/**
	 * Rénitialise le nombre d'itération
	 */
	public void initTimerCount() {
		_timerCount = 0;
	}
	
	
}


