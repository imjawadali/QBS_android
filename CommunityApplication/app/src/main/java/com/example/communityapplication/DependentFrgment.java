package com.example.communityapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.savedstate.Recreator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.communityapplication.ModelClasses.AddDependent_ModelClass_Payload;
import com.example.communityapplication.ModelClasses.ResponseModelClass;
import com.example.communityapplication.WebService.Endpoints;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DependentFrgment extends Fragment {
    EditText edit_full_name_text,edit_text_email;
    RadioGroup radioGroup;
    TextView textdateofbirthset;
    AppCompatButton adddependent;

    String selectedDateString,selectedOption;
    Endpoints endpoints;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dependent_frgment, container, false);
        endpoints = RetrofitClient.getEndpoints();
        initialization(view);
        onClickListeners(view);
        return view;
    }

    private void onClickListeners(View view) {

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
                RadioButton radioButton = view.findViewById(checkedId);
                if (radioButton != null) {
                    // Get the text of the selected RadioButton
                    selectedOption = radioButton.getText().toString();
                    // Do something with the selected option
                    Toast.makeText(getContext(), "Selected: " + selectedOption, Toast.LENGTH_SHORT).show();
                }
            }
        });


        adddependent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String fullname = edit_full_name_text.getText().toString();
                    String emailaddress  = edit_text_email.getText().toString();
                    if (!fullname.isEmpty()  && !emailaddress.isEmpty() && !selectedOption.isEmpty() && !selectedDateString.isEmpty()){
                        AddDependent_ModelClass_Payload addDependentModelClassPayload = new AddDependent_ModelClass_Payload(fullname,selectedOption,selectedDateString,emailaddress);
                        Call<ResponseModelClass> call = endpoints.addDepend(addDependentModelClassPayload);
                        call.enqueue(new Callback<ResponseModelClass>() {
                            @Override
                            public void onResponse(Call<ResponseModelClass> call, Response<ResponseModelClass> response) {
                                if (response.isSuccessful() && response.body()!=null){
                                    Toast.makeText(getContext(), "In Onsuccessfull", Toast.LENGTH_LONG).show();
                                    ResponseModelClass responseModelClass = response.body();
                                    Snackbar snackbar = Snackbar.make(v,"Add dependent Successfully",Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                    edit_full_name_text.setText("");
                                    edit_text_email.setText("");
                                }
                                else {
                                    Snackbar snackbar = Snackbar.make(v,"Sorry Failed to adddependent",Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                    Toast.makeText(getContext(), "Sorry Failed to adddependent", Toast.LENGTH_LONG).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseModelClass> call, Throwable t) {
                                Snackbar snackbar = Snackbar.make(v,"Onfailure"+t.getMessage(),Snackbar.LENGTH_LONG);
                                snackbar.show();
                                Toast.makeText(getContext(), "Onfailure"+t.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                    else {
                        Toast.makeText(getContext(), "Please fill field", Toast.LENGTH_LONG).show();
                        edit_full_name_text.setError("Please fill field");
                        edit_text_email.setError("Please fill field");
                        Snackbar snackbar = Snackbar.make(v,"Please fill field",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "Exception:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog mDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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

    private void initialization(View view) {
        edit_full_name_text = view.findViewById(R.id.edit_full_name_text);
        edit_text_email = view.findViewById(R.id.edit_text_email);
        radioGroup = view.findViewById(R.id.radio_group);
        textdateofbirthset = view.findViewById(R.id.text_date_of_birth_set);
        adddependent = view.findViewById(R.id.adddependent);

        //
    }


}