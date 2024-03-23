package com.example.user.reservation;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 16-Sep-17.
 */

public class make_booking2 extends AppCompatActivity {
    public String Checkin_date = "";
    public int cin_date;
    public String Checkout_date = "";
    public int cout_date ;
    String input_date1,input_date2;
    long num_day;
    double total;
    String dom,moy;
    String dom2,moy2;
    NumberFormat mFormat = new DecimalFormat("00");
    final String URL = "http://istay.000webhostapp.com/homestay/check_availability.php";
    RequestQueue mRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_booking2);
        mRequestQueue = Volley.newRequestQueue(make_booking2.this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final TextView Textviewnight = (TextView) findViewById(R.id.nights);
        final TextView Textviewprice = (TextView) findViewById(R.id.price);

        final Intent i = getIntent();
        final String homestay_price_perday = i.getStringExtra("homestay_price_perday");
        final String homestay_id = i.getStringExtra("homestay_id");
        final String homestay_name = i.getStringExtra("homestay_name");
        final String homestay_address = i.getStringExtra("homestay_address");

        final TextView checkin = (TextView) findViewById(R.id.checkin);
        final TextView checkout = (TextView) findViewById(R.id.checkout);

        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(make_booking2.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear+1;

                        if (dayOfMonth < 10) {
                            dom = String.valueOf(mFormat.format(dayOfMonth));
                        }
                        else
                        {
                            dom = String.valueOf(dayOfMonth);
                        }
                        if (monthOfYear < 10) {
                            moy = String.valueOf(mFormat.format(monthOfYear));
                        }
                        else
                        {
                            moy = String.valueOf(monthOfYear);
                        }


                        input_date1 = dom +"/"+moy+"/"+String.valueOf(year) ;
                        checkin.setText(input_date1);

                        Checkin_date = String.valueOf(year)+moy+dom;
                        cin_date =Integer.parseInt(Checkin_date);

                        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

                        if(Checkout_date.toString().length() != 0 ) {
                            try {
                                //total days
                                Date date1 = myFormat.parse(input_date1);
                                Date date2 = myFormat.parse(input_date2);
                                long diff = date2.getTime() - date1.getTime();
                                Textviewnight.setText(Long.toString(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
                                //total days

                               //total price
                                num_day = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                                total = num_day * Double.parseDouble(homestay_price_perday);
                                Textviewprice.setText("RM "+Double.toString(total));
                                //total price
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                }, yy, mm, dd);
                datePicker.show();
                datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            }});

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog datePicker = new DatePickerDialog(make_booking2.this, new DatePickerDialog.OnDateSetListener() {




                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear+1;

                        if (dayOfMonth < 10) {
                            dom2 = String.valueOf(mFormat.format(dayOfMonth));
                        }
                        else
                        {
                            dom2 = String.valueOf(dayOfMonth);
                        }
                        if (monthOfYear < 10) {
                            moy2 = String.valueOf(mFormat.format(monthOfYear));
                        }
                        else
                        {
                            moy2 = String.valueOf(monthOfYear);
                        }


                        input_date2 = dom2+"/"+moy2+"/"+String.valueOf(year) ;

                        checkout.setText(input_date2);


                        Checkout_date = String.valueOf(year)+moy2+dom2 ;
                        cout_date=Integer.parseInt(Checkout_date);



                        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

                        if(Checkin_date.toString().length() != 0 )
                        {
                            try {
                                //total days
                                Date date1 = myFormat.parse(input_date1);
                                Date date2 = myFormat.parse(input_date2);
                                long diff = date2.getTime() - date1.getTime();
                                Textviewnight.setText(Long.toString(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
                                //total days

                                //total price
                                num_day = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                                total = num_day * Double.parseDouble(homestay_price_perday);
                                Textviewprice.setText("RM "+Double.toString(total));
                                //total price

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        //  num_day = cout_date - cin_date;
                       // total =  num_day*Double.parseDouble(homestay_price_perday);
                       // Toast.makeText(getApplicationContext(), cout_date, Toast.LENGTH_LONG).show();
                    // Textviewnight.setText(Integer.toString(num_day));


                    }
                }, yy, mm, dd);
                datePicker.show();
                datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            }});


        Button buttonNext = (Button) findViewById(R.id.buttonNext);

         //toast
        LayoutInflater inflater=getLayoutInflater();
        final View customToastroot =inflater.inflate(R.layout.mycustom_toast, null);
        final Toast customtoast=new Toast(getApplicationContext());

        customtoast.setView(customToastroot);
        customtoast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0, 0);
        customtoast.setDuration(Toast.LENGTH_LONG);
        //toast ends

        buttonNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(Checkin_date.toString().length() == 0 || Checkout_date.toString().length() == 0  )
                {
                    TextView text = (TextView) customToastroot.findViewById(R.id.textView1);
                    text.setText("The Checkin Date and Checkout Date cannot be null");
                    customtoast.show();
                }
                else  if(cout_date <= cin_date)
                {
                    TextView text = (TextView) customToastroot.findViewById(R.id.textView1);
                    text.setText("The Checkout Date must be higher than the checkin date");
                    customtoast.show();
                }

                else {

                    /* Intent i = new Intent(make_booking2.this, make_booking3.class);
                    i.putExtra("homestay_id",homestay_price_perday);
                    startActivity(i);

                    TextView text = (TextView) customToastroot.findViewById(R.id.textView1);
                    text.setText(Double.toString(total));
                    customtoast.show(); */

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.contains("success")) {
                             //   Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(make_booking2.this, make_booking3.class);
                                    i.putExtra("total",Double.toString(total));
                                    i.putExtra("num_day",Long.toString(num_day));
                                    i.putExtra("Checkin_date",Checkin_date);
                                    i.putExtra("Checkout_date",Checkout_date);
                                    i.putExtra("homestay_name",homestay_name);
                                    i.putExtra("homestay_address",homestay_address);
                                    i.putExtra("homestay_id",homestay_id);
                                    i.putExtra("input_date1",input_date1);
                                    i.putExtra("input_date2",input_date2);
                                    startActivity(i);
                            }
                            else if(response.contains("fail")) {
                                Toast.makeText(getApplicationContext(), "The date is not available", Toast.LENGTH_SHORT).show();
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
                            params.put("checkin", Checkin_date);
                            params.put("checkout", Checkout_date);
                            params.put("homestay_id", homestay_id);
                            return params;
                        }
                    };

                    mRequestQueue.add(stringRequest);

                } //else end
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
                        Intent intent = new Intent(make_booking2.this,MainActivity.class);
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
