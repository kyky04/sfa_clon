package com.pertaminalubricants.mysfa.rest;

import com.pertaminalubricants.mysfa.model.LoginInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nunu on 10/30/2016.
 */

public interface LoginService {
    @FormUrlEncoded
    @POST("users/login")
    Call<LoginInfo> basicLogin(@Field("username") String username, @Field("password") String password);



}
