package com.example.wanderlogix.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanderlogix.R;
import com.example.wanderlogix.databinding.HomeCardLayoutBinding;
import com.example.wanderlogix.logix.Logix;
import com.example.wanderlogix.logix.LogixDatabase;

import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter <HomeRecyclerViewAdapter.ViewHolder>{

    LogixDatabase database;
    public HomeRecyclerViewAdapter(LogixDatabase db) {
        this.database = db;
    }

    @Override
    public HomeRecyclerViewAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        HomeCardLayoutBinding binding= HomeCardLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    // this method binds the view holder created with data that will be displayed
    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        final Logix unit = database.getLogix(position);
        viewHolder.binding.logixTiltle.setText(unit.title);
        viewHolder.binding.logixTitleImage.setImageBitmap(unit.titleImgae);
        viewHolder.binding.logixIntroduce.setText(unit.Introduce);
        viewHolder.binding.cardView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle arg=new Bundle();
                        arg.putParcelable("image",unit.image);
                        arg.putString("content",unit.content);
                        replaceFragment(arg,v);
                    }
                }
        );
    }

    private void replaceFragment(Bundle arg, View view) {
        FragmentActivity fragmentActivity = (FragmentActivity) view.getContext();
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment)
                fragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(R.id.logixContentFragment, arg);
    }

    @Override
    public int getItemCount() {
        return database.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private HomeCardLayoutBinding binding;
        public ViewHolder(HomeCardLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
