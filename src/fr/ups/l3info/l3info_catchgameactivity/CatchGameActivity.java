package fr.ups.l3info.l3info_catchgameactivity;

import fr.ups.l3info.l3info_catchgamedatastructure.Fruit;
import fr.ups.l3info.l3info_catchgamedatastructure.FruitBasket;
import fr.ups.l3info.l3info_catchgametemplate.R;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.graphics.Point;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* 
 * Main screen for the game
 * Comments to be added
 * To be modified to implement your own version of the game
 */
public class CatchGameActivity extends Activity {
	Timer t = new Timer();
	List<Fruit> fruitList;
	static FruitBasket basket;
	CatchGameView fruitView;
	Button bStart;
	Button bStop;
	int fruitFallDelay = 1000;
	int init1 = 15;
	int init2 = 255;
	final int FPS = 40;
	static int fruitRadius = 22;
	Point basketLocation = new Point (10,400);

	//TimerTask updateBall = new UpdateBallTask();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_catch_game);
		basket = new FruitBasket(basketLocation);
		fruitView = (CatchGameView)findViewById(R.id.l3InfoCatchGameView1);
		bStart = (Button)findViewById(R.id.buttonStart);
		bStop = (Button)findViewById(R.id.buttonStop);
		bStop.setEnabled(false);
		
		
		bStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				buttonStartClickEventHandler();
				
			}
	

		});
		
		testInitFruitList();
		fruitView.setFruitList(fruitList);
		
	}

	private void testInitFruitList() {
		fruitList = new ArrayList<Fruit>();
		fruitList.add(new Fruit(new Point(init1, 15), fruitRadius));
		fruitList.add(new Fruit(new Point(init2, 215), fruitRadius));
		
	}

	private void buttonStartClickEventHandler() {
		fruitView.initTimer();	
		//t.schedule(updateBall, 0, 1000);
		//initTimer();
		bStop.setEnabled(true);
		bStart.setEnabled(false);
	}
	

	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.catch_game, menu);
		return true;
	}
	
	
//	public void initTimer(){
//		t = new Timer();
//		t.schedule(new TimerTask() {			
//			@Override
//			public void run() {
//				try{
//				if(init1 >= 630)
//					init1=0;
//				else
//					init1+=10;
//				if(init2 >= 630)
//					init2=0;
//				else
//					init2+=10;
//				
//			   for(Fruit f: fruitList){
//				   if(f.getLocationInScreen().y == 15)
//				   		f.setLocation(new Point(init1, f.getLocationInScreen().y));
//					else
//						f.setLocation(new Point(init2, f.getLocationInScreen().y));
//			   }
//			  
////			   for(Fruit f : fruitList){
////				   System.out.println(f.getLocationInScreen());
////			   }
//			   fruitView.setFruitList(fruitList);
//			   //fruitView.invalidate();
//				}
//				catch(Exception e){
//					
//				}
//			}
//			
//		}, 0, fruitFallDelay);
//	}
	
}


        	
	   


