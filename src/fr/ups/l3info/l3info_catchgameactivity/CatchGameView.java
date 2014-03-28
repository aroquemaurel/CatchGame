package fr.ups.l3info.l3info_catchgameactivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import fr.ups.l3info.l3info_catchgamedatastructure.Fruit;
import fr.ups.l3info.l3info_catchgametemplate.R;

/* 
 * Custom view for displaying falling fruits
 * Comments to be added
 * To be modified to implement your own version of the game
 */
public class CatchGameView extends View {
	private int endEcran = 630; //sur ma tablette
	private int timerCount = 0;
	private int gameField = 300;
	private int appearSpeed = 5;
	private float pressedPositionX = -1;
	private float pressedPositionY = -1;
	private List<Rect> fallingDownFruitsList;
	private Bitmap applePict;
	private Timer timerFallingFruits;
	
	public CatchGameView(Context context) {
		super(context);
		applePict = BitmapFactory.decodeResource(getResources(),R.drawable.apple);
		fallingDownFruitsList = new ArrayList<Rect>();
	}
	
	public CatchGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		applePict = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
		fallingDownFruitsList = new ArrayList<Rect>();
	}
	
	public CatchGameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		applePict = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
		fallingDownFruitsList = new ArrayList<Rect>();
	}	
	
	public void initTimer() {
		timerFallingFruits = new Timer();
		timerFallingFruits.schedule(new TimerTask() {			
			@Override
			public void run() {
				timerEventHandler();
			}
			
		}, 0, CatchGameActivity.game.getFruitFallDelay());
	}
	
	public void stopTimer() {
		timerFallingFruits.cancel();
	}
	 
	private void timerEventHandler() {
		Rect toMemorise = null;

		if (timerCount%appearSpeed == 0) {
			Random rand = new Random();
			int coord1 = rand.nextInt(gameField);
			fallingDownFruitsList.add(new Rect(0, coord1, CatchGameActivity.fruitRadius*2, 
														coord1+CatchGameActivity.fruitRadius*2));
		}
		if (timerCount % 20 == 0) {
			
			// TODO speed
			timerCount = 0;
			Log.i("test", "test");
		}
		for (Rect fruitBounds : fallingDownFruitsList) {
			if(fruitBounds.left >= endEcran) {
				toMemorise = fruitBounds;
				CatchGameActivity.game.losingFruit();
			} else {
				if (fruitBounds.contains((int)pressedPositionY,(int) pressedPositionX)) {
					toMemorise = fruitBounds;
					CatchGameActivity.basket.addFruit();
				}
				fruitBounds.set(fruitBounds.left+10, fruitBounds.top, fruitBounds.right+10, fruitBounds.bottom);
			}
	
		}
		
		if (toMemorise != null) {
			fallingDownFruitsList.remove(toMemorise);
		}
		
		timerCount++;
		pressedPositionX = -1;
		pressedPositionY = -1;
	}
	
	public void setFruitFallDelay(int delay) {
//		fruitFallDelay = delay;
	}
	
	public void setFruitList(List<Fruit> fruitList){
		Rect fruitBounds;
		fallingDownFruitsList.clear();
		for (Fruit fruit:fruitList){
			
			fruitBounds = new Rect(fruit.getLocationInScreen().x, fruit.getLocationInScreen().y, fruit.getLocationInScreen().x+2*(fruit.getRadius()), fruit.getLocationInScreen().y+2*(fruit.getRadius()));
			fallingDownFruitsList.add(fruitBounds);
		}
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(color.holo_green_dark);
		Paint p = new Paint();

		if(CatchGameActivity.game != null) {
			if(CatchGameActivity.game.lose()){
				stopTimer();
				Toast.makeText((Activity)getContext(), 
						"The game has finished, you have catched " + 
							// TODO		CatchGameActivity.basket.getNbFruits() +
						" fruits", Toast.LENGTH_SHORT).show();
			} else {
			//	canvas.drawText("Basket: " + CatchGameActivity.basket.getNbFruits(), 
			//			CatchGameActivity.basket.getLocationInScreen().y, 
				//		CatchGameActivity.basket.getLocationInScreen().x, p);
			//	CatchGameActivity.labelNbFruits.setText("Numbers of fruits: ");
				
				for (Rect fruitBounds:fallingDownFruitsList){
					canvas.drawBitmap(applePict, fruitBounds.top, fruitBounds.left,null);
				}
			}
		}
		
		invalidate();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
	    pressedPositionX = e.getX();
	    pressedPositionY = e.getY();

	    return e.getAction() == MotionEvent.ACTION_DOWN;
	}
}
