package com.example.aircraftwar2024.DAO;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Record {
    private Map<String, Object> record = new HashMap<>();
    public Record(String name, int score, String date) {
        record.put("name", name);
        record.put("score", score);
        record.put("date", date);
    }
    public Record(Map<String, Object> record) {
        this.record.put("name", record.get("name"));
        this.record.put("score", record.get("score"));
        this.record.put("date", record.get("date"));
    }

    @Override
    public int hashCode() {
        return Objects.hash(record.values());
    }

    public Map<String, Object> getRecord(){return record;}

}
