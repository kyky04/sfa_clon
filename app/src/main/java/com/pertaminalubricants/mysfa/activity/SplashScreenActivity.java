package com.pertaminalubricants.mysfa.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.pertaminalubricants.mysfa.R;
import com.pertaminalubricants.mysfa.activity.LoginActivity;
import com.pertaminalubricants.mysfa.library.SessionManager;
import com.pertaminalubricants.mysfa.model.User;
import com.pertaminalubricants.mysfa.rest.ServiceGenerator;
import com.pertaminalubricants.mysfa.rest.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nunu on 10/19/2016.
 */

public class SplashScreenActivity extends AppCompatActivity {
//    EstjavaApiGet api;
    private AlertDialog alertDialog;
    private SessionManager session;

    private ProgressDialog dialog;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        ctx = getBaseContext();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                session = new SessionManager(getBaseContext());
                if (session.isLoggedIn() == true) {
                    setDataUser();
                } else {
                    Intent i;
                    i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }



            }
        }, 1500);
    }

    private void setDataUser(){
        openDialog();
        UserService apiService = ServiceGenerator.createService(UserService.class);
        Call<User> call = apiService.getUser(session.getID().toString(), session.getToken().toString());

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                closeDialog();
                if(response.code() == 200){
                    session.setEmail(response.body().getEmail());
                    session.setStatus(response.body().getStatus());
                    session.setRegion(response.body().getIdRegion());
                    session.setDistributor(response.body().getIdDistributor());

                    Intent i;
                    i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }   else{
                    Intent i;
                    i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                closeDialog();
                // Log error here since request failed
                Intent i;
                i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

//    private void setDataCustomer(){
//        openDialog();
//        UserService apiService = ServiceGenerator.createService(UserService.class);
//        Call<User> call = apiService.getUser(session.getID().toString(), session.getToken().toString());
//
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                closeDialog();
//                if(response.code() == 200){
//                    session.setEmail(response.body().getEmail());
//                    session.setStatus(response.body().getStatus());
//                    session.setRegion(response.body().getIdRegion());
//                    session.setDistributor(response.body().getIdDistributor());
//
//                    Intent i;
//                    i = new Intent(SplashScreenActivity.this, MainActivity.class);
//                    startActivity(i);
//                    finish();
//                }   else{
//                    Intent i;
//                    i = new Intent(SplashScreenActivity.this, LoginActivity.class);
//                    startActivity(i);
//                    finish();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                closeDialog();
//                // Log error here since request failed
//                Intent i;
//                i = new Intent(SplashScreenActivity.this, LoginActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });
//    }

    private void closeDialog(){
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void openDialog(){
        dialog = new ProgressDialog(ctx);
    }

}
