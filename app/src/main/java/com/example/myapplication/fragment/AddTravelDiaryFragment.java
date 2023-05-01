package com.example.myapplication.fragment;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import android.text.TextUtils;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.SelectMapActivity;
import com.example.myapplication.databinding.AddTravelDiaryFragmentBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTravelDiaryFragment extends Fragment {
    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText locationEditText;
    private EditText expenseEditText;
    private DatePicker datePicker;

    //private Spinner categorySpinner;
    private SeekBar satisfactionSeekBar;
    private TextView satisfactionScoreTextView;
    private RadioGroup weatherRadioGroup;

    private AddTravelDiaryFragmentBinding binding;
    //private AddFragmentBinding addBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = AddTravelDiaryFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        titleEditText = binding.titleEdittext;
        descriptionEditText = binding.descriptionEdittext;
        locationEditText = binding.locationEdittext;
        expenseEditText = binding.expenseEdittext;
        datePicker = binding.datePicker;
        //categorySpinner = findViewById(R.id.category_spinner);
        satisfactionSeekBar = binding.satisfactionSlider;
        satisfactionScoreTextView = binding.satisfactionScoreTextview;
        weatherRadioGroup = binding.weatherRadiogroup;

        // Set up the category spinner category_array
       /* String[] categories = getResources().getStringArray(R.array.category_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);*/

        // Set up the satisfaction slider
        satisfactionSeekBar.setMax(10);
        satisfactionSeekBar.setProgress(5);
        satisfactionScoreTextView.setText("5");
        satisfactionSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                satisfactionScoreTextView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

         //Set up the save button
        Button saveButton = binding.saveButton;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if title, description, and location are empty
                if (TextUtils.isEmpty(titleEditText.getText())) {
                    titleEditText.setError("Title is required.");
                    String msg = "Title is required";
                    toastMsg(msg);
                    return;
                }

                if (TextUtils.isEmpty(expenseEditText.getText())) {
                    expenseEditText.setError("expense is required.");
                    String msg = "expense is required";
                    toastMsg(msg);
                    return;
                }

                if (TextUtils.isEmpty(descriptionEditText.getText())) {
                    descriptionEditText.setError("Description is required.");
                    String msg = "Description is required.";
                    toastMsg(msg);
                    return;
                }

                if (TextUtils.isEmpty(locationEditText.getText())) {
                    locationEditText.setError("Location is required.");
                    String msg = "Location is required.";
                    toastMsg(msg);
                    return;
                }

                // Check if date is in the future
                /*Calendar calendar = Calendar.getInstance();
                if (calendar.getTimeInMillis() < datePicker.getMiilis()) {
                    datePicker.setError("Date must be in the past.");
                    return;
                }*/

                // Check if weather is selected
                if (weatherRadioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getContext(), "Please select weather.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get the selected category and satisfaction score
                //String category = categorySpinner.getSelectedItem().toString();
                //int satisfactionScore = satisfactionSeekBar.getProgress();

                // Get the user input
                String title = titleEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();
                String location = locationEditText.getText().toString().trim();
                double expense = Double.parseDouble(expenseEditText.getText().toString());
                //int categoryIndex = categorySpinner.getSelectedItemPosition();
                //String satisfaction = satisfactionScoreTextView.getText().toString();
                int satisfaction = satisfactionSeekBar.getProgress();
                int weatherId = weatherRadioGroup.getCheckedRadioButtonId();
                RadioButton weatherRadioButton = view.findViewById(weatherId);
                String weather = weatherRadioButton.getText().toString();

                // Get the date from the date picker
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                Date date = calendar.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String dateString = sdf.format(date);

                // Do something with the user input, such as save it to a database

            }
        });
        binding.mapbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String location=locationEditText.getText().toString();
                if(location.equals("")){
                    Toast.makeText(getContext(), "type any location",Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences preferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("location", location);
                    editor.apply();
                    Intent intent = new Intent(getContext(), SelectMapActivity.class);
                    //intent.putExtra("location", location);
                    startActivity(intent);

                }
                // 跳转到另一个名为ButtonActivity的界面
            }
        });

        binding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addBinding.editText.setText("");
                titleEditText.setText("");
                descriptionEditText.setText("");
                locationEditText.setText("");
                expenseEditText.setText("");
                // categorySpinner.setSelection(0);
                satisfactionSeekBar.setProgress(5);
                satisfactionScoreTextView.setText("5");
                RadioButton sunnyRadioButton = binding.sunnyRadiobutton;
                weatherRadioGroup.check(sunnyRadioButton.getId());

            }
        });

        return view;
    }
    private void toastMsg(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /*public void onSaveButtonClicked(View view) {
        // Get the user input
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();
        double expense = Double.parseDouble(expenseEditText.getText().toString());
        //int categoryIndex = categorySpinner.getSelectedItemPosition();
        //String satisfaction = satisfactionScoreTextView.getText().toString();
        int satisfaction = satisfactionSeekBar.getProgress();
        int weatherId = weatherRadioGroup.getCheckedRadioButtonId();
        RadioButton weatherRadioButton = view.findViewById(weatherId);
        String weather = weatherRadioButton.getText().toString();

        // Get the date from the date picker
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String dateString = sdf.format(date);

        // Validate the user input
        boolean hasError = false;
        if (title.isEmpty()) {
            titleEditText.setError("Required field");
            hasError = true;
        }
        if (location.isEmpty()) {
            locationEditText.setError("Required field");
            hasError = true;
        }
        if (hasError) {
            return;
        }

        // Do something with the user input, such as save it to a database
    }*/

    /*
    public void onClearButtonClicked(View view) {

        titleEditText.setText("");
        descriptionEditText.setText("");
        locationEditText.setText("");
        expenseEditText.setText("");
       // categorySpinner.setSelection(0);
        satisfactionSeekBar.setProgress(5);
        satisfactionScoreTextView.setText("5");
        RadioButton sunnyRadioButton = binding.sunnyRadiobutton;
        weatherRadioGroup.check(sunnyRadioButton.getId());

    }

     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}