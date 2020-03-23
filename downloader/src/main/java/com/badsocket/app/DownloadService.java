package com.badsocket.app;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.badsocket.R;
import com.badsocket.core.downloader.DownloaderContext;
import com.badsocket.core.downloader.InternetDownloader;

/**
 * Created by skyrim on 2017/10/10.
 */

public class DownloadService extends Service {

	public static final String TAG = "DownloadService";

	@Override
	public void onCreate() {
		super.onCreate();

		Notification.Builder mNotifyBuilder = new Notification.Builder(DownloadService.this)
				.setSmallIcon(R.mipmap.ic_launcher)
				.setTicker("这是什么东西？")
				.setWhen(System.currentTimeMillis())
				.setContentTitle(getString(R.string.app_name))
				.setContentText("下载任务进行中");
		Notification notification = mNotifyBuilder.build();
		this.startForeground(0xffff, notification);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		return START_REDELIVER_INTENT;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		DownloadAdapter downloader = null;
		try {
			downloader = new DownloadAdapter(new InternetDownloader(new DownloaderContext(this)));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return downloader;
	}

	public boolean onUnbind(Intent intent) {
		super.onUnbind(intent);
		return false;
	}

}
