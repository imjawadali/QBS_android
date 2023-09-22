package com.example.shakerapplication.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shakerapplication.Adapters.Goods_Issue_Screen_Adapter;
import com.example.shakerapplication.ModelClasses.Goods_Issue_Serial_number_Data_Api;
import com.example.shakerapplication.ModelClasses.LoadSerialHistory;
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

public class GoodsIssueScreen extends AppCompatActivity implements Goods_Issue_Screen_Adapter.OnItemClickListener {
    TextView totalscan, material_num_to_be_displayed, material_descript_to_be_displayed, delivery_number_to_be_displayed;
    MaterialButton proceed, finishGI;
    CardView AddToList;
    private int count_of_scanned_items = 0, quantity = 0, isValidSN = 0, isSerialScanned = 0;
    private ProgressDialog progressDialog;
    private ArrayList<String> serial_numbers_received = new ArrayList<>();
    private Retrofit retrofit;
    private Routes routes;
    String matnrValue, vbelnValue, posnrValue, maktxValue, fimgValue, ebeln, ebelp, werks, ean11, lgort, menge, meins, netpr, netwr, aedat, reswk, serialno, bwart, grund, umwrk, umlgo, sernr, obd_number, lfimgValue, serial_chk, uii = "", deliv_type;

    EditText editText, editText2;

