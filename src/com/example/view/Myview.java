package com.example.view;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.mobilemanager.R;

public class Myview extends View {
	public float currentx;
	public float currenty;
	public Bitmap myview, myview1;
	public Matrix matrix;
	private Timer timer;

	public Myview(Context context) {
		super(context);
		matrix = new Matrix();
		myview = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.smalllogo);

		setFocusable(true);
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				myhandle.sendEmptyMessage(0x1024);
			}
		}, 0, 40);
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint p = new Paint();
		canvas.drawBitmap(myview1, currentx, currenty, p);
	}

	Handler myhandle = new Handler() {
		int rotation = 0;

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0x1024) {
				if (rotation == 360) {
					rotation = 0;
				}
				rotation++;
				matrix.setRotate(rotation);

				int width = myview.getWidth();
				int height = myview.getHeight();
				myview1 = Bitmap.createBitmap(myview, 0, 0, width, height,
						matrix, false);
			}
		}
	};

	public void destroy() {
		timer.cancel();
	}
}
