package com.example.deannebaldemor.wip;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Deanne Baldemor on 06/04/2018.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder{

    private TextView username, hscore, rank;

    public ItemViewHolder(View view){
        super(view);
        username = view.findViewById(R.id.user_name);
        hscore   = view.findViewById(R.id.user_score);
        rank = view.findViewById(R.id.user_rank);
    }

    public TextView getUsernameTxt(){return username;}
    public TextView getHscoreTxt(){return hscore;}
    public TextView getRank(){ return rank;}
}
