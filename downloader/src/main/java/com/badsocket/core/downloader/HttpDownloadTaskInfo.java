/*
package com.badsocket.core.downloader;

import com.badsocket.io.exceptions.FileFormatException;
import com.badsocket.io.writer.DataReader;
import com.badsocket.io.writer.DataWriter;
import com.badsocket.net.SocketReceiver;
import com.badsocket.net.SocketRequest;
import com.badsocket.net.SocketResponse;
import com.badsocket.net.http.HttpReceiver;
import com.badsocket.net.http.BaseHttpRequest;
import com.badsocket.net.http.HttpResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

*/
/**
 * Information for download task.
 *//*

public class HttpDownloadTaskInfo extends DownloadTaskInfo {

	public final static int MAX_NAME_LEN = 0xff;


	public HttpDownloadTaskInfo(DownloadTaskDescriptor r) {
		super(r);
	}


	public HttpDownloadTaskInfo() {}


	@Override
	public void update(Response r) {
		HttpResponse rep = (HttpResponse) r;
		length = rep.getContentLength();
		name = rep.getFileName();
	}


	@Override
	public void update(Request[] r) {
		BaseHttpRequest hr;
		long[] pStart = new long[r.length],
				pLen = new long[r.length];
		for (int i = 0; i < r.length; i++) {
			if ((hr = (BaseHttpRequest) r[i]) == null) {
				continue;
			}

			Request.Range rg = hr.getRange();
			pStart[i] = rg != null ? rg.start : 0;
			pLen[i] = rg != null ? rg.getRange() : 0;
		}

		setPartOffsetStart(pStart);
		setPartLength(pLen);
	}


	@Override
	public void update(Receiver[] rs) {
		HttpReceiver hr;
		long[] pDownLen = new long[rs.length];
		long downloadLen = 0;
		boolean unknowLen = false;

		for (int i = 0; i < rs.length; i++) {
			hr = (HttpReceiver) rs[i];
			if (hr == null) {
				continue;
			}
			pDownLen[i] = hr.getReceivedLength();
			downloadLen += pDownLen[i];
			if (getPartLength()[i] == 0) {
				unknowLen = true;
			}
		}

		setPartDownloadLength(pDownLen);
		setDownloadLength(downloadLen);
		if (unknowLen) {
			setPartLength(pDownLen);
		}

		updateProgress();
	}


	protected void updateProgress() {
		setProgress((float) getDownloadLength() / getLength());
	}


	@Override
	public Storage storage() {
		return new Storage(this);
	}



	protected static class Storage implements DownloadTaskInfo.Storage {

		protected HttpDownloadTaskInfo taskExtraInfo;

		protected ByteArrayOutputStream data = new ByteArrayOutputStream();

		protected DataWriter dataWriter = new DataWriter(data);

		protected DataReader dataReader;

		public Storage(HttpDownloadTaskInfo taskExtraInfo) {
			this.taskExtraInfo = taskExtraInfo;
		}


		public byte[] save() throws IOException {
			dataWriter = new DataWriter(new File(taskExtraInfo.getPath(), taskExtraInfo.getName()));
			dataWriter.write(HttpDownloadTaskInfo.FILE_FORMAT_INFO);
			dataWriter.writeLong(taskExtraInfo.getLength());
			dataWriter.writeLong(taskExtraInfo.getDownloadLength());
			dataWriter.writeLong(taskExtraInfo.getStartTime());
			dataWriter.writeLong(taskExtraInfo.getUsedTime());
			dataWriter.writeInt(taskExtraInfo.getTotalThreads());

			int parts = taskExtraInfo.getTotalThreads();
			for (int i = 0; i < parts; i++) {
				dataWriter.writeLong(taskExtraInfo.getPartOffsetStart()[i]);
				dataWriter.writeLong(taskExtraInfo.getPartLength()[i]);
				dataWriter.writeLong(taskExtraInfo.getPartDownloadLength()[i]);
			}

			return data.toByteArray();
		}


		public void read(byte[] data) throws IOException {
			dataReader = new DataReader(new ByteArrayInputStream(data));
			taskExtraInfo = new HttpDownloadTaskInfo();
			if (!Arrays.equals(dataReader.read(HttpDownloadTaskInfo.FILE_FORMAT_INFO.length),
						HttpDownloadTaskInfo.FILE_FORMAT_INFO)) {
				throw new FileFormatException();
			}

			taskExtraInfo.setLength(dataReader.readLong());
			taskExtraInfo.setDownloadLength(dataReader.readLong());
			taskExtraInfo.setStartTime(dataReader.readLong());
			taskExtraInfo.setUsedTime(dataReader.readLong());
			taskExtraInfo.setTotalThreads(dataReader.readInt());

			int parts = taskExtraInfo.getTotalThreads();
			long[]  partOffsetStarts = new long[parts],
					partLengths = new long[parts],
					partDownloadLengths = new long[parts];

			for (int i = 0; i < parts; i++) {
				partOffsetStarts[i] = dataReader.readLong();
				partLengths[i] = dataReader.readLong();
				partDownloadLengths[i] = dataReader.readLong();
			}

			taskExtraInfo.setPartOffsetStart(partOffsetStarts);
			taskExtraInfo.setPartLength(partLengths);
			taskExtraInfo.setPartDownloadLength(partDownloadLengths);
		}
	}
}
*/
