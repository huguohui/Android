package com.downloader;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView2);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(Integer.toString(i));
        }

        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, list));
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
                new AlertDialog.Builder(this).setTitle("请输入").setIcon(
                            android.R.drawable.ic_dialog_info).setView(
                            new EditText(this)).setPositiveButton("确定", null)
                            .setNegativeButton("取消", null).show();
                break;
            default:
                Toast.makeText(this, "你点击了" + item.getTitle() + "!", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
