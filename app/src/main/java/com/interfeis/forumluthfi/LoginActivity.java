package com.interfeis.forumluthfi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void btnLoginClicked(View view) {
        TextInputEditText txtUsername = (TextInputEditText) findViewById(R.id.txtUsername);
        TextInputEditText txtPassword = (TextInputEditText) findViewById(R.id.txtPassword);

        String strUsername = txtUsername.getText().toString();
        String strPassword = txtPassword.getText().toString();

        if(strUsername.length() == 0){

            txtUsername.setError(getString(R.string.email_required));

        }else if(!Helper.isEmailValid(strUsername)){

            txtUsername.setError(getString(R.string.email_not_valid));

        }else if(strPassword.length() == 0){

            txtPassword.setError(getString(R.string.password_required));

        }else{

            SharedPreferences.Editor data_app = getSharedPreferences("DATA_APP", MODE_PRIVATE).edit();

            data_app.putString("data_email", strUsername);
            data_app.commit();

            Intent i = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(i);
        }
    }
}
