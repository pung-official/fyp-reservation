package com.example.user.reservation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 09-Jul-17.
 */

public class ProfileActivity extends AppCompatActivity {

    RequestQueue rq;
    TextView usernameText,nameText,contactText,emailText,genderText;
    String username,name,contact, email, gender;
    String url = "http://istay.000webhostapp.com/homestay/profile.php?id=";
    String url_last ;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //Fetching email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String id = sharedPreferences.getString(Config.ID_SHARED_PREF,"Not Available");
        url_last = url+id;

        rq = Volley.newRequestQueue(this);
        usernameText = (TextView) findViewById(R.id.Username);
        nameText = (TextView) findViewById(R.id.Name);
        contactText = (TextView) findViewById(R.id.Contact);
        emailText = (TextView) findViewById(R.id.Email);
        genderText= (TextView) findViewById(R.id.Gender);
        image = (ImageView) findViewById(R.id.profile_image);

        sendjsonrequest();

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

                    String img_url = response.getString("image");

                    usernameText.setText(": "+username);
                    nameText.setText(": "+name);
                    contactText.setText(": "+contact);
                    emailText.setText(": "+email);
                    genderText.setText(": "+gender);

                    //set  image
                    if (!img_url.equalsIgnoreCase(""))
                        Picasso.with(getApplicationContext())
                                .load(img_url)
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .placeholder(R.drawable.loading_icon)// Place holder image from drawable folder
                                .error(R.drawable.profile)
                                .into(image);
                    //set image end


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

    //Logout function
    private void logout(){
        //Creating an alert dialog to confirm logoutz
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config.EMAIL_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuLogout) {
            //calling logout method when the logout button is clicked
            logout();
        }
        return super.onOptionsItemSelected(item);
    }
    //logout end

    //back button in title
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    }
