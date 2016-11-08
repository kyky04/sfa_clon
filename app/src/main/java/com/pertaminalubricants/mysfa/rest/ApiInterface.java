package com.pertaminalubricants.mysfa.rest;

/**
 * Created by nunu on 10/28/2016.
 */

import com.pertaminalubricants.mysfa.model.Contributor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributors(
            @Path("owner") String owner,
            @Path("repo") String repo
    );
}
