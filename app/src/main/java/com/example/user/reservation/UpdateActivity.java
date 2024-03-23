
package com.example.user.reservation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by User on 13-Jul-17.
 */

public class UpdateActivity  extends AppCompatActivity implements View.OnClickListener {

    RequestQueue rq;
    EditText usernameText,nameText,contactText,emailText;
    TextView editPassword,editImage;
    RadioGroup rbgroupgender;
    RadioButton checkMale,checkFemale;
    Button buttonUpdate,buttonCancel;
    String username,name,contact, email, gender,id,gg,image_url;

    String url = "http://istay.000webhostapp.com/homestay/show.php?id=";
    String url_last ;
    int password_true = 1 ;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //back button in title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rq = Volley.newRequestQueue(this);

        //Fetching email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        id = sharedPreferences.getString(Config.ID_SHARED_PREF,"Not Available");
        url_last = url+id;


       // editImage = (TextView) findViewById(R.id.changePicture);
       // editPassword = (TextView) findViewById(R.id.changePassword);

        usernameText = (EditText) findViewById(R.id.Username);
        nameText = (EditText) findViewById(R.id.Name);

        contactText = (EditText) findViewById(R.id.Contact);
        emailText = (EditText) findViewById(R.id.Email);

        rbgroupgender = (RadioGroup) findViewById(R.id.rbgroupgender);
        checkMale = (RadioButton) findViewById(R.id.radioMale);
        checkFemale = (RadioButton) findViewById(R.id.radioFemale);

        sendjsonrequest();

/*
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getApplicationContext(),update_image.class);
                Intent.putExtra("image_url", image_url);
                startActivity(Intent);
            }
        });


        editPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getApplicationContext(),change_password.class);
                startActivity(Intent);
            }
        }); */

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(this);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.Username, "[A-Za-z\\\\s]{4,10}$", R.string.letter);
        awesomeValidation.addValidation(this, R.id.Name, "^[\\p{L} .'-]+$", R.string.name);
        awesomeValidation.addValidation(this, R.id.Email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.Contact, "^[0-9]{7,11}$", R.string.mobileerror);

        /*
       String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(this, Password, regexPassword, R.string.password_combination);
       awesomeValidation.addValidation(this, R.id.Password2, R.id.Password, R.string.err_password_confirmation);

        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password_true = 1;
                String result = s.toString().replaceAll(" ", "");
                if (!s.toString().equals(result)) {
                    password_true = 0;
                    passwordText.setError("No Space Allowed");
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
                    passwordText.setError("No Space Allowed");
                    // alert the user
                }
            }
        });*/
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    public void sendjsonrequest(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_last, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    username = response.getString("username");
                    name = response.getString("name");
                    contact = response.getString("contact");
                    gender = response.getString("gender");
                    email = response.getString("email");

                    image_url =  response.getString("image");


                    usernameText.setText(username);
                    nameText.setText(name);
                    contactText.setText(contact);
                    emailText.setText(email);

                    if(gender.equals("Female"))
                        checkFemale.setChecked(true);
                    else
                        checkMale.setChecked(true);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        rq.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonUpdate) {
            submitForm();
        }
    }

    public void submitForm (){
         if (awesomeValidation.validate()) {

              Update backgroundWorker = new Update(this);
            String afterusername,afterpassword,aftername,aftercontact,afteremail;

                afterusername = usernameText.getText().toString();
                aftername = nameText.getText().toString();
                aftercontact = contactText.getText().toString();
                afteremail = emailText.getText().toString();

            if (checkMale.isChecked()) {
                gg = "Male";
            }
            else {
                gg = "Female";
            }
              backgroundWorker.execute( afterusername, aftername, aftercontact,afteremail, gg,id);
            new Timer().schedule(new TimerTask(){
                public void run() {
                    UpdateActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            startActivity(new Intent(UpdateActivity.this, ProfileActivity.class));
                        }
                    });
                }
            }, 2000);

        }

    }

}
