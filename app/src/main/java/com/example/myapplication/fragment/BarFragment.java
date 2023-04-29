package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.BarFragmentBinding;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BarFragment extends Fragment {
    private BarFragmentBinding binding;

    public BarFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState){
        // Inflate the View for this fragment using the binding
        binding = BarFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        List<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, 50));
        barEntries.add(new BarEntry(1, 24));
        barEntries.add(new BarEntry(2, 18));
        BarDataSet barDataSet = new BarDataSet(barEntries, "travel Score");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        List<String> xAxisValues = new ArrayList<>(Arrays.asList("Good", "General", "Unsatisfied"));
        binding.barChart.getXAxis().setValueFormatter(new
                com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));
        BarData barData = new BarData(barDataSet);
        binding.barChart.setData(barData);
        barData.setBarWidth(1.0f);
        binding.barChart.setVisibility(View.VISIBLE);
        binding.barChart.animateY(10);
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