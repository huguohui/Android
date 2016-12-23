package com.downloader;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.downloader.net.Downloader;
import com.downloader.net.Requester;
import com.downloader.net.http.Http;
import com.downloader.net.http.HttpDownloader;
import com.downloader.net.http.HttpRequester;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    ListView listView;
    Downloader.Listener listener;
    Looper looper = Looper.getMainLooper();
    Handler handler ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView2);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            list.add(Integer.toString(i));
        }

        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, list));


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch(msg.what) {
                    case  0:
                        _do(msg.arg1);
                        break;
                }
            }
        };
        listener = new D(handler);
    }


    public void _do(int len) {
        ArrayAdapter<String> ad = ((ArrayAdapter<String>)listView.getAdapter());
        ad.remove(ad.getItem(0));
        ad.add(len + "Bytes");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.action_new_task:
                final EditText ev = new EditText(this);
                ev.setText("http://file.douyucdn.cn/download/client/douyu_pc_client_v1.0.zip");

                new AlertDialog.Builder(this).setTitle("请输入").setIcon(
                            android.R.drawable.ic_dialog_info).setView(ev)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final String url = ev.getText().toString();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Requester r = new HttpRequester(new URL(url), Http.Method.GET);
                                            r.send();
                                            System.out.println(r.getHeader());
                                            Downloader d = new HttpDownloader(r);
                                            d.setDownloadListener(listener);
                                            d.download(android.os.Environment.getExternalStorageDirectory() + "/a.txt");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }
                        })
                        .setNegativeButton("取消", null).show();
                break;
            default:
                showToast("你点击了" + item.getTitle() + "!");
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}


class D implements HttpDownloader.Listener {
    Handler handler;
    D(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onStart(Downloader downloader) {

    }

    @Override
    public void onReceive(Downloader downloader) {
        Message msg = new Message();
        msg.arg1 = (int) downloader.getDownloadedLength();
        handler.sendMessage(msg);

    }

    @Override
    public void onPause(Downloader downloader) {

    }

    @Override
    public void onResume(Downloader downloader) {

    }

    @Override
    public void onFinish(Downloader downloader) {

    }
}
