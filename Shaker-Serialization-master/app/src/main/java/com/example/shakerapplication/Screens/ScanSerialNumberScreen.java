package com.example.shakerapplication.Screens;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shakerapplication.Adapters.Scan_Serial_Number_Adapter;
import com.example.shakerapplication.ModelClasses.LoadSerialHistory;
import com.example.shakerapplication.ModelClasses.Load_Already_Scanned_SERNR_GRN_TP;
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

public class ScanSerialNumberScreen extends AppCompatActivity implements Scan_Serial_Number_Adapter.OnItemClickListener {
    RecyclerView recycle;
    TextView totalreceiptscan, material_num_to_be_displayed, material_descript_to_be_displayed;
    MaterialButton proceed, finishGRN;
    EditText edittext, edittext2;
    CardView AddToList;
    private String
            stono,
            stoitem,
            material,
            materialdescription,
            qty,
            aedat,
            bwart,
            ean11,
            grund,
            lfimg,
            lgort,
            meins,
            netwr,
            netpr,
            posnr,
            reswk,
            serialno,
            sernr,
            umlgo,
            umwrk,
            vbeln,
            werks,
            gi_qty,
            sto_typ,
            isUserComingFromTransferPosting, plant_to_compare_with = "";
    private int isSerialNumberAlreadyScannedatBackend = 0;

    private Transfer_GRN_Model_Class[] transfer_grn_model_classes;

    private ArrayList<Transfer_GRN_Model_Class> transfer_grn_array_list = new ArrayList<>();

    //    private ArrayList<Load_Already_Scanned_SERNR_GRN_TP> already_scanned_sernr_grn_tps = new ArrayList<>();

    private int count_of_scanned_items = 0, quantity = 0, gi_quantity = 0;
    private int isSerialNumberAlreadyScanned = 0;
    private Retrofit retrofit;
    private Routes routes;
    private ArrayList<String> serial_numbers_received = new ArrayList<>();
    private ArrayList<String> serial_numbers_list_for_trf_to_eliminate_global_duplicate = new ArrayList<>();

    private String GM_Type = "";
    ProgressDialog progressDialog;

