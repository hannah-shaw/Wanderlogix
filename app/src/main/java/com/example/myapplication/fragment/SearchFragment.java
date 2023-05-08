package com.example.myapplication.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.databinding.ReportFragmentBinding;
import com.example.myapplication.databinding.SearchFragmentBinding;
import com.example.myapplication.retrofit.Items;
import com.example.myapplication.retrofit.RetrofitClient;
import com.example.myapplication.retrofit.RetrofitInterface;
import com.example.myapplication.retrofit.SearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private static final String API_KEY = "AIzaSyCWt-cuEjmGZIwrO2XB3MkOHw4csbGKF8Y";
    private static final String SEARCH_ID_cx = "96f39634a531a4079";
    private String keyword;
    private SearchFragmentBinding binding;
    private RetrofitInterface retrofitInterface;
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState){
        binding = SearchFragmentBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        retrofitInterface = RetrofitClient.getRetrofitService();
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword=binding.editText.getText().toString();
                Call<SearchResponse> callAsync = retrofitInterface.customSearch(API_KEY,SEARCH_ID_cx, keyword);
//makes an async request & invokes callback methods when the response returns
                callAsync.enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call,
                                           Response<SearchResponse> response) {
                        if (response.isSuccessful()) {
                            List<Items> list = response.body().items; //getting snippet from the object in the position
                            String result= list.get(0).getSnippet();
                            Log.i("Error ",result);
                            binding.tvResult.setText(result);
                        }
                        else {
                            Log.i("Error ","Response failed"); }
                    }
                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t){
                        Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_SHORT);
                    } });
            } });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }


}
