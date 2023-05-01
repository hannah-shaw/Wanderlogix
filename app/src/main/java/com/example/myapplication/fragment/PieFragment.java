package com.example.myapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.PieFragmentBinding;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class PieFragment extends Fragment {
    private PieFragmentBinding binding;

    public PieFragment() {}


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState){
        // Inflate the View for this fragment using the binding
        binding = PieFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        List<PieEntry> PieEntries = new ArrayList<>();
        PieEntries.add(new PieEntry(5000, "Shanghai"));
        PieEntries.add(new PieEntry(4050, "Beijing"));
        PieEntries.add(new PieEntry(25000, "London"));
        PieEntries.add(new PieEntry(20000, "Paris"));
        PieEntries.add(new PieEntry(30010, "NewYork"));
        PieEntries.add(new PieEntry(2200, "Suzhou"));
        PieEntries.add(new PieEntry(2800, "Nanjing"));
        PieEntries.add(new PieEntry(6500, "Hong kong"));
        PieDataSet PieDataSet = new PieDataSet(PieEntries,"Travel Cost");
        PieDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        PieData PieData = new PieData(PieDataSet);
        binding.pieChart.setData(PieData);
        binding.pieChart.setDrawEntryLabels(false);
        binding.pieChart.setEntryLabelColor(Color.WHITE);
        binding.pieChart.setEntryLabelTextSize(20f);
        binding.pieChart.setDrawHoleEnabled(true);
        binding.pieChart.setHoleRadius(42f);
        binding.pieChart.setHoleColor(Color.WHITE);
        binding.pieChart.setDrawCenterText(false);
        binding.pieChart.setCenterText("");
        binding.pieChart.setCenterTextSize(10f);
        binding.pieChart.setCenterTextColor(Color.RED);
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
