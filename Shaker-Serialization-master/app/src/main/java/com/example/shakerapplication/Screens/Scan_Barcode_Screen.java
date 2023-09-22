package com.example.shakerapplication.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.shakerapplication.ModelClasses.LoadSerialHistory;
import com.example.shakerapplication.R;
import com.example.shakerapplication.SSLConfiguration.OkHttpUtils;
import com.example.shakerapplication.Services.Routes;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Scan_Barcode_Screen extends AppCompatActivity {


    private EditText barcode_scanning_by_scanner, barcode_manual_entery;

    private MaterialButton proceed_btn, cancel_btn;

    private Routes routes;
    private Retrofit retrofit;
    private ProgressDialog progressDialog;
    private String material_number, material_descript;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_barcode_screen);
        getSupportActionBar().hide();


        initialization();

        proceed_btn.setOnClickListener(view -> {
            if(!barcode_manual_entery.getText().toString().equals("")){

                call_serial_history(barcode_manual_entery.getText().toString());
                progressDialog.show();


            }
            else{
                Snackbar snackbar = Snackbar.make(view, "Please Enter Barcode!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });


        barcode_scanning_by_scanner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(barcode_scanning_by_scanner.getText().toString().length() != 0) {
                     call_serial_history(barcode_scanning_by_scanner.getText().toString());
                     progressDialog.show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        cancel_btn.setOnClickListener(view -> onBackPressed());


    }



    private void initialization(){
        barcode_scanning_by_scanner = findViewById(R.id.barcode_scanned_by_scanner_serial_reg);
        barcode_manual_entery = findViewById(R.id.manuallly_entered_barcode_serial_reg);
        proceed_btn = findViewById(R.id.proceed_serial_reg);
        cancel_btn = findViewById(R.id.cancel_barcode_scanning);
        retrofit = OkHttpUtils.createRetrofit(this,  // context of your Android app
                "https://bar.shaker.com.sa/",  // base URL of API
                "bilal.pfx",  // filename of the certificate in the assets folder
                "123123123"  // password for the certificate
        );


        routes = retrofit.create(Routes.class);
        progressDialog = new ProgressDialog(Scan_Barcode_Screen.this);

        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Fetching Barcode...");
        progressDialog.setCancelable(false);
    }




    private void call_serial_history(String model_number_scanned) {

        Call<List<LoadSerialHistory>> load_history = routes.getSerialHistoryData("", model_number_scanned);
        load_history.enqueue(new Callback<List<LoadSerialHistory>>() {
            @Override
            public void onResponse(Call<List<LoadSerialHistory>> call, Response<List<LoadSerialHistory>> response) {

                if(response.isSuccessful()){

                    List<LoadSerialHistory> data = response.body();

                    if(data.size() > 0){
                        progressDialog.dismiss();
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Material Number Fetched! You Can Scan Serial Numbers Now!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        material_number = data.get(0).getModel();
                        material_descript = data.get(0).getModelDescription();

                        Intent intent = new Intent(getApplicationContext(), LoadInventory.class);
                        intent.putExtra("barcode", model_number_scanned);
                        intent.putExtra("material_number", material_number);
                        intent.putExtra("material_descript", material_descript);
                        startActivity(intent);
                    }
                    else{
                        progressDialog.dismiss();
                        barcode_scanning_by_scanner.setText("");
                        barcode_manual_entery.setText("");
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please Scan Valid Barcode!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
                else{
                    progressDialog.dismiss();
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! Please Try Again Later", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }

            @Override
            public void onFailure(Call<List<LoadSerialHistory>> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+t.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });


    }


    public void skipbarcode(View view){
        onBackPressed();
    }

    public void gotodashboard(View view) {
        startActivity(new Intent(getApplicationContext(), DashboardScreen.class));
        finishAffinity();
        finish();
    }

}