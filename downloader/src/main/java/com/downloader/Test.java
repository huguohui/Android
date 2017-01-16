import java.net.URL;

import com.downloader.client.DownloadTask;
import com.downloader.client.DownloadTaskManager;
import com.downloader.net.AbsReceiver;
import com.downloader.net.http.HttpRequest;
public class Test implements AbsReceiver.Listener {
	
	@Override
	public void onStart(AbsReceiver downloader) {
		System.out.println("Start download.");
	}
	
	@Override
	public void onResume(AbsReceiver downloader) {
		
	}
	
	@Override
	public void onReceive(AbsReceiver downloader) {
		
	}
	
	@Override
	public void onPause(AbsReceiver downloader) {
		
	}
	
	@Override
	public void onFinish(AbsReceiver downloader) {
		/*try {
			//Runtime.getRuntime().exec("notepad c:\\users\\admin\\appData\\local\\temp\\0.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		System.out.println(downloader.getRequest().getHeader());
		System.out.println("Finished!.............");
	}
	
	
	public static void main(String[] args) throws Exception {
		HttpRequest r = new HttpRequest();
		URL url = new URL("http://w.x.baidu.com/alading/anquan_soft_down_all/35013");
//		r.open(url);
//		r.getHeader().add(Http.RANGE, "bytes=0-102400");
//		r.send();
//		AbsReceiver rcv = r.getReceiver();
//		rcv.setSaveTo(new FileOutputStream("c:\\users\\admin\\appData\\local\\temp\\0.txt"));
//		rcv.setReceiverListener(new Test());
//		rcv.start();
//		rcv.getThread().join();
		DownloadTaskManager m = DownloadTaskManager.getInstance();
		m.addTask(new DownloadTask(url));
		m.startTask(0);
		
	}

	@Override
	public void onStop(AbsReceiver absReceiver) {
		
	}

}

