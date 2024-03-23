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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 16-Sep-17.
 */

public class make_booking3 extends AppCompatActivity {
    RequestQueue mRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_booking3);
        mRequestQueue = Volley.newRequestQueue(make_booking3.this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String member_id = sharedPreferences.getString(Config.ID_SHARED_PREF,"Not Available");

        final Intent i = getIntent();
        final String total = i.getStringExtra("total");
        final String num_day = i.getStringExtra("num_day");
        final String checkin = i.getStringExtra("Checkin_date");
        final String checkout = i.getStringExtra("Checkout_date");
        final String homestay_name = i.getStringExtra("homestay_name");
        final String homestay_address = i.getStringExtra("homestay_address");
        final String homestay_id = i.getStringExtra("homestay_id");
        final String input_date1 = i.getStringExtra("input_date1");
        final String input_date2 = i.getStringExtra("input_date2");

        Button buttonBook = (Button) findViewById(R.id.buttonBook);

        final TextView name = (TextView) findViewById(R.id.name);
        final TextView night = (TextView) findViewById(R.id.night);
        final TextView price = (TextView) findViewById(R.id.price);
        final TextView checkin_date = (TextView) findViewById(R.id.checkinDate);
        final TextView checkout_date = (TextView) findViewById(R.id.checkoutDate);
        final TextView address = (TextView) findViewById(R.id.address);


        name.setText(homestay_name);
        night.setText(num_day);
        price.setText("RM "+total);
        checkin_date.setText(input_date1);
        checkout_date.setText(input_date2);
        address.setText(homestay_address);

        buttonBook.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               //Intent i = new Intent(make_booking3.this,bottomlayoutframe.class);
               //startActivity(i);
            //   Toast.makeText(getApplicationContext(), num_day+homestay_id, Toast.LENGTH_SHORT).show();


                StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://istay.000webhostapp.com/homestay/insert_booking.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("fail")) {
                            Toast.makeText(getApplicationContext(), "Fail to insert", Toast.LENGTH_SHORT).show();
                        }
                        if(response.contains("success")) {
                            Intent i = new Intent(make_booking3.this,bottomlayoutframe.class);
                            startActivity(i);

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Connection Error",Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("member_id", member_id);
                        params.put("total", total);
                        params.put("num_day", num_day);
                        params.put("checkin", checkin);
                        params.put("checkout", checkout);
                        params.put("homestay_id", homestay_id);
                        return params;
                    }
                };

                mRequestQueue.add(stringRequest);
            }

        });
    }

    //back button in title
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
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
                        Intent intent = new Intent(make_booking3.this,MainActivity.class);
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

}
