package com.example.user.reservation;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.reservation.homestay_show.Product;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 10-Jun-17.
 */

public class search extends Fragment {
    public String Checkin_date = "";
    public int cin_date;
    public String Checkout_date = "",state;
    public int cout_date ;
    String input_date1,input_date2;
    String dom,moy;
    String dom2,moy2;
    NumberFormat mFormat = new DecimalFormat("00");
    TextView checkinText,checkoutText,day;
    EditText editTextName,editTextState,editTextCapacity;
    Button buttonSearch ;
    private static final String[]paths = {"All States","Johor", "Melaka", "Penang","Negeri Sembilan","Selangor","Pahang","Perak","Terengganu","Kelantan","Kedah","Perlis"};

    //a list to store all the products
    List<Product> homestay_list_content;
    //the recyclerview
    RecyclerView recyclerView;
  //  RequestQueue stringRequest;

    public static search newInstance() {
        search fragment = new search();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        getActivity().setTitle("Search");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search, container, false);

        //initializing the productlist
        homestay_list_content = new ArrayList<>();
       // stringRequest = Volley.newRequestQueue(getActivity());

        Button checkinButton = (Button) view.findViewById(R.id.checkinButton);
        Button checkoutButton = (Button) view.findViewById(R.id.checkoutButton);

        //spinner
       Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                state = item;
                if(state == "All States")
                    state = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                state="";
            }
        });
        //spinner

          checkinText = (TextView) view.findViewById(R.id.textViewCheckin);
          checkoutText = (TextView) view.findViewById(R.id.textViewCheckout);
          day = (TextView) view.findViewById(R.id.textViewDay);

         editTextName = (EditText) view.findViewById(R.id.editTextName);
       //  editTextState = (EditText) view.findViewById(R.id.editTextState);
         editTextCapacity = (EditText) view.findViewById(R.id.editTextCpacity);
         buttonSearch = (Button) view.findViewById(R.id.buttonSearch);

//toast
        final View customToastroot =inflater.inflate(R.layout.mycustom_toast, null);
        final Toast customtoast=new Toast(getActivity());

        customtoast.setView(customToastroot);
        customtoast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0, 0);
        customtoast.setDuration(Toast.LENGTH_LONG);
//toast

        checkinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear+1;
                        /*
                        String date = String.valueOf(year) +"-"+String.valueOf(monthOfYear)
                                +"-"+String.valueOf(dayOfMonth);
                        checkinText.setText(date);*/
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
                        checkinText.setText(input_date1);

                        Checkin_date = String.valueOf(year)+moy+dom;
                        cin_date =Integer.parseInt(Checkin_date);

                        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

                        if(Checkout_date.toString().length() != 0 ) {
                            try {
                                //total days
                                Date date1 = myFormat.parse(input_date1);
                                Date date2 = myFormat.parse(input_date2);
                                long diff = date2.getTime() - date1.getTime();
                                day.setText((Long.toString(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)))+" Night(s)");
                                //total days

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, yy, mm, dd);
                datePicker.show();
            }});

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

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

                        checkoutText.setText(input_date2);


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
                                day.setText(Long.toString(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)) +" Night(s)");
                                //total days

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, yy, mm, dd);
                datePicker.show();
            }});

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Checkin_date.toString().length() == 0 || Checkout_date.toString().length() == 0  )
                {
                    TextView text = (TextView) customToastroot.findViewById(R.id.textView1);
                    text.setText("The Checkin Date and Checkout Date cannot be null");
                    customtoast.show();
                }
                else  if(cout_date <= cin_date)
                {
                    TextView text = (TextView) customToastroot.findViewById(R.id.textView1);
                    text.setText("The Checkout Date must be greater than the checkin date");
                    customtoast.show();
                }
                else{
                Intent i = new Intent(getActivity(), search_homestay_list.class);
                i.putExtra("name",editTextName.getText().toString().trim());
                i.putExtra("state",state.trim());
                i.putExtra("capacity",editTextCapacity.getText().toString().trim());
                i.putExtra("Checkin_date",Checkin_date.trim());
                i.putExtra("Checkout_date",Checkout_date.trim());
                startActivity(i);
                }
             //   Toast.makeText(getActivity(), "Testing 123", Toast.LENGTH_LONG).show();
            }});

        return view;
    }

    //Logout function
    private void logout(){
        //Creating an alert dialog to confirm logoutz
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config.EMAIL_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(search.this.getActivity(),MainActivity.class);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu,inflater);

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
