package com.example.myapplication.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.myapplication.dao.DiaryDao;
import com.example.myapplication.database.DiaryDatabase;
import com.example.myapplication.entity.DiaryEntry;

import java.util.List;

public class DiaryRepository {
    //private CustomerDAO customerDao;
    private DiaryDao diaryDao;
    private LiveData<List<DiaryEntry>> allDiary;
    public DiaryRepository(Application application){
        DiaryDatabase db = DiaryDatabase.getInstance(application);
        diaryDao =db.diaryDao();
        allDiary= diaryDao.getAll();
    }
    // Room executes this query on a separate thread
    public LiveData<List<DiaryEntry>> getAllDiary(){
        return allDiary;
    }
    public void insert(final DiaryEntry diary){
        DiaryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                diaryDao.insert(diary);
            }
        });
    }
}
