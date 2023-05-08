package com.example.myapplication.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.Exclude;

@Entity(tableName = "diary_entries")
public class DiaryEntry {
    @PrimaryKey(autoGenerate = true)
    public Integer id;

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

    @ColumnInfo(name = "updated")
    public  boolean updated;

    //No argument constructor for serialize
    public  DiaryEntry(){
    }

    public DiaryEntry(String title, String date, String description, String weather, String location, int fee, int rating) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.weather = weather;
        this.location = location;
        this.fee = fee;
        this.rating = rating;
        this.updated=false;
    }

    @Exclude
    public int getId() {
        return id;
    }
    @Exclude
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    @Exclude
    public boolean getUpdated(){return updated;};
    @Exclude
    public void setUpdated(boolean updated){this.updated=updated;};

}
