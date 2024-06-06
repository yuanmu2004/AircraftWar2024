package com.example.aircraftwar2024.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataAccess extends SQLiteOpenHelper implements DAO{

    public static final String DATABASE_NAME_EASY = "easyModeDataBase.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TABLE_CREATE =
            "CREATE TABLE scores (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "score INTEGER, " +
                    "date TEXT);";

    public DataAccess(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME_EASY, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS scores");
        onCreate(sqLiteDatabase);
    }

    @Override
    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> dataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("scores", null, null, null, null, null,"score DESC");

        int rank = 1;

        while (cursor.moveToNext()) {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("rank", rank);
            dataMap.put("name", cursor.getString(cursor.getColumnIndexOrThrow("name")));
            dataMap.put("score", cursor.getInt(cursor.getColumnIndexOrThrow("score")));
            dataMap.put("date", cursor.getString(cursor.getColumnIndexOrThrow("date")));
            dataMap.put("id", cursor.getString(cursor.getColumnIndexOrThrow("id")));
            dataList.add(dataMap);
            rank++;
        }
        cursor.close();
        return dataList;
    }

    @Override
    public void delete(Record record) {
        String dateToDelete = (String) record.getRecord().get("id");
        this.getWritableDatabase().delete("scores", "id"+"=?", new String[]{dateToDelete});
    }
    public int delete(String table,
                      String whereClause,
                      String[] whereArgs)
    {
        return this.getWritableDatabase().delete(table, whereClause, whereArgs);
    }

    @Override
    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("scores",null, null);
    }

    @Override
    public void addData(Record record) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", record.hashCode());
        values.put("name", (String) record.getRecord().get("name"));
        values.put("score", (int) record.getRecord().get("score"));
        values.put("date", (String) record.getRecord().get("date"));

        db.insert("scores", null, values);
        db.close();
    }


    public long addData(String name, int score, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        Record record = new Record(name, score, date);
        ContentValues values = new ContentValues();
        values.put("id", record.hashCode());
        values.put("name", (String) record.getRecord().get("name"));
        values.put("score", (int) record.getRecord().get("score"));
        values.put("date", (String) record.getRecord().get("date"));

        long newRowId = db.insert("scores", null, values);
        db.close();

        return newRowId;
    }
//    public void deleteData(long id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String selection = "id = ?";
//        String[] selectionArgs = { String.valueOf(id) };
//        try {
//            db.delete("scores", selection, selectionArgs);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
