package com.example.myapplication.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.myapplication.dao.DiaryDao;
import com.example.myapplication.database.DiaryDatabase;
import com.example.myapplication.entity.DiaryEntry;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class DiaryRepository {
    private DiaryDao diaryDao;
    public DiaryRepository(Context application){
        DiaryDatabase db=DiaryDatabase.getInstance(application);
        diaryDao=db.diaryDao();
    }
    public void insert(DiaryEntry diaryEntry){
        DiaryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                diaryDao.insert(diaryEntry);
            }
        });
    }
    public LiveData<List<Integer>> getAllId(){
        return diaryDao.getId();
    }

    public DiaryEntry findDiarybyId(int diaryId){
        return  diaryDao.findByID(diaryId);
    }
}
