package com.example.user.reservation.announcement_folder;

/**
 * Created by User on 07-Dec-17.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.reservation.Config;
import com.example.user.reservation.MainActivity;
import com.example.user.reservation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 19-Sep-17.
 */

/**
 * Created by User on 09-Jul-17.
 */

public class announcement extends AppCompatActivity {

     String homestay_id;
    //a list to store all the products
    List<announcement_content> announcementList;

    //the recyclerview
    RecyclerView recyclerView;
    TextView emptyview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcement_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyview = (TextView) findViewById(R.id.empty_view);

        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.custom_divider));
        recyclerView.addItemDecoration(divider);
        //initializing the productlist
        announcementList = new ArrayList<>();


        final Intent intent = getIntent();
        homestay_id = intent.getStringExtra("homestay_id");
        //this method will fetch and parse json
        //to display it in recyclerview
        loadAnnouncement();

    }
    private void loadAnnouncement() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://istay.000webhostapp.com/homestay/view_announcement.php?homestay_id="+Integer.parseInt(homestay_id),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject Object = array.getJSONObject(i);

                                //adding the product to product list
                                announcementList.add(new announcement_content(
                                        Object.getString("announcement_date"),
                                        Object.getString("announcement_description"),
                                        Object.getString("announcement_title")

                                ));
                            }
                            if(announcementList.isEmpty())
                            {
                                emptyview.setVisibility(View.VISIBLE);

                            }else{

                                emptyview.setVisibility(View.INVISIBLE);
                                //creating adapter object and setting it to recyclerview
                                announcement_adapter adapter = new announcement_adapter(announcement.this, announcementList);
                                recyclerView.setAdapter(adapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
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
                        Intent intent = new Intent(announcement.this,MainActivity.class);
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
