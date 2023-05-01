package com.example.myapplication.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.LogixContentFragmentBinding;

public class LogixContentFragment extends Fragment {
    LogixContentFragmentBinding binding;
    Bitmap image;
    String content;
    public LogixContentFragment(Bitmap img, String content){
        this.image=img;
        this.content=content;
    }
    public LogixContentFragment(){
    }

    @Override
    public void onCreate(Bundle arg){
        super.onCreate(arg);
        if(getArguments()!=null){
            this.image=getArguments().getParcelable("image");
            this.content=getArguments().getString("content");
        }else{
        this.image=null;
        this.content="";
        }
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
