package com.example.deannebaldemor.wip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {
    private Button start;
    private Button leaderboard;
    private Button exit;
    private Button credits;

    // User name and statekeys.
    private String USERSTATE_NUM_KEY = "USERSTATE_NUM_KEY";
    private String USERNAME_NUM_KEY = "USERNAME_NUM_KEY";
    private boolean IS_RUNNING;

    // Declaration on Shared Preferences.
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        if(!IS_RUNNING) {
            startService(new Intent(this, MusicService.class));
            IS_RUNNING=true;
        }
        this.start = findViewById(R.id.startBtn);
        this.leaderboard = findViewById(R.id.leaderboardBtn);
        this.exit = findViewById(R.id.exitBtn);
        this.credits = findViewById(R.id.creditsBtn);

        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sharedPreferences= getSharedPreferences("userCred", Context.MODE_PRIVATE);
                int currentState = sharedPreferences.getInt(USERSTATE_NUM_KEY,0);

                if(currentState == 0 ) {
                    // Redirect user to sign up page.
                    Intent i = new Intent(MainActivity.this,EnterUsername.class);
                    startActivity(i);
                } else{
                    Intent i = new Intent(MainActivity.this,EnterLocation.class);
                    startActivity(i);
                    final String existingUser = sharedPreferences.getString(USERNAME_NUM_KEY,"");
                    Log.v("TESTLIST", existingUser);

                    // Redirect user to tutorial page, then the map page.

                }
            }
        });
        leaderboard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Leaderboard.class);
                startActivity(i);
            }
        });
        credits.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Credits.class);
                startActivity(i);
            }
        });
        exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, MusicService.class));
        finishAffinity();
        super.onDestroy();
    }

}
