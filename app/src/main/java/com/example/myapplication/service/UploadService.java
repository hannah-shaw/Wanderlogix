package com.example.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.User.User;
import com.example.myapplication.entity.DiaryEntry;
import com.example.myapplication.repository.DiaryRepository;
import com.example.myapplication.viewmodel.DiaryViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class UploadService extends Service {
    //private DiaryViewModel diaryViewModel;
    //Viewmodel is not recommended in service!
    private FirebaseFirestore firedb = FirebaseFirestore.getInstance();

    private DiaryRepository dRepository=new DiaryRepository(getApplication());;

    public UploadService() {
        Log.e("TestWork", "Method: UploadService method is called.");
        UpdateFirebase();
    }

    private void UpdateFirebase() {
        Log.e("TestWork", "Method: UpdateFirebase method is called.");
        List<Integer> localId= dRepository.getAllId().getValue();
        if(localId==null){
            return;
        }
        for(int i=0;i<localId.size();i++){
            Integer id=localId.get(i);
            // If not updated before, update it
            if(!dRepository.getUpdated(id)){
                DiaryEntry diaryEntry=dRepository.findDiarybyId(id);
                insert(diaryEntry);
            }
        }
//        diaryViewModel= ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(DiaryViewModel.class);
//        List<Integer> localId= diaryViewModel.getAllId().getValue();
//        for(int i=0;i<localId.size();i++){
//            Integer id=localId.get(i);
//            // If not updated before, update it
//            if(!diaryViewModel.getUpdated(id)){
//                DiaryEntry diaryEntry=diaryViewModel.findDiarybyId(id);
//                diaryViewModel.insert(diaryEntry);
//            }
//        }
    }

    public void insert(DiaryEntry diaryEntry) {
        Integer id = dRepository.insert(diaryEntry);
        diaryEntry.id=id;
        firedb.collection(User.getUserEmail()).document(Integer.toString(diaryEntry.id)).set(diaryEntry)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dRepository.setUpdate(diaryEntry.id, true);
                    }
                });
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}