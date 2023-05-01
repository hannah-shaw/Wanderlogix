package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.HomeFragmentBinding;
import com.example.myapplication.entity.DiaryEntry;
import com.example.myapplication.logix.LogixDatabase;
import com.example.myapplication.repository.DiaryRepository;
import com.example.myapplication.viewmodel.DiaryViewModel;

import java.util.List;

public class HomeFragment extends Fragment {
    private HomeFragmentBinding binding;
    private HomeRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DiaryViewModel diaryViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        diaryViewModel= ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(DiaryViewModel.class);

        //Test simulated data
        DiaryEntry c=new DiaryEntry();
        c.title="Test title";
        c.description="Test description";
        c.date="Test content";
        diaryViewModel.insert(c);
        c.title="Test1 title";
        c.description="Test1 description";
        c.date="Test1 content";
        diaryViewModel.insert(c);

        LiveData<List<Integer>> diaryList=diaryViewModel.getAllId();
        adapter = new HomeRecyclerViewAdapter(diaryViewModel,diaryList.getValue());
        //this just creates a line divider between rows
        binding.recyclerView.addItemDecoration(new
                DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        binding.recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(layoutManager);
        diaryList.observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                adapter = new HomeRecyclerViewAdapter(diaryViewModel,integers);
                //this just creates a line divider between rows
                binding.recyclerView.addItemDecoration(new
                        DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                binding.recyclerView.setAdapter(adapter);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
