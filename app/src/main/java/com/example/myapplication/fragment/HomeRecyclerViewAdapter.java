package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.HomeCardLayoutBinding;
import com.example.myapplication.entity.DiaryEntry;
import com.example.myapplication.viewmodel.DiaryViewModel;

import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    private DiaryViewModel diaryViewModel;
    private List<Integer> diaryListId;

    public HomeRecyclerViewAdapter(DiaryViewModel diaryViewModel, List<Integer> diaryListId) {
        this.diaryViewModel = diaryViewModel;
        this.diaryListId = diaryListId;
    }

    @Override
    public HomeRecyclerViewAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        HomeCardLayoutBinding binding = HomeCardLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    // this method binds the view holder created with data that will be displayed
    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        DiaryEntry diary;
        Integer idDiary = diaryListId.get(position);
        diary = diaryViewModel.findDiarybyId(idDiary);
        if (diary.id % 10 == 0) {
            viewHolder.binding.image.setImageResource(R.drawable.i1);
        } else if (diary.id % 10 == 1) {
            viewHolder.binding.image.setImageResource(R.drawable.i2);
        } else if (diary.id % 10 == 2) {
            viewHolder.binding.image.setImageResource(R.drawable.i3);
        } else if (diary.id % 10 == 3) {
            viewHolder.binding.image.setImageResource(R.drawable.i4);
        } else if (diary.id % 10 == 4) {
            viewHolder.binding.image.setImageResource(R.drawable.i5);
        } else if (diary.id % 10 == 5) {
            viewHolder.binding.image.setImageResource(R.drawable.i6);
        } else if (diary.id % 10 == 6) {
            viewHolder.binding.image.setImageResource(R.drawable.i7);
        } else if (diary.id % 10 == 7) {
            viewHolder.binding.image.setImageResource(R.drawable.i8);
        } else if (diary.id % 10 == 8) {
            viewHolder.binding.image.setImageResource(R.drawable.i9);
        } else if (diary.id % 10 == 9) {
            viewHolder.binding.image.setImageResource(R.drawable.i10);
        }

        viewHolder.binding.logixTiltle.setText(diary.title);
        //viewHolder.binding.logixTitleImage.setImageBitmap(unit.titleImgae);
        viewHolder.binding.logixIntroduce.setText(diary.description);
        viewHolder.binding.cardView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle arg = new Bundle();
                        //arg.putParcelable("image",unit.image);
                        arg.putString("content", String.valueOf(diary.id));
                        replaceFragment(arg, v);
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
        if (diaryListId == null) return 0;
        return diaryListId.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private HomeCardLayoutBinding binding;

        public ViewHolder(HomeCardLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
