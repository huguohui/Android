package com.downloader.client;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.downloader.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity {
    ListView listView;
    Looper looper = Looper.getMainLooper();
    Handler handler ;
    String savePath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView2);
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("progress", i);
            list.add(map);
        }

        final MyAdspter ma = new MyAdspter(this, list);
        listView.setAdapter(ma);



        final Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int last = i;
                i += new Random().nextInt(30);
                int num = 0, diff = i - last;
                if (i >= 100) {
                    timer.cancel();
                }



                while(num++ < diff) {
                    Message m = new Message();
                    m.what = 0;
                    m.arg1 = last + num;
                    ((Map<String, Object>) ma.getItem(0)).put("progress", last + num);
                    Log.e("progress", String.valueOf(last + num));
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                    handler.sendMessage(m);
                }
            }
        }, 0, 1000);


        handler = new Handler() {
            public int last = 0;
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch(msg.what) {
                    case  0:
                        ma.notifyDataSetChanged();
                        break;
                }
            }
        };
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


class MyAdspter extends BaseAdapter {
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public MyAdspter(Context context,List<Map<String, Object>> data){
        this.context=context;
        this.data=data;
        this.layoutInflater= LayoutInflater.from(context);
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
        pb.setProgress((Integer) data.get(0).get("progress"));
        return cv;
    }
}
