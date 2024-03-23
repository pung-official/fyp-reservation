package com.example.user.reservation;

/**
 * Created by User on 28-Jan-18.
 */


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 13-Jul-17.
 */

public class change_password  extends AppCompatActivity  {

    RequestQueue rq;
    EditText old_password, new_password1, new_password2;
    Button buttonUpdate;
    String  id;


    String url_last = "http://istay.000webhostapp.com/homestay/edit_password.php";
    int password_true = 1;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        rq = Volley.newRequestQueue(change_password.this);

        //back button in title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rq = Volley.newRequestQueue(this);

        //Fetching email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        id = sharedPreferences.getString(Config.ID_SHARED_PREF, "Not Available");



        old_password = (EditText) findViewById(R.id.oldpassword);
        new_password1 = (EditText) findViewById(R.id.newpassword1);
        new_password2 = (EditText) findViewById(R.id.newpassword2);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);

       awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(this, R.id.newpassword1, regexPassword, R.string.password_combination);
        awesomeValidation.addValidation(this, R.id.newpassword2, R.id.newpassword1, R.string.err_password_confirmation);

        new_password1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password_true = 1;
                String result = s.toString().replaceAll(" ", "");
                if (!s.toString().equals(result)) {
                    password_true = 0;
                    new_password1.setError("No Space Allowed");
                    // alert the user
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                password_true = 1;
                String result = s.toString().replaceAll(" ", "");
                if (!s.toString().equals(result)) {
                    password_true = 0;
                    new_password1.setError("No Space Allowed");
                    // alert the user
                }
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {
                    if (password_true == 1) {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_last, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.contains("success")) {
                                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();

                                } else if (response.contains("fail")) {
                                    Toast.makeText(getApplicationContext(), "Original password is wrong !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("old_password", old_password.getText().toString());
                                params.put("new_password1", new_password1.getText().toString());
                                params.put("member_id", id);
                                return params;
                            }
                        };
                        rq.add(stringRequest);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "The password should not consist of space", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}