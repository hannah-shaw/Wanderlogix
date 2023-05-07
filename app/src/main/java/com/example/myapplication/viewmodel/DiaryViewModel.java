package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.User.User;
import com.example.myapplication.entity.DiaryEntry;
import com.example.myapplication.repository.DiaryRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DiaryViewModel  extends AndroidViewModel {
    private FirebaseFirestore firedb = FirebaseFirestore.getInstance();
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

    //The method will insert data in local room database and try to add diary data to remote firebase cloud.
    //If remote failed, just failed, if succeed, update the value of key:update in local.
    public void insert(DiaryEntry diaryEntry) {
        dRepository.insert(diaryEntry);
        firedb.collection(User.getUserEmail()).document(Integer.toString(diaryEntry.id)).set(diaryEntry)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dRepository.setUpdate(diaryEntry.id, true);
                    }
                });
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
    public List<String> getDate(){return dRepository.getDate();}

    //Synchronize for get data from firebase Cloud, add remote new data to local.
    public void SynchronizeGetData(){
        List<Integer> localId=getAllId().getValue();
        List<String> localDocId=new ArrayList<>();
        CollectionReference diaryRef = firedb.collection(User.getUserEmail());
        if(localId!=null) {
            for (Integer id:localId) {localDocId.add(Integer.toString(id));};
            diaryRef.whereNotIn(FieldPath.documentId(),localDocId)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot doc : task.getResult()){
                                    DiaryEntry diary=doc.toObject(DiaryEntry.class);
                                    diary.updated=true;
                                    dRepository.insert(diary);
                                }
                            }
                        }
                    });
        }else{
            diaryRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot doc : task.getResult()){
                            DiaryEntry diary=doc.toObject(DiaryEntry.class);
                            diary.updated=true;
                            dRepository.insert(diary);
                        }
                    }
                }
            });
        }
    }
}
