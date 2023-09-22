package com.example.communityapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.communityapplication.Adapter.PersonDetaillAdapter;


public class CategoryFragment extends Fragment {


    View view;
    RecyclerView recyclerView;
    PersonDetaillAdapter personDetaillAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);



        initlization();




        return view;
    }

    private void initlization() {

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        personDetaillAdapter = new PersonDetaillAdapter(getContext());
        recyclerView.setAdapter(personDetaillAdapter);

    }




}