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
        diaryViewModel.SynchronizeGetData();
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
