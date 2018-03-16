package com.example.mobilemanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.db.Dbblack;

public class Blacklist extends Activity{
	Dbblack dbhelper;
	List<Map<String,String>> list=new ArrayList<Map<String,String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.blacklist);
		dbhelper = new Dbblack(Blacklist.this);
		SQLiteDatabase dbs = dbhelper.getWritableDatabase();
		Cursor cursor = dbs.rawQuery("select * from black", null);
		while (cursor.moveToNext()) {
			Map<String,String> black=new HashMap<String,String>();
			black.put("Bnum",cursor.getString(0));
			list.add(black);
		}
		ListView blv=(ListView) findViewById(R.id.blv);
		SimpleAdapter adapter=new SimpleAdapter(Blacklist.this, list, R.layout.item, new String[]{"Bnum"}, new int[]{R.id.black});
		blv.setAdapter(adapter);
	}
}
