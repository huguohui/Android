package com.xstream.client;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.xstream.engine.downloader.InternetDownloader;

/**
 * Created by skyrim on 2017/10/10.
 */

public class DownloadService extends Service {

	public static final String TAG = "DownloadService";

	@Override
	public void onCreate() {
		super.onCreate();
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
		return new DownloadAdapter(new InternetDownloader(new Context() {}));
	}


	public boolean onUnbind(Intent intent) {
		super.onUnbind(intent);
		return false;
	}

}
