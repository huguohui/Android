package com.badsocket;

import com.badsocket.net.http.BaseHttpRequest;
import com.badsocket.net.http.HttpReceiver;
import com.badsocket.net.newidea.URI;
import com.badsocket.util.FileUtils;
import com.badsocket.util.TimeCounter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

public class Test {
	static long length = 0;

	static ThreadLocal<Date> date = new ThreadLocal<>();

	public HttpReceiver mRec = null;
	long time;
	static String[] urls = {
			"http://sta-op.douyucdn.cn/dypc-client/pkg/Douyu_Live_PC_Client/20200813212236902/DouyuLive_8.3.7.5.exe"
	};

	static File fileA = new File("D:\\temp\\a.txt");

	static void debugTest() throws IOException {
		BaseHttpRequest request = new BaseHttpRequest(new URI(urls[0]));
		request.open();
		//request.setHeader(Http.RANGE, new HttpRequest.Range().toString());
	}

	/*static void serializeTest() throws IOException {

		DownloadTaskInfoStorage.TaskList list = new DownloadTaskInfoStorage.TaskList();
		for (int i = 0; i < 1024; i++) {
			list.list().add(new BaseFileDownloadTaskInfoStorage.DownloadTaskInfoListItem(i * 100, new String(i * 1024 * 1024 + "")));
		}

		TimeCounter.begin();
		ObjectUtils.writeObject(list, new File("D:\\temp\\a.txt"));
		System.out.println("Protostuff Serialize:\t\t" + TimeCounter.end());

		TimeCounter.begin();
		ObjectUtils.writeObjectBuildIn(list, new FileOutputStream("D:\\temp\\b.txt"));
		System.out.println("Java Origin Serialize:\t\t" + TimeCounter.end());
	}

	static void deserializeTest() throws Exception {
		TimeCounter.begin();
		Object a = ObjectUtils.readObject(DownloadTaskInfoStorage.TaskList.class, new FileInputStream("D:\\temp\\a.txt"));
		System.out.println("Protostuff Deserialize:\t\t" + TimeCounter.end());

		TimeCounter.begin();
		Object b = ObjectUtils.readObjectBuildIn(new File("D:\\temp\\b.txt"));
		System.out.println("Java Origin Deserialize:\t\t" + TimeCounter.end());

	}*/

	static File file(String path) {
		return new File(path);
	}

	public static void main(String[] args) throws Exception {
//		System.out.println(2 >> 1);
//		System.out.println(1 << 2);
//		long a = ~1;
//		System.out.println(a + ", " + Long.toBinaryString(a));

/*		RandomReadWrite rrw = new RandomReadWrite();
		rrw.randomWrite();
		rrw.close();*/

		String src = "D:\\Files\\Videos\\动漫\\2011 因果日记" ;
	}

	public static void println(String args) {
		System.out.println(args);
	}

}
