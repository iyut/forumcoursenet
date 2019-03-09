package com.interfeis.forumluthfi.MainActivityFragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.interfeis.forumluthfi.dbstructure.Thread;
import com.interfeis.forumluthfi.R;

import java.util.ArrayList;

public class ThreadAdapter extends RecyclerView.Adapter<ThreadCardViewHolder> {

    ArrayList<Thread> datas;
    @NonNull
    @Override
    public ThreadCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View vw = LayoutInflater.from( viewGroup.getContext() )
                .inflate(R.layout.card_thread, viewGroup, false);

        ThreadCardViewHolder tcvh = new ThreadCardViewHolder( vw );

        return tcvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ThreadCardViewHolder threadCardViewHolder, int i) {

        Thread threadData = datas.get( i );
        threadCardViewHolder.threadName.setText( threadData.title );
        threadCardViewHolder.threadID.setText( Integer.toString( threadData.id )  );
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
