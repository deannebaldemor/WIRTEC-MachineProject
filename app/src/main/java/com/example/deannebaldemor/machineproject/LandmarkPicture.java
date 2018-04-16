package com.example.deannebaldemor.machineproject;

/*
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.InputStream;
*/

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.gjiazhe.panoramaimageview.PanoramaImageView;

import java.io.IOException;
import java.io.InputStream;


public class LandmarkPicture extends Activity {
    private AssetManager assetManager;
    private String filename;
    private GyroscopeObserver gyroscopeObserver;

    private int picWidth;
    private int picHeight;

    private int screenWidth;
    private int screenHeight;
    int finalHeight, finalWidth;
    PanoramaImageView panoramaPic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_landmark_picture);

        filename=getIntent().getExtras().getString("filename");
        assetManager = this.getAssets();
        /*
        String link ="http://www.airpano.com/Photogallery.php?gallery=109&photo=2305";
        try {
            Document document = Jsoup.connect(link).get();
            Elements img = document.select("div#fotoStageScrollImg");
            // Download image from URL
            InputStream input = new java.net.URL(img).openStream();
            // Decode Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        gyroscopeObserver = new GyroscopeObserver();
        gyroscopeObserver.setMaxRotateRadian(Math.PI/9);
        //orig
        //PanoramaImageView panoramaPic = findViewById(R.id.panoPic);
        //panoramaPic.setGyroscopeObserver(gyroscopeObserver);
        Log.e("pic",filename);
        /*int source = getResources().getIdentifier(filename, "drawable", getPackageName());
        PanoramaImageView panoramaPic = new PanoramaImageView(this);
        panoramaPic.setImageResource(source);*/

        String file = "panorama/" + filename +".jpg";
        panoramaPic = findViewById(R.id.panoPic);
        try {
            InputStream ims = assetManager.open(file);
            Drawable d = Drawable.createFromStream(ims, null);
            panoramaPic.setImageDrawable(d);
        } catch (IOException ex) {
            return;
        }
        panoramaPic.setGyroscopeObserver(gyroscopeObserver);

        getImageDimensions();
        getScreenDimensions();
        scroll();
    }
    @Override
    protected void onResume(){
        super.onResume();
        gyroscopeObserver.register(this);
    }
    @Override
    protected void onPause(){
        super.onPause();
        gyroscopeObserver.unregister();
    }


    public void getImageDimensions(){
        ViewTreeObserver vto = panoramaPic.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                panoramaPic.getViewTreeObserver().removeOnPreDrawListener(this);
                picHeight = panoramaPic.getMeasuredHeight();
                picWidth = panoramaPic.getMeasuredWidth();
                return true;
            }
        });
    }

    public void getScreenDimensions(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight= displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
    }
    public void scroll(){

        // maximum scroll amount based on center of image
        int maxX = (int)((picWidth / 2) - (screenWidth / 2));
        int maxY = (int)((picHeight / 2) - (screenHeight / 2));

        //scroll limits
        final int maxLeft = (maxX * -1);
        final int maxRight = maxX;
        final int maxTop = (maxY * -1);
        final int maxBottom = maxY;


        // set touchlistener
        panoramaPic.setOnTouchListener(new View.OnTouchListener()
        {
            float downX, downY;
            int totalX, totalY;
            int scrollByX, scrollByY;
            public boolean onTouch(View view, MotionEvent event)
            {
                float currentX, currentY;
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        currentX = event.getX();
                        currentY = event.getY();
                        scrollByX = (int)(downX - currentX);
                        scrollByY = (int)(downY - currentY);

                        // scrolling to left side of image (pic moving to the right)
                        if (currentX > downX)
                        {
                            if (totalX == maxLeft)
                            {
                                scrollByX = 0;
                            }
                            if (totalX > maxLeft)
                            {
                                totalX = totalX + scrollByX;
                            }
                            if (totalX < maxLeft)
                            {
                                scrollByX = maxLeft - (totalX - scrollByX);
                                totalX = maxLeft;
                            }
                        }

                        // scrolling to right side of image (pic moving to the left)
                        if (currentX < downX)
                        {
                            if (totalX == maxRight)
                            {
                                scrollByX = 0;
                            }
                            if (totalX < maxRight)
                            {
                                totalX = totalX + scrollByX;
                            }
                            if (totalX > maxRight)
                            {
                                scrollByX = maxRight - (totalX - scrollByX);
                                totalX = maxRight;
                            }
                        }

                        panoramaPic.scrollBy(scrollByX, 0);
                        downX = currentX;
                        downY = currentY;
                        break;

                }
                return true;
            }
        });
    }
}
