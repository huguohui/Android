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

/**
 * Information for download task.
 */
public class HttpDownloadTaskInfo extends DownloadTaskInfo {

	public final static int MAX_NAME_LEN = 0xff;


	public HttpDownloadTaskInfo(DownloadDescriptor r) {
		super(r);
	}


	public HttpDownloadTaskInfo() {}


	@Override
	public void update(SocketResponse r) {
		HttpResponse rep = (HttpResponse) r;
		length = rep.getContentLength();
		name = rep.getFileName();
	}


	@Override
	public void update(SocketRequest[] r) {
		BaseHttpRequest hr;
		long[] pStart = new long[r.length],
				pLen = new long[r.length];
		for (int i = 0; i < r.length; i++) {
			if ((hr = (BaseHttpRequest) r[i]) == null) {
				continue;
			}

			SocketRequest.Range rg = hr.getRange();
			pStart[i] = rg != null ? rg.start : 0;
			pLen[i] = rg != null ? rg.getRange() : 0;
		}

		setPartOffsetStart(pStart);
		setPartLength(pLen);
	}


	@Override
	public void update(SocketReceiver[] rs) {
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

		protected HttpDownloadTaskInfo info;

		protected ByteArrayOutputStream data = new ByteArrayOutputStream();

		protected DataWriter dataWriter = new DataWriter(data);

		protected DataReader dataReader;

		public Storage(HttpDownloadTaskInfo info) {
			this.info = info;
		}


		public byte[] save() throws IOException {
			dataWriter = new DataWriter(new File(info.getPath(), info.getName()));
			dataWriter.write(HttpDownloadTaskInfo.FILE_FORMAT_INFO);
			dataWriter.writeLong(info.getLength());
			dataWriter.writeLong(info.getDownloadLength());
			dataWriter.writeLong(info.getStartTime());
			dataWriter.writeLong(info.getUsedTime());
			dataWriter.writeInt(info.getTotalThreads());

			int parts = info.getTotalThreads();
			for (int i = 0; i < parts; i++) {
				dataWriter.writeLong(info.getPartOffsetStart()[i]);
				dataWriter.writeLong(info.getPartLength()[i]);
				dataWriter.writeLong(info.getPartDownloadLength()[i]);
			}

			return data.toByteArray();
		}


		public void read(byte[] data) throws IOException {
			dataReader = new DataReader(new ByteArrayInputStream(data));
			info = new HttpDownloadTaskInfo();
			if (!Arrays.equals(dataReader.read(HttpDownloadTaskInfo.FILE_FORMAT_INFO.length),
						HttpDownloadTaskInfo.FILE_FORMAT_INFO)) {
				throw new FileFormatException();
			}

			info.setLength(dataReader.readLong());
			info.setDownloadLength(dataReader.readLong());
			info.setStartTime(dataReader.readLong());
			info.setUsedTime(dataReader.readLong());
			info.setTotalThreads(dataReader.readInt());

			int parts = info.getTotalThreads();
			long[]  partOffsetStarts = new long[parts],
					partLengths = new long[parts],
					partDownloadLengths = new long[parts];

			for (int i = 0; i < parts; i++) {
				partOffsetStarts[i] = dataReader.readLong();
				partLengths[i] = dataReader.readLong();
				partDownloadLengths[i] = dataReader.readLong();
			}

			info.setPartOffsetStart(partOffsetStarts);
			info.setPartLength(partLengths);
			info.setPartDownloadLength(partDownloadLengths);
		}
	}
}
