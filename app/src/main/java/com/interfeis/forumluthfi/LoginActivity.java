package com.interfeis.forumluthfi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.interfeis.forumluthfi.settings.General;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    String extrastring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getIntent().hasExtra("successmessage")) {
            extrastring = getIntent().getStringExtra("successmessage");
            if (extrastring.length() > 0) {
                Toast.makeText(getApplicationContext(), extrastring, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void btnLoginClicked(final View view) {
        final TextInputEditText txtUsername = (TextInputEditText) findViewById(R.id.txtUsername);
        final TextInputEditText txtPassword = (TextInputEditText) findViewById(R.id.txtPassword);

        final String strUsername = txtUsername.getText().toString();
        String strPassword = txtPassword.getText().toString();

        if (strUsername.length() == 0) {

            txtUsername.setError(getString(R.string.email_required));

        } else if (!Helper.isEmailValid(strUsername)) {

            txtUsername.setError(getString(R.string.email_not_valid));

        } else if (strPassword.length() == 0) {

            txtPassword.setError(getString(R.string.password_required));

        } else {

            OkHttpClient okHC = new OkHttpClient();

            RequestBody okReqBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("username", strUsername)
                    .addFormDataPart("password", strPassword)
                    .build();

            Request okReq = new Request.Builder()
                    .post(okReqBody)
                    .url(General.get_url_server())
                    .build();

            final ProgressDialog pd = new ProgressDialog(LoginActivity.this);

            pd.setTitle(getString(R.string.loading));
            pd.setMessage(getString(R.string.please_wait));
            pd.setCancelable(false);
           // pd.setIcon(R.drawable.ic_email_afafaf_24dp);

            pd.show();

            okHC.newCall(okReq).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            Snackbar.make(view, getString(R.string.cannot_connect_server), Snackbar.LENGTH_LONG).show();
                        }
                    });

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String respString = response.body().string();

                    try {
                        JSONObject respJSON = new JSONObject(respString);

                        boolean r = respJSON.getBoolean("result");

                        if (r == true) {

                            pd.dismiss();

                            SharedPreferences.Editor data_app = getSharedPreferences("DATA_APP", MODE_PRIVATE).edit();

                            data_app.putString("data_email", strUsername);
                            data_app.commit();

                            Intent i = new Intent( getApplicationContext(), MainActivity.class );

                            startActivity(i);
                            finish();

                        } else {

                            pd.dismiss();

                            String f = respJSON.getString("field");
                            String m = respJSON.getString("message");

                            if (f.equals("username")) {
                                txtUsername.requestFocus();
                                txtUsername.setError(m);
                            } else if (f.equals("password")) {

                                txtPassword.requestFocus();
                                txtPassword.setError(m);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });


        }
    }

    public void btnSignupClicked(View view) {

        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
    }
}
