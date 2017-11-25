package com.badsocket.client;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.badsocket.R;
import com.badsocket.engine.Context;
import com.badsocket.engine.downloader.InternetDownloader;

/**
 * Created by skyrim on 2017/10/10.
 */

public class DownloadService extends Service {

	public static final String TAG = "DownloadService";

	@Override
	public void onCreate() {
		super.onCreate();

//		Notification.Builder mNotifyBuilder = new Notification.Builder(DownloadService.this)
//				.setSmallIcon(R.mipmap.ic_launcher)
//				.setTicker("")
//				.setWhen(System.currentTimeMillis())
//				.setContentTitle(getString(R.string.app_name))
//				.setContentText("asdfasdf");
//		Notification notification = mNotifyBuilder.build();
//		this.startForeground(1111, notification);
		Log.e(TAG, "onCreate() executed");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(TAG, "onStartCommand() executed");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "onDestroy() executed");
	}


	@Override
	public IBinder onBind(Intent intent) {
		Log.e(TAG, "onBind() executed");
		return new DownloadAdapter(new InternetDownloader(new Context() {}));
	}


	public boolean onUnbind(Intent intent) {
		Log.e(TAG, "onUnbind() executed");
		super.onUnbind(intent);
		return false;
	}

}