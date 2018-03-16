package com.example.mobilemanager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.db.Dbbox;
import com.example.fun.Addse;
import com.example.fun.Getbitmap;
import com.example.fun.Getpath;
import com.example.server.Info;
import com.example.server.InfoAdapter;

public class Box extends Activity {
	Dbbox dbhelper;
	String state = null;
	Bitmap bm = null;
	private final String IMAGE_TYPE = "image/*";
	private final int IMAGE_CODE = 0;
	List<Info> ne;
	InfoAdapter<Info> adapter;
	ListView listView;
	List<Info> listinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbhelper = new Dbbox(Box.this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.list);
		Button add = (Button) findViewById(R.id.add);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
				getAlbum.setType(IMAGE_TYPE);
				startActivityForResult(getAlbum, IMAGE_CODE);
			}
		});
		listView = (ListView) findViewById(R.id.lv);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent,View view,int position,long id){
				final int a=position;
				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						//运行代码
						try {
							Addse.unLockImage(ne.get(a).getNadd(),ne.get(a).getBadd(),2);
							File file = new File(ne.get(a).getBadd()); 
							file.renameTo(new File(ne.get(a).getNadd()));
							file=new File(ne.get(a).getNadd());
							file.renameTo(new File(ne.get(a).getBadd()));
							Intent intent = new Intent(Box.this, Show.class);
							Bundle bundle = new Bundle();
							bundle.putString("url", ne.get(a).getBadd());
							bundle.putString("url2", ne.get(a).getNadd());
							intent.putExtras(bundle);
							Box.this.startActivity(intent);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
				
				
			}
		});
		
		
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}

		ContentResolver resolver = getContentResolver();
		if (requestCode == IMAGE_CODE) {
			try {
				Uri originalUri = data.getData(); // 获得图片的uri
				bm = MediaStore.Images.Media.getBitmap(resolver, originalUri); // 显得到bitmap图片
				String path = new Getpath().getPathByUri4kitkat(this,
						originalUri);
				String bp = path.substring(path.lastIndexOf("/") + 1,
						path.length());
				String bpath=path+".manager";
				
				
				Bitmap bitm = new Getbitmap().getImageThumbnail(path, 100, 100);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				//转byte
			    bitm.compress(Bitmap.CompressFormat.PNG, 100, baos);
			    byte[] b1=baos.toByteArray();
			   
			    dbhelper = new Dbbox(Box.this);
				String[] args = { bp,bpath,path };
				String[] column = { "[Fname]", "[Nadd]", "[Badd]" };

				ContentValues c = new ContentValues();
				for (int i = 0; i < args.length; i++) {
					c.put("[Bitmap]",b1);
					c.put(column[i], args[i]);
				}
				SQLiteDatabase dbs = dbhelper.getWritableDatabase();
				dbs.insert("box", null, c);
				 
				Addse.lockImage(path,bpath,2);
				File file = new File(bpath);
				file.renameTo(new File(path));
				file=new File(path);
				file.renameTo(new File(bpath));
				Log.w("Local", path);
				Log.w("Local", bpath);
				
			} catch (IOException e) {

				Log.e("Lostinai", e.toString());

			}
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//向ListView写入数据
				listinfo=new ArrayList<Info>();
				
				SQLiteDatabase dbs = dbhelper.getWritableDatabase();
				Cursor cursor = dbs.rawQuery("select * from box", null);
				while (cursor.moveToNext()) {
					Info in=new Info();
					in.setBitmap(cursor.getBlob(0));
					in.setFname(cursor.getString(1));
					in.setNadd(cursor.getString(2));
					in.setBadd(cursor.getString(3));
					listinfo.add(in);
				}
				ne=listinfo;
				adapter = new InfoAdapter<Info>(Box.this, listinfo, R.layout.simple);
				listView.setAdapter(adapter);
//				adapter.notifyDataSetChanged();
	}

}
