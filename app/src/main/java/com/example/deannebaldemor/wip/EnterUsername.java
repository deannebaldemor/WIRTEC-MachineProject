package com.example.deannebaldemor.wip;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EnterUsername extends Activity {

    // Firebase and SharedPreferences
    private DatabaseReference mDatabase;
    private SharedPreferences sharedPreferences;

    // Field and Button declaration
    private Button register;
    private EditText username;

    // Highscore and default number of coins
    private int hscore=0;
    private int ucoins=0;
    private int state=1;

    // KEYS
    private String USERNAME_NUM_KEY = "USERNAME_NUM_KEY";
    private String COINS_NUM_KEY = "COINS_NUM_KEY";
    private String HIGHSCORE_NUM_KEY = "HIGHSCORE_NUM_KEY";
    private String USERSTATE_NUM_KEY = "USERSTATE_NUM_KEY";
    private String USERKEY_NUM_KEY = "USERKEY_NUM_KEY";

    // List of All WIP users.
    private List<User> topUsers = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_enter_username);

        setupUI();


    }
    /** The method setupUI initializes the register button and the username text field.
     * Listener contains the code where the user credentials are inserted to the database.
     */
    public void setupUI(){

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        sharedPreferences= getSharedPreferences("userCred", Context.MODE_PRIVATE);

        username = findViewById(R.id.username);
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.getText().clear();
            }
        });


        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check if there is internet
                //if(isNetworkAvailable()==true){
                if( TextUtils.isEmpty(username.getText())){
                    /**
                     *   You can Toast a message here that the Username is Empty
                     **/
                    //displayToast();
                    username.setError( "Username is required!" );

                }else{
                    String usernameins = String.valueOf(username.getText());
                    saveData(usernameins,ucoins,hscore);
                }

                //loadTopUsers();
                //}

                /*
                Intent i = new Intent(EnterUsername.this,StartScreen.class);
                startActivity(i);*/
            }
        });

    }

    public void displayToast() {
        Toast.makeText(this, "Username is required!", Toast.LENGTH_SHORT).show();
    }

    /** The method saveData saves the user credentials after setup to the database
     * and through the shared preferences.
     */

    /*
    //orig
    public void saveData(String username, int numcoins, int highscore){
        String userID = mDatabase.push().getKey();
        User user = new User(username, numcoins, highscore);
        mDatabase.child(userID).setValue(user);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(USERKEY_NUM_KEY,userID);
        editor.putString(USERNAME_NUM_KEY, username);
        editor.putInt(HIGHSCORE_NUM_KEY, highscore);
        editor.putInt(COINS_NUM_KEY, numcoins);
        editor.putInt(USERSTATE_NUM_KEY,state);
        editor.commit();
    }*/

    public void saveData(final String username, final int numcoins, final int highscore){
        String userid = sharedPreferences.getString(USERKEY_NUM_KEY, "");

        Log.e("KEKERSS", "CURRENT USERNAME" + String.valueOf(mDatabase.child("users").child("username").child(username)));
        Query query = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("username").equalTo(username);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    Log.v("KEKERSS", "MAY ACCOUNT NA NYAN TRY AGAIN");
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(EnterUsername.this);
                    alertDialog.setTitle("WARNING");
                    alertDialog.setMessage("Username already exists! Use another username.");
                    alertDialog.setNegativeButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    Log.v("KEKERSS", "DI PA NAGEEXIST TEH");
                    final String userID = mDatabase.push().getKey();
                    User user = new User(username, numcoins, highscore);
                    mDatabase.child(userID).setValue(user);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(USERKEY_NUM_KEY,userID);
                    editor.putString(USERNAME_NUM_KEY, username);
                    editor.putInt(HIGHSCORE_NUM_KEY, highscore);
                    editor.putInt(COINS_NUM_KEY, numcoins);
                    editor.putInt(USERSTATE_NUM_KEY,state);
                    editor.commit();

                    Intent i = new Intent(EnterUsername.this,StartScreen.class);
                    startActivity(i);
                    EnterUsername.this.finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
/*
    public int getAvatarNumber(){
        int avatarNo;

        if(getUserSize()>30){
            //randomize an avatar
            Random r = new Random();
            avatarNo = r.nextInt(30-1)+1;
        }
        else{
            avatarNo=getUserSize()+1;
        }
        return avatarNo;
    }


    public void loadTopUsers(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
        ref.orderByChild("highScore").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        getUsers((Map<String,Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }*/

    /*
    public void getUsers(Map<String,Object> users){
        for (Map.Entry<String,Object> entry : users.entrySet()){
            Map singleUser = (Map) entry.getValue();
            topUsers.add(new User((String) singleUser.get("username"),0, ((Long) singleUser.get("highScore")).intValue(), ((Long) singleUser.get("avatarNumber")).intValue()));
        }
    }

    public int getUserSize(){
        return topUsers.size();
    }*/

    //private boolean isNetworkAvailable() {
    //  ConnectivityManager connectivityManager
    //        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    //NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    //return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    //}
}
