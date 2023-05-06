package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.databinding.ReportFragmentBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ReportFragment extends Fragment {
    ReportFragmentBinding binding;
    PageAdapter pageAdapter;
    ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ReportFragmentBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        pageAdapter = new PageAdapter(this);
        viewPager = binding.pager;
        viewPager.setAdapter(pageAdapter);
        TabLayout tabLayout = binding.tabLayout;
        new TabLayoutMediator(tabLayout, viewPager,(tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Travel Cost");
                    break;
                case 1:
                    tab.setText("Travel Score");
                    break;
            }
        }).attach();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
