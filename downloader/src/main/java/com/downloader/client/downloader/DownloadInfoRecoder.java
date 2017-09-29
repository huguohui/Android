package com.downloader.client.downloader;

import com.downloader.io.DataReader;
import com.downloader.io.DataWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class DownloadInfoRecoder {
	public final static String DOWNLOAD_INFO_EXT = ".dti";

	protected DownloadTaskInfo info;

	protected DataWriter dataWriter;

	protected DataReader dataReader;

	public DownloadInfoRecoder(DownloadTaskInfo info) throws FileNotFoundException {
		this.info = info;
		dataWriter = new DataWriter(new FileOutputStream(info.getName()));
	}


	public DownloadInfoRecoder(File file) throws FileNotFoundException {
		dataReader = new DataReader(new FileInputStream(file));
		info = new DownloadTaskInfo();
	}


	public void write() throws IOException {
		dataWriter.write(DownloadTaskInfo.FILE_FORMAT_CHARS);
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
	}


	public DownloadTaskInfo read() throws IOException {
		Arrays.equals(dataReader.read(DownloadTaskInfo.FILE_FORMAT_CHARS.length), DownloadTaskInfo.FILE_FORMAT_CHARS);
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
		return info;
	}


	public void close() throws IOException {
		if (dataWriter != null) {
			dataWriter.close();
		}

		if (dataReader != null) {
			dataReader.close();
		}
	}
}
