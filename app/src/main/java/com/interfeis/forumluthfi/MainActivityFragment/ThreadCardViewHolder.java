package com.interfeis.forumluthfi.MainActivityFragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.interfeis.forumluthfi.R;

public class ThreadCardViewHolder extends RecyclerView.ViewHolder {

    TextView threadName;
    RatingBar ratingThread;

    public ThreadCardViewHolder(@NonNull View itemView) {
        super(itemView);

        threadName = (TextView) itemView.findViewById( R.id.tvThreadName );

    }

}
