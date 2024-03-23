package com.example.user.reservation.homestay_show;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.user.reservation.Config;
import com.example.user.reservation.MainActivity;
import com.example.user.reservation.R;
import com.example.user.reservation.announcement_folder.announcement;
import com.example.user.reservation.make_booking2;
import com.example.user.reservation.package_folder.package_list;
import com.example.user.reservation.rating;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by User on 16-Sep-17.
 */

public class make_booking extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    private ImageView image1;

    //imageslider
    TextView textView;
    SliderLayout sliderLayout;
    HashMap<String,String> Hash_file_maps ;
    RequestQueue requestQueue;
    //imageslider

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_booking);
        requestQueue = Volley.newRequestQueue(this);


        //title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button buttonBook = (Button) findViewById(R.id.buttonBook);




        final Intent intent = getIntent();
         final String homestay_id = intent.getStringExtra("homestay_id");
        final String homestay_name = intent.getStringExtra("homestay_name");
         final String homestay_price_perday = intent.getStringExtra("homestay_price_perday");
        final String homestay_address = intent.getStringExtra("homestay_address");
        String homestay_postcode = intent.getStringExtra("homestay_postcode");
        String homestay_state = intent.getStringExtra("homestay_state");
        String homestay_city = intent.getStringExtra("homestay_city");
        String homestay_capacity = intent.getStringExtra("homestay_capacity");
        String homestay_room = intent.getStringExtra("homestay_room");
        String homestay_status = intent.getStringExtra("homestay_status");
        String homestay_description = intent.getStringExtra("homestay_description");

        TextView textView_description = (TextView) findViewById(R.id.description);
        TextView textView_name = (TextView) findViewById(R.id.name);
        TextView textView_price = (TextView) findViewById(R.id.price);
        TextView textView_capacity = (TextView) findViewById(R.id.capcity);
        TextView textView_room = (TextView) findViewById(R.id.room);
        TextView textView_status = (TextView) findViewById(R.id.status);
        TextView textView_postcode = (TextView) findViewById(R.id.postcode);
        TextView textView_city = (TextView) findViewById(R.id.city);
        TextView textView_state = (TextView) findViewById(R.id.state);
        TextView textView_address = (TextView) findViewById(R.id.address);
        // Toast.makeText(this, homestay_room, Toast.LENGTH_SHORT).show();

        textView_description.setText(homestay_description);
        textView_name.setText(homestay_name);
        textView_price.setText("RM "+homestay_price_perday);
        textView_capacity.setText(homestay_capacity);
        textView_room.setText(homestay_room);
        textView_status.setText(homestay_status);
        textView_postcode.setText(homestay_postcode);
        textView_city.setText(homestay_city);
        textView_state.setText(homestay_state);
        textView_address.setText(homestay_address);


        if(homestay_status.equals("Under Renovation")) {
            buttonBook.setVisibility(View.INVISIBLE);
        }

        buttonBook.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), make_booking2.class);
                i.putExtra("homestay_price_perday",homestay_price_perday);
                i.putExtra("homestay_id",homestay_id);
                i.putExtra("homestay_name",homestay_name);
                i.putExtra("homestay_address",homestay_address);
                startActivity(i);
            }

        });

        final TextView rating_click = (TextView) findViewById(R.id.rating);
        rating_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),rating.class);
                intent.putExtra("homestay_id",homestay_id);
                startActivity(intent);
            }
        });


        final TextView announcement_click = (TextView) findViewById(R.id.announcement);
        announcement_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),announcement.class);
                intent.putExtra("homestay_id",homestay_id);
                startActivity(intent);
            }
        });

        final TextView package_click = (TextView) findViewById(R.id.list_package);
        package_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), package_list.class);
                intent.putExtra("homestay_id",homestay_id);
                startActivity(intent);
            }
        });

        //imageslider
        sliderLayout = (SliderLayout)findViewById(R.id.slider);

        requestQueue = Volley.newRequestQueue(this);
        String url = "http://istay.000webhostapp.com/homestay/fetch_homestay_image.php?homestay_id="+Integer.parseInt(homestay_id);
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Hash_file_maps = new HashMap<String, String>();
                        for(int i = 0; i < jsonArray.length(); i++) {
                            try {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String image_name = jsonObject.getString("image_name");
                                String image_name2 = "http://istay.000webhostapp.com/owner/"+image_name;
                                String image_id = jsonObject.getString("image_id");

                                Hash_file_maps.put(image_name,image_name2);

                            }
                            catch(JSONException e) {

                            }

                        }



                        for(String name : Hash_file_maps.keySet()){
                            //TextSliderView
                            DefaultSliderView textSliderView = new DefaultSliderView (make_booking.this);
                            textSliderView
                                    .description(name)
                                    .image(Hash_file_maps.get(name))
                                    .setScaleType(BaseSliderView.ScaleType.Fit)
                                    .setOnSliderClickListener(make_booking.this);
                            textSliderView.bundle(new Bundle());
                            textSliderView.getBundle()
                                    .putString("extra",name);
                            sliderLayout.addSlider(textSliderView);
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(make_booking.this, "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        requestQueue.add(request);
        //volley end

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(this);


    }

    //back button in title
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    //imageslider
    @Override
    protected void onStop() {

        sliderLayout.stopAutoCycle();

        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {

        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}
//imageslider



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
                        Intent intent = new Intent(make_booking.this,MainActivity.class);
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
