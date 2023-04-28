package com.example.wanderlogix.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.wanderlogix.databinding.LogixContentFragmentBinding;

public class LogixContentFragment extends Fragment {
    LogixContentFragmentBinding binding;
    Bitmap image;
    String content;
    public LogixContentFragment(Bitmap img, String content){
        this.image=img;
        this.content=content;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment using the binding
        binding = LogixContentFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        TextView textView=new TextView(getActivity());
        textView.setText(this.content);
        ImageView imageView= new ImageView(getActivity());
        imageView.setImageBitmap(image);
        binding.linearLayout.addView(imageView);
        binding.linearLayout.addView(textView);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
