package com.badsocket.client;

import android.content.ComponentName;
import android.content.Context;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.badsocket.R;
import com.badsocket.engine.MonitorWatcher;
import com.badsocket.engine.downloader.DownloadDescriptor;
import com.badsocket.engine.downloader.DownloadTask;
import com.badsocket.engine.downloader.DownloadTaskInfo;
import com.badsocket.engine.downloader.Downloader;
import com.badsocket.net.WebAddress;
import com.badsocket.util.ComputeUtils;
import com.badsocket.util.Log;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
	@BindView(R.id.listView2)
	protected ListView listView;

	protected Downloader downloader;

	protected IBinder binder;

	protected Looper looper = Looper.getMainLooper();

	protected String savePath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();

	protected Handler handler;

	protected boolean isServiceConnected;

	protected boolean isServiceStarted;

	protected List<DownloadTask> tasks = new ArrayList<>();

	protected MonitorWatcher watcher;

	protected MyAdspter adapter;

	protected ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = service;
			isServiceConnected = true;
			downloader = (Downloader) service;
			downloader.addWatcher(watcher);

			try {
				downloader.start();
			} catch (Exception e) {
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
		adapter = new MyAdspter(this, tasks);
		watcher = new DownloadTaskWatcher(handler);
		listView.setAdapter(adapter);
    }


    public void onStart() {
		super.onStart();
		Log.error("ERROR", "OnStart");
		startService();
	}


	private void startService() {
		if (!isServiceStarted) {
			startService(new Intent(this, DownloadService.class));
//			isServiceStarted = true;
//			if (!isServiceConnected) {
//				bindService(new Intent(this, DownloadService.class), serviceConnection, BIND_AUTO_CREATE);
//			}
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
		Log.error("ERROR", "OnPause");
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
                ev.setText("http://file.douyucdn.cn/download/client/douyu_pc_client_v1.0.zip");

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
											downloader.newTask(new DownloadDescriptor.Builder()
													.setAddress(new WebAddress(new URL(url)))
													.setPath(savePath)
												.build()
											);
										} catch (Exception e) {
											Looper.prepare();
											showToast(Log.getStackTraceString(e));
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
                Log.error("ERROR", "你点击了" + item.getTitle() + "!");
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    public void onRestart() {
		super.onRestart();
		Log.error("ERROR", "OnRestart");
	}


    public void onStop() {
		super.onStop();
		Log.error("ERROR", "OnStop");
	}


    public void onDestroy() {
		super.onDestroy();
		Log.error("ERROR", "OnDestory");
//		stopService();
	}


	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Log.error("back button", "back button");
			moveTaskToBack(false);
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}


//	public void onBackPressed() {
//		Intent intent = new Intent(Intent.ACTION_MAIN, null);
//		intent.addCategory(Intent.CATEGORY_HOME);
//		startActivity(intent);
//	}



	class MyAdspter extends BaseAdapter {
		private List<DownloadTask> data;
		private LayoutInflater layoutInflater;
		private Context context;
		public MyAdspter(Context context,List<DownloadTask> data){
			this.context=context;
			this.data=data;
			this.layoutInflater= LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return data.size();
		}
		/**
		 * 获得某一位置的数据
		 */
		@Override
		public Object getItem(int position) {
			return data.get(position);
		}
		/**
		 * 获得唯一标识
		 */
		@Override
		public long getItemId(int position) {
			return position;
		}


		@Override
		public View getView(int position, View cv, ViewGroup parent) {
			cv = cv == null ? layoutInflater.inflate(R.layout.list, null) : cv;
			ProgressBar pb = (ProgressBar) cv.findViewById(R.id.progress_bar);
			TextView fileName = (TextView) cv.findViewById(R.id.file_name);
			TextView progressText = (TextView) cv.findViewById(R.id.progress_text);
			TextView progressPercent = (TextView) cv.findViewById(R.id.progress_percent);
			Button controlButton = (Button) cv.findViewById(R.id.control_button);
			DownloadTask task = data.get(position);
			DownloadTaskInfo info = (DownloadTaskInfo) task.info();

			if (info != null) {
				fileName.setText(info.getName());
				pb.setProgress((int) (info.getProgress() * 100));
				progressText.setText(ComputeUtils.getFriendlyUnitOfBytes(info.getDownloadLength(), 2)
						+ "/" + ComputeUtils.getFriendlyUnitOfBytes(info.getLength(), 2));
				progressPercent.setText(new DecimalFormat("##0.00").format(info.getProgress() * 100) + "%");

				int resourceId = R.drawable.stoped;
				switch (task.getState()) {
					case running:
						resourceId = R.drawable.downloading;
						break;

					case paused:
						resourceId = R.drawable.paused;
						break;

					case stoped:
						resourceId = R.drawable.stoped;
				}
				controlButton.setBackgroundResource(resourceId);
			}

			return cv;
		}
	}
}

