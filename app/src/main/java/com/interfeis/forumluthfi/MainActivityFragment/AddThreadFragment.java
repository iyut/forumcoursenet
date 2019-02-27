package com.interfeis.forumluthfi.MainActivityFragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.interfeis.forumluthfi.R;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class AddThreadFragment extends Fragment {


    TextInputEditText txtTitle;
    EditText txtContent;
    Button btnSubmit;
    public AddThreadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View infView = inflater.inflate(R.layout.fragment_add_thread, container, false);

        txtTitle    = (TextInputEditText) infView.findViewById( R.id.txttitle );
        txtContent  = (EditText) infView.findViewById( R.id.txtcontent );
        btnSubmit   = (Button) infView.findViewById( R.id.btnsubmit );

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return infView;
    }

    public void btnSubmitOnClicked(final View view ){

        String strTitle     = txtTitle.getText().toString();
        String strContent   = txtContent.getText().toString();

        if( strTitle.length() == 0 ){

            txtTitle.setError(getString(R.string.title_required));
            txtTitle.requestFocus();

        } else if( strContent.length() == 0 ){

            txtContent.setError( getString(R.string.content_required) );
            txtContent.requestFocus();

        } else {

            OkHttpClient okHC       = new OkHttpClient();

            RequestBody okReqBody   = new MultipartBody.Builder()
                    .addFormDataPart("txttitle", strTitle )
                    .addFormDataPart("txtcontent", strContent )
                    .setType( MultipartBody.FORM )
                    .build();

            Request okReq           = new Request.Builder()
                    .url(General.get_url_server() + "proses_thread.php" )
                    .post( okReqBody )
                    .build();

            final ProgressDialog pd = new ProgressDialog( getActivity() );
            pd.setCancelable( false );
            pd.setTitle( getString( R.string.loading ));
            pd.setMessage( getString( R.string.please_wait ));
            pd.show();

            okHC.newCall( okReq ).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            pd.dismiss();
                            Snackbar.make( view, getString(R.string.cannot_connect_server), Snackbar.LENGTH_LONG ).show();

                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    final String strResponse = response.body().string();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                JSONObject objResponse = new JSONObject( strResponse );

                                boolean is_result_ok    = objResponse.getBoolean( "result");
                                String message          = objResponse.getString("message");
                                String field            = objResponse.getString( "field");

                                if( is_result_ok == false ){

                                    if( field.equals("title") ) {

                                        txtTitle.setError( message );
                                        txtTitle.requestFocus();

                                    } else if( field.equals("content") ) {

                                        txtContent.setError( message );
                                        txtContent.requestFocus();

                                    }
                                } else {

                                    Snackbar.make(view, message, Snackbar.LENGTH_LONG);

                                }

                                pd.dismiss();


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
