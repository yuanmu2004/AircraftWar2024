package com.example.aircraftwar2024.DAO;

import java.util.List;
import java.util.Map;

public interface DAO {
    public List<Map<String, Object>> getData();
    public void delete(Record record);
    public void clear();
    public void addData(Record record);

    public int delete(String table,
                      String whereClause,
                      String[] whereArgs);

    public long addData(String john, int i, String string);
}
