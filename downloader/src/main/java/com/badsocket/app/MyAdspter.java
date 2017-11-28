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
import com.badsocket.core.downloader.DownloadTask;
import com.badsocket.core.downloader.DownloadTaskInfo;
import com.badsocket.util.ComputeUtils;

import java.text.DecimalFormat;
import java.util.List;

public class MyAdspter extends BaseAdapter {

	private List<DownloadTask> data;
	private LayoutInflater layoutInflater;
	private Context context;


	public MyAdspter(Context context, List<DownloadTask> data) {
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