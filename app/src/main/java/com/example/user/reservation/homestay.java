package com.example.user.reservation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.reservation.homestay_show.homestay_list;
import com.example.user.reservation.homestay_show.homestay_list_melaka;
import com.example.user.reservation.homestay_show.homestay_list_penang;

/**
 * Created by User on 15-Jun-17.
 */
public class homestay extends Fragment {

    String[] states = new String[]{
            "Johor",
            "Melaka",
            "Penang",
            "Negeri Sembilan",
            "Selangor",
            "Pahang",
            "Perak",
            "Terengganu",
            "Kelantan",
            "Kedah",
            "Perlis"

    };

    public static homestay newInstance() {
        homestay fragment = new homestay();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("Homestay Location");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.homestay, container, false);
        ListView lvstate = (ListView) view.findViewById(R.id.lvstate);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,states);


        lvstate.setAdapter(adapter);


        lvstate.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub

                // Toast.makeText(getActivity().getApplicationContext(),"Position: "+position +"Item: "+states[position],Toast.LENGTH_LONG).show();

                if(states[position] == "Johor") {
                    Intent IntentUpdate = new Intent(getActivity(),homestay_list.class);
                    startActivity(IntentUpdate);
                }
                if(states[position] == "Melaka") {
                    Intent IntentUpdate = new Intent(getActivity(),homestay_list_melaka.class);
                    startActivity(IntentUpdate);
                }
                if(states[position] == "Penang") {
                    Intent IntentUpdate = new Intent(getActivity(),homestay_list_penang.class);
                    startActivity(IntentUpdate);
                }

            }
        });

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
                        Intent intent = new Intent(homestay.this.getActivity(),MainActivity.class);
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

