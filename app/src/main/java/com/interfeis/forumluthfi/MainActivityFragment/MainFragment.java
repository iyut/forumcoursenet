package com.interfeis.forumluthfi.MainActivityFragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.interfeis.forumluthfi.MainActivity;
import com.interfeis.forumluthfi.R;
import com.interfeis.forumluthfi.settings.General;
import com.interfeis.forumluthfi.dbstructure.Thread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public FloatingActionButton btnAddThread;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fview = inflater.inflate(R.layout.fragment_main, container, false);
        // Inflate the layout for this fragment

        btnAddThread = (FloatingActionButton) fview.findViewById(R.id.btn_add_thread);

        showSlider(fview);

        showThreadList(fview);

        btnAddThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAddThreadOnClicked(view);
            }
        });

        return fview;
    }

    public void btnAddThreadOnClicked(View view) {

        MainActivity ma = (MainActivity) view.getContext();

        AddThreadFragment atf = new AddThreadFragment();

        ma.openAppFragment(atf);

    }


    public void showSlider(View fview) {

        SliderLayout slMain = (SliderLayout) fview.findViewById(R.id.slmain);

        TextSliderView tsvSlider = new TextSliderView(getContext());

        String name = "testing";
        tsvSlider.description(name)
                .image(R.drawable.logo_forumnet)
                .setScaleType(BaseSliderView.ScaleType.FitCenterCrop)
                .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {

                    }
                });

        tsvSlider.bundle(new Bundle());
        tsvSlider.getBundle()
                .putString("extra", name);

        slMain.addSlider(tsvSlider);
        // slMain.addSlider( tsvSlider );

        slMain.setPresetTransformer(SliderLayout.Transformer.Accordion);
        slMain.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slMain.setCustomAnimation(new DescriptionAnimation());
        slMain.setDuration(4000);

        slMain.stopAutoCycle();

    }

    public void showThreadList(final View fview) {


        final RecyclerView rvList = (RecyclerView) fview.findViewById(R.id.rvlist);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        rvList.setLayoutManager(llm);

        OkHttpClient okHC = new OkHttpClient();

        Request okReq = new Request.Builder()
                .get()
                .url(General.get_url_server() + "get_thread.php")
                .build();

        final ProgressDialog pd = new ProgressDialog(getActivity());

        pd.setTitle(getString(R.string.get_thread_data));
        pd.setMessage(getString(R.string.loading));
        pd.setCancelable(false);

        pd.show();

        okHC.newCall(okReq).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        Snackbar.make(fview, getString(R.string.cannot_connect_server), Snackbar.LENGTH_LONG);
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

                            JSONObject objResponse = new JSONObject(respString);

                            boolean resultBool = objResponse.getBoolean("result");
                            String m = objResponse.getString("message");

                            ThreadAdapter adapter = new ThreadAdapter();
                            adapter.datas = new ArrayList<>();

                            if (resultBool == true) {
                                Log.e("resultBool", resultBool + "");
                                JSONArray datasArr = objResponse.getJSONArray("data");

                                for (int i = 0; i < datasArr.length(); i++) {

                                    Thread thread = new Thread();

                                    JSONObject dataObj = datasArr.getJSONObject(i);

                                    thread.author = dataObj.getInt("author");
                                    thread.content = dataObj.getString("content");
                                    thread.title = dataObj.getString("title");
                                    thread.id = dataObj.getInt("id");

                                    adapter.datas.add(thread);
                                }
                            }

                            rvList.setAdapter(adapter);

                            pd.dismiss();

                            Snackbar.make(fview, m, Snackbar.LENGTH_LONG).show();

                        } catch (JSONException e) {

                            Log.e("Thread get data", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

}
