package com.example.aircraftwar2024.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RecordDAOSQLite extends SQLiteOpenHelper implements RecordDAO {

    public static final String DATABASE_NAME = "RecordsSave.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TABLE_CREATE =
            "CREATE TABLE scores (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name TEXT, " +
                    "score INTEGER, " +
                    "date TEXT);";

    private List<Map<String, Object>> records;
    public RecordDAOSQLite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.v("info", "RecordDAO instantiated");
        records = new LinkedList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }


    public void flush() {
        records.clear();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("scores", null, null, null, null, null,"score DESC");
        int rank = 0;
        while (cursor.moveToNext()) {
            rank += 1;
            Map<String, Object> record = new HashMap<>();
            record.put("rank", rank);
            record.put("name", cursor.getString(cursor.getColumnIndexOrThrow("name")));
            record.put("score", cursor.getInt(cursor.getColumnIndexOrThrow("score")));
            record.put("date", cursor.getString(cursor.getColumnIndexOrThrow("date")));
            record.put("id", cursor.getString(cursor.getColumnIndexOrThrow("id")));
            records.add(record);
        }
        cursor.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS scores");
        onCreate(db);
    }

    @Override
    public List<Map<String, Object>> getData() {
        flush();
        return records;
    }


    private void delete(String table,
                      String whereClause,
                      String[] whereArgs)
    {
        this.getWritableDatabase().delete(table, whereClause, whereArgs);
    }

    @Override
    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("scores",null, null);
    }


    public void add(Record record) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", record.hashCode());
        values.put("name", (String) record.get("name"));
        values.put("score", (int) record.get("score"));
        values.put("date", (String) record.get("date"));

        db.insert("scores", null, values);
        db.close();
        flush();
    }

    public void add(String name, int score, String date) {
        add(new Record(name, score, date));

    }
    public void delete(int position) {
        delete("scores", "id" + "=?", new String[]{(String) records.get(position).get("id")});
//        records.remove(position);
        flush();
    }



}
