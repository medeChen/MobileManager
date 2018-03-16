package com.example.mobilemanager;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.db.Dbsafe;

public class Setpwd extends Activity {
	Dbsafe dbhelper;
	String state = null;
	String pwd = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set);
		Button cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbhelper = new Dbsafe(Setpwd.this);
				SQLiteDatabase dbs = dbhelper.getWritableDatabase();
				Cursor cursor = dbs.rawQuery("select * from isset", null);
				while (cursor.moveToNext()) {
					pwd = cursor.getString(2);
				}
				if (((EditText) findViewById(R.id.pwd1))
						.getText()
						.toString()
						.equals(((EditText) findViewById(R.id.pwd2)).getText()
								.toString())
						&& !((EditText) findViewById(R.id.pwd1)).getText()
								.toString().equals(null)
						&& pwd != null
						&& pwd.equals(((EditText) findViewById(R.id.pwd1))
								.getText().toString())) {
					dbs.delete("isset", "sid=?", new String[] { "1" });
					finish();
				} else if (pwd == null) {
					finish();
				} else {
					Toast.makeText(Setpwd.this, "√‹¬Î¥ÌŒÛ", Toast.LENGTH_LONG)
							.show();
				}

			}
		});
		Button set = (Button) findViewById(R.id.set);
		set.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbhelper = new Dbsafe(Setpwd.this);
				SQLiteDatabase dbs = dbhelper.getWritableDatabase();
				Cursor cursor = dbs.rawQuery("select * from isset", null);
				while (cursor.moveToNext()) {
					state = cursor.getString(0);
				}
				if (((EditText) findViewById(R.id.pwd1))
						.getText()
						.toString()
						.equals(((EditText) findViewById(R.id.pwd2)).getText()
								.toString())
						&& !((EditText) findViewById(R.id.pwd1)).getText()
								.toString().equals("")) {
					pwd = ((EditText) findViewById(R.id.pwd1)).getText()
							.toString();
					if (state != null) {
						String[] args = { "1", "set", pwd };
						String[] column = { "[Sid]", "[Set]", "[Spwd]" };

						ContentValues c = new ContentValues();
						for (int i = 0; i < args.length; i++) {
							c.put(column[i], args[i]);
						}

						dbs.update("isset", c, "Sid=?", new String[] { "1" });
					} else {
						String[] args = { "1", "set", pwd };
						String[] column = { "[Sid]", "[Set]", "[Spwd]" };

						ContentValues c = new ContentValues();
						for (int i = 0; i < args.length; i++) {
							c.put(column[i], args[i]);
						}

						dbs.insert("isset", null, c);
					}
				}

				finish();
			}
		});
	}
}
