package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.HomeFragmentBinding;
import com.example.myapplication.logix.LogixDatabase;

public class HomeFragment extends Fragment {
    private HomeFragmentBinding binding;
    private HomeRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        adapter = new HomeRecyclerViewAdapter(new LogixDatabase());
        //this just creates a line divider between rows
        binding.recyclerView.addItemDecoration(new
                DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        binding.recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
