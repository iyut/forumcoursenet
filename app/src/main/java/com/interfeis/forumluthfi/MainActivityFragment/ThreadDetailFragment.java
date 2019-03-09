package com.interfeis.forumluthfi.MainActivityFragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.telephony.mbms.MbmsErrors;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.interfeis.forumluthfi.R;
import com.interfeis.forumluthfi.settings.General;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
public class ThreadDetailFragment extends Fragment {

    TextView tvThreadTitle;
    TextView tvThreadContent;
    RecyclerView rvThreadList;
    SharedPreferences data_app;

    TextInputEditText txtComment;

    String threadID;

    Button btnComment;

    public ThreadDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View infView = inflater.inflate(R.layout.fragment_thread_detail, container, false);

        data_app = getActivity().getSharedPreferences("DATA_APP", Context.MODE_PRIVATE);
        rvThreadList = (RecyclerView) infView.findViewById(R.id.rvthreadlist);

        txtComment = (TextInputEditText) infView.findViewById(R.id.txtcomment);
        btnComment = (Button) infView.findViewById(R.id.btncomment);

        getThreadDetail(infView);

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCommentOnClicked(v);
            }
        });

        return infView;
    }

    public void getThreadDetail(final View infView) {

        tvThreadContent = (TextView) infView.findViewById(R.id.tvThreadContent);
        tvThreadTitle = (TextView) infView.findViewById(R.id.tvThreadTitle);

        threadID = getArguments().getString("threadID");

        OkHttpClient okHC = new OkHttpClient();

        Request okReq = new Request.Builder()
                .get()
                .url(General.get_url_server() + "get_thread_detail.php?id=" + threadID)
                .build();

        final ProgressDialog pd = new ProgressDialog(getActivity());

        pd.setTitle(getString(R.string.loading));
        pd.setMessage(getString(R.string.please_wait));
        pd.setCancelable(false);

        pd.show();

        okHC.newCall(okReq).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        Snackbar.make(infView, getString(R.string.cannot_connect_server), Snackbar.LENGTH_LONG);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String respString = response.body().string();

                try {
                    JSONObject respJSON = new JSONObject(respString);

                    boolean isResultOK = respJSON.getBoolean("result");
                    String m = respJSON.getString("message");
                    if (isResultOK) {

                        JSONObject data = respJSON.getJSONObject("data");

                        tvThreadTitle.setText(data.getString("title"));
                        tvThreadContent.setText(data.getString("content"));

                        Snackbar.make(infView, m, Snackbar.LENGTH_LONG).show();
                        pd.dismiss();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void btnCommentOnClicked(final View infView) {

        String strEmail = (String) data_app.getString("data_email", getString(R.string.user));

        threadID = getArguments().getString("threadID");

        String strComment = txtComment.getText().toString();

        if (strComment.isEmpty()) {

            txtComment.setError(getString(R.string.comment_required));

        } else {

            OkHttpClient okHC = new OkHttpClient();

            RequestBody okReqBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("txtcomment", strComment)
                    .addFormDataPart("txtauthor", strEmail)
                    .addFormDataPart("threadid", threadID)
                    .build();

            Request okReq = new Request.Builder()
                    .post(okReqBody)
                    .url(General.get_url_server() + "proses_comment.php")
                    .build();

            final ProgressDialog pd = new ProgressDialog(getActivity());

            pd.setTitle(getString(R.string.loading));
            pd.setMessage(getString(R.string.please_wait));
            pd.setCancelable(false);

            pd.show();

            okHC.newCall(okReq).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            Snackbar.make(infView, getString(R.string.cannot_connect_server), Snackbar.LENGTH_LONG);
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    final String respString = response.body().string();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                JSONObject respJSON = new JSONObject(respString);

                                boolean isResultOK = respJSON.getBoolean("result");
                                String m = respJSON.getString("message");

                                if (isResultOK) {


                                }

                                Snackbar.make(infView, m, Snackbar.LENGTH_LONG).show();
                                pd.dismiss();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("ThreadDetailFragment Button Comment", e.getMessage());
                            }
                        }
                    });

                }
            });
        }

    }

}
