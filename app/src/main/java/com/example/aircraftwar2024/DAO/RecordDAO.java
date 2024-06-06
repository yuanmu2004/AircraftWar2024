package com.example.aircraftwar2024.DAO;

import java.util.List;
import java.util.Map;

public interface RecordDAO {

    public List<Map<String, Object>> getData();

    public void clear();

    public void add(Record record);

    public void delete(int position);

    public void add(String name, int score, String date);
}
