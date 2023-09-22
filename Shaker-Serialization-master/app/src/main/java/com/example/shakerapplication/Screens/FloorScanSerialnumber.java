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

import com.example.shakerapplication.Adapters.Floor_Audit_Screen_Adapter;
import com.example.shakerapplication.ModelClasses.Floor_Audit_Model_Class;
import com.example.shakerapplication.ModelClasses.LoadSerialHistory;
import com.example.shakerapplication.ModelClasses.Serial_Registration_Model_Class;
import com.example.shakerapplication.R;
import com.example.shakerapplication.SSLConfiguration.OkHttpUtils;
import com.example.shakerapplication.Services.Routes;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FloorScanSerialnumber extends AppCompatActivity implements Floor_Audit_Screen_Adapter.OnItemClickListener{
    private RecyclerView recycle;
    private Floor_Audit_Screen_Adapter floor_audit_screen_adapter;

    private CardView add_to_list_btn;

    private String model_number_scanned,material_number, plant, st_loc, material_descript, logged_in_user, currentDate, currentTime;

    private int scanned_serial_number_counter=0;
    private Routes routes;

    private ProgressDialog progressDialog;

    private Retrofit retrofit;

    private EditText serial_number_scanned, serial_number_entered;

    private MaterialButton submit_serial_reg;

    private ArrayList<Floor_Audit_Model_Class> floor_audit_saving = new ArrayList<>();


    private TextView total_qty_scanned, plant_display, storage_loc_display;


    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    // Get current time
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_scan_serialnumber);
        getSupportActionBar().hide();



        try {
            initialization();

            Intent intent = getIntent();

            model_number_scanned = intent.getStringExtra("model_number");
            material_number = intent.getStringExtra("material_number");
            material_descript = intent.getStringExtra("material_descript");




            add_to_list_btn.setOnClickListener(view -> {

                if(!serial_number_entered.getText().toString().equals("")){
                    progressDialog.show();
                    add_items_to_list(serial_number_entered.getText().toString());
                }
                else{
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please Scan Valid Model Number!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            });


            serial_number_scanned.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {
                        if (!serial_number_scanned.getText().toString().equals("")){
                            progressDialog.show();
                            add_items_to_list(serial_number_scanned.getText().toString());
                        }
                    }
                    catch (Exception e){
                        System.out.println(e.getMessage());
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+e.getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });


            submit_serial_reg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    submit_floor_audit();
                }
            });


        }
        catch (Exception e){
            progressDialog.dismiss();
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }


    }



    private void initialization() {
        recycle = findViewById(R.id.recycle_view_serial_reg);
        serial_number_scanned = findViewById(R.id.serial_number_scanned_by_scanner_serial_reg);
        serial_number_entered = findViewById(R.id.manuallly_entered_snum_serial_reg);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycle.setLayoutManager(linearLayoutManager);
        floor_audit_screen_adapter = new Floor_Audit_Screen_Adapter();
        floor_audit_screen_adapter.setOnItemClickListener(this);
        recycle.setAdapter(floor_audit_screen_adapter);
        add_to_list_btn = findViewById(R.id.addtolist_btn_serial_reg);
        submit_serial_reg = findViewById(R.id.submit_serial_reg);
        plant_display = findViewById(R.id.plant_to_be_displayed_serial_reg);
        storage_loc_display = findViewById(R.id.material_description_to_be_displayed_serial_reg);
        total_qty_scanned = findViewById(R.id.totalserialscan);

        currentDate = dateFormat.format(new Date());
        currentTime  = timeFormat.format(new Date());


        SharedPreferences sharedPreferences = getSharedPreferences("plant_and_storagelocation_preference", Context.MODE_PRIVATE);
        plant = sharedPreferences.getString("plant", "");
        st_loc = sharedPreferences.getString("storage_location", "");
        logged_in_user = sharedPreferences.getString("username", "");



        plant_display.setText("Plant: "+plant);
        storage_loc_display.setText("Storage Locataion: "+st_loc);
        total_qty_scanned.setText("Total Scanned Quantities: "+scanned_serial_number_counter);


        retrofit = OkHttpUtils.createRetrofit(
                this,  // context of your Android app
                "https://bar.shaker.com.sa/",  // base URL of API
                "bilal.pfx",  // filename of the certificate in the assets folder
                "123123123"  // password for the certificate
        );


        routes = retrofit.create(Routes.class);

        progressDialog = new ProgressDialog(FloorScanSerialnumber.this);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Adding Serial Number to the list.....");
        progressDialog.setCancelable(false);

    }


    private void add_items_to_list(String serial_number){
        scanned_serial_number_counter++;

        Call<String> call_api = routes.CheckSerialNumber(serial_number);
        call_api.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    if (response.body().equalsIgnoreCase("false")) {

                        Call<List<LoadSerialHistory>> load_history = routes.getSerialHistoryData(serial_number,"");
                        load_history.enqueue(new Callback<List<LoadSerialHistory>>() {


                            @Override
                            public void onResponse(Call<List<LoadSerialHistory>> call, Response<List<LoadSerialHistory>> response) {

                                if (response.isSuccessful()) {

                                    List<LoadSerialHistory> data = response.body();

                                    if (data.size() > 0) {
                                        progressDialog.dismiss();
                                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "This Serial Number Already Exists!", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    } else {
                                        progressDialog.dismiss();
                                        floor_audit_saving.add(new Floor_Audit_Model_Class(serial_number, model_number_scanned, material_number, st_loc, plant, logged_in_user,currentDate, currentTime ));
                                        floor_audit_screen_adapter.addItem(Integer.toString(scanned_serial_number_counter), material_number, material_descript, serial_number);
                                        serial_number_entered.setText("");
                                        serial_number_scanned.setText("");
                                        total_qty_scanned.setText("Total Scanned Quantities: "+scanned_serial_number_counter);                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<LoadSerialHistory>> call, Throwable t) {
                                progressDialog.dismiss();
                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "You have scanned Model Number! Please Scan Serial Number", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }

                        });
                    }
                    else {
                        progressDialog.dismiss();
                        serial_number_scanned.setText("");
                        serial_number_entered.setText("");
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "You have scanned Model Number! Please Scan Serial Number", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + t.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });







    }



    public void skipload(View view) {
        onBackPressed();
    }

    public void cancel(View view) {
        onBackPressed();
    }

    public void gotodashboard(View view) {
        startActivity(new Intent(getApplicationContext(), DashboardScreen.class));
        finishAffinity();
        finish();
    }



    @Override
    public void onItemClick(int position) {
        try {
            floor_audit_saving.remove(position);
            scanned_serial_number_counter--;
            total_qty_scanned.setText("Total Scanned Quantities: "+scanned_serial_number_counter);
        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }




    private void submit_floor_audit() {

        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Posting Data...");
        progressDialog.show();

        Gson gson = new Gson();
        String jsonPayload = gson.toJson(floor_audit_saving);

        Call<String> submit_serial = routes.FloorAuditPosting(jsonPayload);
        submit_serial.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    String api_response = response.body();
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(FloorScanSerialnumber.this);
                    View view1 = getLayoutInflater().inflate(R.layout.popupmessage, null);
                    alertDialog.setView(view1);
                    alertDialog.setCancelable(false);
                    AlertDialog dialog = alertDialog.create();

                    TextView tv = view1.findViewById(R.id.status);
                    TextView tv1 = view1.findViewById(R.id.grn_doc_num);
                    TextView tv2 = view1.findViewById(R.id.docnum);

                    tv1.setVisibility(View.INVISIBLE);
                    tv.setText(api_response);
                    tv2.setText("MESSAGE:");
                    dialog.show();

                    MaterialButton btn = view1.findViewById(R.id.gotodashboard);
                    btn.setOnClickListener(view -> {
                        dialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), DashboardScreen.class));
                        finish();
                    });

                }

                else{
                    progressDialog.dismiss();
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! Please Try Again Later", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(t.getMessage());
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! An Error Occurred! CAUSED BY: "+t.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });


    }


}