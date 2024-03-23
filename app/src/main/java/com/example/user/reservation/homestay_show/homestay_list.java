package com.example.user.reservation.homestay_show;

/**
 * Created by User on 06-Sep-17.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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


public class homestay_list extends AppCompatActivity {

    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    public static final String URL_PRODUCTS = "http://istay.000webhostapp.com/homestay/member_view_homestay.php";

    //a list to store all the products
    List<Product> homestay_list_content;

    //the recyclerview
    RecyclerView recyclerView;
    TextView emptyview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homestay_list);
        //back button in title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyview = (TextView) findViewById(R.id.empty_view);
        //initializing the productlist
        homestay_list_content = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts();
    }

    private void loadProducts() {

            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                homestay_list_content.add(new Product(
                                        product.getInt("id"),
                                        product.getString("homestay_name"),
                                        product.getString("homestay_address"),
                                        product.getInt("homestay_postcode"),
                                        product.getString("homestay_state"),
                                        product.getString("homestay_city"),
                                        product.getInt("homestay_capacity"),
                                        product.getInt("homestay_room"),
                                        product.getString("homestay_status"),
                                        product.getDouble("homestay_price_perday"),
                                        product.getString("homestay_description"),
                                        product.getString("image_name")

                                ));
                            }
                            if(homestay_list_content.isEmpty())
                            {
                                emptyview.setVisibility(View.VISIBLE);

                            }else{

                                emptyview.setVisibility(View.INVISIBLE);
                                //creating adapter object and setting it to recyclerview
                                homestay_list_adapter adapter = new homestay_list_adapter(homestay_list.this, homestay_list_content);
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
                    Intent intent = new Intent(homestay_list.this,MainActivity.class);
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

    //back title button
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    //back title button end

}