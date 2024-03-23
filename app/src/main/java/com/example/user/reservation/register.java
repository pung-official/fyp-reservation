package com.example.user.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by User on 07-Jun-17.
 */
public class register  extends AppCompatActivity implements View.OnClickListener{

    RadioGroup rbgroupgender;
    RadioButton rbgender;
    public EditText editTextUsername,editTextName,editTextPassword, editTextContact, editTextEmail,editTextConfirmPassword;
    private Button buttonSubmit;
    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    int password_true = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

         awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
       // awesomeValidation = new AwesomeValidation(ValidationStyle.COLORATION);

        //initializing view objects
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextContact = (EditText) findViewById(R.id.editTextContact);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextConfirmPassword =  (EditText) findViewById(R.id.editTextConfirmPassword);

        rbgroupgender = (RadioGroup) findViewById(R.id.rbgroupgender);
        int radiobuttongenderid = rbgroupgender.getCheckedRadioButtonId();
        rbgender = (RadioButton) findViewById(radiobuttongenderid);

        buttonSubmit = (Button) findViewById(R.id.buttonsubmit);



        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.editTextUsername, "[A-Za-z\\\\s]{4,10}$", R.string.letter);
        awesomeValidation.addValidation(this, R.id.editTextName, "^[\\p{L} .'-]+$", R.string.name);
        awesomeValidation.addValidation(this, R.id.editTextEmail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.editTextContact, "^[0-9]{7,11}$", R.string.mobileerror);

        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(this, R.id.editTextPassword, regexPassword, R.string.password_combination);
        awesomeValidation.addValidation(this, R.id.editTextConfirmPassword, R.id.editTextPassword, R.string.err_password_confirmation);
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password_true = 1;
                String result = s.toString().replaceAll(" ", "");
                if (!s.toString().equals(result)) {
                    password_true = 0;
                    editTextPassword.setError("No Space Allowed");
                    // alert the user
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                password_true = 1;
                String result = s.toString().replaceAll(" ", "");
                if (!s.toString().equals(result)) {
                    password_true = 0;
                    editTextPassword.setError("No Space Allowed");
                    // alert the user
                }
            }
        });


        buttonSubmit.setOnClickListener(this);
    }

    private void submitForm() {
            // Toast.makeText(this, "Registration Successfull", Toast.LENGTH_LONG).show();
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();
            String name = editTextName.getText().toString();
            String contact = editTextContact.getText().toString();
            String email = editTextEmail.getText().toString();

            String gender = rbgender.getText().toString();
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(username, password, name, contact, email, gender);
    }

        @Override
        public void onClick(View view) {
            if (view == buttonSubmit) {

                if (password_true == 0 )
                editTextPassword.setError("No Space Allowed");

                else if (awesomeValidation.validate() && password_true == 1) {
                        submitForm();
                    new Timer().schedule(new TimerTask(){
                        public void run() {
                            register.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    startActivity(new Intent(register.this, MainActivity.class));
                                }
                            });
                        }
                    }, 10000);
                    }
                }
              }

            public void buttoncancel(View v) {
               // Intent nextpage = new Intent(register.this, MainActivity.class);
             //   register.this.startActivity(nextpage);
                editTextUsername.setText("");
                editTextUsername.setError(null);

                editTextName.setText("");
                editTextName.setError(null);

                    editTextPassword.setText("");
                    editTextPassword.setError(null);

                    editTextConfirmPassword.setText("");
                    editTextConfirmPassword.setError(null);

                editTextContact.setText("");
                editTextContact.setError(null);

                editTextEmail.setText("");
                editTextEmail.setError(null);
            }
}
