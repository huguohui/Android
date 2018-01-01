package com.badsocket.app;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.badsocket.R;
import com.badsocket.core.Context;
import com.badsocket.core.DownloadTask;
import com.badsocket.core.MonitorWatcher;
import com.badsocket.core.downloader.DownloadTaskDescriptor;
import com.badsocket.core.downloader.Downloader;
import com.badsocket.net.DownloadAddress;
import com.badsocket.util.Log;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

	@BindView(R.id.listView)
	protected ListView listView;

	protected Downloader downloader;

	protected Context downloaderContext;

	protected IBinder binder;

	protected Looper looper = Looper.getMainLooper();

	protected String savePath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();

	protected Handler handler;

	protected boolean isServiceConnected;

	protected boolean isServiceStarted;

	protected List<DownloadTask> tasks = new ArrayList<>();

	protected MonitorWatcher watcher;

	protected SimpleTaskListAdspter adapter;

	protected ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = service;
			isServiceConnected = true;
			downloader = (Downloader) service;
			downloader.addWatcher(watcher);
			downloaderContext = downloader.getDownloaderContext();

			try {
				downloader.start();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}


		@Override
		public void onServiceDisconnected(ComponentName name) {
			isServiceConnected = false;
		}

	};


	class MessageHanlder extends Handler {

		public void handleMessage(Message msg) {
			tasks.clear();
			tasks.addAll((List<DownloadTask>) msg.obj);
			adapter.notifyDataSetChanged();
		}

	}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		PermissionChecker.requestPermissons(this);

		handler = new MessageHanlder();
		adapter = new SimpleTaskListAdspter(this, tasks);
		watcher = new DownloadTaskListWatcher(handler);
		listView.setAdapter(adapter);
    }


    public void onStart() {
		super.onStart();
		startService();
	}


	private void startService() {
		if (!isServiceStarted) {
			startService(new Intent(this, DownloadService.class));
			isServiceStarted = true;
			if (!isServiceConnected) {
				bindService(new Intent(this, DownloadService.class), serviceConnection, BIND_AUTO_CREATE);
			}
		}
	}


	private void stopService() {
		if (isServiceStarted) {
			stopService(new Intent(this, DownloadService.class));
			if (isServiceConnected) {
				unbindService(serviceConnection);
			}
		}
	}


	public void onPause() {
		super.onPause();
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.action_new_task:
                final EditText ev = new EditText(this);
                ev.setText(
					"http://down.sandai.net/thunder9/Thunder9.1.40.898.exe\n" +
					"http://dl.doyo.cn/hz/xiazaiba/doyoinstall.exe/downloadname/game_%E5%B0%98%E5%9F%834_10104719_3174.exe\n" +
					"http://file.douyucdn.cn/download/client/douyu_pc_client_v1.0.zip"
				);

                new AlertDialog.Builder(this)
						.setTitle("请输入")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setView(ev)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final String url = ev.getText().toString();

								new Thread() {
									public void run() {
										try {
											downloader.newTask(new DownloadTaskDescriptor
													.Builder()
													.setAddress(new DownloadAddress(new URL(url)))
													.build());
										}
										catch (Exception e) {
											Looper.prepare();
											showToast(Log.getStackTraceString(e), Toast.LENGTH_LONG);
											Log.e(e);
											looper.loop();
										}
									}
								}.start();
							}
						})
                        .setNegativeButton("取消", null)
						.show();
                break;
            default:
                Log.e("ERROR", "你点击了" + item.getTitle() + "!");
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


	public void showToast(String msg, int duration) {
		Toast.makeText(this, msg, duration).show();
	}


    public void onRestart() {
		super.onRestart();
	}


    public void onStop() {
		super.onStop();
	}


    public void onDestroy() {
		super.onDestroy();
		stopService();
	}

/*
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Log.e("back button", "back button");
			moveTaskToBack(false);
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}
*/


	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}

}

