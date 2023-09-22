package com.example.shakerapplication.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shakerapplication.Adapters.Delivery_Screen_Adapter;
import com.example.shakerapplication.Adapters.Goods_Issue_Screen_Adapter;
import com.example.shakerapplication.ModelClasses.GI_Response_POJO_Class;
import com.example.shakerapplication.ModelClasses.GRN_Response_POJO_Class;
import com.example.shakerapplication.ModelClasses.Load_Outbound_Delivery;
import com.example.shakerapplication.ModelClasses.PgiItem;
import com.example.shakerapplication.ModelClasses.Post_Good_Issue_Model_Class;
import com.example.shakerapplication.ModelClasses.Transfer_GI_Model_Class;
import com.example.shakerapplication.ModelClasses.Transfer_GRN_Model_Class;
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

public class DeliveryScreen extends AppCompatActivity {
    RecyclerView recycle;
private String delivery_number;
    ProgressDialog progressDialog;
    private TextView dnumber;
    private Retrofit retrofit;

    private  int size_of_data_passed=0;
    private  Transfer_GI_Model_Class[] receivedArray;

    private Routes routes;
    private Delivery_Screen_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_screen);
     try {
         getSupportActionBar().hide();
         //Configuring SSL and Making Retrofit Instance
         retrofit = OkHttpUtils.createRetrofit(
                 this,  // context of your Android app
                 "https://bar.shaker.com.sa/",  // base URL of API
                 "bilal.pfx",  // filename of the certificate in the assets folder
                 "123123123"  // password for the certificate
         );

         routes = retrofit.create(Routes.class);
         initialization();

         adapter = new Delivery_Screen_Adapter(getApplicationContext());
         recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
         recycle.setAdapter(adapter);
         receivedArray = (Transfer_GI_Model_Class[]) getIntent().getSerializableExtra("transfer_gi_model_classes");

         size_of_data_passed = receivedArray.length;
         System.out.println("The vbeln passed is: " + receivedArray[0].getVbeln());

         delivery_number = receivedArray[0].getVbeln();
         dnumber.setText("Delivery No: " + delivery_number);


         for (int i = 0; i < receivedArray.length; i++) {
             adapter.addItem(receivedArray[i].getSeq_num(), receivedArray[i].getMaterial(), receivedArray[i].getMaterialdescript(), receivedArray[i].getSernr());
         }

     }catch (Exception e){
         Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred!", Snackbar.LENGTH_LONG);
         snackbar.show();
     }

    }

    private void initialization() {
        recycle = findViewById(R.id.recycle);
        dnumber = findViewById(R.id.dn);
    }

    //      AlertBox show Method
    public void submitdata(View view) {

        try {
            progressDialog = new ProgressDialog(DeliveryScreen.this);
            progressDialog.setTitle("Wait");
            progressDialog.setMessage("Data Posting");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String I_VBELN = delivery_number;
            List<PgiItem> pgiItemList = new ArrayList<>();

            for(int i =0; i< size_of_data_passed; i++){
               pgiItemList.add(new PgiItem(receivedArray[i].getPosnr(), receivedArray[i].getSernr(), receivedArray[i].getUII()));
            }

            Post_Good_Issue_Model_Class post_good_issue_model_class = new Post_Good_Issue_Model_Class(I_VBELN, pgiItemList);

            Call<List<GI_Response_POJO_Class>> call = routes.postGIPayload(post_good_issue_model_class);

            call.enqueue(new Callback<List<GI_Response_POJO_Class>>() {
                @Override
                public void onResponse(Call<List<GI_Response_POJO_Class>> call, Response<List<GI_Response_POJO_Class>>  response) {
                    if (response.isSuccessful()){
                      try {
                          progressDialog.dismiss();

                          List<GI_Response_POJO_Class> giResponses = response.body();

                          AlertDialog.Builder alertDialog = new AlertDialog.Builder(DeliveryScreen.this);
                          View view1 = getLayoutInflater().inflate(R.layout.popupmessage, null);
                          alertDialog.setView(view1);
                          alertDialog.setCancelable(false);
                          AlertDialog dialog = alertDialog.create();

                          TextView tv = view1.findViewById(R.id.status);
                          TextView tv1 = view1.findViewById(R.id.grn_doc_num);

//                          try {
//                              System.out.println(giResponses.size());
//                              System.out.println(I_VBELN);
//                              System.out.println(pgiItemList.get(0).getPosnr());
//                              System.out.println(pgiItemList.get(0).getSernr());
//                              System.out.println(pgiItemList.get(1).getPosnr());
//                              System.out.println(pgiItemList.get(1).getSernr());
//
//                          } catch (Exception e) {
//                              System.out.println("The response returned is null");
//                          }

                         try {
                             if(!(giResponses.get(0).getMSGV1().equals(""))) {
                                 tv1.setText(giResponses.get(0).getMSGV1());
                             }
                             else{
                                 tv1.setText(giResponses.get(0).getARKTX());
                             }
                         }catch(Exception e){
                             System.out.println(e.getMessage());
                         }

                          tv.setText(giResponses.get(0).getREMARKS());

                          dialog.show();

                          MaterialButton btn = view1.findViewById(R.id.gotodashboard);
                          btn.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {

                                  if(giResponses.get(0).getARKTX().equals("")){
                                      onBackPressed();
                                      dialog.dismiss();
                                  }
                                  else{
                                      dialog.dismiss();
                                      startActivity(new Intent(getApplicationContext(), DashboardScreen.class));
                                      finish();
                                  }
                              }
                          });
                      }catch (Exception e){
                          Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: "+ e.getMessage(), Snackbar.LENGTH_LONG);
                          snackbar.show();
                      }
                    }
                    else{
                        if(progressDialog != null){
                        progressDialog.dismiss();
                        }
                        Snackbar snackbar = Snackbar.make(view, "An Error Occurred!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }

                @Override
                public void onFailure(Call<List<GI_Response_POJO_Class>>  call, Throwable t) {
                    if(progressDialog != null){
                        progressDialog.dismiss();
                    }
                    Toast.makeText(DeliveryScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println("Error:  "+t.getMessage());
                }
            });

        }
        catch (Exception e){
            Snackbar snackbar = Snackbar.make(view, "An Error Occurred! CAUSED BY: "+e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    //    Back Button Method
    public void skipdelivery(View view) {
        onBackPressed();
    }

    public void gotodashboard(View view) {
        startActivity(new Intent(getApplicationContext(),DashboardScreen.class));
        finishAffinity();
        finish();
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