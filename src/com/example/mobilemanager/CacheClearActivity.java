package com.example.mobilemanager;

import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.server.CacheInfo;
import com.example.server.CacheInfoProvider;

public class CacheClearActivity extends ListActivity
{
	private static final int LOADING = 0;
	private static final int FINISH = 1;

	private CacheInfoProvider provider;

	private ListView lv_list;
	private LinearLayout ll_load;

	private Vector<CacheInfo> cacheInfos;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case LOADING:
					ll_load.setVisibility(View.VISIBLE);
					break;

				case FINISH:
					ll_load.setVisibility(View.INVISIBLE);
					// 当加载完成之后，就调用provider里面的get方法，
					// 这样就可以得到一个加载完成后的数据了
					cacheInfos = provider.getCacheInfos();
					lv_list.setAdapter(new CacheAdapter());
					break;

				default:
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cache_clear);
		provider = new CacheInfoProvider(handler, this);

		lv_list = getListView();
		ll_load = (LinearLayout) findViewById(R.id.ll_cache_clear_load);
		lv_list.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				
				Intent intent = new Intent();
				intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
				intent.addCategory("android.intent.category.DEFAULT");
				intent.setData(Uri.parse("package:" + cacheInfos.get(position).getPackageName()));
				startActivity(intent);
			}
		});

		loadData();
	}
	
	private void loadData()
	{
		ll_load.setVisibility(View.VISIBLE);
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				provider.initCacheInfos();
			}
		}).start();
	}


	private class CacheAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			return cacheInfos.size();
		}

		@Override
		public Object getItem(int position)
		{
			return cacheInfos.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View view;
			ViewHolder holder;
			CacheInfo info = cacheInfos.get(position);
			if (convertView == null)
			{
				view = View.inflate(CacheClearActivity.this,
						R.layout.cache_clear_item, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) view
						.findViewById(R.id.iv_cache_icon);
				holder.tv_name = (TextView) view
						.findViewById(R.id.tv_cache_name);
				holder.tv_code = (TextView) view
						.findViewById(R.id.tv_cache_code);
				holder.tv_data = (TextView) view
						.findViewById(R.id.tv_cache_data);
				holder.tv_cache = (TextView) view
						.findViewById(R.id.tv_cache_cache);
				view.setTag(holder);
			}
			else
			{
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			holder.iv_icon.setImageDrawable(info.getIcon());
			holder.tv_name.setText(info.getName());
			holder.tv_code.setText("应用大小：" + info.getCodeSize());
			holder.tv_data.setText("数据大小：" + info.getDataSize());
			holder.tv_cache.setText("缓存大小：" + info.getCacheSize());
			return view;
		}

	}

	private class ViewHolder
	{
		ImageView iv_icon;
		TextView tv_name;
		TextView tv_cache;
		TextView tv_code;
		TextView tv_data;

	}

}
