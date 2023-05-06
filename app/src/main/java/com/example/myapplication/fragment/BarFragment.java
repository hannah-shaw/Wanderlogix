package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.BarFragmentBinding;
import com.example.myapplication.viewmodel.DiaryViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BarFragment extends Fragment {
    private BarFragmentBinding binding;
    private DiaryViewModel diaryViewModel;

    public BarFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState){
        // Inflate the View for this fragment using the binding
        binding = BarFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        diaryViewModel= ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(DiaryViewModel.class);
        List<Integer> ratingList = diaryViewModel.getRating();
        Map<Integer, Integer> ratingCounts = new HashMap<>();
        for (int i = 0; i <= 10; i++) {
            ratingCounts.put(i, 0);
        }
        for (Integer rating : ratingList) {
            ratingCounts.put(rating, ratingCounts.get(rating) + 1);
        }
        List<BarEntry> barEntries = new ArrayList<>();
        for(int i=0;i<=10;i++) {
            barEntries.add(new BarEntry(i, ratingCounts.get(i)));
        }

        //Bar chart settings
        BarDataSet barDataSet = new BarDataSet(barEntries, "travel Score");
        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        List<String> xAxisValues = new ArrayList<>(Arrays.asList("0", "1", "2","3", "4", "5","6",
                "7", "8","9", "10"));
        binding.barChart.getXAxis().setValueFormatter(new
                com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));
        BarData barData = new BarData(barDataSet);
        binding.barChart.setData(barData);
        binding.barChart.setScaleEnabled(false);
        binding.barChart.getDescription().setEnabled(false);
        barData.setBarWidth(0.7f);
        binding.barChart.setVisibility(View.VISIBLE);
        //refresh the chart
        binding.barChart.invalidate();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}