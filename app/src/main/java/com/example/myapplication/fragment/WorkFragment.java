package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.myapplication.databinding.SearchFragmentBinding;
import com.example.myapplication.databinding.WorkFragmentBinding;
import com.example.myapplication.work.PeriodicWork;
import com.example.myapplication.work.TestWork;

import java.util.concurrent.TimeUnit;


public class WorkFragment extends Fragment {
    private WorkFragmentBinding binding;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        binding = WorkFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        PeriodicWorkRequest periodicWorkRequest=new PeriodicWorkRequest.Builder(
                PeriodicWork.class,24, TimeUnit.HOURS)
                .build();
        WorkManager.getInstance().enqueue(periodicWorkRequest);

        binding.workButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OneTimeWorkRequest oneTimeWorkRequest =
                        new OneTimeWorkRequest.Builder(TestWork.class)
                                .addTag("myTag")
                                .build();

                WorkManager.getInstance().enqueue(oneTimeWorkRequest);
                Toast.makeText(getActivity(),"Test work has been done.", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}