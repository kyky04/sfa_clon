package com.pertaminalubricants.mysfa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pertaminalubricants.mysfa.R;
import com.pertaminalubricants.mysfa.library.SessionManager;

/**
 * Created by nunu on 10/21/2016.
 */

public class ProfileFragment extends Fragment {

    TextView kodeSales,namaSales,passwordLama,passwordBaru,ulangiPassword;
    FloatingActionButton fab;
    View forsnack;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.change_password,container,false);
        kodeSales  = (TextView)rootview.findViewById(R.id.kode_personal);
        namaSales = (TextView)rootview.findViewById(R.id.nama_personal);
        SessionManager sm = new SessionManager(getActivity());
        kodeSales.setText(sm.getID());
        namaSales.setText(sm.getFullname());
        kodeSales.setEnabled(false);
        namaSales.setEnabled(false);


        passwordLama = (TextView)rootview.findViewById(R.id.password_lama);
        passwordBaru = (TextView)rootview.findViewById(R.id.password_baru);
        ulangiPassword = (TextView)rootview.findViewById(R.id.password_ulangi);

        fab = (FloatingActionButton)rootview.findViewById(R.id.button_submit_password);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cekValid()){
//                    doChangePassword();
                }
            }
        });
        forsnack = rootview;
        return rootview;
    }

    public boolean cekValid(){
        if (passwordBaru.getText().toString().length() < 6){
            Snackbar.make(forsnack,"Minimal Pasword 6 Karakter", Snackbar.LENGTH_SHORT).show();
            return false;
        }else if (!ulangiPassword.getText().toString().equals(passwordBaru.getText().toString())){
            Snackbar.make(forsnack,"Ulangi Password Harus sama dengan Password Baru", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

//    private static String URL = LoginActivity.SERVER_HOST +"api-change-password";
//    public void doChangePassword(){
//        final String usText = this.kodeSales.getText().toString();
//        final String pasText = this.passwordLama.getText().toString();
//        final String pasNew = this.passwordBaru.getText().toString();
//
//        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Authentication...", "Please wait...", false, false);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String s) {
//                        try {
//                            JSONObject S = new JSONObject(s);
//                            String status = S.getString("message");
//                            Snackbar.make(forsnack,status,Snackbar.LENGTH_SHORT).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        loading.dismiss();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        loading.dismiss();
//                        Snackbar.make(forsnack, "Permasalahan Jaringan ", Snackbar.LENGTH_SHORT).show();
//                    }
//                }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new Hashtable<String, String>();
//                params.put("id_personal", usText);
//                params.put("password",pasNew);
//                params.put("password_lama",pasText);
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        requestQueue.add(stringRequest);
//    }
}
