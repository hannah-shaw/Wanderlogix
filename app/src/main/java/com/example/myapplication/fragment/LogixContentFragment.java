package com.example.myapplication.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.LogixContentFragmentBinding;
import com.example.myapplication.entity.DiaryEntry;
import com.example.myapplication.viewmodel.DiaryViewModel;

public class LogixContentFragment extends Fragment {
    LogixContentFragmentBinding binding;
    private DiaryViewModel diaryViewModel;
    Bitmap image;
    String id;
    public LogixContentFragment(Bitmap img, String content){
        this.id=content;
    }
    public LogixContentFragment(){
    }

    @Override
    public void onCreate(Bundle arg){
        super.onCreate(arg);
        if(getArguments()!=null){
            this.id=getArguments().getString("content");
        }else{
        this.image=null;
        this.id="";
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment using the binding
        binding = LogixContentFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        diaryViewModel= ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(DiaryViewModel.class);
        System.out.println(this.id);
        int id = Integer.parseInt(this.id);
        DiaryEntry diary = diaryViewModel.findDiarybyId(id);
        if(id%10==0){
            binding.image.setImageResource(R.drawable.i1);
        }
        else if(id%10==1){
            binding.image.setImageResource(R.drawable.i2);
        }
        else if(id%10==2){
            binding.image.setImageResource(R.drawable.i3);
        }
        else if(id%10==3){
            binding.image.setImageResource(R.drawable.i4);
        }
        else if(id%10==4){
            binding.image.setImageResource(R.drawable.i5);
        }
        else if(id%10==5){
            binding.image.setImageResource(R.drawable.i6);
        }
        else if(id%10==6){
            binding.image.setImageResource(R.drawable.i7);
        }
        else if(id%10==7){
            binding.image.setImageResource(R.drawable.i8);
        }
        else if(id%10==8){
            binding.image.setImageResource(R.drawable.i9);
        }
        else if(id%10==9){
            binding.image.setImageResource(R.drawable.i10);
        }

        binding.title.setText(diary.title);
        binding.date.setText("Date: "+diary.date);
        binding.fee.setText("Fee: "+Integer.toString(diary.fee));
        binding.score.setText("Rating: "+Integer.toString(diary.rating));
        binding.content.setText(diary.description);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
