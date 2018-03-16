package com.example.mobilemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.db.Dbblack;

public class Black extends Activity {
	Dbblack dbhelper;
	View layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.black);
		Button add = (Button) findViewById(R.id.add);
		add.setOnClickListener(new OnClickListener() {
			Dialog dialog;
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				dbhelper = new Dbblack(Black.this);
				final SQLiteDatabase dbs = dbhelper.getWritableDatabase();
				layout = View.inflate(Black.this, R.layout.login,
						null);
				dialog = new AlertDialog.Builder(Black.this)
						.setTitle("输入手机号")
						.setView(layout)
						.setNegativeButton("取消", null)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface dialoginterface,
											int i) {
										if(!((EditText) layout.findViewById(R.id.pwd)).getText().toString().equals(null)){
											String[] args = { ((EditText) layout.findViewById(R.id.pwd)).getText().toString() };
											String[] column = { "Bnum" };

											ContentValues c = new ContentValues();
											for (int i1 = 0; i1 < args.length; i1++) {
												c.put(column[i1], args[i1]);
											}
											dbs.insert("black", null, c);
										}
									}
								}).create();
				dialog.show();
			}
		});
		Button query = (Button) findViewById(R.id.query);
		query.setOnClickListener(new OnClickListener() {
			Dialog dialog;
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbhelper = new Dbblack(Black.this);
				final SQLiteDatabase dbs = dbhelper.getWritableDatabase();
				final View layout = View.inflate(Black.this, R.layout.login,
						null);
				dialog = new AlertDialog.Builder(Black.this)
						.setTitle("删除")
						.setView(layout)
						.setNegativeButton("取消", null)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface dialoginterface,
											int i) {
										String num=((EditText) layout.findViewById(R.id.pwd)).getText().toString();
										Cursor cursor= dbs.rawQuery("select count(*) from black where Bnum=?", new String[]{num});
										if(cursor.getCount()==0){
											Toast.makeText(Black.this, "记录不存在",Toast.LENGTH_LONG).show();
										}else{
											dbs.delete("black", "Bnum like ?", new String[]{num});
										}
									}
								}).create();
				dialog.show();
			}
		});
		Button find=(Button) findViewById(R.id.find);
		find.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Black.this, Blacklist.class);
				Black.this.startActivity(intent);
			}
		});
	}
}
