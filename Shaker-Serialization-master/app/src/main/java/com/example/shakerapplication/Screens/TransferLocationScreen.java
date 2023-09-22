package com.example.shakerapplication.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shakerapplication.ModelClasses.GRN_Response_POJO_Class;
import com.example.shakerapplication.ModelClasses.Load_Plants_Model_Class;
import com.example.shakerapplication.ModelClasses.Load_Storage_Location;
import com.example.shakerapplication.ModelClasses.Post_GRN_Model_Class;
import com.example.shakerapplication.ModelClasses.Post_Transfer_Serial_Model_Class;
import com.example.shakerapplication.ModelClasses.Transfer_GRN_Model_Class;
import com.example.shakerapplication.ModelClasses.Transfer_Serial_Posting;
import com.example.shakerapplication.R;
import com.example.shakerapplication.SSLConfiguration.OkHttpUtils;
import com.example.shakerapplication.Services.Routes;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TransferLocationScreen extends AppCompatActivity {
    String plant;
    String storageLoc;
    Spinner plant1;
    Spinner storagelocation;
    private Retrofit retrofit;
    private Routes routes;

    private Transfer_Serial_Posting [] receivedArray;
    private  Transfer_GRN_Model_Class[] receivedArray2;
    private AlertDialog dialog;

    private  int size_of_data_passed=0;
    private  String hasUserSelectedSTLOCtoSTLOC;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_location_screen);

    try {
        getSupportActionBar().hide();
        retrofit = OkHttpUtils.createRetrofit(
                this,  // context of your Android app
                "https://bar.shaker.com.sa/",  // base URL of API
                "bilal.pfx",  // filename of the certificate in the assets folder
                "123123123"  // password for the certificate
        );

        routes = retrofit.create(Routes.class);
        progressDialog = new ProgressDialog(TransferLocationScreen.this);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        initialization();
        SharedPreferences sharedPreferences = getSharedPreferences("plant_and_storagelocation_preference", Context.MODE_PRIVATE);
        plant = sharedPreferences.getString("plant", "Failed");
        storageLoc = sharedPreferences.getString("storage_location", "Failed");
        Intent intent = getIntent();

        try {

            //Check if user is coming from GRN via P-T-P Transfer Posting!
            if (intent.getStringExtra("isUserComingFromTransferPosting").equals("true")) {

                System.out.println("Yes, the user is coming from GRN via P-T-P Transfer Posting!");
                receivedArray2 = (Transfer_GRN_Model_Class[]) getIntent().getSerializableExtra("transfer_grn_model_classes");
                size_of_data_passed = receivedArray2.length;
                System.out.println("The size of the data passed via P-T-P Transfer Posting is :=> " + size_of_data_passed);
            } else {
                receivedArray = (Transfer_Serial_Posting[]) getIntent().getSerializableExtra("transfer_serial_posting");
                size_of_data_passed = receivedArray.length;
            }


            hasUserSelectedSTLOCtoSTLOC = intent.getStringExtra("move_stock_sloc_to_sloc");


            if (hasUserSelectedSTLOCtoSTLOC.equals("false")) {
                progressDialog.dismiss();
                ArrayList<String> plantsList = new ArrayList<>();
                plantsList.add(receivedArray2[0].getWERKS());

                //Updating Spinner Items With API DATA!
                ArrayAdapter<String> adapter = new ArrayAdapter<>(TransferLocationScreen.this, android.R.layout.simple_spinner_item, plantsList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                plant1.setAdapter(adapter);

                ArrayList<String> storageList = new ArrayList<>();
                storageList.add(receivedArray2[0].getLGORT());
                //Updating Spinner Items With API DATA!
                System.out.println(storageList.get(0));

                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(TransferLocationScreen.this, android.R.layout.simple_spinner_item, storageList);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                storagelocation.setAdapter(adapter2);
                System.out.println("Done!");;

            } else {
                retrieve_storage_location_by_plant(plant);
                ArrayList<String> plantsList = new ArrayList<>();
                plantsList.add(plant);

                //Updating Spinner Items With API DATA!
                ArrayAdapter<String> adapter = new ArrayAdapter<>(TransferLocationScreen.this, android.R.layout.simple_spinner_item, plantsList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                plant1.setAdapter(adapter);
            }


        } catch (Exception e) {
            System.out.println("An Exception Occurred => " + e.getMessage());
        }
    }catch (Exception e){
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    }

    private void initialization() {
        try {
            plant1 = findViewById(R.id.plant1);
            storagelocation = findViewById(R.id.storagelocation);
        }catch (Exception e){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void skiptransfer(View view) {
        onBackPressed();
    }

    public void gotopopup(View view) {
try{
        progressDialog = new ProgressDialog(TransferLocationScreen.this);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Submitting Data...");
        progressDialog.setCancelable(false);

        progressDialog.show();

        Spinner spinner = findViewById(R.id.storagelocation);

        Spinner spinner1 = findViewById(R.id.plant1);
        String new_storage_location = spinner.getSelectedItem().toString();

        String new_or_old_plant_location;


        retrofit = OkHttpUtils.createRetrofit(
                this,  // context of your Android app
                "https://bar.shaker.com.sa/",  // base URL of API
                "bilal.pfx",  // filename of the certificate in the assets folder
                "123123123"  // password for the certificate
        );


        routes = retrofit.create(Routes.class);

        List<Post_Transfer_Serial_Model_Class> payloadList = new ArrayList<>();

        List<Post_GRN_Model_Class> payloadList2 = new ArrayList<>();

        System.out.println("I am here! " + hasUserSelectedSTLOCtoSTLOC);

        if (hasUserSelectedSTLOCtoSTLOC.equals("false")) {
            SharedPreferences sharedPreferences = getSharedPreferences("plant_and_storagelocation_preference", Context.MODE_PRIVATE);
//            new_or_old_plant_location = spinner1.getSelectedItem().toString();
            new_storage_location = sharedPreferences.getString("storage_location", "");
            for (int i = 0; i < size_of_data_passed; i++) {
                Post_GRN_Model_Class post_grn_model_class = new Post_GRN_Model_Class(receivedArray2[i].getEBELN(), receivedArray2[i].getEBELP(), receivedArray2[i].getMATNR(), receivedArray2[i].getWERKS(), new_storage_location/*receivedArray2[i].getLGORT()*/, receivedArray2[i].getMENGE(), receivedArray2[i].getAEDAT(), receivedArray2[i].getRESWK(), receivedArray2[i].getMAKTX(), receivedArray2[i].getSERIALNO());
                payloadList2.add(post_grn_model_class);
            }
        } else {
            for (int i = 0; i < size_of_data_passed; i++) {
                Post_Transfer_Serial_Model_Class post_transfer_serial_model_class = new Post_Transfer_Serial_Model_Class(receivedArray[i].getMATNR(), receivedArray[i].getWERKS(), receivedArray[i].getLGORT(), new_storage_location, receivedArray[i].getSERIALNO());
                payloadList.add(post_transfer_serial_model_class);
            }
        }

//        System.out.println("The data being posted is:  "+payloadList.get(0).getMATNR());
//        System.out.println("The data being posted is:  "+payloadList.get(0).getWERKS());
//        System.out.println("The data being posted is:  "+payloadList.get(0).getLGORT());
//        System.out.println("The data being posted is:  "+payloadList.get(0).getUMLGO());
//        System.out.println("The data being posted is:  "+payloadList.get(0).getSERIALNO());



//Add Checker Here!
        if (hasUserSelectedSTLOCtoSTLOC.equals("true")) {
            Call<List<GRN_Response_POJO_Class>> call = routes.postSerialTransfer(payloadList);
            call.enqueue(new Callback<List<GRN_Response_POJO_Class>>() {
                @Override
                public void onResponse(Call<List<GRN_Response_POJO_Class>> call, Response<List<GRN_Response_POJO_Class>> response) {
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        // Request successful
                        List<GRN_Response_POJO_Class> grnResponses = response.body();
                        try {
                            System.out.println("The MBLNR is: " + grnResponses.get(0).getMBLNR());
                        } catch (Exception e) {
                            System.out.println("Exception Occured = " + e.getMessage());
                        }

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TransferLocationScreen.this);
                        View view1 = getLayoutInflater().inflate(R.layout.popupmessage, null);
                        alertDialog.setView(view1);
                        alertDialog.setCancelable(false);
                        AlertDialog dialog = alertDialog.create();
                        TextView tv = view1.findViewById(R.id.grn_doc_num);
                        TextView tv2 = view1.findViewById(R.id.docnum);
                        TextView tv3 = view1.findViewById(R.id.status);
                        if (grnResponses.get(0).getMBLNR().equals("") || grnResponses.get(0).getMBLNR().equals(null)) {
                            tv2.setText("REMARKS");
                            tv.setText(grnResponses.get(0).getREMARKS());
                            tv3.setText("");
                        } else {
                            System.out.println("The MBLNR is: "+grnResponses.get(0).getMBLNR());
                            tv.setText(grnResponses.get(0).getMBLNR());
                        }

                        dialog.show();

                        MaterialButton btn = view1.findViewById(R.id.gotodashboard);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                if(grnResponses.get(0).getMBLNR().equals("") || grnResponses.get(0).getMBLNR().equals(null)){
                                    onBackPressed();
                                }
                                else{
                                        startActivity(new Intent(getApplicationContext(), DashboardScreen.class));
                                        finish();
                                }
                            }
                        });

                        // Handle response data here
                    } else {
                        // Request failed
                        System.out.println("The post request failed!");
                    }
                }

                @Override
                public void onFailure(Call<List<GRN_Response_POJO_Class>> call, Throwable t) {
                    // Request failed due to network failure or other reasons
                    System.out.println("The post request failed! " + t.getMessage());
                }
            });

        }


        else if (hasUserSelectedSTLOCtoSTLOC.equals("false")) {

                        System.out.println("Data printing starts here: >");




            Call<List<GRN_Response_POJO_Class>> call = routes.postPlantToPlantTransferPayload(payloadList2);

            call.enqueue(new Callback<List<GRN_Response_POJO_Class>>() {
                @Override
                public void onResponse(Call<List<GRN_Response_POJO_Class>> call, Response<List<GRN_Response_POJO_Class>> response) {
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        // Request successful
                        List<GRN_Response_POJO_Class> grnResponses = response.body();

                        System.out.println("The remark is: "+ grnResponses.get(0).getREMARKS());

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TransferLocationScreen.this);
                        View view1 = getLayoutInflater().inflate(R.layout.popupmessage, null);
                        alertDialog.setView(view1);
                        alertDialog.setCancelable(false);
                        dialog = alertDialog.create();
                        TextView tv = view1.findViewById(R.id.grn_doc_num);
                        TextView tv2 = view1.findViewById(R.id.docnum);
                        TextView tv3 = view1.findViewById(R.id.status);

                        if (grnResponses.get(0).getMBLNR().equals("") || grnResponses.get(0).getMBLNR().equals(null)) {
                            tv2.setText("");
                            tv3.setText(grnResponses.get(0).getREMARKS());
                            tv.setText("REMARKS");
                        } else {
                            tv.setText(grnResponses.get(0).getMBLNR());
                        }

                        dialog.show();

                        MaterialButton btn = view1.findViewById(R.id.gotodashboard);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), DashboardScreen.class));
                                finish();
                            }
                        });


                        // Handle response data here
                    } else {
                        // Request failed
                        System.out.println("The post request failed!");
                    }
                }

                @Override
                public void onFailure(Call<List<GRN_Response_POJO_Class>> call, Throwable t) {
                    System.out.println("The post request failed! " + t.getMessage());
                }
            });

        }

    }catch(Exception e){
    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
    snackbar.show();
    }


    }

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
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(TransferLocationScreen.this, android.R.layout.simple_spinner_item, storageList);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                storagelocation.setAdapter(adapter);
                                progressDialog.dismiss();
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(TransferLocationScreen.this,"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Load_Storage_Location>> call, Throwable t) {
                    System.out.println(t.getMessage());
                    Toast.makeText(TransferLocationScreen.this,"Error: "+t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });



        }catch (Exception e){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }


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
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(TransferLocationScreen.this, android.R.layout.simple_spinner_item, plantsList);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                plant1.setAdapter(adapter);
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(TransferLocationScreen.this,"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Load_Plants_Model_Class>> call, @NonNull Throwable t) {
                    System.out.println(t.getMessage());
                    Toast.makeText(TransferLocationScreen.this,"Error: "+t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });


        }catch (Exception e){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }


    public void gotodashboard(View view) {

      try {
          startActivity(new Intent(getApplicationContext(), DashboardScreen.class));
          finishAffinity();
          finish();
      }catch (Exception e){
          Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
          snackbar.show();
      }
    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        SharedPreferences sharedPreferences = getSharedPreferences("plant_and_storagelocation_preference", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.apply();
//        finishAffinity();
//    }



}