package com.example.user.reservation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.Random;

/**
 * Created by User on 01-Sep-17.
 */

public class forgetpass extends Activity {
    String id = null;
    Random random = new Random();
    Button buttonrequest,buttonverify;
    TextView setTime ;
    public EditText editTextEmail,editTextCode;
    String subject="This is From Istay.";
    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        // awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);



        buttonrequest = (Button) findViewById(R.id.buttonsubmit);
        buttonverify = (Button) findViewById(R.id.buttonverify);

        setTime= (TextView) findViewById(R.id.guideline);
        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextCode = (EditText) findViewById(R.id.editText_code);

       editTextCode.setVisibility(TextView.INVISIBLE);
       buttonverify.setVisibility(TextView.INVISIBLE);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.editText_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);


        buttonrequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    id = String.format("%04d", random.nextInt(10000));
                    String message = "Your authentication code is: "+id;
                    //send email
                    if (awesomeValidation.validate()) {

                        editTextCode.setVisibility(TextView.VISIBLE);
                        buttonverify.setVisibility(TextView.VISIBLE);


                        //Creating SendMail object
                        SendMail sm = new SendMail(forgetpass.this, editTextEmail.getText().toString(), subject, message);
                        //Executing sendmail to send email
                        sm.execute();
                        //send email

                        //button
                        buttonrequest.setEnabled(false);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //start your service here
                                buttonrequest.setEnabled(true);

                            }
                        }, 50000);
                        //button ends

                        // timer
                        new CountDownTimer(50000, 1000) {
                            int time = 50;

                            public void onTick(long millisUntilFinished) {
                                setTime.setText("The code can be sent in " + time + " seconds");
                                time--;
                            }

                            public void onFinish() {
                                setTime.setText("Code can is ready to send");
                            }

                        }.start();
                        // timer
                    }


                } //button  onlick listener
            });



        buttonverify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (id.equals(editTextCode.getText().toString())) {

                    Intent Intent = new Intent(getApplicationContext(),password_reset.class);
                    Intent.putExtra("email", editTextEmail.getText().toString());
                    startActivity(Intent);
                }
                else if(editTextCode.getText().toString().matches("")) {
                    Toast.makeText(forgetpass.this,"Code should not be blank", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(forgetpass.this,"Code is not correct", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}
