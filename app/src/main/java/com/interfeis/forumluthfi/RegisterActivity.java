package com.interfeis.forumluthfi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void btnBacktologinClicked(View view) {

        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }

    public void btnSubmitClicked(final View view) {
        final TextInputEditText txtFullname = (TextInputEditText) findViewById(R.id.txtFullname);

        final TextInputEditText txtUsername = (TextInputEditText) findViewById(R.id.txtUsername);
        final TextInputEditText txtPassword = (TextInputEditText) findViewById(R.id.txtPassword);

        String strFullname = txtFullname.getText().toString();
        String strUsername = txtUsername.getText().toString();
        String strPassword = txtPassword.getText().toString();

        if (strFullname.length() == 0) {

            txtFullname.setError(getString(R.string.full_name_required));

        } else if (strFullname.length() < 4) {

            txtFullname.setError(getString(R.string.full_name_must_longer));

        } else if (strUsername.length() == 0) {

            txtUsername.setError(getString(R.string.email_required));

        } else if (!Helper.isEmailValid(strUsername)) {

            txtUsername.setError(getString(R.string.email_not_valid));

        } else if (strPassword.length() == 0) {

            txtPassword.setError(getString(R.string.password_required));

        } else {


            OkHttpClient okHC = new OkHttpClient();

            RequestBody reqBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("name", strFullname)
                    .addFormDataPart("email", strUsername)
                    .addFormDataPart("password", strPassword)
                    .build();

            Request reqPost = new Request.Builder()
                    .post(reqBody)
                    .url(General.get_url_server() + "proses_register.php")
                    .build();

            final ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
            pd.setTitle(getString(R.string.loading));
            pd.setMessage(getString(R.string.please_wait));
            pd.setCancelable(false);
            pd.setIcon(R.drawable.ic_email_afafaf_24dp);

            okHC.newCall(reqPost).enqueue(new Callback() {
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

                    final String respResult = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject resultJSON = new JSONObject(respResult);

                                boolean r = resultJSON.getBoolean("result");

                                if (r == true) {

                                    pd.dismiss();

                                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                    i.putExtra("successmessage", "You are registered now");

                                    startActivity(i);

                                    finish();

                                } else {

                                    String f = resultJSON.getString("field");
                                    String m = resultJSON.getString("message");

                                    if (f.equals("name")) {

                                        txtFullname.requestFocus();
                                        txtFullname.setError(m);

                                    } else if (f.equals("email")) {

                                        txtUsername.requestFocus();
                                        txtUsername.setError(m);

                                    } else if (f.equals("password")) {

                                        txtPassword.requestFocus();
                                        txtPassword.setError(m);

                                    }

                                    pd.dismiss();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            });
        }
    }
}
