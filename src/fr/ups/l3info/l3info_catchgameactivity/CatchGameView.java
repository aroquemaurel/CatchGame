package fr.ups.l3info.l3info_catchgameactivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.R.color;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import fr.ups.l3info.l3info_catchgamedatastructure.Game;
import fr.ups.l3info.l3info_catchgametemplate.R;
import fr.ups.l3info.utils.ITimer;

/* 
 * Custom view for displaying falling fruits
 * Comments to be added
 * To be modified to implement your own version of the game
 */
@SuppressLint("NewApi")
public class CatchGameView extends View implements ITimer {
	public final static int END_ECRAN = 630; //sur ma tablette
	public final static int GAME_FIELD = 300;
	public final static int FRUIT_RADIUS = 22;

	private float pressedPositionX = -1;
	private float pressedPositionY = -1;
	private List<Rect> fallingDownFruitsList;
	private Bitmap applePict;
	private Bitmap _newApple;
	private Paint _paint;

	
	public CatchGameView(Context context) {
		super(context);
		initAttributes();
	}
	
	public CatchGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAttributes();
	}
	
	public CatchGameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initAttributes();
	}

	/**
	 * Initialise les attributs
	 */
	private void initAttributes() {
		_paint = new Paint();
		applePict = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
		fallingDownFruitsList = Collections.synchronizedList(new ArrayList<Rect>());
	}	
	
	
	/**
	 * Fonction de callback du timer
	 */
	@Override
	public void timerEventHandler() {
		_newApple = Bitmap.createScaledBitmap(applePict, applePict.getWidth()*Game.getInstance().getFruitSize(), 
											  applePict.getHeight()*Game.getInstance().getFruitSize(), false);
		Game.getInstance().addApple(fallingDownFruitsList);
		Game.getInstance().quickly(this);

		synchronized (fallingDownFruitsList) {
			Iterator<Rect> it = fallingDownFruitsList.iterator();
			Rect fruitBounds;
			while(it.hasNext()) {
				fruitBounds = it.next();
				if(fruitBounds.left >= END_ECRAN) {
					Game.getInstance().losingFruit();
					it.remove();
				} else {
					if (fruitBounds.contains((int)pressedPositionY,(int) pressedPositionX)) {
						it.remove();
						Game.getInstance().fruitBasket.addFruit(Game.getInstance().getLevel());
					}
					fruitBounds.set(fruitBounds.left+2, fruitBounds.top, fruitBounds.right+2, fruitBounds.bottom);
				}
			}
		}
		
		pressedPositionX = -1;
		pressedPositionY = -1;
	}
	
	
	/**
	 * À chaque repeinture de l'écran
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(color.holo_green_dark);
		
		if(Game.getInstance().lose()) {
			endGame();
		} else {
			writeInfos(canvas);
			synchronized (fallingDownFruitsList) {
				for (Rect fruitBounds : fallingDownFruitsList) {
					canvas.drawBitmap(_newApple, fruitBounds.top, fruitBounds.left,null);
				}
			}
		}
		
		invalidate();
		super.onDraw(canvas);
	}
	
	/**
	 * Écris les informations du jeu en haut de l'écran
	 * @param canvas Canvas
	 */
	private void writeInfos(Canvas canvas) {
		canvas.drawText("Score: " + Game.getInstance().fruitBasket.getScore(), 5, 10, _paint);
		canvas.drawText("Best score: " + Game.getInstance().fruitBasket.getBestScore(), 5, 30, _paint);	
		canvas.drawText("Nb of lives: " + Game.getInstance().fruitLimit, 5, 50, _paint);	
	}

	/**
	 * Termine le jeu
	 */
	public void endGame() {
		fallingDownFruitsList.clear();
		Game.getInstance().finish();
	}
	
	/**
	 * Lors que le joueur touche l'écran
	 */
	@Override
	public boolean onTouchEvent(MotionEvent e) {
	    pressedPositionX = e.getX();
	    pressedPositionY = e.getY();

	    return e.getAction() == MotionEvent.ACTION_DOWN;
	}
}
