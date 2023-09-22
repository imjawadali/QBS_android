package com.example.communityapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.communityapplication.ModelClasses.AddDependent_ModelClass_Payload;
import com.example.communityapplication.ModelClasses.ResponseModelClass;
import com.example.communityapplication.ModelClasses.Signup_ModelClass_Payload;
import com.example.communityapplication.WebService.Endpoints;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDependentActivity extends AppCompatActivity {


    EditText edit_full_name_text,edit_text_email;
    RadioGroup radioGroup;
    TextView textdateofbirthset;
    AppCompatButton adddependent;

    String selectedDateString,selectedOption;
    Endpoints endpoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dependent);
        initlization();
        onClicklistener();
        endpoints = RetrofitClient.getEndpoints();
    }

    private void onClicklistener() {

        textdateofbirthset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayDatePicker();

            }
        });




        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Get the selected RadioButton
                RadioButton radioButton = findViewById(checkedId);
                if (radioButton != null) {
                    // Get the text of the selected RadioButton
                    selectedOption = radioButton.getText().toString();
                    // Do something with the selected option
                    Toast.makeText(AddDependentActivity.this, "Selected: " + selectedOption, Toast.LENGTH_SHORT).show();
                }
            }
        });


        adddependent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(AddDependentActivity.this, "hahhahaha", Toast.LENGTH_LONG).show();
                    String fullname = edit_full_name_text.getText().toString();
                    String emailaddress  = edit_text_email.getText().toString();
                    if (!fullname.isEmpty()  && !emailaddress.isEmpty()){
                        AddDependent_ModelClass_Payload addDependentModelClassPayload = new AddDependent_ModelClass_Payload(fullname,emailaddress,selectedOption,selectedDateString);
                        Call<ResponseModelClass> call = endpoints.addDepend(addDependentModelClassPayload);
                        call.enqueue(new Callback<ResponseModelClass>() {
                            @Override
                            public void onResponse(Call<ResponseModelClass> call, Response<ResponseModelClass> response) {
                                if (response.isSuccessful() && response.body()!=null){
                                    Toast.makeText(AddDependentActivity.this, "In Onsuccessfull", Toast.LENGTH_LONG).show();
                                    ResponseModelClass responseModelClass = response.body();
                                    Snackbar snackbar = Snackbar.make(v,"Add dependent Successfully",Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                    edit_full_name_text.setText("");
                                    edit_text_email.setText("");
                                }
                                else {
                                    Snackbar snackbar = Snackbar.make(v,"Sorry Failed to adddependent",Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                    Toast.makeText(AddDependentActivity.this, "Sorry Failed to adddependent", Toast.LENGTH_LONG).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseModelClass> call, Throwable t) {
                                Snackbar snackbar = Snackbar.make(v,"Onfailure"+t.getMessage(),Snackbar.LENGTH_LONG);
                                snackbar.show();
                                Toast.makeText(AddDependentActivity.this, "Onfailure"+t.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                    else {
                        Toast.makeText(AddDependentActivity.this, "Please fill field", Toast.LENGTH_LONG).show();
                        edit_full_name_text.setError("Please fill field");
                        edit_text_email.setError("Please fill field");
                        Snackbar snackbar = Snackbar.make(v,"Please fill field",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(AddDependentActivity.this, "Exception:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private void displayDatePicker() {
        Calendar calendar = Calendar.getInstance();


        DatePickerDialog mDialog = new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                try {
                    String tempString = i + "-" + (i1 + 1) + "-" + i2;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat prettyFormat = new SimpleDateFormat("EEEE MMM dd, yyyy");
                    Date date = format.parse(tempString);
                    textdateofbirthset.setText(prettyFormat.format(date));
                    selectedDateString = format.format(format.parse(tempString));


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        mDialog.show();

    }


    private void displayDialog(String title, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddDependentActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });


        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }


    private void initlization() {
        edit_full_name_text = findViewById(R.id.edit_full_name_text);
        edit_text_email = findViewById(R.id.edit_text_email);
        radioGroup = findViewById(R.id.radio_group);
        textdateofbirthset = findViewById(R.id.text_date_of_birth_set);
        adddependent = findViewById(R.id.adddependent);
    }


}