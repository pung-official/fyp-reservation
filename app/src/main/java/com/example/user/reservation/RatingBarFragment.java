package com.example.user.reservation;

/**
 * Created by User on 26-Dec-17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by learnzone.info on 29/3/17.
 */
public class RatingBarFragment extends DialogFragment {
    RatingBar ratingBar;
    Button getRating;
    EditText comment;
    String homestay_id;
    String rating,member_id;

    public static final String URL_PRODUCTS = "http://istay.000webhostapp.com/homestay/insert_rating.php";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating, container, false);
        getDialog().setTitle("Rating the Homestay");

        Bundle bundle = getArguments();
        homestay_id = bundle.getString("homestay_id","");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
         member_id = sharedPreferences.getString(Config.ID_SHARED_PREF,"Not Available");

        comment = (EditText) view.findViewById(R.id.comment);
        ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);
        getRating = (Button) view.findViewById(R.id.get_rating);
        getRating.setOnClickListener(new OnGetRatingClickListener());
        return view;
    }

    private class OnGetRatingClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            //RatingBar$getRating() returns float value, you should cast(convert) it to string to display in a view
             rating = String.valueOf(ratingBar.getRating());
           // Toast.makeText(getActivity(),"Rated "+rating+" stars",Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(),homestay_id,Toast.LENGTH_SHORT).show();
            if(Double.parseDouble(rating) < 0.5)
                Toast.makeText(getActivity(),"Rating cannot be empty",Toast.LENGTH_SHORT).show();
            else
            loadProducts();
        }
    }
   void loadProducts(){

       StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               if(response.contains("success")) {
                   //Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                //dismiss();
                   Activity currentActivity = getActivity();
                  // currentActivity.finish();
                   dismiss();
                   startActivity(currentActivity.getIntent());
               }
               if(response.contains("rated")) {
                   Toast.makeText(getActivity(), "You already gave ratings and comments", Toast.LENGTH_SHORT).show();

               }
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(getActivity(),"failed",Toast.LENGTH_SHORT).show();
           }
       }){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> params = new HashMap<>();
               params.put("rating",rating);
               params.put("comment", comment.getText().toString());
               params.put("homestay_id", homestay_id);
               params.put("member_id", member_id);
               return params;
           }
       };

       Volley.newRequestQueue(getActivity()).add(stringRequest);


    }
}