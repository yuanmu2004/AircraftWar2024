package com.example.aircraftwar2024.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.DAO.RecordDAO;
import com.example.aircraftwar2024.DAO.RecordDAOSQLite;
import com.example.aircraftwar2024.R;

public class RankListActivity extends AppCompatActivity {

    private RecordDAO dataAccess = new RecordDAOSQLite(RankListActivity.this);

//    private List<Map<String, Object>> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
//        dataAccess.clear();
//        dataAccess.add("Lin", 234, new Date(System.currentTimeMillis()).toString());
//        dataAccess.add("Lu", 432, new Date(System.currentTimeMillis()).toString());
//        for (int i = 0; i <= 100; ++i) {
//            dataAccess.add("Lin1111111111111111111", 1111111111, "0");
//        }

        setContentView(R.layout.activity_record);

        ListView listView =(ListView) findViewById(R.id.rank_list);

//        data = getData();

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this,
                dataAccess.getData(),
                R.layout.activity_item,
                new String[]{"rank", "name", "score", "date"},
                new int[]{R.id.item_rank, R.id.item_name, R.id.item_score, R.id.item_date}
        );

        listView.setAdapter(simpleAdapter);

        // 设置 ListView 的点击事件监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                new AlertDialog.Builder(RankListActivity.this)
                        .setTitle("DELETE OPTION")
                        .setMessage("DO U WAN TO DELETE?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dataAccess.delete(position);
                                simpleAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("NO",null)
                        .show();


            }
        });
    }


}
