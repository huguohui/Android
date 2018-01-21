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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.badsocket.R;
import com.badsocket.core.Context;
import com.badsocket.core.DownloadTask;
import com.badsocket.core.MonitorWatcher;
import com.badsocket.core.downloader.DownloadTaskDescriptor;
import com.badsocket.core.downloader.Downloader;
import com.badsocket.core.downloader.factory.ThreadFactory;
import com.badsocket.net.DownloadAddress;
import com.badsocket.util.Log;
import com.badsocket.util.TimeCounter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity
		extends AppCompatActivity
		implements View.OnClickListener, ListView.OnItemClickListener, ListView.OnItemLongClickListener
{

	@BindView(R.id.listView)
	protected ListView listView;

	@BindView(R.id.button_add)
	protected Button btnAdd;

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

	protected ThreadFactory threadFactory;

	protected ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = service;
			isServiceConnected = true;
			downloader = (Downloader) service;
			downloader.addWatcher(watcher);
			downloaderContext = downloader.getDownloaderContext();
			threadFactory = downloaderContext.getThreadFactory();

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
			if (tasks != null) {
				tasks.clear();
				tasks.addAll((List<DownloadTask>) msg.obj);
				adapter.notifyDataSetChanged();
			}
		}

	}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		startServiceAsync();
        setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		PermissionChecker.requestPermissons(this);

		handler = new MessageHanlder();
		adapter = new SimpleTaskListAdspter(this, tasks);
		watcher = new DownloadTaskListWatcher(handler);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		btnAdd.setOnClickListener(this);
    }


    void taskSwitch(final DownloadTask task) {
		if (task.isCompleted()) {
			return;
		}
		if (threadFactory != null) {
			threadFactory.createThread(() -> {
				try {
					int state = task.getState();
					if (state == DownloadTask.DownloadTaskState.RUNNING) {
						downloader.pauseTask(task);
					}
					else if (state == DownloadTask.DownloadTaskState.PAUSED
							|| state == DownloadTask.DownloadTaskState.STOPED
							|| state == DownloadTask.DownloadTaskState.RESTORED) {
						downloader.resumeTask(task);
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_add:
				showToast("你点击了？？？");
				break;
			case R.id.control_button:
				DownloadTask task = (DownloadTask) v.getTag();
				taskSwitch(task);
				break;
		}
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		taskSwitch(tasks.get(position));
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		downloader.deleteTask(tasks.get(position));
		return false;
	}


	public void onStart() {
		super.onStart();
	}


	private void startServiceAsync() {
		new Thread(() -> {
			startService();
		}).start();
	}


	private void startService() {
		if (!isServiceStarted) {
			startService(new Intent(this, DownloadService.class));
			isServiceStarted = true;
			if (!isServiceConnected) {
				bindService(new Intent(this, DownloadService.class), serviceConnection, BIND_AUTO_CREATE);
				isServiceConnected = true;
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
                		"http://issuecdn.baidupcs.com/issue/netdisk/apk/BaiduNetdisk_8.2.0.apk"
						/*"http://down.sandai.net/thunder9/Thunder9.1.40.898.exe\n" +
						"http://dl.doyo.cn/hz/xiazaiba/doyoinstall.exe/downloadname/game_%E5%B0%98%E5%9F%834_10104719_3174.exe\n" +
						"http://file.douyucdn.cn/download/client/douyu_pc_client_v1.0.zip"*/
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
											showToast(e.getMessage(), Toast.LENGTH_LONG);
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


	void toDesktop() {
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}


	public void onBackPressed() {
		if (downloader != null) {
			try {
				downloader.exit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		finish();
	}

}

