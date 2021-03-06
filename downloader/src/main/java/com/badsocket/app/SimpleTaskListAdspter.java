package com.badsocket.app;

import android.animation.ObjectAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.badsocket.R;
import com.badsocket.core.DownloadTask;
import com.badsocket.util.CalculationUtils;

import java.text.DecimalFormat;
import java.util.List;

public class SimpleTaskListAdspter extends BaseAdapter {

	private List<DownloadTask> data;
	private LayoutInflater layoutInflater;
	private MainActivity mainActivity;

	public SimpleTaskListAdspter(MainActivity context, List<DownloadTask> data) {
		this.mainActivity = context;
		this.data = data;
		this.layoutInflater = LayoutInflater.from(context);
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
		if (cv == null) {
			cv = layoutInflater.inflate(R.layout.list, null);
			ProgressBar pb = cv.findViewById(R.id.progress_bar);
			TextView fileName = cv.findViewById(R.id.file_name);
			TextView progressText = cv.findViewById(R.id.progress_text);
			TextView progressPercent = cv.findViewById(R.id.progress_percent);
			Button controlButton = cv.findViewById(R.id.control_button);
			DownloadTask task = data.get(position);

			if (task != null) {
				ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", (int) (task.progress() * 100));
				animation.setDuration(100);
				animation.start();

				fileName.setText(task.name());
				progressText.setText(CalculationUtils.getFriendlyUnitOfBytes(task.downloadedSize(), 2)
						+ "/" + CalculationUtils.getFriendlyUnitOfBytes(task.size(), 2));
				progressPercent.setText(new DecimalFormat("##0.00").format(task.progress() * 100) + "%");

				int resourceId = R.drawable.paused;
				switch (task.state()) {
					case DownloadTask.DownloadTaskState.RUNNING:
					case DownloadTask.DownloadTaskState.STARTING:
					case DownloadTask.DownloadTaskState.PREPARING:
						resourceId = R.drawable.downloading;
						break;

					case DownloadTask.DownloadTaskState.PAUSED:
					case DownloadTask.DownloadTaskState.PAUSING:
						resourceId = R.drawable.paused;
						break;

					case DownloadTask.DownloadTaskState.STOPED:
					case DownloadTask.DownloadTaskState.STOPPING:
						resourceId = R.drawable.stoped;
						break;
				}

				if (task.isCompleted()) {
					controlButton.setVisibility(View.GONE);
				}

				controlButton.setBackgroundResource(resourceId);
				controlButton.setOnClickListener(new OnControlButtonClickListener(mainActivity, task));
			}
		}

		return cv;
	}

	public class OnControlButtonClickListener implements View.OnClickListener {
		DownloadTask task;
		MainActivity activity;
		public OnControlButtonClickListener(MainActivity activity, DownloadTask task) {
			this.task = task;
			this.activity = activity;
		}

		@Override
		public void onClick(View v) {
			activity.taskSwitch(task);
		}
	}
}