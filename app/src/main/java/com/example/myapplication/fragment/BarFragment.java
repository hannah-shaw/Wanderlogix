package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.BarFragmentBinding;
import com.example.myapplication.viewmodel.DiaryViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BarFragment extends Fragment {
    private BarFragmentBinding binding;
    private DiaryViewModel diaryViewModel;
    private DatePicker datePickerBegin;
    private DatePicker datePickerEnd;


    public BarFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState){
        // Inflate the View for this fragment using the binding
        binding = BarFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        datePickerBegin = binding.dateBarPickerBegin;
        datePickerEnd = binding.dateBarPickerEnd;

        diaryViewModel= ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(DiaryViewModel.class);
        List<Integer> ratingList = diaryViewModel.getRating();
        List<String> dateList = diaryViewModel.getDate();
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
        barSetting(barEntries);

        binding.SetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Get the date from the date picker
                int beginDay = datePickerBegin.getDayOfMonth();
                int beginMonth = datePickerBegin.getMonth();
                int beginYear = datePickerBegin.getYear();
                Calendar beginCalendar = Calendar.getInstance();
                beginCalendar.set(beginYear, beginMonth, beginDay);
                Date beginDate = beginCalendar.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String beginDateString = sdf.format(beginDate);
                System.out.println(beginDateString);

                // Get the date from the date picker
                int EndDay = datePickerEnd.getDayOfMonth();
                int EndMonth = datePickerEnd.getMonth();
                int EndYear = datePickerEnd.getYear();
                Calendar EndCalendar = Calendar.getInstance();
                EndCalendar.set(EndYear, EndMonth, EndDay);
                Date EndDate = EndCalendar.getTime();
                String EndDateString = sdf.format(EndDate);
                System.out.println(EndDateString);

                int i = -1;
                List<BarEntry> BarEntriesDB = new ArrayList<>();
                Map<Integer, Integer> ratingCountsDate = new HashMap<>();
                for (int j = 0; j <= 10; j++) {
                    ratingCountsDate.put(j, 0);
                }
                for (String dateString : dateList) {
                    i++;
                    try {
                        Date dateDB = sdf.parse(dateString);
                        if (dateDB.after(beginDate)) {
                            if (EndDate.after(dateDB)) {
                                ratingCountsDate.put(ratingList.get(i), ratingCountsDate.get(ratingList.get(i)) + 1);
                                for(int k=0;k<=10;k++) {
                                    BarEntriesDB.add(new BarEntry(k, ratingCountsDate.get(k)));
                                    barSetting(BarEntriesDB);
                                }
                            }
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void barSetting(List<BarEntry> barEntries){
        //Bar chart settings
        BarDataSet barDataSet = new BarDataSet(barEntries, "travel Score");
        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        List<String> xAxisValues = new ArrayList<>(Arrays.asList("0", "1", "2","3", "4", "5","6",
                "7", "8","9", "10"));
        binding.barChart.getXAxis().setValueFormatter(new
                com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.7f);
        binding.barChart.setData(barData);
        binding.barChart.setScaleEnabled(false);
        binding.barChart.getDescription().setEnabled(false);
        binding.barChart.setVisibility(View.VISIBLE);
        //refresh the chart
        binding.barChart.invalidate();
    }


}