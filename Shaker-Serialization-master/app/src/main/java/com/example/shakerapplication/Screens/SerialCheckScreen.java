package com.example.shakerapplication.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shakerapplication.ModelClasses.LoadSerialHistory;
import com.example.shakerapplication.R;
import com.example.shakerapplication.SSLConfiguration.OkHttpUtils;
import com.example.shakerapplication.Services.Routes;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SerialCheckScreen extends AppCompatActivity {
    private CardView cardcontainer;
    EditText edittext1;
    //         , edittext2;
    ProgressDialog progressDialog;
    TextView brand, category, modelno, group, receivedate, sizedimen, customer, lastplant, laststloc, currentplant, currentstloc, modeldescription, last_mov;

    private String scan_serial_no;
    private String scan_model_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_check_screen);
        getSupportActionBar().hide();
        initialization();
    }

    private void initialization() {

      try {
          cardcontainer = findViewById(R.id.cardcontainer);
          cardcontainer.setVisibility(View.GONE);

          edittext1 = findViewById(R.id.edittext1);
//        edittext2 = findViewById(R.id.edittext2);
          edittext1.requestFocus();
          getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
          edittext1.addTextChangedListener(new TextWatcher() {
              @Override
              public void beforeTextChanged(CharSequence s, int start, int count, int after) {
              }

              @Override
              public void onTextChanged(CharSequence s, int start, int before, int count) {
                  // Check if the text has content
//                if (s.length() > 0) {
//                    // Request focus on the second EditText
//                    edittext2.requestFocus();
//                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//                }
              }

              @Override
              public void afterTextChanged(Editable s) {
              }
          });
      }catch (Exception e){
          Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
          snackbar.show();
      }
    }


    public void skipserialcheck(View view) {
        onBackPressed();
    }


    public void ClickCard(View view) {
        try {
            edittext1 = findViewById(R.id.edittext1);
            brand = findViewById(R.id.brand);
            category = findViewById(R.id.category);
            modelno = findViewById(R.id.modelno);
            group = findViewById(R.id.group);
            receivedate = findViewById(R.id.receivedate);
            sizedimen = findViewById(R.id.sizedimen);
            customer = findViewById(R.id.customer);
            lastplant = findViewById(R.id.lastplant);
            laststloc = findViewById(R.id.laststloc);
            currentplant = findViewById(R.id.currentplant);
            currentstloc = findViewById(R.id.currentstloc);
            modeldescription = findViewById(R.id.modeldescription);
            last_mov = findViewById(R.id.last_mov);
            scan_serial_no = edittext1.getText().toString();
            scan_model_no = "";


            if (!scan_serial_no.isEmpty()) {

               if(scan_serial_no.length() <=35) {
//                if (scan_serial_no.equals("10001") && scan_model_no.equals("LGABCD")){
                   System.out.println("This is Serial Number => " + scan_serial_no);
                   System.out.println("This is Model Number => " + scan_model_no);
                   progressDialog = new ProgressDialog(SerialCheckScreen.this);
                   progressDialog.setTitle("Wait");
                   progressDialog.setMessage("Data Fetching");
                   progressDialog.setCancelable(false);
                   progressDialog.show();

                   Retrofit retrofit = OkHttpUtils.createRetrofit(
                           this,  // context of your Android app
                           "https://bar.shaker.com.sa/",  // base URL of API
                           "bilal.pfx",  // filename of the certificate in the assets folder
                           "123123123"  // password for the certificate
                   );

                   Routes routes = retrofit.create(Routes.class);


                   Call<String> call_api = routes.CheckSerialNumber(scan_serial_no);
                   call_api.enqueue(new Callback<String>() {
                       @Override
                       public void onResponse(Call<String> call, Response<String> response) {

                           if (response.isSuccessful()) {
                               if (response.body().equals("False")) {

                                   Call<List<LoadSerialHistory>> load_history = routes.getSerialHistoryData(scan_serial_no, scan_model_no);
                                   load_history.enqueue(new Callback<List<LoadSerialHistory>>() {

                                       @Override
                                       public void onResponse(Call<List<LoadSerialHistory>> call, Response<List<LoadSerialHistory>> response) {
                                           progressDialog.dismiss();
                                           List<LoadSerialHistory> data = response.body();
//                                        System.out.println("This is Data => " + data.size());

                                           if (data.size() != 0) {
                                               LoadSerialHistory object;
                                               for (int i = 0; i < data.size(); i++) {
                                                   object = data.get(i);
                                                   System.out.println(object.getModel());
                                                   brand.setText(object.getBrand());
                                                   category.setText(object.getCategory());
                                                   modelno.setText(object.getModel());
                                                   group.setText(object.getGroup());
                                                   receivedate.setText(object.getReceiveDate());
                                                   sizedimen.setText(object.getSizeDimension());
                                                   customer.setText(object.getCustomer());
                                                   lastplant.setText(object.getLastPlant());
                                                   laststloc.setText(object.getLastStockLocation());
                                                   currentplant.setText(object.getCurrentPlant());
                                                   currentstloc.setText(object.getCurrentStockLocation());
                                                   modeldescription.setText(object.getModelDescription());
                                                   last_mov.setText(object.getLast_mov());
                                               }
                                               cardcontainer.setVisibility(View.VISIBLE);
                                           } else {
                                               progressDialog.dismiss();
                                               Snackbar snackbar = Snackbar.make(view, "Please Scan Valid Serial Number", Snackbar.LENGTH_LONG);
                                               snackbar.show();
                                               edittext1.setText("");
                                           }
                                       }

                                       @Override
                                       public void onFailure(Call<List<LoadSerialHistory>> call, Throwable t) {
                                           progressDialog.dismiss();
                                           Snackbar snackbar = Snackbar.make(view, "An Error Occurred!", Snackbar.LENGTH_LONG);
                                           snackbar.show();
                                       }
                                   });

                               } else {
                                   progressDialog.dismiss();
                                   cardcontainer.setVisibility(View.GONE);
                                   Snackbar snackbar = Snackbar.make(view, "You have scanned Model Number! Please Scan Serial Number", Snackbar.LENGTH_LONG);
                                   snackbar.show();
                               }

                           } else {
                               progressDialog.dismiss();
                               Snackbar snackbar = Snackbar.make(view, "Request Failed! Try Again Later", Snackbar.LENGTH_LONG);
                               snackbar.show();
                           }
                       }

                       @Override
                       public void onFailure(Call<String> call, Throwable t) {
                           progressDialog.dismiss();
                           Snackbar snackbar = Snackbar.make(view, "An Error Occurred: " + t.getMessage(), Snackbar.LENGTH_LONG);
                           snackbar.show();
                       }
                   });
               }
                else{
                    progressDialog.dismiss();
                    Snackbar snackbar = Snackbar.make(view, "Serial Number cannot be greater than 35 digits!" , Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
            else{
                Snackbar snackbar = Snackbar.make(view, "Please Scan Serial Number!" , Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        } catch (Exception e) {
            progressDialog.dismiss();
            Snackbar snackbar = Snackbar.make(view, "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
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