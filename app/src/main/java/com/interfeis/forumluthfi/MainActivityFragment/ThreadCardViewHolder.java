package com.interfeis.forumluthfi.MainActivityFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.interfeis.forumluthfi.MainActivity;
import com.interfeis.forumluthfi.R;

public class ThreadCardViewHolder extends RecyclerView.ViewHolder {

    TextView threadName;
    TextView threadID;
    RatingBar ratingThread;

    public ThreadCardViewHolder(@NonNull final View itemView) {
        super(itemView);

        threadName = (TextView) itemView.findViewById( R.id.tvThreadName );
        threadID    = (TextView) itemView.findViewById( R.id.tvThreadID );

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity ma             = (MainActivity) itemView.getContext();
                String strID                = threadID.getText().toString();
                ThreadDetailFragment tdf    = new ThreadDetailFragment();

                Bundle b = new Bundle();
                b.putString("threadID", strID);

                tdf.setArguments( b );

                ma.openAppFragment( tdf );
            }
        });

    }

}
