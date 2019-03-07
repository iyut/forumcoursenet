package com.interfeis.forumluthfi.MainActivityFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.interfeis.forumluthfi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreadDetailFragment extends Fragment {


    public ThreadDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View infView = inflater.inflate(R.layout.fragment_thread_detail, container, false);


        return infView;
    }

}
