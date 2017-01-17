import java.net.URL;
import java.util.Arrays;

import com.AbsReceiver;
import com.DownloadTask;
import com.downloader.client.DownloadTaskManager;
import com.downloader.http.HttpDownloadTask;
import com.downloader.http.HttpRequest;
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
		System.out.println(downloader.getReceivedLength());
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
		
		
		System.out.println(1000 / 3 + "," + 1000 % 3);
	}
	
	
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://w.x.baidu.com/alading/anquan_soft_down_all/35013");
		DownloadTaskManager m = DownloadTaskManager.getInstance();
		//m.addTask(new DownloadTask(url));
	//	new HttpDownloadTask(url);
		System.out.println(1000 / 3 + "," + 1000 % 3);
		for (int i = 1; i <= 3; i++) {
			System.out.println(i + ":" + (1000 / 3 + (i == 3 ? 1000 % 3 : 0)));
		}
	}

	@Override
	public void onStop(AbsReceiver absReceiver) {
		
	}

}

