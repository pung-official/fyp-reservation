package com.example.user.reservation;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by User on 09-Jun-17.
 */

public class profile extends Fragment {

    private TextView textView;

    public static profile newInstance() {
        profile fragment = new profile();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("More");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile, container, false);

        //Initializing textview
        //textView = (TextView) view.findViewById(R.id.textView);
        //Showing the current logged in email to textview
       // textView.setText("Hi: "+username);

        //Fetching email from shared preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");



        final TextView profileLink =(TextView) view.findViewById(R.id.gotoProfile);
        profileLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getActivity(),ProfileActivity.class);
               startActivity(Intent);
            }
        });

        final TextView profileeditLink =(TextView) view.findViewById(R.id.gotoProfileEdit);
        profileeditLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentUpdate = new Intent(getActivity(),UpdateActivity.class);
                startActivity(IntentUpdate);
            }
        });

        final TextView editpictureLink =(TextView) view.findViewById(R.id.gotoEditPicture);
        editpictureLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentUpdate = new Intent(getActivity(),update_image.class);
                startActivity(IntentUpdate);
                /*
                    Intent Intent = new Intent(getApplicationContext(),update_image.class);
                Intent.putExtra("image_url", image_url);
                startActivity(Intent);
                 */
            }
        });

        final TextView editpasswordLink =(TextView) view.findViewById(R.id.gotoEditPassword);
        editpasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentUpdate = new Intent(getActivity(),change_password.class);
                startActivity(IntentUpdate);
            }
        });






        final TextView aboutlink = (TextView) view.findViewById(R.id.gotoAboutUs);
        aboutlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getActivity(),about.class);
                startActivity(Intent);
            }
        });

        final TextView policy = (TextView) view.findViewById(R.id.gotoRule);
        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getActivity(),policy.class);
                startActivity(Intent);
            }
        });
        final TextView facebooklink = (TextView) view.findViewById(R.id.gotoFacebook);
        facebooklink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/coolboyz.pan"));
                startActivity(browserIntent);
            }
        });


        final TextView contactlink = (TextView) view.findViewById(R.id.gotoContact);
        contactlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.setContentView(R.layout.dialogue);
                    TextView txt = (TextView)dialog.findViewById(R.id.textView1);


                String sourceString = "<b>" + "| Service Center |"+"<br><br>"+ "</b>" + "014-2365620";
                txt.setText(Html.fromHtml(sourceString));




                dialog.show();
            }
        });

        final TextView emaillink = (TextView) view.findViewById(R.id.gotoEmail);
       // emaillink.setText(Html.fromHtml("<a href=\"mailto:ask@me.it\" >Send Feedback</a>"));

        String content = "<a href=\"mailto:pungkuisin@gmail.com\" >Send Feedback</a>";
        Spannable s = (Spannable) Html.fromHtml(content);
        for (URLSpan u: s.getSpans(0, s.length(), URLSpan.class)) {
            s.setSpan(new UnderlineSpan() {
                public void updateDrawState(TextPaint tp) {
                    tp.setUnderlineText(false);
                }
            }, s.getSpanStart(u), s.getSpanEnd(u), 0);
        }
        emaillink.setText(s);
        emaillink.setMovementMethod(LinkMovementMethod.getInstance());

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
                        SharedPreferences preferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config.EMAIL_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(profile.this.getActivity(),MainActivity.class);
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
