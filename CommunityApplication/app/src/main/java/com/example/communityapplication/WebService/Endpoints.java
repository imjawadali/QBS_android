package com.example.communityapplication.WebService;

import com.example.communityapplication.ModelClasses.AddDependent_ModelClass_Payload;
import com.example.communityapplication.ModelClasses.Community_ModelClass;
import com.example.communityapplication.ModelClasses.Login_ModelClass_Payload;
import com.example.communityapplication.ModelClasses.ResponseModelClass;
import com.example.communityapplication.ModelClasses.Signup_ModelClass_Payload;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Endpoints {

    @POST("signup")
    Call<ResponseModelClass> registerUser(@Body Signup_ModelClass_Payload signupModelClassPayload);

    @POST("login")
    Call<ResponseModelClass> authUser(@Body Login_ModelClass_Payload login_modelClass_payload);

    @POST("adddependent")
    Call<ResponseModelClass> addDepend(@Body AddDependent_ModelClass_Payload addDependent_modelClass_payload);


    @GET("community")
    Call<List<Community_ModelClass>> retrieve_community_data();

}
