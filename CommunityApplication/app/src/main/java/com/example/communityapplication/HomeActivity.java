package com.example.communityapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class HomeActivity extends AppCompatActivity {

    RelativeLayout layoutdash,layoutfeed,layoutcategory,layoutdependent;
    int currentTabIndex = 0;
    View viewprofile, viewhome, viewdasboard,viewhomenew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initlization();
        onClickLsitener();
        loadDAShFragment();
    }

    private void loadDAShFragment() {

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {

            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        DashboardFragment fragment = new DashboardFragment();
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main, fragment, "dash");
        transaction.commit();

    }

    private  void LoadFeedFragment(){


        for (Fragment fragment : getSupportFragmentManager().getFragments()) {

            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        FeedFragment fragment = new FeedFragment();
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main, fragment, "feed");
        transaction.commit();


    }

    private  void loadCategoryFragment(){


        for (Fragment fragment : getSupportFragmentManager().getFragments()) {

            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        CategoryFragment fragment = new CategoryFragment();
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main, fragment, "category");
        transaction.commit();


    }

    private void  loadDependentFragment(){
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        DependentFrgment fragment = new DependentFrgment();
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main, fragment, "dependent");
        transaction.commit();
    }

    private void onClickLsitener() {


        layoutdash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentTabIndex != 0) {
                    currentTabIndex = 0;
                    viewhomenew.setVisibility(View.VISIBLE);
                    viewdasboard.setVisibility(View.GONE);
                    viewprofile.setVisibility(View.GONE);
                    viewhome.setVisibility(View.GONE);
                    loadDAShFragment();
                }





            }
        });

        layoutfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (currentTabIndex != 1) {
                    currentTabIndex = 1;
                    viewhomenew.setVisibility(View.GONE);
                    viewdasboard.setVisibility(View.VISIBLE);
                    viewprofile.setVisibility(View.GONE);
                    viewhome.setVisibility(View.GONE);
                    LoadFeedFragment();

                }



            }
        });


        layoutcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentTabIndex != 2) {
                    currentTabIndex = 2;
                    viewhomenew.setVisibility(View.GONE);
                    viewdasboard.setVisibility(View.GONE);
                    viewprofile.setVisibility(View.GONE);
                    viewhome.setVisibility(View.VISIBLE);
                    loadCategoryFragment();
                }

            }
        });

        layoutdependent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentTabIndex != 3) {
                    currentTabIndex = 3;
                    viewhomenew.setVisibility(View.GONE);
                    viewdasboard.setVisibility(View.GONE);
                    viewprofile.setVisibility(View.VISIBLE);
                    viewhome.setVisibility(View.GONE);
                    loadDependentFragment();
                }



            }
        });



    }

    private void initlization() {

        layoutdash = findViewById(R.id.layout_menu);
        layoutcategory = findViewById(R.id.layout_home);
        layoutdependent = findViewById(R.id.layout_profile);
        layoutfeed = findViewById(R.id.layout_dasboard);
        viewprofile = findViewById(R.id.view_profile);
        viewhome = findViewById(R.id.view_home);
        viewdasboard = findViewById(R.id.view_dasboard);
        viewhomenew = findViewById(R.id.view_home_new);

    }
}