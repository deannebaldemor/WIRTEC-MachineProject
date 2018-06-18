package com.example.deannebaldemor.wip;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Deanne Baldemor on 16/04/2018.
 */

public class CreditsAdapter extends RecyclerView.Adapter<CreditsViewHolder>{
    private List<Source> sourceList;
    public CreditsAdapter(List<Source> sourceList){
        this.sourceList = sourceList;
    }


    public CreditsViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.credits_source,parent,false);
        return new CreditsViewHolder(itemView);
    }

    public void onBindViewHolder(CreditsViewHolder holder, int position) {
        Source source = sourceList.get(position);
        holder.getSource().setText(source.getSource());
    }

    public int getItemCount() {
        return this.sourceList.size();
    }
}
