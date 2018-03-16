package com.example.server;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.RemoteException;

public class CacheInfoProvider
{
	private Handler handler;
	private PackageManager packageManager;
	private Vector<CacheInfo> cacheInfos;
	private int size = 0;

	public CacheInfoProvider(Handler handler, Context context)
	{
		// �õ�һ����������
		packageManager = context.getPackageManager();
		this.handler = handler;
		cacheInfos = new Vector<CacheInfo>();
	}

	public void initCacheInfos()
	{
		// ��ȡ�����а�װ�˵�Ӧ�ó������Ϣ��������Щж���˵ģ���û��������ݵ�Ӧ�ó���
		List<PackageInfo> packageInfos = packageManager
				.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		size = packageInfos.size();
		for (int i = 0; i < size; i++)
		{
			PackageInfo packageInfo = packageInfos.get(i);
			CacheInfo cacheInfo = new CacheInfo();
			// �õ�����
			String packageName = packageInfo.packageName;
			cacheInfo.setPackageName(packageName);
			// �õ�Ӧ�ó������Ϣ
			ApplicationInfo applicationInfo = packageInfo.applicationInfo;
			// �õ�Ӧ�ó���ĳ�����
			String name = applicationInfo.loadLabel(packageManager).toString();
			cacheInfo.setName(name);
			// �õ�Ӧ�ó����ͼ��
			Drawable icon = applicationInfo.loadIcon(packageManager);
			cacheInfo.setIcon(icon);

			initDataSize(cacheInfo, i);
		}
	}

	private void initDataSize(final CacheInfo cacheInfo, final int position)
	{
		try
		{
			Method method = PackageManager.class.getMethod(
					"getPackageSizeInfo", new Class[] { String.class,
							IPackageStatsObserver.class });
			method.invoke(packageManager,
					new Object[] { cacheInfo.getPackageName(),
							new IPackageStatsObserver.Stub()
							{
								@Override
								public void onGetStatsCompleted(
										PackageStats pStats, boolean succeeded)
										throws RemoteException
								{
									System.out.println("onGetStatsCompleted" + position);
									long cacheSize = pStats.cacheSize;
									long codeSize = pStats.codeSize;
									long dataSize = pStats.dataSize;

									cacheInfo.setCacheSize(TextFormater
											.dataSizeFormat(cacheSize));
									cacheInfo.setCodeSize(TextFormater
											.dataSizeFormat(codeSize));
									cacheInfo.setDataSize(TextFormater
											.dataSizeFormat(dataSize));

									cacheInfos.add(cacheInfo);

									if (position == (size - 1))
									{
										// ����ȫ��ȡ����Ϣ֮�󣬷���һ���ɹ�����Ϣ
										// 1��Ӧ�ľ���CacheClearActivity�����FINISH
										handler.sendEmptyMessage(1);
									}
								}
							} });
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public Vector<CacheInfo> getCacheInfos()
	{
		return cacheInfos;
	}

	public void setCacheInfos(Vector<CacheInfo> cacheInfos)
	{
		this.cacheInfos = cacheInfos;
	}

}
