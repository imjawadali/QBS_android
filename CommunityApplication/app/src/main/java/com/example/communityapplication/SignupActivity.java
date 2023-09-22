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

public class SignupActivity extends AppCompatActivity {

    EditText editfullnametext,edittextcontact,edittextemail,edittextaddressmalawi,edittextaddresspak,edittextpassword;
    RadioGroup radioGroup;
    TextView textdateofbirthset;
    ImageView button_back;
    AppCompatButton loginbtn;

    String selectedDateString,selectedOption;
    Endpoints endpoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
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
                    Toast.makeText(SignupActivity.this, "Selected: " + selectedOption, Toast.LENGTH_SHORT).show();
                }
            }
        });


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullname = editfullnametext.getText().toString();
                String contactnumber = edittextcontact.getText().toString();
                String emailaddress  = edittextemail.getText().toString();
                String addressmalwai = edittextaddressmalawi.getText().toString();
                String addresspakaistan = edittextaddresspak.getText().toString();
                String password = edittextpassword.getText().toString();

                try {
                    if (!fullname.equals("") && !contactnumber.equals("") && !emailaddress.equals("") && !addressmalwai.equals("") && !addresspakaistan.equals("") && !password.equals("")){
                        Signup_ModelClass_Payload signupModelClassPayload = new Signup_ModelClass_Payload(fullname,selectedOption,selectedDateString,contactnumber,emailaddress,addressmalwai,addresspakaistan,password);
                        Call<ResponseModelClass> call = endpoints.registerUser(signupModelClassPayload);
                        call.enqueue(new Callback<ResponseModelClass>() {
                            @Override
                            public void onResponse(Call<ResponseModelClass> call, Response<ResponseModelClass> response) {
                                if (response.isSuccessful() && response.body()!=null){
                                    ResponseModelClass responseModelClass = response.body();
                                    Snackbar snackbar = Snackbar.make(v,"The status is: "+responseModelClass.getStatus(),Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                    Snackbar snackbar1 = Snackbar.make(v,"User Register Successfully",Snackbar.LENGTH_LONG);
                                    snackbar1.show();
                                    editfullnametext.setText("");
                                    edittextcontact.setText("");
                                    edittextemail.setText("");
                                    edittextaddressmalawi.setText("");
                                    edittextaddresspak.setText("");
                                    edittextpassword.setText("");
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                }
                                else {
                                    Snackbar snackbar1 = Snackbar.make(v,"Sorry User have not registered",Snackbar.LENGTH_LONG);
                                    snackbar1.show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseModelClass> call, Throwable t) {
                                Snackbar snackbar1 = Snackbar.make(v,"Onfailure message"+t.getMessage(),Snackbar.LENGTH_LONG);
                                snackbar1.show();
                            }
                        });
                    }
                    else {
                        Snackbar snackbar = Snackbar.make(v,"Please fill field",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }


            }
            catch (Exception e){
                Toast.makeText(SignupActivity.this, "Exception:"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }


    private void displayDatePicker() {
        Calendar calendar = Calendar.getInstance();


        DatePickerDialog mDialog = new DatePickerDialog(SignupActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignupActivity.this)
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
        editfullnametext = findViewById(R.id.edit_full_name_text);
        edittextcontact = findViewById(R.id.edit_text_contact);
        edittextemail = findViewById(R.id.edit_text_email);
        edittextaddressmalawi = findViewById(R.id.edit_text_address_malawi);
        edittextaddresspak = findViewById(R.id.edit_text_address_pak);
        radioGroup = findViewById(R.id.radio_group);
        textdateofbirthset = findViewById(R.id.text_date_of_birth_set);
        edittextpassword = findViewById(R.id.edit_text_password);
        loginbtn = findViewById(R.id.login_btn);
        button_back = findViewById(R.id.button_back);
    }
}