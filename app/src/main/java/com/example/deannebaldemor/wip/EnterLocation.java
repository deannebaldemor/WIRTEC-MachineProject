package com.example.deannebaldemor.wip;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class EnterLocation extends Activity{

    private Button locationSubmit;
   // private EditText location;

    public ImageButton southAmButton;
    public ImageButton northAmButton;
    public ImageButton asiaButton;
    public ImageButton europeButton;
    public ImageButton africaButton;
    public ImageButton ausButton;
    public ImageButton antarticaButton;
    private Button returnBtn;

    private EditText location;
    private String completedContinent;
    private GuessLandmark guessLandmark;

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_enter_location);

        //this.locationSubmit = findViewById(R.id.locationSubmit);
        //this.location = findViewById(R.id.enterLocation);

        intent = getIntent();

        southAmButton=   findViewById(R.id.southAmBtn);
        northAmButton=   findViewById(R.id.northAmBtn);;
        asiaButton=      findViewById(R.id.asiaBtn);
        europeButton=    findViewById(R.id.europeBtn);
        africaButton=    findViewById(R.id.africaBtn);
        ausButton=       findViewById(R.id.ausBtn);
        antarticaButton= findViewById(R.id.antarticaBtn);

        returnBtn = findViewById(R.id.returnBtn);

        northAmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EnterLocation.this,GuessLandmark.class);
                i.putExtra("location","North America");
                startActivity(i);
            }
        });

        southAmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.v("LOCATION", "SOUTH AMERICA");
                Intent i = new Intent(EnterLocation.this,GuessLandmark.class);
                i.putExtra("location","South America");
                startActivity(i);
            }
        });

        ausButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.v("LOCATION", "AUSTRALIA");
                Intent i = new Intent(EnterLocation.this,GuessLandmark.class);
                i.putExtra("location","Australia");
                startActivity(i);
            }
        });

        africaButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.v("LOCATION", "AFRICA");
                Intent i = new Intent(EnterLocation.this,GuessLandmark.class);
                i.putExtra("location","Africa");
                startActivity(i);
            }
        });

        antarticaButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.v("LOCATION", "ANTARTICA");
                Intent i = new Intent(EnterLocation.this,GuessLandmark.class);
                i.putExtra("location","Antartica");
                startActivity(i);
            }
        });

        asiaButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.v("LOCATION", "ASIA");
                Intent i = new Intent(EnterLocation.this,GuessLandmark.class);
                i.putExtra("location","Asia");
                startActivity(i);
            }
        });

        europeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.v("LOCATION", "EUROPE");
                Intent i = new Intent(EnterLocation.this,GuessLandmark.class);
                i.putExtra("location","Europe");
                startActivity(i);
            }
        });
        check();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (preferences.contains("completedContinents")) {
            String str = preferences.getString("completedContinents", "0");
            Log.d("FRESH", " " + str);
            String[] completeContinents = str.split(",");

            for(int i = 0; i < completeContinents.length; i++) {
                loadComplete(completeContinents[i]);
            }
        }


        returnBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EnterLocation.this.finish();
                Intent i = new Intent(EnterLocation.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    /*

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("PUMASOK", "pumasok");
        completedContinent=getIntent().getExtras().getString("completedContinent");

        if(requestCode==1){
            Log.v("PUMASOK", "pumasokkkkkkkk");
            ImageView i = null;
            if(completedContinent.equalsIgnoreCase("europe")){
                i =findViewById(R.id.euCheck);
            }
            else if(completedContinent.equalsIgnoreCase("asia")){
                i =findViewById(R.id.asCheck);
            }
            else if(completedContinent.equalsIgnoreCase("south america")){
                i =findViewById(R.id.saCheck);
            }
            else if(completedContinent.equalsIgnoreCase("north america")){
                i =findViewById(R.id.naCheck);
            }
            else if(completedContinent.equalsIgnoreCase("austrailia")){
                i =findViewById(R.id.auCheck);
            }
            else if(completedContinent.equalsIgnoreCase("antartica")){
                i =findViewById(R.id.anCheck);
            }
            else if(completedContinent.equalsIgnoreCase("africa")){
                i =findViewById(R.id.afCheck);
            }

            i.setVisibility(View.VISIBLE);
        }
    }*/
    public void loadComplete(String completedContinent) {

        Log.v("PUMASOK", "pumasokkkkkkkk");
        ImageView i = null;
        if (completedContinent.equalsIgnoreCase("europe")) {
            i = findViewById(R.id.euCheck);
            europeButton.setEnabled(false);
        } else if (completedContinent.equalsIgnoreCase("asia")) {
            asiaButton.setEnabled(false);
            i = findViewById(R.id.asCheck);
        } else if (completedContinent.equalsIgnoreCase("south america")) {
            i = findViewById(R.id.saCheck);
            southAmButton.setEnabled(false);
        } else if (completedContinent.equalsIgnoreCase("north america")) {
            i = findViewById(R.id.naCheck);
            northAmButton.setEnabled(false);
        } else if (completedContinent.equalsIgnoreCase("australia")) {
            i = findViewById(R.id.auCheck);
            ausButton.setEnabled(false);
        } else if (completedContinent.equalsIgnoreCase("antartica")) {
            i = findViewById(R.id.anCheck);
            antarticaButton.setEnabled(false);
        } else if (completedContinent.equalsIgnoreCase("africa")) {
            i = findViewById(R.id.afCheck);
            africaButton.setEnabled(false);
        }

        i.setVisibility(View.VISIBLE);

    }

    public void check() {
        Bundle extras = intent.getExtras();
        if (extras != null){
            completedContinent = getIntent().getExtras().getString("completedContinent");

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();

            if (preferences.contains("completedContinents")) {
                Log.d("CHECK", "EXIST ");
                String str = preferences.getString("completedContinents", "0");
                str += "," + completedContinent;
                Log.d("CHECK", "EXIST " + str);
                editor.putString("completedContinents", str);
            }
            else {
                editor.putString("completedContinents", completedContinent);
            }
            editor.apply();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        EnterLocation.this.finish();
        return false;
    }
}
