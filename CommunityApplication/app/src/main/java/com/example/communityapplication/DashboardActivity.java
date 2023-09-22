package com.example.communityapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.communityapplication.Adapter.MovieAdapter;
import com.example.communityapplication.ModelClasses.CommunityModel;
import com.example.communityapplication.ModelClasses.Community_ModelClass;
import com.example.communityapplication.WebService.Endpoints;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
ArrayList<Community_ModelClass> data = new ArrayList<>();
    MovieAdapter movieAdapter;
    Endpoints endpoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initlization();
        endpoints = RetrofitClient.getEndpoints();
        Call<List<Community_ModelClass>> call = endpoints.retrieve_community_data();
        call.enqueue(new Callback<List<Community_ModelClass>>() {
            @Override
            public void onResponse(Call<List<Community_ModelClass>> call, Response<List<Community_ModelClass>> response) {
                if (response.isSuccessful() && response.body()!=null){
                    List<Community_ModelClass> res = response.body();
                    data.addAll(res);
                }
            }

            @Override
            public void onFailure(Call<List<Community_ModelClass>> call, Throwable t) {

            }
        });

    }





    private void initlization() {
        recyclerView = findViewById(R.id.recyclerView);
    }
}