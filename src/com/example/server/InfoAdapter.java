package com.example.server;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.db.Dbbox;
import com.example.fun.Getbitmap;
import com.example.mobilemanager.R;

public class InfoAdapter<T> extends CommonAdapter<T> {
	Dbbox dbhelper;
	Context context1;

	public InfoAdapter(Context context, List<T> mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
		context1=context;
	}

	@Override
	public void convert(ViewHolder helper, T item, int position) {
		Info row = (Info) item;
		helper.setText(R.id.text, row.getFname());
		 //byteתbitmap
		
	    if(row.getBitmap().length!=0){
	    	Bitmap bitm1= BitmapFactory.decodeByteArray(row.getBitmap(), 0, row.getBitmap().length);
	    	helper.setImageBitmap(R.id.img, bitm1);
		}
		
	}


}
