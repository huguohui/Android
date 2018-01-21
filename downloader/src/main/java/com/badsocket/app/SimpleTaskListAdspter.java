package com.badsocket.app;

import android.animation.ObjectAnimator;
import android.content.Context;
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
	private Context context;


	public SimpleTaskListAdspter(Context context, List<DownloadTask> data) {
		this.context = context;
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
		cv = cv == null ? layoutInflater.inflate(R.layout.list, null) : cv;
		ProgressBar pb = cv.findViewById(R.id.progress_bar);
		TextView fileName = cv.findViewById(R.id.file_name);
		TextView progressText = cv.findViewById(R.id.progress_text);
		TextView progressPercent = cv.findViewById(R.id.progress_percent);
		Button controlButton = cv.findViewById(R.id.control_button);
		DownloadTask task = data.get(position);

		if (task != null) {
			ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", (int)(task.getProgress() * 100));
			animation.setDuration(100);
			animation.start();

			fileName.setText(task.getName());
			progressText.setText(CalculationUtils.getFriendlyUnitOfBytes(task.getDownloadedLength(), 2)
					+ "/" + CalculationUtils.getFriendlyUnitOfBytes(task.getLength(), 2));
			progressPercent.setText(new DecimalFormat("##0.00").format(task.getProgress() * 100) + "%");

			int resourceId = R.drawable.paused;
			switch (task.getState()) {
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
			controlButton.setTag(task);
			controlButton.setOnClickListener((View.OnClickListener) context);
		}

		return cv;
	}
}