    Scan_Serial_Number_Adapter scan_serial_number_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_serial_number_screen);
        try {
            getSupportActionBar().hide();

            progressDialog = new ProgressDialog(ScanSerialNumberScreen.this);

            progressDialog.setTitle("Wait");
            progressDialog.setMessage("Loading Already Scanned Serial Numbers.....");
            progressDialog.setCancelable(false);

            initialization();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            recycle.setLayoutManager(linearLayoutManager);
            scan_serial_number_adapter = new Scan_Serial_Number_Adapter(getApplicationContext());
            scan_serial_number_adapter.setOnItemClickListener(this);
            recycle.setAdapter(scan_serial_number_adapter);
            Intent i = getIntent();
            stono = i.getStringExtra("stono");
            System.out.println("BO The STO is: "+stono);
            stoitem = i.getStringExtra("stoitem");
            material = i.getStringExtra("material");
            materialdescription = i.getStringExtra("materialdescription");
            qty = i.getStringExtra("qty");
            aedat = i.getStringExtra("aedat");
            bwart = i.getStringExtra("bwart");
            ean11 = i.getStringExtra("ean11");
            grund = i.getStringExtra("grund");
            lfimg = i.getStringExtra("lfimg");
            lgort = i.getStringExtra("lgort");
            meins = i.getStringExtra("meins");
            netwr = i.getStringExtra("netwr");
            netpr = i.getStringExtra("netpr");
            posnr = i.getStringExtra("posnr");
            reswk = i.getStringExtra("reswk");
            serialno = i.getStringExtra("serialno");
            sernr = i.getStringExtra("sernr");
            umlgo = i.getStringExtra("umlgo");
            umwrk = i.getStringExtra("umwrk");
            vbeln = i.getStringExtra("vbeln");
            werks = i.getStringExtra("werks");
            gi_qty = i.getStringExtra("gi_qty");
            sto_typ = i.getStringExtra("sto_typ");

            System.out.println("The vbeln recieved is: "+vbeln);


            isUserComingFromTransferPosting = i.getStringExtra("isUserComingFromTransferPosting");


            if (!(sto_typ.equals("GRN"))) {
                progressDialog.show();
            }


            if (isUserComingFromTransferPosting.equals("true")) {
                GM_Type = "TRF";
            } else if (isUserComingFromTransferPosting.equals("false")) {
                GM_Type = "GRN";
            }

            quantity = Integer.parseInt(qty.substring(0, qty.indexOf(".")));
            gi_quantity = Integer.parseInt(gi_qty.substring(0, gi_qty.indexOf(".")));
            AddToList = findViewById(R.id.addtolistbtn);
            proceed = findViewById(R.id.proceed);
            finishGRN = findViewById(R.id.finishGRN);

            try {
                transfer_grn_model_classes = (Transfer_GRN_Model_Class[]) getIntent().getSerializableExtra("transfer_grn_model_classes");

                for (int k = 0; k < transfer_grn_model_classes.length; k++) {
                    transfer_grn_array_list.add(transfer_grn_model_classes[k]);

                    int counts = 0;

                    System.out.println("transfer_grn_model_classes MATNR: => " + transfer_grn_model_classes[k].getMATNR());
                    System.out.println("Put Extra Material: => " + material);

                    if (transfer_grn_model_classes[k].getMATNR().equals(material)) {
                        ++count_of_scanned_items;
                        counts = count_of_scanned_items;
                    } else {
                        counts = k + 1;
                    }
                    scan_serial_number_adapter.addItem(Integer.toString(counts), transfer_grn_array_list.get(k).getMATNR(), transfer_grn_array_list.get(k).getMAKTX(), transfer_grn_array_list.get(k).getSERIALNO());
                }


                if (transfer_grn_array_list.size() > 0) {
                    finishGRN.setEnabled(true);
                    finishGRN.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00BCD5")));
                }
            } catch (Exception e) {
                System.out.println("No Bundle Received to Scan Serial Number Screen! => " + e.getMessage());
            }


            try {
                totalreceiptscan.setText("Total Scanned Quantities: " + count_of_scanned_items + "/" + quantity);

                String mat_num_text = "Material Number: " + material;
                int splitIndex = mat_num_text.indexOf(":") + 2; // get the index of the colon and add 2 to skip the colon and the following space

                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(mat_num_text);

                spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), 0, splitIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                spannableStringBuilder.setSpan(new StyleSpan(Typeface.NORMAL), splitIndex, mat_num_text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                material_num_to_be_displayed.setText(spannableStringBuilder);

                String mat_desc_text = "Material Descript: " + materialdescription;

                spannableStringBuilder = new SpannableStringBuilder(mat_desc_text);
                spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), 0, splitIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableStringBuilder.setSpan(new StyleSpan(Typeface.NORMAL), splitIndex, mat_num_text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                material_descript_to_be_displayed.setText(spannableStringBuilder);



//                String delivery_number_text = "Delivery Number: " + vbeln;
//
//                spannableStringBuilder = new SpannableStringBuilder(delivery_number_text);
//                spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), 0, splitIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                spannableStringBuilder.setSpan(new StyleSpan(Typeface.NORMAL), splitIndex, delivery_number_text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                delivery_number_to_be_displayed.setText(spannableStringBuilder);





                AddToList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        progressDialog.setTitle("Wait");
                        progressDialog.setMessage("Adding Item in the List");
                        progressDialog.setCancelable(false);

                        //FIRST USAGE (SET IF CONDITION)
                        if (isUserComingFromTransferPosting.equals("true") || sto_typ.equals("TRF")) {
                            //                        GM_Type = "TRF";
                            System.out.println("The material# is " + material);
                            addScannedItemsToList(view, edittext2.getText().toString());
                        } else if (isUserComingFromTransferPosting.equals("false") || sto_typ.equals("GRN")) {
                            //                        GM_Type = "GRN";
                            addSCannedItemsToListForNormalGRN(view, edittext2.getText().toString());
                        }
                    }
                });


                proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        send_data_to_previous_activity();
                    }
                });


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //        isUserComingFromTransferPosting = i.getStringExtra("isUserComingFromTransferPosting");


            if (!(sto_typ.equals("GRN"))) {
                Call<List<Load_Already_Scanned_SERNR_GRN_TP>> load_already_scanned_serial_numbers = routes.CheckSerialNumberforTPandGRN(stono, stoitem);
                load_already_scanned_serial_numbers.enqueue(new Callback<List<Load_Already_Scanned_SERNR_GRN_TP>>() {
                    @Override
                    public void onResponse(Call<List<Load_Already_Scanned_SERNR_GRN_TP>> call, Response<List<Load_Already_Scanned_SERNR_GRN_TP>> response) {

                        if (response.isSuccessful() && response != null) {
                            progressDialog.dismiss();
                            List<Load_Already_Scanned_SERNR_GRN_TP> data = response.body();
                            for (int i = 0; i < data.size(); i++) {
                                //                            System.out.println("GRN type is: "+GM_Type);
                                if (data.get(i).getGmType().equals(GM_Type)) {
                                    serial_numbers_received.add(data.get(i).getSernr());
                                }

                                if (isUserComingFromTransferPosting.equals("false")) {
                                    if (data.get(i).getGmType().equals("TRF")) {
                                        serial_numbers_list_for_trf_to_eliminate_global_duplicate.add(data.get(i).getSernr());
                                    }
                                }

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Load_Already_Scanned_SERNR_GRN_TP>> call, Throwable t) {
                        progressDialog.dismiss();
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + t.getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
            }

        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }


    }


    private void send_data_to_previous_activity() {

        try {
            Intent intent = new Intent(ScanSerialNumberScreen.this, ScanProductScreen.class);
            transfer_grn_model_classes = new Transfer_GRN_Model_Class[transfer_grn_array_list.size()];
            for (int i = 0; i < transfer_grn_array_list.size(); i++) {
                transfer_grn_model_classes[i] = transfer_grn_array_list.get(i);
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("transfer_grn_model_classes", transfer_grn_model_classes);
            intent.putExtras(bundle);
            intent.putExtra("stono", stono);
            intent.putExtra("isUserComingFromTransferPosting", isUserComingFromTransferPosting);
            startActivity(intent);

        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }


    private void addScannedItemsToList(View view, String snum) {

        try {

            if (!snum.equals("")) {

                int global_duplicacy_removal_checker = 0;


                if (isUserComingFromTransferPosting.equals("false") && serial_numbers_list_for_trf_to_eliminate_global_duplicate.size() > 0) {

                    for (int i = 0; i < serial_numbers_list_for_trf_to_eliminate_global_duplicate.size(); i++) {
                        if (serial_numbers_list_for_trf_to_eliminate_global_duplicate.get(i).equals(snum)) {
                            global_duplicacy_removal_checker = 1;
                        }
                    }
                }

                if (isUserComingFromTransferPosting.equals("false") && global_duplicacy_removal_checker == 0 && serial_numbers_list_for_trf_to_eliminate_global_duplicate.size() > 0) {
                    global_duplicacy_removal_checker = -1;
                }


                if (global_duplicacy_removal_checker == 1 || global_duplicacy_removal_checker == 0) {

                    if (snum.length() <= 35) {

                        progressDialog.show();

                        Call<List<LoadSerialHistory>> load_history = routes.getSerialHistoryData(snum, "");
                        load_history.enqueue(new Callback<List<LoadSerialHistory>>() {
                            @Override
                            public void onResponse(Call<List<LoadSerialHistory>> call, Response<List<LoadSerialHistory>> response) {

                                if (response.isSuccessful()) {
                                    List<LoadSerialHistory> data = response.body();

                                    if (data.size() != 0) {


                                        if ((!(data.get(0).getLast_mov().equals("601")))) {
                                            if ((!(data.get(0).getLast_mov().equals("653")))) {


                                                if (isUserComingFromTransferPosting.equals("false")) {
                                                    plant_to_compare_with = werks;
                                                } else if (isUserComingFromTransferPosting.equals("true")) {
                                                    plant_to_compare_with = reswk;
                                                }

                                                if (data.get(0).getModel().equals(material) && data.get(0).getCurrentPlant().equals(plant_to_compare_with)) {
                                                    progressDialog.dismiss();

                                                    for (int i = 0; i < serial_numbers_received.size(); i++) {
                                                        //                                        System.out.println("Size of Received Serial Numbers: " + serial_numbers_received.get(i));
                                                        if (serial_numbers_received.get(i).equals(snum)) {
                                                            ++isSerialNumberAlreadyScannedatBackend;
                                                        }
                                                    }
                                                    if (isSerialNumberAlreadyScannedatBackend == 0) {
                                                        Call<String> call_api = routes.CheckSerialNumber(snum);
                                                        call_api.enqueue(new Callback<String>() {
                                                            @Override
                                                            public void onResponse(Call<String> call, Response<String> response) {

                                                                if (response.isSuccessful()) {
                                                                    if (response.body().equals("False")) {

                                                                        System.out.println("The data after checking serial number is: " + response.body());
                                                                        Call<List<LoadSerialHistory>> load_history = routes.getSerialHistoryData(snum, "");
                                                                        load_history.enqueue(new Callback<List<LoadSerialHistory>>() {
                                                                            @Override
                                                                            public void onResponse(Call<List<LoadSerialHistory>> call, Response<List<LoadSerialHistory>> response) {

                                                                                try {
                                                                                    if (response.isSuccessful()) {


                                                                                        List<LoadSerialHistory> data = response.body();

                                                                                        if (data.size() != 0) {
                                                                                            SharedPreferences sharedPreferences = getSharedPreferences("plant_and_storagelocation_preference", Context.MODE_PRIVATE);
                                                                                            String plant = sharedPreferences.getString("plant", "");
                                                                                            String storageloc = sharedPreferences.getString("storage_location", "");

                                                                                            //              && data.get(0).getCurrentStockLocation().equals(storageloc)
                                                                                            //
                                                                                            //              System.out.println("The plant from data is: "+data.get(0).getCurrentPlant());
                                                                                            //              System.out.println("The plant is: "+plant);
                                                                                            //
                                                                                            //              System.out.println("The material is: "+material);
                                                                                            //              System.out.println("The model is: "+data.get(0).getModel());

                                                                                            if (data.get(0).getModel().equals(material) && data.get(0).getCurrentPlant().equals(plant_to_compare_with)) {

                                                                                                if (transfer_grn_array_list.size() != 0) {
                                                                                                    for (int i = 0; i < transfer_grn_array_list.size(); i++) {
                                                                                                        //System.out.println("The entered serial number is: "+edittext.getText().toString());
                                                                                                        if (transfer_grn_array_list.get(i).getSERIALNO().equals(snum)) {
                                                                                                            System.out.println("I am inside if condition");
                                                                                                            isSerialNumberAlreadyScanned = 1;
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                                if (!snum.equals("")) {

                                                                                                    if (isSerialNumberAlreadyScanned != 1) {

                                                                                                        progressDialog.dismiss();

                                                                                                        ++count_of_scanned_items;

                                                                                                        if (count_of_scanned_items <= quantity) {

                                                                                                            totalreceiptscan.setText("Total Scanned Quantities: " + count_of_scanned_items + "/" + quantity);
                                                                                                            //                                                                                                        lgort
                                                                                                            transfer_grn_array_list.add(new Transfer_GRN_Model_Class(stono, stoitem, material, werks, lgort, qty, aedat, reswk, materialdescription, snum));
                                                                                                            scan_serial_number_adapter.addItem(Integer.toString(count_of_scanned_items), material, materialdescription, snum);

                                                                                                            if (count_of_scanned_items > 0) {
                                                                                                                finishGRN.setEnabled(true);
                                                                                                                finishGRN.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00BCD5")));
                                                                                                            }
                                                                                                            edittext.setText("");
                                                                                                            edittext2.setText("");
                                                                                                        } else if (count_of_scanned_items > quantity) {
                                                                                                            progressDialog.dismiss();

                                                                                                            edittext.setText("");
                                                                                                            edittext2.setText("");
                                                                                                            count_of_scanned_items = quantity;
                                                                                                            Snackbar snackbar = Snackbar.make(view, "You have scanned all the quantity", Snackbar.LENGTH_LONG);
                                                                                                            snackbar.show();
                                                                                                        }

                                                                                                    } else {
                                                                                                        progressDialog.dismiss();

                                                                                                        edittext.setText("");
                                                                                                        edittext2.setText("");
                                                                                                        Snackbar snackbar = Snackbar.make(view, "This Serial Number has already been scanned!", Snackbar.LENGTH_LONG);
                                                                                                        snackbar.show();
                                                                                                        isSerialNumberAlreadyScanned = 0;
                                                                                                        isSerialNumberAlreadyScanned = 0;
                                                                                                    }


                                                                                                } else {
                                                                                                    progressDialog.dismiss();

                                                                                                    edittext.setText("");
                                                                                                    edittext2.setText("");

                                                                                                    Snackbar snackbar = Snackbar.make(view, "Please enter serial number", Snackbar.LENGTH_LONG);
                                                                                                    snackbar.show();
                                                                                                }

                                                                                            } else {
                                                                                                progressDialog.dismiss();

                                                                                                edittext.setText("");
                                                                                                edittext2.setText("");
                                                                                                Snackbar snackbar = Snackbar.make(view, "Either the Model Number/Plant/Storage Location does not match!", Snackbar.LENGTH_LONG);
                                                                                                snackbar.show();
                                                                                            }
                                                                                        } else {
                                                                                            progressDialog.dismiss();

                                                                                            edittext.setText("");
                                                                                            edittext2.setText("");
                                                                                            Snackbar snackbar = Snackbar.make(view, "Please Scan Valid Serial Number!", Snackbar.LENGTH_LONG);
                                                                                            snackbar.show();
                                                                                        }


                                                                                    } else {
                                                                                        progressDialog.dismiss();

                                                                                        edittext.setText("");
                                                                                        edittext2.setText("");
                                                                                        Snackbar snackbar = Snackbar.make(view, "You have scanned Model Number. Please scan Serial Number!", Snackbar.LENGTH_LONG);
                                                                                        snackbar.show();
                                                                                    }
                                                                                } catch (
                                                                                        Exception e) {
                                                                                    progressDialog.dismiss();

                                                                                    edittext.setText("");
                                                                                    edittext2.setText("");
                                                                                    Snackbar snackbar = Snackbar.make(view, "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
                                                                                    snackbar.show();
                                                                                }


                                                                            }

                                                                            @Override
                                                                            public void onFailure(Call<List<LoadSerialHistory>> call, Throwable t) {
                                                                                progressDialog.dismiss();

                                                                                edittext.setText("");
                                                                                edittext2.setText("");
                                                                                Snackbar snackbar = Snackbar.make(view, "An Error Occurred! CAUSED BY: " + t.getMessage(), Snackbar.LENGTH_LONG);
                                                                                snackbar.show();
                                                                            }
                                                                        });

                                                                    } else {
                                                                        progressDialog.dismiss();

                                                                        edittext.setText("");
                                                                        edittext2.setText("");

                                                                        Snackbar snackbar = Snackbar.make(view, "You have scanned Model Number. Please scan Serial Number!", Snackbar.LENGTH_LONG);
                                                                        snackbar.show();
                                                                    }
                                                                }

                                                            }


                                                            @Override
                                                            public void onFailure(Call<String> call, Throwable t) {
                                                                progressDialog.dismiss();

                                                                edittext.setText("");
                                                                edittext2.setText("");

                                                                Snackbar snackbar = Snackbar.make(view, "An Error Occurred. Try Again Later!", Snackbar.LENGTH_LONG);
                                                                snackbar.show();
                                                            }
                                                        });
                                                    } else {
                                                        isSerialNumberAlreadyScannedatBackend = 0;
                                                        isSerialNumberAlreadyScanned = 0;
                                                        progressDialog.dismiss();

                                                        edittext.setText("");
                                                        edittext2.setText("");
                                                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "This Serial Number Has Already Been Scanned!", Snackbar.LENGTH_LONG);
                                                        snackbar.show();
                                                    }

                                                } else {
                                                    progressDialog.dismiss();

                                                    edittext.setText("");
                                                    edittext2.setText("");
                                                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "The Model Number or the Current Plant does not match!", Snackbar.LENGTH_LONG);
                                                    snackbar.show();
                                                }
                                            } else {
                                                progressDialog.dismiss();

                                                edittext.setText("");
                                                edittext2.setText("");
                                                Snackbar snackbar = Snackbar.make(view, "PGI Already Done For This Serial Number!", Snackbar.LENGTH_LONG);
                                                snackbar.show();
                                            }
                                        } else {
                                            progressDialog.dismiss();

                                            edittext.setText("");
                                            edittext2.setText("");
                                            Snackbar snackbar = Snackbar.make(view, "PGI Already Done For This Serial Number!", Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                        }

                                    } else {
                                        progressDialog.dismiss();

                                        edittext.setText("");
                                        edittext2.setText("");
                                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No such Serial Number Exists!", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }

                                } else {
                                    progressDialog.dismiss();

                                    edittext.setText("");
                                    edittext2.setText("");
                                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! Please Try Again Later", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<LoadSerialHistory>> call, Throwable t) {
                                progressDialog.dismiss();

                                edittext.setText("");
                                edittext2.setText("");
                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + t.getMessage(), Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        });

                    } else {
                        progressDialog.dismiss();

                        edittext.setText("");
                        edittext2.setText("");
                        Snackbar snackbar = Snackbar.make(view, "Serial Number cannot be greater than 35 digits!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                } else {
                    global_duplicacy_removal_checker = 0;

                    edittext.setText("");
                    edittext2.setText("");
                    Snackbar snackbar = Snackbar.make(view, "This Serial Number Does Not Exist!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }


            } else {

                edittext.setText("");
                edittext2.setText("");
                Snackbar snackbar = Snackbar.make(view, "Please enter serial number", Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        } catch (Exception e) {

            edittext.setText("");
            edittext2.setText("");
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }


    private void addSCannedItemsToListForNormalGRN(View view, String snum) {

        try {


            if (!(snum.equals(""))) {

                if (snum.length() <= 35) {
                    progressDialog.show();

                    //=============> THIS SCENARIO CHECKS IF SNO IS SCANNED AT BACKEND <=============

                    //            for(int i =0; i< serial_numbers_received.size(); i++){
                    //                System.out.println("Size of Received Serial Numbers: "+serial_numbers_received.get(i));
                    //                if(serial_numbers_received.get(i).equals(snum)){
                    //                     ++isSerialNumberAlreadyScannedatBackend;
                    //                }
                    //            }

                    Call<List<LoadSerialHistory>> load_history = routes.getSerialHistoryData(snum, "");
                    load_history.enqueue(new Callback<List<LoadSerialHistory>>() {
                        @Override
                        public void onResponse(Call<List<LoadSerialHistory>> call, Response<List<LoadSerialHistory>> response) {

                            if (response.isSuccessful()) {
                                List<LoadSerialHistory> data = response.body();

                                if (data.size() > 0) {

                                    if ((!(data.get(0).getLast_mov().equals("601")))) {
                                        if ((!(data.get(0).getLast_mov().equals("653")))) {
                                            progressDialog.dismiss();

                                            edittext.setText("");
                                            edittext2.setText("");
                                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "This Serial Number Already Exists!", Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                        } else {
                                            progressDialog.dismiss();

                                            edittext.setText("");
                                            edittext2.setText("");
                                            Snackbar snackbar = Snackbar.make(view, "PGI Already Done For This Serial Number!", Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                        }
                                    } else {
                                        progressDialog.dismiss();

                                        edittext.setText("");
                                        edittext2.setText("");
                                        Snackbar snackbar = Snackbar.make(view, "PGI Already Done For This Serial Number!", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }

                                } else {
                                    Call<String> call_api = routes.CheckSerialNumber(snum);
                                    call_api.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {

                                            if (response.isSuccessful()) {
                                                if (response.body().equals("False")) {

                                                    //                              if(isSerialNumberAlreadyScannedatBackend == 0) {

                                                    if (transfer_grn_array_list.size() != 0) {
                                                        for (int i = 0; i < transfer_grn_array_list.size(); i++) {
                                                            if (transfer_grn_array_list.get(i).getSERIALNO().equals(snum)) {
                                                                isSerialNumberAlreadyScanned = 1;
                                                            }
                                                        }
                                                    }

                                                    if (isSerialNumberAlreadyScanned != 1) {

                                                        ++count_of_scanned_items;

                                                        if (count_of_scanned_items <= quantity) {

                                                            progressDialog.dismiss();
                                                            totalreceiptscan.setText("Total Scanned Quantities: " + count_of_scanned_items + "/" + quantity);
                                                            //                                                            lgort
                                                            transfer_grn_array_list.add(new Transfer_GRN_Model_Class(stono, stoitem, material, werks, lgort, qty, aedat, reswk, materialdescription, snum));
                                                            scan_serial_number_adapter.addItem(Integer.toString(count_of_scanned_items), material, materialdescription, snum);

                                                            if (count_of_scanned_items > 0) {
                                                                finishGRN.setEnabled(true);
                                                                finishGRN.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00BCD5")));
                                                            }
                                                            edittext.setText("");
                                                            edittext2.setText("");
                                                        } else if (count_of_scanned_items > quantity) {
                                                            progressDialog.dismiss();

                                                            edittext.setText("");
                                                            edittext2.setText("");
                                                            count_of_scanned_items = quantity;
                                                            Snackbar snackbar = Snackbar.make(view, "You have scanned all the quantity", Snackbar.LENGTH_LONG);
                                                            snackbar.show();
                                                        }

                                                    } else {
                                                        progressDialog.dismiss();

                                                        edittext.setText("");
                                                        edittext2.setText("");
                                                        Snackbar snackbar = Snackbar.make(view, "This Serial Number has already been scanned!", Snackbar.LENGTH_LONG);
                                                        snackbar.show();
                                                        isSerialNumberAlreadyScanned = 0;
                                                    }
                                                    //                          }
                                                    //                          else{
                                                    //                              isSerialNumberAlreadyScannedatBackend=0;
                                                    //                              isSerialNumberAlreadyScanned =0;
                                                    //                              progressDialog.dismiss();
                                                    //                              Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "This Serial Number Has Already Been Scanned!", Snackbar.LENGTH_LONG);
                                                    //                              snackbar.show();
                                                    //                          }


                                                } else {
                                                    progressDialog.dismiss();

                                                    edittext.setText("");
                                                    edittext2.setText("");
                                                    Snackbar snackbar = Snackbar.make(view, "You have scanned Model Number. Please scan Serial Number!", Snackbar.LENGTH_LONG);
                                                    snackbar.show();
                                                }
                                            } else {
                                                progressDialog.dismiss();

                                                edittext.setText("");
                                                edittext2.setText("");
                                                Snackbar snackbar = Snackbar.make(view, "An Error Occurred! Please Try Again", Snackbar.LENGTH_LONG);
                                                snackbar.show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            progressDialog.dismiss();

                                            edittext.setText("");
                                            edittext2.setText("");
                                            Snackbar snackbar = Snackbar.make(view, "An Error Occurred! CAUSED BY: " + t.getMessage(), Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                        }
                                    });
                                }


                            }

                        }

                        @Override
                        public void onFailure(Call<List<LoadSerialHistory>> call, Throwable t) {
                            progressDialog.dismiss();

                            edittext.setText("");
                            edittext2.setText("");
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! Please Try Again Later " + t.getMessage(), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    });

                } else {
                    progressDialog.dismiss();

                    edittext.setText("");
                    edittext2.setText("");
                    Snackbar snackbar = Snackbar.make(view, "Serial Number cannot be greater than 35 digits!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            } else {
                progressDialog.dismiss();

                edittext.setText("");
                edittext2.setText("");
                Snackbar snackbar = Snackbar.make(view, "Please enter serial number", Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        } catch (Exception e) {

            edittext.setText("");
            edittext2.setText("");
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    private void initialization() {

        try {

            recycle = findViewById(R.id.recycle);
            totalreceiptscan = findViewById(R.id.totalreceiptscan);
            material_num_to_be_displayed = findViewById(R.id.material_number_to_be_displayed);
            material_descript_to_be_displayed = findViewById(R.id.material_description_to_be_displayed);
//            delivery_number_to_be_displayed = findViewById(R.id.delivery_num_grn);
            edittext = findViewById(R.id.serial_number_scanned_by_scanner_grn);
            edittext2 = findViewById(R.id.edittext2);
            edittext.requestFocus();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            edittext.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!edittext.getText().toString().equals("")) {

                        //SET IF CONDITION HERE!
                        if (isUserComingFromTransferPosting.equals("true") || sto_typ.equals("TRF")) {
                            //                            System.out.println("The scanned serial number is: "+edittext.getText().toString());
                            addScannedItemsToList(findViewById(android.R.id.content), edittext.getText().toString());
                        } else if (isUserComingFromTransferPosting.equals("false") || sto_typ.equals("GRN")) {
                            //                            System.out.println("The scanned serial number is: "+edittext.getText().toString());
                            addSCannedItemsToListForNormalGRN(findViewById(android.R.id.content), edittext.getText().toString());
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            retrofit = OkHttpUtils.createRetrofit(
                    this,  // context of your Android app
                    "https://bar.shaker.com.sa/",  // base URL of API
                    "bilal.pfx",  // filename of the certificate in the assets folder
                    "123123123"  // password for the certificate
            );

            routes = retrofit.create(Routes.class);


        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void gotogoodsrecipt(View view) {

        try {
            transfer_grn_model_classes = new Transfer_GRN_Model_Class[transfer_grn_array_list.size()];

            for (int i = 0; i < transfer_grn_array_list.size(); i++) {
                transfer_grn_model_classes[i] = transfer_grn_array_list.get(i);
            }

            //======== HERE WE WILL CHECK WHETHER WE HAVE TO NAVIGATE THE USER TO GRN OR P-T-P TRANSFER ========


            if (isUserComingFromTransferPosting.equals("false")) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("transfer_grn_model_classes", transfer_grn_model_classes);
                Intent intent = new Intent(getApplicationContext(), GoodsReceiptScreen.class);
                intent.putExtras(bundle);
                intent.putExtra("isUserComingFromTransferPosting", "false");
                startActivity(intent);
            } else if (isUserComingFromTransferPosting.equals("true")) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("transfer_grn_model_classes", transfer_grn_model_classes);
                Intent intent = new Intent(getApplicationContext(), TransferLocationScreen.class);
                intent.putExtras(bundle);
                intent.putExtra("isUserComingFromTransferPosting", "true");
                intent.putExtra("move_stock_sloc_to_sloc", "false");
                System.out.println("The plant and storage location being sent is: " + transfer_grn_model_classes[0].getWERKS() + "   and   " + transfer_grn_model_classes[0].getLGORT());
                startActivity(intent);
            }

        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    public void scanserialno(View view) {
        onBackPressed();
    }

    public void cancel(View view) {
        onBackPressed();
    }


    @Override
    public void onItemClick(int position) {
        try {
            transfer_grn_array_list.remove(position);
            transfer_grn_model_classes = new Transfer_GRN_Model_Class[transfer_grn_array_list.size()];
            for (int i = 0; i < transfer_grn_array_list.size(); i++) {
                transfer_grn_model_classes[i] = transfer_grn_array_list.get(i);
            }

            System.out.println("the new size is: " + transfer_grn_model_classes.length);
            count_of_scanned_items--;
            totalreceiptscan.setText("Total Scanned Quantities: " + count_of_scanned_items + "/" + quantity);
            if (count_of_scanned_items == 0) {
                finishGRN.setEnabled(false);
            }

        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }


    @Override
    public void onBackPressed() {
        try {
            send_data_to_previous_activity();
        } catch (Exception e) {
            System.out.println("No Items Scanned To Be Transfered!");
        }
        super.onBackPressed();
    }


    public void gotodashboard(View view) {
        startActivity(new Intent(getApplicationContext(), DashboardScreen.class));
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