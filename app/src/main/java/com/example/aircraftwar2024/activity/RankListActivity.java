package com.example.aircraftwar2024.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.DAO.DAO;
import com.example.aircraftwar2024.DAO.DataAccess;
import com.example.aircraftwar2024.DAO.Record;
import com.example.aircraftwar2024.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankListActivity extends AppCompatActivity {

    private DAO dataAccess = new DataAccess(RankListActivity.this, null, null, 1);

    private List<Map<String, Object>> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        dataAccess.clear();

        String name = "John4";
        int score = 100;
        String date = (new Date(System.currentTimeMillis())).toString();
        // 创建一个新的 Map 对象
        Map<String, Object> dataMap = new HashMap<>();
        // 将数据放入 Map 中
        dataMap.put("name", name);
        dataMap.put("score", score);
        dataMap.put("date", date);
        dataAccess.addData(new Record(dataMap));

        dataAccess.addData("John4", 100, (new Date(System.currentTimeMillis())).toString());
        dataAccess.addData("John5", 101, (new Date(System.currentTimeMillis())).toString());
        dataAccess.addData("John6", 102, (new Date(System.currentTimeMillis())).toString());

        setContentView(R.layout.activity_rank_list);

        ListView listView =(ListView) findViewById(R.id.rankList);

        data = getData();

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this,
                data,
                R.layout.listitem,
                new String[]{"rank", "name", "score", "date"},
                new int[]{R.id.item_rank, R.id.item_name, R.id.item_score, R.id.item_date}
        );

        listView.setAdapter(simpleAdapter);

        // 设置 ListView 的点击事件监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Map<String, Object> selectedItem = (Map<String, Object>) parent.getItemAtPosition(position);

                new AlertDialog.Builder(RankListActivity.this)
                        .setTitle("DELETE OPTION")
                        .setMessage("DO U WAN TO DELETE?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteItem(selectedItem);
                                //data = getData();
                                //data.remove(selectedItem);
                                simpleAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("NO",null)
                        .show();

                // 获取点击的数据项
                //Map<String, Object> selectedItem = (Map<String, Object>) parent.getItemAtPosition(position);

                // 在这里处理选中的数据，例如删除数据
                //deleteItem(selectedItem);

                // 刷新 ListView
                //simpleAdapter.notifyDataSetChanged();
            }
        });
    }

    private List<Map<String, Object>> getData(){
        //ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        return  dataAccess.getData();
    }
    // 删除选中的数据项
    private void deleteItem(Map<String, Object> item) {

        String dateToDelete = (String) item.get("id");

        Record record = new Record(item);

        //dataAccess.delete(record);
        dataAccess.delete("scores", "id" + "=?", new String[]{dateToDelete});
        //List<Map<String, Object>> data = getData();
        data.remove(item);
        Log.v("tag", Integer.toString(record.hashCode()) );
        Log.v("tag", getData().toString());

        // 从数据库中删除数据


        // 从数据源中删除数据项
        //data = getData();
        //Log.v("tag", data.toString());


    }
}
