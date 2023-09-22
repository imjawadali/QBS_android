package com.example.shakerapplication.Screens;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shakerapplication.Adapters.Scan_Transfer_Screen_Adapter;
import com.example.shakerapplication.ModelClasses.LoadSerialHistory;
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

public class ScanTransferScreen extends AppCompatActivity implements  Scan_Transfer_Screen_Adapter.OnItemClickListener{
    TextView serialno;
    ProgressDialog progressDialog;
    Scan_Transfer_Screen_Adapter scan_transfer_screen_adapter;
    RecyclerView transferrecycle;
    CardView transferadddToList;
    EditText transferserialno, transferserialno2;

    private MaterialButton proceedbtn;

    private String brand,
            category,
            modelno,
            group,
            receivedate,
            sizedimen,
            customer,
            lastplant,
            laststloc,
            currentplant,
            currentstloc,
            modeldescription, onLoginSelectedPlant, OnLoginSelectedSTLoc, isComingFromWhichScreen;

    private int isSerialNumberAlreadyScanned = 0;
    private Retrofit retrofit;
    private Routes routes;

    private Transfer_Serial_Posting[] transfer_serial_postings;
    ArrayList<Transfer_Serial_Posting> transferSerialPostings = new ArrayList<>();

    private int object_counter = 0;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_transfer_screen);

        try {
            getSupportActionBar().hide();
            progressDialog = new ProgressDialog(ScanTransferScreen.this);
            progressDialog.setTitle("Wait");
            progressDialog.setMessage("Adding Item in the List");
            progressDialog.setCancelable(false);
            SharedPreferences sharedPreferences = getSharedPreferences("plant_and_storagelocation_preference", Context.MODE_PRIVATE);
            onLoginSelectedPlant = sharedPreferences.getString("plant", "");
            OnLoginSelectedSTLoc = sharedPreferences.getString("storage_location", "");
            initialization();

            Intent intent = getIntent();
            isComingFromWhichScreen = intent.getStringExtra("move_stock_sloc_to_sloc");

            transferadddToList = findViewById(R.id.transferadddToList);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            transferrecycle.setLayoutManager(linearLayoutManager);
            scan_transfer_screen_adapter = new Scan_Transfer_Screen_Adapter(getApplicationContext());
            scan_transfer_screen_adapter.setOnItemClickListener(this);
            transferrecycle.setAdapter(scan_transfer_screen_adapter);

            try {
                transferadddToList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addScannedItemsToList(v, transferserialno2.getText().toString());
                    }
                });
            } catch (Exception e) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }


    private void addScannedItemsToList(View v, String snum) {
try{
        if (!snum.equals("")) {


            if(snum.length() <=35){

            progressDialog.show();
            Call<String> call_api = routes.CheckSerialNumber(snum);
            call_api.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (response.isSuccessful()) {
                        if (response.body().equals("False")) {

                            if (transferSerialPostings.size() != 0) {

                                for (int i = 0; i < transferSerialPostings.size(); i++) {
                                    if (transferSerialPostings.get(i).getSERIALNO().equals(snum)) {
                                        isSerialNumberAlreadyScanned = 1;
                                    }
                                }
                            }

                            if (isSerialNumberAlreadyScanned != 1) {
                                load_serial_history(snum);
                                ++counter;
                            } else {
                                progressDialog.dismiss();
                                Snackbar snackbar = Snackbar.make(v, "This Serial Number has already been scanned!", Snackbar.LENGTH_LONG);
                                snackbar.show();
                                isSerialNumberAlreadyScanned = 0;
                            }

                        } else {
                            progressDialog.dismiss();
                            Snackbar snackbar = Snackbar.make(v, "You have scanned Model Number. Please scan Serial Number!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Snackbar snackbar = Snackbar.make(v, "An Error Occurred. Try Again Later!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });

        } else {
            Snackbar snackbar = Snackbar.make(v, "Serial Number cannot be greater than 35 digits!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        } else {
            Snackbar snackbar = Snackbar.make(v, "Please enter serial no", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }catch(Exception e){
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}



    private void initialization() {
try{
        proceedbtn = findViewById(R.id.tp_proceed);
        transferrecycle = findViewById(R.id.transferrecycle);
        retrofit = OkHttpUtils.createRetrofit(
                this,  // context of your Android app
                "https://bar.shaker.com.sa/",  // base URL of API
                "bilal.pfx",  // filename of the certificate in the assets folder
                "123123123"  // password for the certificate
        );

        routes = retrofit.create(Routes.class);
        transferserialno = findViewById(R.id.transferserialno);
        transferserialno2 = findViewById(R.id.transferserialno2);

        transferserialno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!transferserialno.getText().toString().equals("")) {
                    addScannedItemsToList(findViewById(android.R.id.content), transferserialno.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }catch(Exception e){
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    }

    public void skiptransfer(View view) {
        onBackPressed();
    }



    public void gotodeliveryscreen(View view) {

        try {
            transfer_serial_postings = new Transfer_Serial_Posting[transferSerialPostings.size()];
            for (int i = 0; i < transferSerialPostings.size(); i++) {
                transfer_serial_postings[i] = transferSerialPostings.get(i);
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("transfer_serial_posting", transfer_serial_postings);
            Intent intent = new Intent(getApplicationContext(), TransferLocationScreen.class);
            intent.putExtras(bundle);
            intent.putExtra("move_stock_sloc_to_sloc", isComingFromWhichScreen);
            intent.putExtra("isUserComingFromTransferPosting", "false");
            startActivity(intent);


        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }



    public void cancel(View view) {
        onBackPressed();
    }


    private void load_serial_history(String scanned_serial_no) {

        try{
        Call<List<LoadSerialHistory>> call = routes.getSerialHistoryData(scanned_serial_no, "");
        call.enqueue(new Callback<List<LoadSerialHistory>>() {

            @Override
            public void onResponse(Call<List<LoadSerialHistory>> call, Response<List<LoadSerialHistory>> response) {

                if (response.isSuccessful()) {
                    List<LoadSerialHistory> data = response.body();
                    LoadSerialHistory object;

                    for (int i = 0; i < data.size(); i++) {
                        object = data.get(i);
                        brand = object.getBrand();
                        category = object.getCategory();
                        modelno = object.getModel();
                        group = object.getGroup();
                        receivedate = object.getReceiveDate();
                        sizedimen = object.getSizeDimension();
                        customer = object.getCustomer();
                        lastplant = object.getLastPlant();
                        laststloc = object.getLastStockLocation();
                        currentplant = object.getCurrentPlant();
                        currentstloc = object.getCurrentStockLocation();
                        modeldescription = object.getModelDescription();
//                        System.out.println("The data coming in transfer posting is:  " + modeldescription + " " + currentplant + " " + currentstloc+" "+modelno);
                    }
                    progressDialog.dismiss();
                    if (data.size() == 0) {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please Enter Correct Serial Number", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        --counter;
                    } else if (!(data.get(0).getCurrentPlant().equals(onLoginSelectedPlant)) || !(data.get(0).getCurrentStockLocation().equals(OnLoginSelectedSTLoc))) {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Wrong Serial No!, please enter the relevant Serial No", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        --counter;
                    } else {

                        if ((!(data.get(0).getLast_mov().equals("601")))) {
                            if ((!(data.get(0).getLast_mov().equals("653")))) {
                                scan_transfer_screen_adapter.addItem(Integer.toString(counter), scanned_serial_no, currentstloc, currentplant, modeldescription);
                                transferSerialPostings.add(new Transfer_Serial_Posting(modelno, currentplant, currentstloc, scanned_serial_no));
                                ++object_counter;
                            }
                            else {
                                progressDialog.dismiss();
                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "PGI Already Done For This Serial Number!", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        }
                        else {
                            progressDialog.dismiss();
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "PGI Already Done For This Serial Number!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }

                    transferserialno.setText("");
                    transferserialno2.setText("");

                    if(counter > 0){
                        proceedbtn.setEnabled(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<LoadSerialHistory>> call, Throwable t) {
                System.out.println("An error occured in transfer posting! " + t.getMessage());
            }
        });
    }catch(Exception e){
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    }


    public void gotodashboard(View view) {
        startActivity(new Intent(getApplicationContext(),DashboardScreen.class));
        finishAffinity();
        finish();
    }


    @Override
    public void onItemClick(int position) {
        try{
            transferSerialPostings.remove(position);
            transfer_serial_postings = new Transfer_Serial_Posting[transferSerialPostings.size()];

            for (int i = 0; i < transferSerialPostings.size(); i++) {
                transfer_serial_postings[i] = transferSerialPostings.get(i);
            }

            System.out.println("the new size is: " + transfer_serial_postings.length);
            counter--;
            object_counter--;
            if(counter > 0){
                proceedbtn.setEnabled(true);
            }
            else{
                proceedbtn.setEnabled(false);
            }

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