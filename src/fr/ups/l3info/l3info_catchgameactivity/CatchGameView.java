package fr.ups.l3info.l3info_catchgameactivity;

//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import fr.ups.l3info.l3info_catchgamedatastructure.Fruit;
import fr.ups.l3info.l3info_catchgametemplate.R;

/* 
 * Custom view for displaying falling fruits
 * Comments to be added
 * To be modified to implement your own version of the game
 */
public class CatchGameView extends View {
	int endEcran = 630; //sur ma tablette
	int timerCount = 0;
	int gameField = 300;
	int appearSpeed = 5;
	float pressedPositionX = -1;
	float pressedPositionY = -1;
	
	List<Fruit> fruitList;
	List<Rect> fallingDownFruitsList = new ArrayList<Rect>();

	Bitmap applePict = BitmapFactory.decodeResource(getResources(),R.drawable.apple);
	//Bitmap panier = BitmapFactory.decodeResource(getResources(),R.drawable.panier);
	Bitmap applePict2 = BitmapFactory.decodeResource(getResources(),R.drawable.apple);
	int fruitFallDelay = 1000;
	Timer timerFallingFruits;
	Iterator<Rect> iter = fallingDownFruitsList.iterator();
	
	public CatchGameView(Context context) {
		super(context);
		fallingDownFruitsList.clear();
	}
	
	public CatchGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		fallingDownFruitsList.clear();
	}
	
	public CatchGameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		fallingDownFruitsList.clear();
	}
	
	public void initTimer(){
		timerFallingFruits = new Timer();
		timerFallingFruits.schedule(new TimerTask() {			
			@Override
			public void run() {
				timerEventHandler();
				if (timerCount%appearSpeed == 0){
					Random rand = new Random();
					int coord1 = rand.nextInt(gameField);
					fallingDownFruitsList.add(new Rect(0, coord1, 0+CatchGameActivity.fruitRadius*2, coord1+CatchGameActivity.fruitRadius*2));
				}
				Rect toMemorise = null;
				for (Rect fruitBounds:fallingDownFruitsList){
					if(fruitBounds.left >= endEcran){
						//fruitBounds.set(0, fruitBounds.top, fruitBounds.right, fruitBounds.bottom);
						toMemorise = fruitBounds;
					}
					else{
//						System.out.println((int)pressedPositionX+" "+(int)pressedPositionY);
//						System.out.println(fruitBounds);
						if (fruitBounds.contains((int)pressedPositionY,(int) pressedPositionX)){
							toMemorise = fruitBounds;
							CatchGameActivity.basket.addFruits();
							//System.out.println("ok");
						}
						fruitBounds.set(fruitBounds.left+10, fruitBounds.top, fruitBounds.right+10, fruitBounds.bottom);
					}

				}
				if (toMemorise != null){
					fallingDownFruitsList.remove(toMemorise);
				}
				timerCount++;
				pressedPositionX = -1;
				pressedPositionY = -1;
//				for(Rect f: fallingDownFruitsList){
//					System.out.println(f.left);
//				}

			}
			
		}, 0, fruitFallDelay);
		
	}
	
	public void stopTimer(){
		timerFallingFruits.cancel();
	}
	
	private void timerEventHandler(){
		Log.i("CatchGameView", "timer event handler");
	}
	
	public void setFruitFallDelay(int delay){
		fruitFallDelay = delay;
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
		canvas.drawText("Panier: " + CatchGameActivity.basket.getFruits(), CatchGameActivity.basket.getLocationInScreen().y, CatchGameActivity.basket.getLocationInScreen().x, p);
		//canvas.drawBitmap(panier,400,10,null);
		for (Rect fruitBounds:fallingDownFruitsList){
			canvas.drawBitmap(applePict, fruitBounds.top, fruitBounds.left,null);
		}
		invalidate();
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		boolean pressed = false;
		switch (e.getAction()){
        case MotionEvent.ACTION_DOWN:
            pressed = true;
        break;
		}
	    pressedPositionX = e.getX();
	    pressedPositionY = e.getY();
	   // System.out.println(pressedPositionX+" "+pressedPositionY);
	    return pressed;
	}


}
