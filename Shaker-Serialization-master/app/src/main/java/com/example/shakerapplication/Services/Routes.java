package com.example.shakerapplication.Services;

import com.example.shakerapplication.ModelClasses.GI_Response_POJO_Class;
import com.example.shakerapplication.ModelClasses.GRN_Response_POJO_Class;
import com.example.shakerapplication.ModelClasses.Goods_Issue_Serial_number_Data_Api;
import com.example.shakerapplication.ModelClasses.Goods_Receipt_Select_STO_ModelClass;
import com.example.shakerapplication.ModelClasses.Goods_Receipt_Serial_Sto_Item_ModelClass;
import com.example.shakerapplication.ModelClasses.LoadSerialHistory;
import com.example.shakerapplication.ModelClasses.Load_Already_Scanned_SERNR_GRN_TP;
import com.example.shakerapplication.ModelClasses.Load_Outbound_Delivery;
import com.example.shakerapplication.ModelClasses.Load_Plants_Model_Class;
import com.example.shakerapplication.ModelClasses.Load_Storage_Location;
import com.example.shakerapplication.ModelClasses.Post_GRN_Model_Class;
import com.example.shakerapplication.ModelClasses.Post_Good_Issue_Model_Class;
import com.example.shakerapplication.ModelClasses.Post_Transfer_Serial_Model_Class;
import com.example.shakerapplication.ModelClasses.Serial_Registration_Model_Class;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Routes {

//End Point For Loading Plants ==> (Usage Login Screen)
@GET("api/serialization/plant")
Call<List<Load_Plants_Model_Class>> getPlants();



//End Point For Loading Storage Locations ==> (Usage Login Screen)
@GET("api/serialization/location")
Call<List<Load_Storage_Location>> getLocationByPlant(@Query("plant") String plant);


//End Point For Loading Outbound Deliveries List
@GET("api/serialization/serial-obd-list")
Call<List<Load_Outbound_Delivery>> getOutboundDeliveries(@Query("plant") String plant, @Query("storagLoc") String storagLoc, @Query("I_LFART") String I_LFART);


//End Point For Loading Serial History
@GET("api/serialization/serial-history")
Call<List<LoadSerialHistory>> getSerialHistoryData(@Query("I_SERNR") String I_SERNR, @Query("I_MODEL") String I_MODEL);



//End Point For Authenticating the User
@GET("api/serialization/auth-user")
Call<String> AuthenticateUser (@Query("plant") String plant, @Query("storagLoc") String storagLoc, @Query("username") String username, @Query("password") String password);


//End Point For Goods Receipt Select STO
@GET("api/serialization/serial-sto-list")
Call<List<Goods_Receipt_Select_STO_ModelClass>> getGoodsReceiptStoData(@Query("plant") String plant, @Query("storagLoc") String storagLoc, @Query("I_TPCHK") String I_TPCHK);

//End Point For Goods Receipt Serial STO ITEM
@GET("api/serialization/serial-sto-item")
Call<List<Goods_Receipt_Serial_Sto_Item_ModelClass>> getGoodsReceiptSerialStoData(@Query("I_STONO") String stono,@Query("plant") String plant, @Query("storagLoc") String storagLoc);

// End Point For Good Issue POST
@POST("api/serialization/serial-pgi")
Call<List<GI_Response_POJO_Class>> postGIPayload(@Body Post_Good_Issue_Model_Class payload);


// End Point For Good Issue Api For Get Serial Number
@GET("api/serialization/serial-srno-list")
Call<List<Goods_Issue_Serial_number_Data_Api>> getGoodsIssueSerialNumberData(@Query("I_VBELN") String obdno, @Query("I_POSNR") String itemno);


// End Point For Good Receipt POST
@POST("api/serialization/serial-grn")
Call<List<GRN_Response_POJO_Class>> postGRNPayload(@Body List<Post_GRN_Model_Class> payload);



// End Point For Plant To Plant Transfer POST
@POST("api/serialization/serial-gi")
Call<List<GRN_Response_POJO_Class>> postPlantToPlantTransferPayload(@Body List<Post_GRN_Model_Class> payload);



//End Point For Transfer Posting
@POST("api/serialization/serial-transfer")
Call<List<GRN_Response_POJO_Class>> postSerialTransfer (@Body List<Post_Transfer_Serial_Model_Class>  payload);



//END POINT TO CHECK SCANNED NUMBER IS SERIAL NUMBER OR MODEL NUMBER
@GET("api/serialization/serial-check")
Call<String>CheckSerialNumber(@Query("I_SERNO")String user_scanned_serial_number);


@GET("api/serialization/serial-srno-dup")
Call<List<Load_Already_Scanned_SERNR_GRN_TP>>CheckSerialNumberforTPandGRN(@Query("I_EBELN") String I_EBELN, @Query("I_EBELP") String I_EBELP);



@POST("api/serialization/serial-load-inv")
Call<String>SerialRegistrationPosting(@Body List< Serial_Registration_Model_Class > payload);


@POST("api/serialization/serial-audit-tv")
Call<String>FloorAuditPosting(@Query("fLOORAUDITs") String fLOORAUDITs);


}