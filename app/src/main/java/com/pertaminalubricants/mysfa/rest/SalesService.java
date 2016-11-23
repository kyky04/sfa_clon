package com.pertaminalubricants.mysfa.rest;

import com.pertaminalubricants.mysfa.model.SalesInOutResponse;
import com.pertaminalubricants.mysfa.model.SalesItemResponse;
import com.pertaminalubricants.mysfa.model.StockResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by nunu on 10/31/2016.
 */

public interface SalesService {
    @GET("SalesOrders")
    Call<List<SalesInOutResponse>> getAllOrder(@Query("access_token") String token, @Query(value = "filter", encoded = true) String filter);
//    Call<List<SalesInOutResponse>> getAllOrder(@Query("access_token") String token, @Query("trx_type") String type, @Query("sales_status") String status, @Query("id_salesman") int idSalesman);

    @GET("SalesOrders/{id}/salesitems")
    Call<List<SalesItemResponse>> getSalesItemsByOrder(@Path("id") String id, @Query("access_token") String token);

    @FormUrlEncoded
    @POST("SalesOrders")
    Call<SalesInOutResponse> addOrder(@FieldMap Map<String, String> params, @Query("access_token") String token);

    @POST("SalesOrders")
    Call<SalesInOutResponse> addOrderJson(@Body String params, @Query("access_token") String token);

    @POST("SalesOrders/saveSO")
    Call<SalesInOutResponse> addSO(@Body String params, @Query("access_token") String token);

    @GET("salesitems")
    Call<List<SalesItemResponse>> getAllSalesItem(@Query("access_token") String token);

    @GET("stocks")
    Call<List<StockResponse>> getAllStock(@Query("access_token") String token, @Query(value = "filter", encoded = true) String filter);

    @POST("SalesOrders/{id}/salesitems")
    Call<SalesItemResponse> addOrderItem(@Path("id") String id, @Body String params, @Query("access_token") String token);

    @GET("SalesOrders")
    Call<List<SalesInOutResponse>> getAllInvoice(@Query("access_token") String token, @Query("trx_type") String type, @Query("id_salesman") int idSalesman);

    @PUT("SalesOrders/{id}")
    Call<SalesInOutResponse> setOrderToFinal(@Path("id") String id, @Body String params, @Query("access_token") String token);

}


