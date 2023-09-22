package com.example.communityapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.communityapplication.Adapter.ParticpentAdapter;
import com.example.communityapplication.Adapter.RowGateAdapter;
import com.example.communityapplication.ModelClasses.Community_ModelClass;
import com.example.communityapplication.WebService.Endpoints;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedFragment extends Fragment {


    View view;
    RecyclerView recyclerViews,recyclerviewgate,recyclervieservice;
    ParticpentAdapter particpentAdapter;
    RowGateAdapter rowGateAdapter;
    ArrayList<Community_ModelClass> participants = new ArrayList<>();
    Endpoints endpoints;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feed, container, false);
        endpoints = RetrofitClient.getEndpoints();
        initlization();
        onCLickListeners();
        return view;
    }

    private void onCLickListeners() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViews.setLayoutManager(linearLayoutManager);
        ParticpentAdapter particpentAdapter1 = new ParticpentAdapter(getContext(),participants);
        recyclerViews.setAdapter(particpentAdapter1);






        recyclerviewgate.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rowGateAdapter = new RowGateAdapter(getContext());
        recyclerviewgate.setAdapter(rowGateAdapter);




        recyclervieservice.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rowGateAdapter = new RowGateAdapter(getContext());
        recyclervieservice.setAdapter(rowGateAdapter);














        Call<List<Community_ModelClass>> call = endpoints.retrieve_community_data();
        call.enqueue(new Callback<List<Community_ModelClass>>() {
            @Override
            public void onResponse(Call<List<Community_ModelClass>> call, Response<List<Community_ModelClass>> response) {
                if (response.isSuccessful() && response.body()!=null){
                    List<Community_ModelClass> data = response.body();
                    participants.addAll(data);
                    particpentAdapter1.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Community_ModelClass>> call, Throwable t) {

            }
        });
    }

    private void initlization() {
        recyclerViews = view.findViewById(R.id.recyclerview);
        recyclerviewgate = view.findViewById(R.id.recyclerviewgate);
        recyclervieservice = view.findViewById(R.id.recyclervieservice);
    }

}