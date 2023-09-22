package com.example.shakerapplication.Screens;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shakerapplication.ModelClasses.Load_Plants_Model_Class;
import com.example.shakerapplication.ModelClasses.Load_Storage_Location;
import com.example.shakerapplication.R;
import com.example.shakerapplication.SSLConfiguration.OkHttpUtils;
import com.example.shakerapplication.Services.Routes;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginScreen extends AppCompatActivity {
    EditText username;
    TextInputEditText password;

    ProgressDialog progressDialog;
    Spinner spinner1;
    Spinner spinner2;

    private  String spinnervalue1;
    private  String spinnervalue2;

    private Retrofit retrofit;
    private Routes routes;


    //SETTING UP THE PARAMETERS FOR CREATING RETROFIT INSTANCE



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            try {
                setContentView(R.layout.activity_login_screen);
                Objects.requireNonNull(getSupportActionBar()).hide();
                progressDialog = new ProgressDialog(LoginScreen.this);
                progressDialog.setTitle("Wait");
                progressDialog.setMessage("Validating User.....");
                progressDialog.setCancelable(false);

                SharedPreferences sharedPreferences = getSharedPreferences("plant_and_storagelocation_preference", Context.MODE_PRIVATE);
                String checkUserStatus = sharedPreferences.getString("isUserLoggedIn", "");

                System.out.println("=========The status is:=========" + checkUserStatus);

                if (checkUserStatus.equals("true")) {
                    startActivity(new Intent(getApplicationContext(), DashboardScreen.class));
                    finish();
                } else {

                    //Configuring SSL and Making Retrofit Instance
                    retrofit = OkHttpUtils.createRetrofit(
                            this,  // context of your Android app
                            "https://bar.shaker.com.sa/",  // base URL of API
                            "bilal.pfx",  // filename of the certificate in the assets folder
                            "123123123"  // password for the certificate
                    );

                    routes = retrofit.create(Routes.class);

                    initialization();
                    onClickListener();
                    retrieve_plants_from_server();
                }


            }catch(Exception e){
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
    }



//----------------------------------------- METHOD FOR RETRIEVING PLANTS FROM SERVER -----------------------------------------
    private void retrieve_plants_from_server(){

    try{

        Call<List<Load_Plants_Model_Class>> call = routes.getPlants();
        call.enqueue(new Callback<List<Load_Plants_Model_Class>>() {

            @Override
            public void onResponse(@NonNull Call<List<Load_Plants_Model_Class>> call, Response<List<Load_Plants_Model_Class>> response) {
                   if(response.isSuccessful()){
                     try{
                         progressDialog.dismiss();
                         if(response.body() != null){

                         // List To Access Retrieved Plants
                             List<Load_Plants_Model_Class> loadplants = response.body();

                         //ArrayList For Storing Plants
                             ArrayList<String> plantsList = new ArrayList<>();
                             plantsList.add("Select Plant");

                             for(Load_Plants_Model_Class plant : loadplants) {
                                 plantsList.add(plant.getWerks());
                             }

                         //Updating Spinner Items With API DATA!
                             ArrayAdapter<String> adapter = new ArrayAdapter<>(LoginScreen.this, android.R.layout.simple_spinner_item, plantsList);
                             adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                             spinner1.setAdapter(adapter);
                         }
                     }
                     catch (Exception e){
                         Toast.makeText(LoginScreen.this,"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
                     }
                   }
            }

            @Override
            public void onFailure(Call<List<Load_Plants_Model_Class>> call, @NonNull Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(LoginScreen.this,"Error: "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }catch (Exception e){
        Toast.makeText(LoginScreen.this, "An Error Occured! Please Try Later", Toast.LENGTH_SHORT).show();
    }
    }





//----------------------------------------- METHOD FOR RETRIEVING LOCATIONS BY PLANT FROM SERVER -----------------------------------------
    private void retrieve_storage_location_by_plant(String plant_number){
        try {

            Call<List<Load_Storage_Location>> call = routes.getLocationByPlant(plant_number);
            call.enqueue(new Callback<List<Load_Storage_Location>>() {

                @Override
                public void onResponse(Call<List<Load_Storage_Location>> call, Response<List<Load_Storage_Location>> response) {

                    if(response.isSuccessful()){
                        try{
                            if(response.body() != null){

                                // List To Access Retrieved Plants
                                List<Load_Storage_Location> loadstoragelocation = response.body();

                                //ArrayList For Storing Plants
                                ArrayList<String> storageList = new ArrayList<>();
                                storageList.add("Select Storage Location");

                                for(Load_Storage_Location storage : loadstoragelocation) {
                                    storageList.add(storage.getLGORT());
//                                    System.out.println(storage.getLGORT());
                                }

                                //Updating Spinner Items With API DATA!
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginScreen.this, android.R.layout.simple_spinner_item, storageList);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner2.setAdapter(adapter);
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(LoginScreen.this,"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Load_Storage_Location>> call, Throwable t) {
                    System.out.println(t.getMessage());
                    Toast.makeText(LoginScreen.this,"Error: "+t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });



        }catch (Exception e){
            Toast.makeText(LoginScreen.this, "Error: "+ e.getMessage(), Toast.LENGTH_LONG);
        }
    }




    //    Spinner1 and spinner2 onclick listener
    private void onClickListener() {

      try {
          spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  spinnervalue1 = parent.getItemAtPosition(position).toString();
                  retrieve_storage_location_by_plant(spinnervalue1);
              }

              @Override
              public void onNothingSelected(AdapterView<?> parent) {

              }
          });

          spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  spinnervalue2 = parent.getItemAtPosition(position).toString();
//                Snackbar snackbar = Snackbar.make(view, spinnervalue2, Snackbar.LENGTH_LONG);
//                snackbar.show();
              }

              @Override
              public void onNothingSelected(AdapterView<?> parent) {

              }
          });
      }catch (Exception e){
          Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
          snackbar.show();
      }

    }


    //    id initialization method
    private void initialization() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
    }


    //    login button click data
    public void gotodashboard(View view) {

        try {
            String UserName = username.getText().toString();
            String Password = password.getText().toString();

            if (UserName.equals("")) {
                Snackbar snackbar = Snackbar.make(view, "Please enter your username", Snackbar.LENGTH_LONG);
                snackbar.show();
                return;
            } else if (Password.equals("")) {
                Snackbar snackbar = Snackbar.make(view, "Please enter your password", Snackbar.LENGTH_LONG);
                snackbar.show();
                return;
            } else {
                try {

                    Call<String> call = routes.AuthenticateUser(spinnervalue1, spinnervalue2, UserName, Password);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                            if (response.isSuccessful()) {
                                boolean auth_status = Boolean.parseBoolean(response.body());
                                if (auth_status) {
                                    SharedPreferences sharedPreferences = getSharedPreferences("plant_and_storagelocation_preference", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("isUserLoggedIn", "true");
                                    editor.putString("plant", spinnervalue1);
                                    editor.apply();
                                    editor.putString("storage_location", spinnervalue2);
                                    editor.apply();
                                    editor.putString("username", UserName);
                                    editor.apply();
                                    Snackbar snackbar = Snackbar.make(view, "Successfully Login", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                    startActivity(new Intent(getApplicationContext(), DashboardScreen.class));
                                    finish();
                                } else {
                                    Snackbar snackbar = Snackbar.make(view, "Invalid Credentials", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });


                } catch (Exception e) {
                    Toast.makeText(LoginScreen.this, "Failed", Toast.LENGTH_LONG).show();
                    Snackbar snackbar = Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        }catch (Exception e){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

}