    private Goods_Issue_Screen_Adapter adapter;
    private RecyclerView recyclerView;
    private int object_counter = 0;
    private Transfer_GI_Model_Class[] transfer_gi_model_class;
    private ArrayList<Transfer_GI_Model_Class> transfer_gi_array_list = new ArrayList<>();
    private int isSerialNumberAlreadyScanned = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_issue_screen);

        try {
            progressDialog = new ProgressDialog(GoodsIssueScreen.this);

            progressDialog.setTitle("Wait");
            progressDialog.setMessage("Loading Already Scanned Serial Numbers.....");
            progressDialog.setCancelable(false);
            progressDialog.show();


            getSupportActionBar().hide();
            retrofit = OkHttpUtils.createRetrofit(
                    this,  // context of your Android app
                    "https://bar.shaker.com.sa/",  // base URL of API
                    "bilal.pfx",  // filename of the certificate in the assets folder
                    "123123123"  // password for the certificate
            );


            routes = retrofit.create(Routes.class);
            initialization();
            Intent intent = getIntent();
//Initialize kero adaptor aur recycler view ko idher
            adapter = new Goods_Issue_Screen_Adapter(getApplicationContext());
            adapter.setOnItemClickListener(this);

            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(adapter);

            matnrValue = intent.getStringExtra("matnr");
            vbelnValue = intent.getStringExtra("vbeln");
            posnrValue = intent.getStringExtra("posnr");
            maktxValue = intent.getStringExtra("maktx");
            lfimgValue = intent.getStringExtra("lfimg");
            ebeln = intent.getStringExtra("ebeln");
            ebelp = intent.getStringExtra("ebelp");
            werks = intent.getStringExtra("werks");
            ean11 = intent.getStringExtra("ean11");
            lgort = intent.getStringExtra("lgort");
            menge = intent.getStringExtra("menge");
            meins = intent.getStringExtra("meins");
            netpr = intent.getStringExtra("netpr");
            netwr = intent.getStringExtra("netwr");
            aedat = intent.getStringExtra("aedat");
            reswk = intent.getStringExtra("reswk");
            serialno = intent.getStringExtra("serialno");
            bwart = intent.getStringExtra("bwart");
            grund = intent.getStringExtra("grund");
            umwrk = intent.getStringExtra("umwrk");
            umlgo = intent.getStringExtra("umlgo");
            sernr = intent.getStringExtra("sernr");
            obd_number = intent.getStringExtra("obd_number_selected");
            serial_chk = intent.getStringExtra("serial_chk");
            quantity = Integer.parseInt(lfimgValue.substring(0, lfimgValue.indexOf(".")));
            deliv_type = intent.getStringExtra("delivery_type");
            getGoodsIssueSerialNumber(vbelnValue, posnrValue);


            proceed = findViewById(R.id.proceed);
            finishGI = findViewById(R.id.finishGI);


            try {
                transfer_gi_model_class = (Transfer_GI_Model_Class[]) getIntent().getSerializableExtra("transfer_gi_model_classes");
                System.out.println("The length of the previously scanned serial numbers is: " + transfer_gi_model_class.length);
                for (int k = 0; k < transfer_gi_model_class.length; k++) {
                    transfer_gi_array_list.add(transfer_gi_model_class[k]);

                    int counts = 0;


                    if (transfer_gi_model_class[k].getMaterial().equals(matnrValue)) {
                        ++count_of_scanned_items;
                        counts = count_of_scanned_items;
                    } else {
                        counts = k + 1;
                    }

                    adapter.addItem(Integer.toString(counts), transfer_gi_array_list.get(k).getMaterial(), transfer_gi_array_list.get(k).getMaterialdescript(), transfer_gi_array_list.get(k).getSernr());

                }


                if (transfer_gi_array_list.size() > 0) {
                    finishGI.setEnabled(true);
                    finishGI.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00BCD5")));
                }
            } catch (Exception e) {
                System.out.println("No Data Passed From OutBound Delivery Screen!" + e.getMessage());
            }


            try {
                totalscan.setText("Total Scanned Quantities: " + count_of_scanned_items + "/" + quantity);
                String mat_num_text = "Material Number: " + matnrValue;
                int splitIndex = mat_num_text.indexOf(":") + 2; // get the index of the colon and add 2 to skip the colon and the following space
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(mat_num_text);
// set the bold style to the first half of the text
                spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), 0, splitIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

// set the normal style to the second half of the text
                spannableStringBuilder.setSpan(new StyleSpan(Typeface.NORMAL), splitIndex, mat_num_text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

// set the spannable string to the text view
                material_num_to_be_displayed.setText(spannableStringBuilder);

                String mat_desc_text = "Material Descript: " + maktxValue;

                spannableStringBuilder = new SpannableStringBuilder(mat_desc_text);
                spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), 0, splitIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableStringBuilder.setSpan(new StyleSpan(Typeface.NORMAL), splitIndex, mat_num_text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                material_descript_to_be_displayed.setText(spannableStringBuilder);


                String deliv_num_text = "Delivery Number: " + vbelnValue;

                spannableStringBuilder = new SpannableStringBuilder(deliv_num_text);
                spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), 0, splitIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableStringBuilder.setSpan(new StyleSpan(Typeface.NORMAL), splitIndex, deliv_num_text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                delivery_number_to_be_displayed.setText(spannableStringBuilder);


                AddToList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addScannedItemsToList(view, editText2.getText().toString());
                    }
                });
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


            proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    send_data_to_previous_activity();
                }
            });

        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }


    private void send_data_to_previous_activity() {
        try {
            Intent intent = new Intent(GoodsIssueScreen.this, SelectOutboundDeliveryScreen.class);
            transfer_gi_model_class = new Transfer_GI_Model_Class[transfer_gi_array_list.size()];
            for (int i = 0; i < transfer_gi_array_list.size(); i++) {
                transfer_gi_model_class[i] = transfer_gi_array_list.get(i);
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("transfer_gi_model_classes", transfer_gi_model_class);
            intent.putExtras(bundle);
            intent.putExtra("obd_number_selected", obd_number);
            intent.putExtra("delivery_type", deliv_type);

            startActivity(intent);
        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }


    private void addScannedItemsToList(View view, String snum) {
        try {
            if (snum.length() != 0) {


                if (snum.length() <= 35) {
                    progressDialog.setTitle("Wait");
                    progressDialog.setMessage("Adding Item To The List!");
                    progressDialog.setCancelable(false);
                    progressDialog.show();


                    for (int i = 0; i < serial_numbers_received.size(); i++) {
                        System.out.println("Serial Numbers Received are: " + serial_numbers_received.get(i));
                        if (serial_numbers_received.get(i).equals(snum)) {
                            ++isSerialScanned;
                        }
                    }


                    Call<String> call_api = routes.CheckSerialNumber(snum);
                    call_api.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {

                                if (response.body().equals("False")) {


                                    if (isSerialScanned == 0) {
                                        Call<List<LoadSerialHistory>> load_history = routes.getSerialHistoryData(snum, "");
                                        load_history.enqueue(new Callback<List<LoadSerialHistory>>() {
                                            @Override
                                            public void onResponse(Call<List<LoadSerialHistory>> call, Response<List<LoadSerialHistory>> response) {

                                                if (response.isSuccessful()) {
                                                    List<LoadSerialHistory> data = response.body();

//                                                     if(data.size()>0) {


                                                    if (data.size() != 0 || (data.size() == 0 && serial_chk.equalsIgnoreCase("x"))) {

                                                        if ((data.size() == 0 && serial_chk.equalsIgnoreCase("x"))) {
                                                            new AlertDialog.Builder(GoodsIssueScreen.this)
                                                                    .setTitle("Confirmation")
                                                                    .setMessage("This serial number does not exist! Do you want to add this serial number?")
                                                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            uii = "x";
                                                                            addtolist(data, snum, view);
                                                                        }
                                                                    })
                                                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            progressDialog.dismiss();
                                                                            editText2.setText("");
                                                                            editText.setText("");
                                                                        }
                                                                    })
                                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                                    .show();

                                                        } else {
                                                            if (!((data.get(0).getLast_mov().equals("601")) && (deliv_type.equalsIgnoreCase("LF")))) {
                                                                if (!((data.get(0).getLast_mov().equals("653")) && (deliv_type.equalsIgnoreCase("LR")))) {

                                                                    uii = "";
                                                                    addtolist(data, snum, view);
                                                                } else {
                                                                    editText.setText("");
                                                                    editText2.setText("");

                                                                    progressDialog.dismiss();
                                                                    Snackbar snackbar = Snackbar.make(view, "PGI Already Done For This Serial Number!", Snackbar.LENGTH_LONG);
                                                                    snackbar.show();
                                                                }
                                                            } else {
                                                                progressDialog.dismiss();
                                                                editText.setText("");
                                                                editText2.setText("");
                                                                Snackbar snackbar = Snackbar.make(view, "PGI Already Done For This Serial Number!", Snackbar.LENGTH_LONG);
                                                                snackbar.show();
                                                            }
                                                        }

                                                    } else {
                                                        progressDialog.dismiss();
                                                        editText.setText("");
                                                        editText2.setText("");
                                                        Snackbar snackbar = Snackbar.make(view, "Please Scan Valid Serial Number!", Snackbar.LENGTH_LONG);
                                                        snackbar.show();
                                                    }


//                                                     }


//                                                     else{
//                                                         progressDialog.dismiss();
//                                                         Snackbar snackbar = Snackbar.make(view, "No Such Serial Number Exist!", Snackbar.LENGTH_LONG);
//                                                         snackbar.show();
//                                                     }


                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<List<LoadSerialHistory>> call, Throwable t) {
                                                progressDialog.dismiss();
                                                editText.setText("");
                                                editText2.setText("");
                                                Snackbar snackbar = Snackbar.make(view, "An Error Occurred! Please Try Again Later", Snackbar.LENGTH_LONG);
                                                snackbar.show();
                                            }
                                        });
                                    } else {
                                        isSerialScanned = 0;
                                        progressDialog.dismiss();
                                        editText.setText("");
                                        editText2.setText("");
                                        Snackbar snackbar = Snackbar.make(view, "This Serial Number Has Already Been Scanned!", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }

                                } else {
                                    progressDialog.dismiss();
                                    editText.setText("");
                                    editText2.setText("");
                                    Snackbar snackbar = Snackbar.make(view, "You have scanned Model Number! Please Scan Serial Number", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            progressDialog.dismiss();
                            editText.setText("");
                            editText2.setText("");
                            Snackbar snackbar = Snackbar.make(view, "An Error Occurred! Please Try Again Later", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    });

                } else {
                    progressDialog.dismiss();
                    editText.setText("");
                    editText2.setText("");
                    Snackbar snackbar = Snackbar.make(view, "Serial Number cannot be greater than 35 digits!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            } else {
                progressDialog.dismiss();
                editText.setText("");
                editText2.setText("");
                Snackbar snackbar = Snackbar.make(view, "Please enter serial number", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        } catch (Exception e) {
            editText.setText("");
            editText2.setText("");
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }


    private void addtolist(List<LoadSerialHistory> data, String snum, View view) {

        try {
            SharedPreferences sharedPreferences = getSharedPreferences("plant_and_storagelocation_preference", Context.MODE_PRIVATE);
            String plant = sharedPreferences.getString("plant", "");
            String storageloc = sharedPreferences.getString("storage_location", "");

            if (data.size() > 0) {
                if (data.get(0).getModel().equals(matnrValue) && data.get(0).getCurrentPlant().equals(plant) && data.get(0).getCurrentStockLocation().equals(storageloc)) {
                    if (transfer_gi_array_list.size() != 0) {
                        for (int i = 0; i < transfer_gi_array_list.size(); i++) {
                            if (transfer_gi_array_list.get(i).getSernr().equals(snum)) {
                                isSerialNumberAlreadyScanned = 1;
                            }
                        }
                    }
                    if (isSerialNumberAlreadyScanned != 1) {

                        if (!snum.equals("")) {
                            ++count_of_scanned_items;
                        }
                        if (count_of_scanned_items <= quantity) {
                            totalscan.setText("Total Scanned Quantities: " + count_of_scanned_items + "/" + quantity);

//Serial Number Get Kero Input field se
//Adaptor ka function call kerwa lo
                            if (!snum.equals("")) {
                                progressDialog.dismiss();
                                editText2.setText("");
                                editText.setText("");
                                adapter.addItem(Integer.toString(count_of_scanned_items), matnrValue, maktxValue, snum);
                                transfer_gi_array_list.add(new Transfer_GI_Model_Class(vbelnValue, posnrValue, snum, matnrValue, maktxValue, Integer.toString(count_of_scanned_items), uii));
//                                                                ==>CHANGE HERE
                            }


                            if (count_of_scanned_items > 0) {
                                finishGI.setEnabled(true);
                                finishGI.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#299271")));
                            }
                            editText.setText("");
                            editText2.setText("");

                        } else if (count_of_scanned_items > quantity) {
                            progressDialog.dismiss();
                            editText.setText("");
                            editText2.setText("");
                            count_of_scanned_items = quantity;
                            Snackbar snackbar = Snackbar.make(view, "You have filled all the quantity", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                    } else {
                        progressDialog.dismiss();
                        editText.setText("");
                        editText2.setText("");
                        Snackbar snackbar = Snackbar.make(view, "This Serial Number has already been scanned!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        isSerialNumberAlreadyScanned = 0;
                    }

                } else {
                    progressDialog.dismiss();
                    editText.setText("");
                    editText2.setText("");
                    Snackbar snackbar = Snackbar.make(view, "Either the Model Number/Plant/Storage Location does not match!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            } else {
                if (transfer_gi_array_list.size() != 0) {
                    for (int i = 0; i < transfer_gi_array_list.size(); i++) {
                        if (transfer_gi_array_list.get(i).getSernr().equals(snum)) {
                            isSerialNumberAlreadyScanned = 1;
                        }
                    }
                }

                if (isSerialNumberAlreadyScanned != 1) {

                    if (!snum.equals("")) {
                        ++count_of_scanned_items;
                    }
                    if (count_of_scanned_items <= quantity) {
                        totalscan.setText("Total Scanned Quantities: " + count_of_scanned_items + "/" + quantity);

//Serial Number Get Kero Input field se
//Adaptor ka function call kerwa lo
                        if (!snum.equals("")) {
                            progressDialog.dismiss();
                            editText2.setText("");
                            editText.setText("");
                            adapter.addItem(Integer.toString(count_of_scanned_items), matnrValue, maktxValue, snum);
                            transfer_gi_array_list.add(new Transfer_GI_Model_Class(vbelnValue, posnrValue, snum, matnrValue, maktxValue, Integer.toString(count_of_scanned_items), uii));
//                                                                ==>CHANGE HERE
                        }


                        if (count_of_scanned_items > 0) {
                            finishGI.setEnabled(true);
                            finishGI.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#299271")));
                        }
                        editText.setText("");

                    } else if (count_of_scanned_items > quantity) {
                        progressDialog.dismiss();
                        editText.setText("");
                        editText2.setText("");
                        count_of_scanned_items = quantity;
                        Snackbar snackbar = Snackbar.make(view, "You have filled all the quantity", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                } else {
                    progressDialog.dismiss();
                    editText.setText("");
                    editText2.setText("");

                    Snackbar snackbar = Snackbar.make(view, "This Serial Number has already been scanned!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    isSerialNumberAlreadyScanned = 0;
                }
            }

        } catch (Exception e) {
            progressDialog.dismiss();
            editText.setText("");
            editText2.setText("");

            Snackbar snackbar = Snackbar.make(view, "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }


    }


    private void initialization() {

        try {
            recyclerView = findViewById(R.id.recycle);
            totalscan = findViewById(R.id.totalscan);
            material_num_to_be_displayed = findViewById(R.id.gi_mat_num_to_be_displayed);
            material_descript_to_be_displayed = findViewById(R.id.gi_mat_desc_to_be_displayed);
            delivery_number_to_be_displayed = findViewById(R.id.delivery_num);
            AddToList = findViewById(R.id.addtolistbtn);
            editText = findViewById(R.id.serialno);
            editText2 = findViewById(R.id.serialno2);
            editText.requestFocus();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!editText.getText().toString().equals("")) {
                        addScannedItemsToList(findViewById(android.R.id.content), editText.getText().toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }


    private void getGoodsIssueSerialNumber(String obdno, String itemno) {
        try {

            Call<List<Goods_Issue_Serial_number_Data_Api>> call = routes.getGoodsIssueSerialNumberData(obdno, itemno);
            call.enqueue(new Callback<List<Goods_Issue_Serial_number_Data_Api>>() {
                @Override
                public void onResponse(Call<List<Goods_Issue_Serial_number_Data_Api>> call, Response<List<Goods_Issue_Serial_number_Data_Api>> response) {
                    if (response.isSuccessful() && response != null) {
                        progressDialog.dismiss();
                        List<Goods_Issue_Serial_number_Data_Api> data = response.body();
                        for (int i = 0; i < data.size(); i++) {
                            serial_numbers_received.add(data.get(i).getSERNR());
                        }
                    } else {
                        progressDialog.dismiss();
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! Please Try Again Later", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }

                @Override
                public void onFailure(Call<List<Goods_Issue_Serial_number_Data_Api>> call, Throwable t) {
                    progressDialog.dismiss();
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + t.getMessage(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });
        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }


    public void skipserial(View view) {
        onBackPressed();
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

    public void gotodeliveryscreen(View view) {

        try {
            transfer_gi_model_class = new Transfer_GI_Model_Class[transfer_gi_array_list.size()];

            for (int i = 0; i < transfer_gi_array_list.size(); i++) {
                transfer_gi_model_class[i] = transfer_gi_array_list.get(i);
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("transfer_gi_model_classes", transfer_gi_model_class);
            Intent intent = new Intent(getApplicationContext(), DeliveryScreen.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void cancel(View view) {
        onBackPressed();
    }


    @Override
    public void onItemClick(int position) {

        try {
            transfer_gi_array_list.remove(position);
            transfer_gi_model_class = new Transfer_GI_Model_Class[transfer_gi_array_list.size()];
            for (int i = 0; i < transfer_gi_array_list.size(); i++) {
                transfer_gi_model_class[i] = transfer_gi_array_list.get(i);
            }

            System.out.println("the new size is: " + transfer_gi_model_class.length);
            count_of_scanned_items--;
            totalscan.setText("Total Scanned Quantities: " + count_of_scanned_items + "/" + quantity);
            if (count_of_scanned_items == 0) {
                finishGI.setEnabled(false);
            }

        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "An Error Occurred! CAUSED BY: " + e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

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