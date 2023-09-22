package com.example.communityapplication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.communityapplication.ModelClasses.Example;
import com.example.communityapplication.ModelClasses.Login_ModelClass_Payload;
import com.example.communityapplication.ModelClasses.ResponseModelClass;
import com.example.communityapplication.WebService.Endpoints;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText usernamenametext,passwordnametext;
    TextView register;
    AppCompatButton loginbtn;
    Endpoints endpoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initlization();
        onClickListener();
        endpoints = RetrofitClient.getEndpoints();
    }

    private void onClickListener() {

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String email = usernamenametext.getText().toString();
                    String password = passwordnametext.getText().toString();
                    if (!email.contains("@gmail.com")){
                        Snackbar snackbar = Snackbar.make(v,"enter correct email",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else if (password.length()<6) {
                        Snackbar snackbar = Snackbar.make(v,"password too weak",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    else {
                        Login_ModelClass_Payload login_modelClass_payload = new Login_ModelClass_Payload(email,password);
                        Call<ResponseModelClass> call = endpoints.authUser(login_modelClass_payload);
                        call.enqueue(new Callback<ResponseModelClass>() {
                            @Override
                            public void onResponse(Call<ResponseModelClass> call, Response<ResponseModelClass> response) {
                                if (response.isSuccessful() && response.body()!=null){
                                    ResponseModelClass responseModelClass = response.body();
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                    finish();
                                }
                                else {
                                    Snackbar snackbar = Snackbar.make(v,"Invalid Credentials",Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseModelClass> call, Throwable t) {

                            }
                        });
                    }

                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, "Exception"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
            }
        });
    }

    private void initlization() {

        usernamenametext = findViewById(R.id.username_name_text);
        passwordnametext = findViewById(R.id.password_name_text);
        loginbtn = findViewById(R.id.login_btn);
        register = findViewById(R.id.register);

    }


    private void displayDialog(String title, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this)
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
}