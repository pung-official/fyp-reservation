package com.example.user.reservation;

/**
 * Created by User on 30-Sep-17.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;


/**
 * Created by User on 16-Sep-17.
 */


public class password_reset extends AppCompatActivity {
    public EditText  password1,password2;
    Button buttonSubmit;
    String email;
    private AwesomeValidation awesomeValidation;
    int password_true = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_reset);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
         email = bundle.getString("email");

        password1 = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(this, R.id.password, regexPassword, R.string.password_combination);
        awesomeValidation.addValidation(this, R.id.password2, R.id.password, R.string.err_password_confirmation);

        password1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password_true = 1;
                String result = s.toString().replaceAll(" ", "");
                if (!s.toString().equals(result)) {
                    password_true = 0;
                    password1.setError("No Space Allowed");
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
                    password1.setError("No Space Allowed");
                    // alert the user
                }
            }
        });

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                    if(password_true == 1) {
                        password_reset2 backgroundWorker = new password_reset2(password_reset.this);
                        backgroundWorker.execute(email, password1.getText().toString());
                    }
                    else
                        Toast.makeText(getApplicationContext(), "The password should not consist of space", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //back button in title
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }



}
