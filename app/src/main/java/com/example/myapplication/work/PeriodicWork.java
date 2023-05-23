package com.example.myapplication.work;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.myapplication.service.UploadService;

public class PeriodicWork extends Worker {
    private static final String TAG = "PeriodicWork";

    public PeriodicWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e(TAG, "doWork: Work is done.");
        Intent intent = new Intent(getApplicationContext(), UploadService.class);
        // 启动服务
        getApplicationContext().startService(intent);
        return Result.success();
    }
}
