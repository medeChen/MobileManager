package com.example.mobilemanager;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.example.fun.Addse;

public class Show extends Activity {
	Addse se=new Addse();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.image);
		Bundle bundle=this.getIntent().getExtras();
		ImageView show=(ImageView) findViewById(R.id.show);
		show.setImageURI(Uri.parse(bundle.get("url").toString()));
		
	}
	@Override
	public void onDestroy(){
	    super.onDestroy();
	    Bundle bundle=this.getIntent().getExtras(); 
	    String url=bundle.get("url").toString();
	    String url1=bundle.get("url2").toString();
	    final String a=url;
		final String b=url1;
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				//ÔËÐÐ´úÂë
				try {
					Addse.lockImage(a,b,2);
					File file = new File(b); 
					file.renameTo(new File(a));
					file=new File(a);
					file.renameTo(new File(b));
				    Log.w("hello", "destory");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
}
