package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.entity.DiaryEntry;
import com.example.myapplication.repository.DiaryRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DiaryViewModel extends AndroidViewModel {
    private DiaryRepository repo;
    public  DiaryViewModel(Application application){
        super(application);
        repo=new DiaryRepository(application);
    }
    public void insert(DiaryEntry diaryEntry){
        repo.insert(diaryEntry);
    }
    public LiveData<List<Integer>> getAllId(){
        return repo.getAllId();
    }
    public DiaryEntry findDiarybyId(int diaryId){
        return repo.findDiarybyId(diaryId);
    }
}
