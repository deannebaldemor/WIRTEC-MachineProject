package com.example.deannebaldemor.machineproject;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Deanne Baldemor on 16/04/2018.
 */

public class CreditsViewHolder extends RecyclerView.ViewHolder {
    private TextView source;
    public CreditsViewHolder(View itemView) {
        super(itemView);
        source = itemView.findViewById(R.id.sourceTextview);

    }

    public TextView getSource() {
        return source;
    }
}
