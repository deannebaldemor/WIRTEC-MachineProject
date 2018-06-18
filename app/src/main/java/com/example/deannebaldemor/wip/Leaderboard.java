package com.example.deannebaldemor.wip;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Leaderboard extends Activity {

    private SharedPreferences sharedPreferences;

    // Recycler View
    private RecyclerView recyclerView;
    private LeaderboardAdapter leaderboardAdapter;

    // Usernames and highscores from database.
    private ArrayList<String> usernames = new ArrayList<>();
    private ArrayList<Integer> highscore = new ArrayList<>();

    // List of All WIP users.
    private List<User> topUsers = new ArrayList<>();

    private String USERNAME_NUM_KEY = "USERNAME_NUM_KEY";
    private String HIGHSCORE_NUM_KEY = "HIGHSCORE_NUM_KEY";

    private TextView existingUserTxt;
    private TextView existingScoreTxt;
    private TextView existingRankTxt;

    private String existingUser;
    private int userScore;
    private int userRank;

    private TextView textview2;
    private TextView textview4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_leaderboard);

        setupUI();
        loadTopUsers();
    }

    /** The method setupUI initializes the sharedpreferences, recyclerview
     * Also the fields where the current user's stats will be placed.
     *
     */
    public void setupUI(){
        sharedPreferences= getSharedPreferences("userCred", Context.MODE_PRIVATE);

        //existingUserTxt= findViewById(R.id.existing_user);
        existingScoreTxt= findViewById(R.id.existing_score);
        existingRankTxt= findViewById(R.id.existing_rank);
        textview2 = findViewById(R.id.textView2);
        textview4 = findViewById(R.id.textView4);

        recyclerView = findViewById(R.id.recycler_view);
        leaderboardAdapter= new LeaderboardAdapter(topUsers);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(leaderboardAdapter);
    }


    // The method loadTopUsers returns the top scores for the game & adds it to the RecyclerView.
    public void loadTopUsers(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
        ref.orderByChild("highScore").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        collectAllData((Map<String,Object>) dataSnapshot.getValue());
                        getCurrentUserData();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    /** The method collectAllData gets all the user data from the database.
     *  Afterwards the data will be placed in the topUsers Arraylist which will then be sorted by descending order.
     *  and then added to the recycler view.
     */
    public void collectAllData(Map<String,Object> users){

        for (Map.Entry<String,Object> entry : users.entrySet()){
            Map singleUser = (Map) entry.getValue();
            topUsers.add(new User((String) singleUser.get("username"),0, ((Long) singleUser.get("highScore")).intValue()));
        }

        Collections.sort(topUsers);
        //Log.e("KEKIS", "collect data"  + String.valueOf(getUserSize()));
        leaderboardAdapter.notifyDataSetChanged();
    }

    /** The method getCurrentUserData  gets the data of the current user
     *  and then displays it on the screen.
     *
     */

    public void getCurrentUserData(){
        sharedPreferences= getSharedPreferences("userCred", Context.MODE_PRIVATE);
        userScore    =  sharedPreferences.getInt(HIGHSCORE_NUM_KEY,0);
        //Log.e("KEKIS", "main curr data"  + String.valueOf(getUserSize()));
        userRank = topUsers.indexOf(getCurrentUser()) + 1;

        if(userScore==0 && existingUser.isEmpty()){
            textview4.setText("");
            textview2.setText("");
            existingScoreTxt.setText("");
            existingRankTxt.setText("");
        }
        else{
            //existingUserTxt.setText(existingUser);
            existingScoreTxt.setText(String.valueOf(userScore));
            existingRankTxt.setText(String.valueOf(userRank ));
        }



    }

    /** The method getCurrenUsers returns the current user object.
     *
     * @return
     */
    public User getCurrentUser(){
        sharedPreferences= getSharedPreferences("userCred", Context.MODE_PRIVATE);
        existingUser = sharedPreferences.getString(USERNAME_NUM_KEY,"");
        User user = null;
        for (int i=0; i < topUsers.size(); i++){
            if (topUsers.get(i).getUsername().equals(existingUser)){
                user = topUsers.get(i);
            }
        }
        return user;
    }

    public int getUserSize(){ return topUsers.size();}



}
