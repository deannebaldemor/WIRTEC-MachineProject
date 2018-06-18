package com.example.deannebaldemor.wip;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class LandmarkComplete extends Activity {

    private String location;
    private GuessLandmark guessLandmark;
    private List<String> infoLandmarks = new ArrayList<>();
    private Button infoBtn;
    private int index;
    String temp="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_landmark_complete);

        location=getIntent().getExtras().getString("location");
        index=0;
    }

    public void loadInfo(){
        for(int i=0; i<guessLandmark.landmarks.size(); i++){
            if(guessLandmark.landmarks.get(i).getContinent().equals(location.toString())){
                infoLandmarks.add(guessLandmark.landmarks.get(i).getFilename());
            }
        }
        /*
        for(int i=0; i<infoLandmarks.size(); i++){
            String temp = infoLandmarks.get(i)+"-info";
            guessLandmark.loadImage(temp);
        }*/
    }

    public void setupUI(){
        temp = infoLandmarks.get(index)+"-info";
        infoBtn = findViewById(R.id.infoBtn);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index<=infoLandmarks.size()){
                    //guessLandmark.loadImage(temp);
                    index++;
                }
            }
        });
    }

}
