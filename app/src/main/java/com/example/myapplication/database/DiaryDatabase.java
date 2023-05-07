package com.example.myapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.dao.DiaryDao;
import com.example.myapplication.entity.DiaryEntry;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {DiaryEntry.class}, version = 2, exportSchema = false)
public abstract class DiaryDatabase extends RoomDatabase {
    public abstract DiaryDao diaryDao();
    private static DiaryDatabase INSTANCE;
    //we create an ExecutorService with a fixed thread pool so we can later run database operations asynchronously on a background thread.
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    //A synchronized method in a multi threaded environment means that two threads are not allowed to access data at the same time
    public static synchronized DiaryDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DiaryDatabase.class, "DiaryDatabase")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE; }

}
