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
import com.badsocket.core.downloader.DownloadTaskDescriptor;
import com.badsocket.core.downloader.Downloader;
import com.badsocket.core.downloader.factory.ThreadFactory;
import com.badsocket.net.newidea.URI;
import com.badsocket.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity
		extends AppCompatActivity
		implements View.OnClickListener, ListView.OnItemClickListener, ListView.OnItemLongClickListener {

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

	protected SimpleTaskListAdspter adapter;

	protected ThreadFactory threadFactory;

	protected ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = service;
			isServiceConnected = true;
			downloader = (Downloader) service;
			downloaderContext = downloader.getDownloaderContext();
			threadFactory = downloaderContext.getThreadFactory();
			new DownloadStatusMonitor(downloader, handler).start();

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
			switch (msg.what) {
				case 1:
					if (tasks != null) {
						tasks.clear();
						tasks.addAll((List<DownloadTask>) msg.obj);
						adapter.notifyDataSetChanged();
					}
					break;
			}
		}
	}

	class DownloadStatusMonitor extends TimerTask {

		Downloader downloader;

		Handler handler;

		Timer timer = new Timer();

		DownloadStatusMonitor(Downloader downloader, Handler handler) {
			this.downloader = downloader;
			this.handler = handler;
		}

		void start() {
			timer.schedule(this, 0, 1000);
		}

		@Override
		public void run() {
			if (downloader == null)
				return;

			Message msg = handler.obtainMessage();
			msg.what = 1;
			msg.obj = downloader.taskList();
			handler.sendMessage(msg);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startService();
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		PermissionChecker.requestPermissions(this);

		handler = new MessageHanlder();
		adapter = new SimpleTaskListAdspter(this, tasks);

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		btnAdd.setOnClickListener(this);
	}

	public void taskSwitch(final DownloadTask task) {
		if (task.isCompleted()) {
			return;
		}

		if (threadFactory != null) {
			try {
				int state = task.state();
				switch(state) {
					case DownloadTask.DownloadTaskState.RUNNING:
						downloader.pauseTask(task);
						break;
					case DownloadTask.DownloadTaskState.UNSTART:
						downloader.startTask(task);
						break;
					case DownloadTask.DownloadTaskState.PAUSED:
					case DownloadTask.DownloadTaskState.STOPED:
					case DownloadTask.DownloadTaskState.STORED:
						downloader.resumeTask(task);
						break;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_add:
				showToast("你点击了？？？");
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

		switch (id) {
			case R.id.action_new_task:
				final EditText ev = new EditText(this);
				ev.setText(
						"http://sta-op.douyucdn.cn/dypc-client/pkg/Douyu_Live_PC_Client/20200813212236902/DouyuLive_8.3.7.5.exe"
				);

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("请输入");
				builder.setIcon(android.R.drawable.ic_dialog_info);
				builder.setView(ev);
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						final String url = ev.getText().toString();

						new Thread(() -> {
							try {
								downloader.newTask(new DownloadTaskDescriptor
										.Builder()
										.setURI(new URI(url))
										.build());
							}
							catch (Exception e) {
								Looper.prepare();
								showToast(e.getMessage(), Toast.LENGTH_LONG);
								Log.e(e);
								looper.loop();
							}
						}).start();
					}
				});
				builder.setNegativeButton("取消", null);
				builder.show();
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

	void backDesktop() {
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}

	public void onBackPressed() {
		if (downloader != null) {
			downloaderContext.getThreadFactory().createThread(() -> {
				try {
					downloader.exit();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
		finish();
	}

}

