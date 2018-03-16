package com.example.mobilemanager;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.blacknum.BlackNumService;
import com.example.db.Dbsafe;

public class MainActivity extends Activity {
	Dbsafe dbhelper;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Intent intent1=new Intent(this, BlackNumService.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startService(intent1);
        Button safe=(Button) findViewById(R.id.safe);
        safe.setOnClickListener(new OnClickListener() {
        	String state,pwd;
        	private Dialog dialog;
			@Override
			public void onClick(View v) {
				state=null;
				pwd=null;
				// TODO Auto-generated method stub
				dbhelper = new Dbsafe(MainActivity.this);
				SQLiteDatabase dbs = dbhelper.getWritableDatabase();
				Cursor cursor = dbs.rawQuery("select * from isset", null);
				while (cursor.moveToNext()) {
					state=cursor.getString(1);
					pwd=cursor.getString(2);
				}
				if(state!=null&&state.equals("set")){
					Log.w("log", "log");
					final View layout = View.inflate(MainActivity.this, R.layout.login,
							null);
					dialog = new AlertDialog.Builder(MainActivity.this)
							.setTitle("密码")
							.setView(layout)
							.setNegativeButton("取消",
									null)
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialoginterface,
												int i) {
											if(pwd.equals(((EditText) layout.findViewById(R.id.pwd)).getText().toString())){
												Log.w("safe", "safe");
												Intent intent=new Intent(MainActivity.this,Safe.class);
												MainActivity.this.startActivity(intent);
											}else{
												Toast.makeText(
														MainActivity.this,
														"密码错误",
														Toast.LENGTH_LONG)
														.show();
											}
										}
									}).create();
					dialog.show();
				}else{
					Log.w("safe", "safe");
					Intent intent=new Intent(MainActivity.this,Safe.class);
					MainActivity.this.startActivity(intent);
				}
				
			}
		});
        Button clean=(Button) findViewById(R.id.clean);
        clean.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,CacheClearActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
        Button black=(Button) findViewById(R.id.black);
        black.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,Black.class);
				MainActivity.this.startActivity(intent);
			}
		});
        Button about=(Button) findViewById(R.id.about);
        about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,About.class);
				MainActivity.this.startActivity(intent);
			}
		});
        final ImageView img=(ImageView) findViewById(R.id.img);
        final Handler myhandle=new Handler(){
        	int rotation=0;
        	@Override
        	public void handleMessage(Message msg){
        		if(msg.what==0x1024){
        			rotation++;
        			img.setRotation(rotation);
        		}
        	}
        };
        new Timer().schedule(new  TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				myhandle.sendEmptyMessage(0x1024);
			}
		}, 0, 60);
    }
}
