package com.pertaminalubricants.mysfa.rest;

import com.pertaminalubricants.mysfa.model.ContractResponse;
import com.pertaminalubricants.mysfa.model.CustomerResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by nunu on 11/2/2016.
 */

public interface CustomerService {
    @GET("customers")
    Call<List<CustomerResponse>> getAllCustomerProspect(@Query("access_token") String token, @Query(value = "filter", encoded = true) String filter);

    @GET("customers/{id}")
    Call<CustomerResponse> getCustomer(@Path("id") String id, @Query("access_token") String token);

    @FormUrlEncoded
    @POST("customers")
    Call<CustomerResponse> addCustomer(@FieldMap Map<String, String> params, @Query("access_token") String token);


    @GET("contracts")
    Call<List<ContractResponse>> getAllContract(@Query("access_token") String token, @Query(value = "filter", encoded = true) String filter);
}
