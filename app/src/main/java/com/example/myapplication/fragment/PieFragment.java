package com.example.myapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.PieFragmentBinding;
import com.example.myapplication.entity.DiaryEntry;
import com.example.myapplication.viewmodel.DiaryViewModel;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieFragment extends Fragment {
    private PieFragmentBinding binding;
    private DiaryViewModel diaryViewModel;

    public PieFragment() {}


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState){
        // Inflate the View for this fragment using the binding
        binding = PieFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        diaryViewModel= ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(DiaryViewModel.class);
        List<Integer> diaryFeeList= diaryViewModel.getFee();
        List<String> diaryLocationList = diaryViewModel.getLocation();

        int diaryCount = 0;
        for(Integer diaryFee:diaryFeeList){
            diaryCount++;
        }
        List<PieEntry> PieEntries = new ArrayList<>();
        for(int i=0;i<diaryCount;i++){
            PieEntries.add(new PieEntry(diaryFeeList.get(i), diaryLocationList.get(i)));
        }
        PieDataSet PieDataSet = new PieDataSet(PieEntries,"Travel Cost");
        PieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        PieData PieData = new PieData(PieDataSet);
        binding.pieChart.setData(PieData);
        binding.pieChart.setDrawEntryLabels(true);
        binding.pieChart.setEntryLabelColor(Color.BLACK);
        binding.pieChart.setEntryLabelTextSize(7f);
        binding.pieChart.setDrawHoleEnabled(true);
        binding.pieChart.setHoleRadius(42f);
        binding.pieChart.setHoleColor(Color.WHITE);
        binding.pieChart.setDrawCenterText(false);
        binding.pieChart.setTransparentCircleRadius(31f);
        binding.pieChart.setTransparentCircleColor(Color.BLACK);
        binding.pieChart.setTransparentCircleAlpha(50);
        binding.pieChart.setRotationAngle(20);
        binding.pieChart.setRotationEnabled(false);
        binding.pieChart.getDescription().setEnabled(false);
        binding.pieChart.setVisibility(View.VISIBLE);
        //refresh the chart
        binding.pieChart.invalidate();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
