package com.example.shakerapplication.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.shakerapplication.Adapters.Last_Good_Receipt_Adapter;
import com.example.shakerapplication.ModelClasses.GRN_Response_POJO_Class;
import com.example.shakerapplication.ModelClasses.Post_GRN_Model_Class;
import com.example.shakerapplication.ModelClasses.Transfer_GRN_Model_Class;
import com.example.shakerapplication.R;
import com.example.shakerapplication.SSLConfiguration.OkHttpUtils;
import com.example.shakerapplication.Services.Routes;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GoodsReceiptScreen extends AppCompatActivity {
RecyclerView grrecycle;
    ProgressDialog progressDialog;
    private Retrofit retrofit;
    private Routes routes;
    private AlertDialog dialog;

    private  int size_of_data_passed=0;
    private  Transfer_GRN_Model_Class[] receivedArray;

    private  String isUserComingFromTransferPosting="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_receipt_screen);
        try {
            getSupportActionBar().hide();
            initialization();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            grrecycle.setLayoutManager(linearLayoutManager);
            Last_Good_Receipt_Adapter last_good_receipt_adapter = new Last_Good_Receipt_Adapter();
            grrecycle.setAdapter(last_good_receipt_adapter);

            Intent intent = getIntent();
            isUserComingFromTransferPosting = intent.getStringExtra("isUserComingFromTransferPosting");


            receivedArray = (Transfer_GRN_Model_Class[]) getIntent().getSerializableExtra("transfer_grn_model_classes");

            size_of_data_passed = receivedArray.length;

            for (int i = 0; i < size_of_data_passed; i++) {
                last_good_receipt_adapter.addItem("" + (i + 1), receivedArray[i].getMATNR(), receivedArray[i].getMAKTX(), receivedArray[i].getSERIALNO());
            }
        }catch (Exception e){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }


    private void initialization() {
        grrecycle = findViewById(R.id.grrecycle);
    }

    public void skipserialcheck(View view) {
        onBackPressed();
    }




public void submitbarcode(View view) {

        try{

        progressDialog = new ProgressDialog(GoodsReceiptScreen.this);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Data Posting");
        progressDialog.setCancelable(false);
        progressDialog.show();
        //Configuring SSL and Making Retrofit Instance
        retrofit = OkHttpUtils.createRetrofit(
                this,  // context of your Android app
                "https://bar.shaker.com.sa/",  // base URL of API
                "bilal.pfx",  // filename of the certificate in the assets folder
                "123123123"  // password for the certificate
        );

        routes = retrofit.create(Routes.class);


        List<Post_GRN_Model_Class> payloadList = new ArrayList<>();

        for (int i = 0; i < size_of_data_passed; i++) {
            Post_GRN_Model_Class post_grn_model_class = new Post_GRN_Model_Class(receivedArray[i].getEBELN(), receivedArray[i].getEBELP(), receivedArray[i].getMATNR(), receivedArray[i].getWERKS(), receivedArray[i].getLGORT(), receivedArray[i].getMENGE(), receivedArray[i].getAEDAT(), receivedArray[i].getRESWK(), receivedArray[i].getMAKTX(), receivedArray[i].getSERIALNO());
            payloadList.add(post_grn_model_class);
        }


        Call<List<GRN_Response_POJO_Class>> call = routes.postGRNPayload(payloadList);
        call.enqueue(new Callback<List<GRN_Response_POJO_Class>>() {
            @Override
            public void onResponse(Call<List<GRN_Response_POJO_Class>> call, Response<List<GRN_Response_POJO_Class>> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    // Request successful
                    List<GRN_Response_POJO_Class> grnResponses = response.body();
//                    System.out.println("The MBLNR is: " + grnResponses.get(0).getMBLNR());


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(GoodsReceiptScreen.this);
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


        }catch(Exception e){
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

        }



public void cancel(View view) {onBackPressed();}


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