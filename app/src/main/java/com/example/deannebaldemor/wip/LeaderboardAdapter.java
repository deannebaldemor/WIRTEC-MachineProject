package com.example.deannebaldemor.wip;

/**
 * Created by Deanne Baldemor on 02/04/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;


/**
 * Created by lander jr on 27/03/2018.
 */

public class LeaderboardAdapter extends RecyclerView.Adapter<ItemViewHolder>{
    private List<User> topScores;
    private int i=1;

    public LeaderboardAdapter(List<User> topScores){
        this.topScores=topScores;
    }

    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.leader_score,parent,false);
        return new ItemViewHolder(itemView);
    }

    public void onBindViewHolder(ItemViewHolder holder, int position){
        User user = topScores.get(position);
        holder.getRank().setText(String.valueOf(topScores.indexOf(user) + 1));
        holder.getUsernameTxt().setText(String.valueOf(user.getUsername()));
        holder.getHscoreTxt().setText(String.valueOf(user.getHighScore()));
        i++;
    }

    @Override
    public int getItemCount() { return topScores.size(); }
}

