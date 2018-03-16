package com.example.blacknum;

import java.lang.reflect.Method;
import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;
import com.example.db.Dbblack;


public class BlackNumService extends Service {
    private TelephonyManager tm;
    private MyPhoneStateListener listener;
    Dbblack dbhelper;
    @Override
    public void onCreate() {
    	Log.w("service","suc");
        super.onCreate();
        tm= (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        listener=new MyPhoneStateListener();
        tm.listen(listener,PhoneStateListener.LISTEN_CALL_STATE);
       // nm= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }
    
    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
    	return START_STICKY;
    }
    private final class MyPhoneStateListener extends PhoneStateListener{
        //private long startTime = 0;
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            // TODO Auto-generated method stub
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    //查询黑名单并写入数据库
                    //重新建立查询语句
                    dbhelper=new Dbblack(BlackNumService.this);
                    SQLiteDatabase dbs=dbhelper.getReadableDatabase();
                    Cursor cursor= dbs.rawQuery("select * from black", null);
                    ArrayList<String> list=new ArrayList<String>();
                    while (cursor.moveToNext()) {
    					String b1 = cursor.getString(0);
    					Log.w("service",b1);
    					list.add(b1);
    				}
                    if (list!=null&&list.contains(incomingNumber))
                    {
                        endCall();
                        return;
                    }

                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    break;

                default:
                    break;
            }
        }

    }
    private void endCall(){
        try {
            Class<?> clazz = Class.forName("android.os.ServiceManager");
            Method method = clazz.getMethod("getService", String.class);
            IBinder ibinder = (IBinder) method.invoke(null, Context.TELEPHONY_SERVICE);
            ITelephony iTelephony = ITelephony.Stub.asInterface(ibinder);
            iTelephony.endCall();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
