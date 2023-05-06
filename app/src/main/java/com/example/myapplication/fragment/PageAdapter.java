package com.example.myapplication.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.fragment.BarFragment;
import com.example.myapplication.fragment.PieFragment;

public class PageAdapter extends FragmentStateAdapter {

    public PageAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0){
            return new PieFragment();
        }
        else if (position == 1){
            return new BarFragment();
        }
        else return new BarFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
