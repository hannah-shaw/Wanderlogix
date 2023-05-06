package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.entity.DiaryEntry;
import com.example.myapplication.repository.DiaryRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DiaryViewModel  extends AndroidViewModel {
    private DiaryRepository dRepository;
    private LiveData<List<DiaryEntry>> alldiaries;
    public DiaryViewModel (Application application) {
        super(application);
        dRepository = new DiaryRepository(application);
        alldiaries = dRepository.getAllDiary();
    }
    public LiveData<List<DiaryEntry>> getAllDiary() {
        return alldiaries;
    }
    public void insert(DiaryEntry diaryEntry) {
        dRepository.insert(diaryEntry);
    }
    public LiveData<List<Integer>> getAllId(){
        return dRepository.getAllId();
    }
    public DiaryEntry findDiarybyId(int diaryId){
        return dRepository.findDiarybyId(diaryId);
    }
    public List<Integer> getRating(){return dRepository.getRating();}
    public List<Integer> getFee(){return dRepository.getFee();}

    public List<String> getLocation(){return dRepository.getLocation();}


}
