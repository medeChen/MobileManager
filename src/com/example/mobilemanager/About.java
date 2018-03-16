package com.example.mobilemanager;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.example.view.Myview;

public class About extends Activity {
	int speed = 10;
	int deg,ca=(int) (Math.random()*30-Math.random()*20+5),cb=(int) (Math.random()*30-Math.random()*20+5);
	private Myview myv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		myv = new Myview(this);
		setContentView(myv);
		myv.setBackgroundResource(R.drawable.bg2);
		WindowManager windowm = this.getWindowManager();
		Display display = windowm.getDefaultDisplay();
		final DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		myv.currentx = metrics.widthPixels / 2 - myv.myview.getWidth() / 2;
		myv.currenty = metrics.heightPixels / 2 - myv.myview.getHeight() / 2;
		deg = (int) (Math.random() * ca)+cb;
		final Handler myhandle = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				myv.invalidate();
				if (msg.what == 0x1020) {
					myv.currentx +=ca;
					myv.currenty +=cb;
					Log.w("ss",myv.currentx+ "");
					Log.w("ss",myv.currenty+ "");
					
				}
			}
		};
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {

				// TODO Auto-generated method stub
				if (myv.currentx < (metrics.widthPixels-myv.myview.getWidth()-20)
						&& myv.currentx >0
						&& myv.currenty <(metrics.heightPixels- myv.myview.getHeight()-100)
						&& myv.currenty >0) {
				} else if ((myv.currentx > (metrics.widthPixels-myv.myview.getWidth()-20))||(myv.currentx) <0) {
					ca=-ca;
				} else if ((myv.currenty > (metrics.heightPixels-myv.myview.getHeight()-100)||(myv.currenty) <0)) {
					cb=-cb;
				}
				myhandle.sendEmptyMessage(0x1020);
			}
		}, 0, 40);
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
		myv.destroy();
		System.gc();
	}
}
