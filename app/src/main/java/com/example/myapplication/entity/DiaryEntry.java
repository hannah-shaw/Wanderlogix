package com.example.myapplication.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "diary_entries")
public class DiaryEntry {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "weather")
    public String weather;

    @ColumnInfo(name = "location")
    public String location;

    @ColumnInfo(name = "fee")
    public int fee;

    @ColumnInfo(name = "rating")
    public int rating;
}
