import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

import com.downloader.net.AbsReceiver;
import com.downloader.net.http.Http;
import com.downloader.net.http.HttpRequest;



public class Test {
	public Object obj = new Object();
	
	
	
	public static void main(String[] args) throws Exception {
		HttpRequest r = new HttpRequest();
		r.open(new URL("http://www.baidu.com"));
		r.getHeader().add(Http.RANGE, "bytes=0-102400");
		r.send();
		System.out.println(r.getHeader());
		AbsReceiver rcv = r.getDownloader();
		rcv.setSaveTo(new FileOutputStream("c:\\users\\admin\\appData\\local\\temp\\0.txt"));
		rcv.start();
	}
}

