package com.example.deannebaldemor.wip;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class LandmarkInfo extends Activity {
    private AssetManager assetManager;
    private String landmark;
    private ImageView imageView;
    private GuessLandmark guessLandmark;
    private String filename;
    private Button continueButton;

    private int loadSuccess;

    public static int info_code=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_landmark_info);

        guessLandmark = new GuessLandmark();
        assetManager = this.getAssets();
        continueButton = findViewById(R.id.continueButton);

        this.loadSuccess= 1;

        landmark= getIntent().getExtras().getString("landmark");
        filename= getIntent().getExtras().getString("filename");
        Log.v("FOUND", landmark);
        Log.v("FOUND", String.valueOf(guessLandmark.landmarks.size()));
        loadImage(filename);

        continueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent();
                i.putExtra("REQUEST_CODE_KEY",1);
                setResult(loadSuccess,i);
                finish();*/



                LandmarkInfo.this.finish();
                Log.v("q","test");
            }
        });
    }

    public void loadImage(String name){
        imageView = findViewById(R.id.imageView);
        String filename = "img2/" + name +"-info.png";
        try {
            InputStream ims = assetManager.open(filename);
            Drawable d = Drawable.createFromStream(ims, null);
            imageView.setImageDrawable(d);
        } catch (IOException ex) {
            return;
        }
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }

}
