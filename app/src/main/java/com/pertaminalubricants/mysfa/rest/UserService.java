package com.pertaminalubricants.mysfa.rest;

import com.pertaminalubricants.mysfa.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by nunu on 10/30/2016.
 */

public interface UserService {
    @GET("users/{id}")
    Call<User> getUser(@Path("id") String id, @Query("access_token") String token);

}
