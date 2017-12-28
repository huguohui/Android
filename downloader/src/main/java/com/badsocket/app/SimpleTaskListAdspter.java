package com.badsocket.app;

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
import com.badsocket.util.ComputeUtils;

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
			fileName.setText(task.getName());
			pb.setProgress((int) (task.getProgress() * 100));
			progressText.setText(ComputeUtils.getFriendlyUnitOfBytes(task.getDownloadedLength(), 2)
					+ "/" + ComputeUtils.getFriendlyUnitOfBytes(task.getLength(), 2));
			progressPercent.setText(new DecimalFormat("##0.00").format(task.getProgress() * 100) + "%");

			int resourceId = R.drawable.stoped;
			switch (task.getStatus().getCode()) {
				case 0:
					resourceId = R.drawable.downloading;
					break;

				case 1:
					resourceId = R.drawable.paused;
					break;

				case 2:
					resourceId = R.drawable.stoped;
			}
			controlButton.setBackgroundResource(resourceId);
		}

		return cv;
	}
}