package com.example.myapplication.repository;
import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.myapplication.dao.DiaryDao;
import com.example.myapplication.database.DiaryDatabase;
import com.example.myapplication.entity.DiaryEntry;

import java.util.List;

public class DiaryRepository {
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
    public Integer insert(final DiaryEntry diary){
        return (int)diaryDao.insert(diary);
    }
    public LiveData<List<Integer>> getAllId(){
        return diaryDao.getId();
    }

    public DiaryEntry findDiarybyId(int diaryId){
        return  diaryDao.findByID(diaryId);
    }

    public List<Integer> getRating(){return diaryDao.getRating();}

    public List<Integer> getFee(){return diaryDao.getFee();}

    public List<String> getLocation(){return diaryDao.getLocation();}

    public List<String> getDate(){return diaryDao.getDate();}

    public void setUpdate(Integer diaryId, boolean updated){
        DiaryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                diaryDao.setUpdated(diaryId, updated);
            }
        });
    }
    public boolean getUpdated(Integer diaryId){
        return diaryDao.getUpdated(diaryId);
    }
}
