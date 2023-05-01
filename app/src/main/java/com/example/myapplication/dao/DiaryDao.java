package com.example.myapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.myapplication.entity.DiaryEntry;
import java.util.*;

@Dao
public interface DiaryDao {
    @Query("SELECT * FROM diary_entries")
    LiveData<List<DiaryEntry>> getAll();

    @Query("SELECT * FROM diary_entries WHERE id = :diaryid")
    DiaryEntry findByID(int diaryid);

    @Insert
    void insert(DiaryEntry diary);

    @Delete
    void delete(DiaryEntry diary);

    @Update
    void updateDiary(DiaryEntry diary);

    @Query("DELETE FROM diary_entries")
    void deleteAll();

    @Query("SELECT fee FROM diary_entries")
    List<Integer> getFee();

    @Query("SELECT rating FROM diary_entries")
    List<Integer> getRating();

    @Query("SELECT location FROM diary_entries")
    List<String> getLocation();

    @Query("SELECT id FROM diary_entries")
    LiveData<List<Integer>> getId();


}
