package com.example.deannebaldemor.machineproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GuessLandmark extends Activity {

    AssetManager assetManager;
    private ImageView imageView;
    public List<LandmarkModel> landmarks = new ArrayList<>();
    private List<QuestionsModel> questions = new ArrayList<>();
    private List<Integer> completedID = new ArrayList<>();
    private Random r;
    private String location;
    private QuestionsModel q;
    private QuestionsModel question;
    private QuestionsModel newQuestion;
    private int countCorrect;
    private int countQuestions;
    private int currLandmark;
    private String file;
    private Boolean skipSelected;


    private String landmark;
    private TextView info;
    private TextView coins;
    private Button[] letterOptions = new Button[14];
    private TextView[] letterAnswers;
    private int[] indexList;
    private char[] wordChars;
    private int charCtr;
    private Button removeLetters;

    private LinearLayout layout;
    private LinearLayout linearLayout;
    private Button clear;
    private Button shuffle;
    private Button hint;;
    private Button viewImgBtn;
    private Button skipBtn;
    private Button viewMapBtn;

    private int coinCtr = 0;
    //private int score = 0;


    private Button revealBtn;
    private Button removeExtraBtn;
    private Button showAnswerBtn;


    public static int guess_code=1;
    private Boolean checkContinent=false;

    public static int COMPLETE_REQUESTCODE =1;

    //SharedPreferences sharedPreferences;
    private String USERNAME_NUM_KEY = "USERNAME_NUM_KEY";
    private String NUMCOINS_NUM_KEY = "NUMCOINS_NUM_KEY";
    private String HIGHSCORE_NUM_KEY = "HIGHSCORE_NUM_KEY";
    private String USERKEY_NUM_KEY= "USERKEY_NUM_KEY";

    // ADD
    private SharedPreferences sharedPreferences;
    private int score;
    // ADD


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("HS", "onCreate: " + score);
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guess_landmark);

        //ADD
        sharedPreferences = getSharedPreferences("userCred", Context.MODE_PRIVATE);
        score = sharedPreferences.getInt(HIGHSCORE_NUM_KEY, 0);
        //ADD


        assetManager = this.getAssets();
        location=getIntent().getExtras().getString("location");
        countCorrect=0;
        countQuestions=0;
        currLandmark=0;
        skipSelected=false;


        createLandmarks();
        getQuestions();
        loadQuestion();
        setupUI();
        playGame();

    }
    public void setupUI(){
        linearLayout = findViewById(R.id.linearLayout);
        info = findViewById(R.id.info);

        letterOptions[0] = findViewById(R.id.button1);
        letterOptions[1] = findViewById(R.id.button2);
        letterOptions[2] = findViewById(R.id.button3);
        letterOptions[3] = findViewById(R.id.button4);
        letterOptions[4] = findViewById(R.id.button5);
        letterOptions[5] = findViewById(R.id.button6);
        letterOptions[6] = findViewById(R.id.button7);
        letterOptions[7] = findViewById(R.id.button8);
        letterOptions[8] = findViewById(R.id.button9);
        letterOptions[9] = findViewById(R.id.button10);
        letterOptions[10] = findViewById(R.id.button11);
        letterOptions[11] = findViewById(R.id.button12);
        letterOptions[12] = findViewById(R.id.button13);
        letterOptions[13] = findViewById(R.id.button14);

        layout = new LinearLayout(this);
        layout = findViewById(R.id.linearLayout);

        clear = findViewById(R.id.clearBtn);
        //shuffle = findViewById(R.id.shuffleBtn);
        coins = findViewById(R.id.coinsTextView);
        refreshCoins();
    }

    public void playGame(){

        final String finalString = generateFinalString(landmark.toUpperCase());

        charCtr = landmark.length() - finalString.length();
        letterAnswers = new TextView[finalString.length()];
        indexList = new int[finalString.length()];

        wordChars = getStringCharacters(finalString, charCtr);

        Log.d("Chars", "" + Arrays.toString(wordChars));

        //log.d("ArrayContent", "" + checkArrayContent(indexList));
        Random randomizer = new Random();
        final char[] landmarkLetters = new char[15];

        int j = 0;

        layout.removeAllViews();
        int textSize;

        if (landmark.length() >= 12) {
            textSize = 28;
        }
        else if (landmark.length() >= 8) {
            textSize = 33;
        }
        else {
            textSize = 37;
        }

        for (int i = 0; i < landmark.length(); i++) {
            final TextView chr;
            if (Character.isLetter(landmark.charAt(i))) {
                letterAnswers[j] = new TextView(this);
                letterAnswers[j].setText("_");
                letterAnswers[j].setTextSize(textSize);
                letterAnswers[j].setId(j);
                chr = letterAnswers[j];

                letterAnswers[j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeWord(chr.getId());
                        chr.setText("_");
                        setCursor();
                    }
                });
                j++;
            }
            else {
                chr = new TextView(this);
                chr.setTextSize(textSize);
                chr.setText(landmark.charAt(i) + "");
            }

            if (i == 0) {
                TextView space = new TextView(this);
                space.setText("  ");
                layout.addView(space);
            }

            layout.addView(chr);
            TextView space = new TextView(this);
            space.setText("  ");
            layout.addView(space);

        }
        setCursor();
        generateButtons(finalString);

        /*shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                generateButtons(finalString);

                if (removeLetters.isEnabled() == false) {
                    setButtonsVisible();
                    removeJumbleWords(finalString);
                }
            }
        });*/


        clear = findViewById(R.id.clearBtn);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        viewImgBtn = findViewById(R.id.viewImgBtn);
        viewImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GuessLandmark.this,LandmarkPicture.class);
                i.putExtra("filename",file);
                startActivity(i);
            }
        });

        skipBtn = findViewById(R.id.skipBtn);
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipSelected=true;
                resetWord(letterAnswers);
                loadQuestion();
                playGame();
                setButtonsVisible();
                setButtonsEnabled();
            }
        });

        viewMapBtn=findViewById(R.id.mapBtn);
        viewMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GuessLandmark.this,EnterLocation.class);
                startActivity(i);
            }
        });

        viewMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GuessLandmark.this,EnterLocation.class);
                startActivity(i);
            }
        });


        revealBtn = findViewById(R.id.revealBtn);
        revealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //removeJumbleWords(finalString);//OL
                //insertWordHint();
                AlertDialog.Builder builder = new AlertDialog.Builder(GuessLandmark.this);
                builder.setTitle("Choose Hint");
                if (finalString.length() == 14 || extraWordsChecker() == false) {
                    CharSequence colors[] = new CharSequence[]{"Reveal a Letter (-1 coin)", "Give the answer (-5 coins)"};
                    builder.setItems(colors, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // the user clicked on colors[which]
                            if (which == 0) {
                                insertWordHint();
                                dialog.dismiss();
                            } else {
                                reveal();
                                dialog.dismiss();
                                return;
                            }
                        }
                    });
                }
                else {
                    CharSequence colors[] = new CharSequence[]{"Reveal a Letter (-1 coin)", "Remove Extra Letters (-3 coins)", "Give the answer (-5 coins)"};
                    builder.setItems(colors, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // the user clicked on colors[which]
                            if (which == 0) {
                                insertWordHint();
                                dialog.dismiss();
                                return;
                            } else if (which == 1) {
                                removeJumbleWords(finalString);
                                dialog.dismiss();
                                return;
                            } else {
                                reveal();
                                dialog.dismiss();
                                return;
                            }
                        }
                    });
                }
                builder.show();
            }
        });
    }

    public boolean extraWordsChecker() {
        boolean check = true;
        for (int i = 0; i < letterOptions.length; i++) {
            if(letterOptions[i].getVisibility() == 4) {
                check = false;
            }
        }

        return check;
    }


    public void setButtonsVisible() {
        for (int i = 0; i < letterOptions.length; i++) {
            letterOptions[i].setVisibility(View.VISIBLE);
        }
    }

    public void setButtonsEnabled() {
        for (int i = 0; i < letterOptions.length; i++) {
            letterOptions[i].setEnabled(true);
        }
    }

    /*public void removeJumbleWords(String str) {
        for (int i = 0; i < letterOptions.length; i++) {
            if (str.indexOf(letterOptions[i].getText().toString()) == -1) {
                letterOptions[i].setVisibility(View.INVISIBLE);
            }
            else {
                str = str.replaceFirst(letterOptions[i].getText().toString(), "");
                //Log.d("TEST", "removeJumbleWords: " + str);
            }
        }
    }*/
    public void refreshCoins() {
        coins.setText(Integer.toString(coinCtr));

        //ADD
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        SharedPreferences sharedPreferences=  getSharedPreferences("userCred", Context.MODE_PRIVATE);

        String userID = sharedPreferences.getString(USERKEY_NUM_KEY, "");
        mDatabase.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot.getRef().child("numCoins").setValue(coinCtr);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User", databaseError.getMessage());
            }
        });

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NUMCOINS_NUM_KEY, coinCtr);
        editor.commit();
        //ADD
    }


    public void removeJumbleWords(String str) {
        if (coinCtr >= 3) {
            for (int i = 0; i < letterOptions.length; i++) {
                if (str.indexOf(letterOptions[i].getText().toString()) == -1) {
                    letterOptions[i].setVisibility(View.INVISIBLE);
                }
                else {
                    str = str.replaceFirst(letterOptions[i].getText().toString(), "");
                    //Log.d("TEST", "removeJumbleWords: " + str);
                }
            }

            coinCtr -= 3;
            refreshCoins();
        }
        else {
            Toast.makeText(this, "Not enough coins!", Toast.LENGTH_SHORT).show();
        }

    }


    public void generateButtons(final String str) {
        Random randomizer = new Random();

        final char[] letters = new char[15];

        for (int i = 0; i < 14; i++) {
            if (i >= str.length()) {
                letters[i] = randomLetter();
            }
            else {
                letters[i] = str.charAt(i);
            }
        }

        int[] checker = new int[14];
        int n;

        for (int i = 0; i < 14; i++) {
            do {
                n = randomizer.nextInt(14);
                if (n == 0) {
                    n = -1;
                }
            }
            while (checkIntArray(n, checker));
            checker[i] = n;

            if (n == -1) {
                n = 0;
            }

            final Button button = letterOptions[i];

            button.setText(letters[n] + "");
            button.setId(i);
            letterOptions[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (checkArrayContent() > 0) {
                        insertWord(button.getText().toString(), button.getId());
                        button.setEnabled(false);
                    }
                    if (checkArrayContent() == 0) {
                        Log.d("Guess", "COMPLETE " + str);
                        if (checkWord(str)) {
                            for(int i=0; i<landmarks.size(); i++){
                                if(landmarks.get(i).getID()==currLandmark){  // && question.getAnswered()==true add is not yet answered
                                    landmarks.get(i).setAnswered(true);
                                }
                            }

                            for(int i=0; i<questions.size(); i++){
                                if(questions.get(i).getLandmarkID()==currLandmark){
                                    questions.remove(i);
                                }
                            }
                            //question.setAnswered(true);

                            final int bonus = computeScoreCoins();
                            coinCtr += bonus;
                            refreshCoins();
                            info.setBackgroundColor(0x8C2ecc71);
                            info.setText("CORRECT!");
                            info.setTextSize(20);
                            countQuestions--;
                            countCorrect++;
                            if(countQuestions==1){
                                skipBtn.setEnabled(false);
                            }

                            //ADD

                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
                            SharedPreferences sharedPreferences=  getSharedPreferences("userCred", Context.MODE_PRIVATE);

                            String userID = sharedPreferences.getString(USERKEY_NUM_KEY, "");

                            //Log. e("SCORE", String.valueOf(score));

                            mDatabase.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    SharedPreferences sharedPreferences=  getSharedPreferences("userCred", Context.MODE_PRIVATE);
                                    User user = dataSnapshot.getValue(User.class);
                                    int hscore= user.highScore;

                                    dataSnapshot.getRef().child("numCoins").setValue(coinCtr);
                                    dataSnapshot.getRef().child("highScore").setValue(score);

                                    Log.e("DB SCORE", String.valueOf("DATABASE" + user.highScore));
                                    Log.e("DB SCORE", String.valueOf("GAME SCORE" + score));

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putInt(NUMCOINS_NUM_KEY, coinCtr);
                                    editor.putInt(HIGHSCORE_NUM_KEY,  score);
                                    editor.commit();
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.d("User", databaseError.getMessage());
                                }
                            });
                            //ADD
                            setButtonsEnabled();
                            nextPage();
                        }
                        else {
                            info.setBackgroundColor(0x8Ce74c3c);
                            info.setText("WRONG!");
                            info.setTextSize(20);
                        }
                    }
                }
            });
        }
    }

    public void clear(){

        for (int i = 0; i < letterAnswers.length; i++) {
            if(letterAnswers[i].getTypeface() == null) {
                letterAnswers[i].setText("_");
                indexList[i] = 0;
            }
        }
        int x = 0;

        for (int i = 0; i < letterOptions.length; i++) {
            if (i == 0) {
                x = -1;
            }
            else {
                x = i;
            }

            if (letterOptions[i].isEnabled() == false && !checkIntArray(x, indexList)) {
                letterOptions[i].setEnabled(true);
            }
        }
        setCursor();
        info.setText("");
        info.setBackgroundColor(0x000000);
    }


    private void loadImage(String landmark){
        imageView = findViewById(R.id.imageView);
        String filename = "img2/" + landmark +".png";
        try {
            InputStream ims = assetManager.open(filename);
            Drawable d = Drawable.createFromStream(ims, null);
            imageView.setImageDrawable(d);
        } catch (IOException ex) {
            return;
        }

        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public void createLandmarks(){


        LandmarkModel l1 = new LandmarkModel(1, "Eiffel Tower", "Europe", "eiffeltower",false);
        LandmarkModel l2 = new LandmarkModel(2, "Stonehenge", "Europe", "stonehenge",false);
        LandmarkModel l3 = new LandmarkModel(3, "Northern Lights", "Europe", "northernlights",false);

        LandmarkModel l4 = new LandmarkModel(4, "Pisa", "Europe", "pisa",false);
        LandmarkModel l5 = new LandmarkModel(5, "Colosseum", "Europe", "colosseum",false);
        LandmarkModel l6 = new LandmarkModel(6, "Trevi Fountain", "Europe", "trevifountain",false);
        LandmarkModel l7 = new LandmarkModel(7, "Christ The King", "Europe", "christtheking",false);
        LandmarkModel l8 = new LandmarkModel(8, "National Pantheon", "Europe", "nationalpantheon",false);

        LandmarkModel l9 = new LandmarkModel(9, "Taj Mahal", "Asia", "tajmahal",false);
        LandmarkModel l10 = new LandmarkModel(10, "Petra", "Asia", "petra",false);
        LandmarkModel l11 = new LandmarkModel(11, "Red Fort", "Asia", "redfort",false);
        LandmarkModel l12 = new LandmarkModel(12, "Halong Bay", "Asia", "halongbay",false);
        LandmarkModel l13 = new LandmarkModel(13, "Rara Jonggrang", "Asia", "rarajonggrang",false);
        LandmarkModel l14 = new LandmarkModel(14, "Borobodur", "Asia", "borobudur",false);
        LandmarkModel l15= new LandmarkModel(15, "Angkor Wat", "Asia", "angkorwat",false);
        LandmarkModel l16 = new LandmarkModel(16, "Disneyland", "Asia", "disneyland",false);

        LandmarkModel l17 = new LandmarkModel(17, "Chichen Itza", "North America", "chichenitza", false);
        LandmarkModel l18 = new LandmarkModel(18, "Mount Rushmore", "North America", "mountrushmore", false);
        LandmarkModel l19 = new LandmarkModel(19, "Niagara Falls", "North America", "niagarafalls", false);
        LandmarkModel l20 = new LandmarkModel(20, "Space Needle", "North America", "spaceneedle", false);
        LandmarkModel l21 = new LandmarkModel(21, "Central Park", "North America", "centralpark", false);
        LandmarkModel l22 = new LandmarkModel(22, "Cloud Gate", "North America", "cloudgate", false);
        LandmarkModel l23 = new LandmarkModel(23, "CN Tower", "North America", "cntower", false);
        LandmarkModel l24 = new LandmarkModel(24, "Grand Canyon", "North America", "grandcanyon", false);

        LandmarkModel l25 = new LandmarkModel(25,"Machu Pichu", "South America", "machupichu",false);
        LandmarkModel l26 = new LandmarkModel(26,"Moai Statues", "South America", "moaistatues",false);
        LandmarkModel l27 = new LandmarkModel(27,"Angel Falls", "South America", "angelfalls",false);
        LandmarkModel l28 = new LandmarkModel(28,"Ollantaytambo", "South America", "ollantaytambo",false);
        LandmarkModel l29 = new LandmarkModel(29,"Christ Redeemer", "South America", "christredeemer",false);
        LandmarkModel l30 = new LandmarkModel(30,"Salinas Grandes", "South America", "salinasgrandes",false);
        LandmarkModel l31 = new LandmarkModel(31,"Iguazu Fall", "South America", "iguazufalls",false);
        LandmarkModel l32 = new LandmarkModel(32,"Plaza De Mayo", "South America", "plazademayo",false);

        LandmarkModel l33 = new LandmarkModel(33,"Danco Coast", "Antartica", "dancocoast",false);
        LandmarkModel l34 = new LandmarkModel(34,"Half Moon Island", "Antartica", "halfmoonisland",false);
        LandmarkModel l35 = new LandmarkModel(35,"Falklan Island", "Antartica", "falklanisland",false);
        LandmarkModel l36 = new LandmarkModel(36,"South Pole", "Antartica", "southpole",false);
        LandmarkModel l37 = new LandmarkModel(37,"Ross Ice Shelf", "Antartica", "rossiceshelf",false);
        LandmarkModel l38 = new LandmarkModel(38,"Lefith Cove", "Antartica", "leithcove",false);
        LandmarkModel l39 = new LandmarkModel(39,"Black Island", "Antartica", "blackisland",false);
        LandmarkModel l40 = new LandmarkModel(40,"Port Lockroy", "Antartica", "portlockroy",false);

        LandmarkModel l41 = new LandmarkModel(41,"Sydney Opera", "Australia", "sydneyopera",false);
        LandmarkModel l42 = new LandmarkModel(42,"Harbour Bridge", "Australia", "harbourbridge",false);
        LandmarkModel l43 = new LandmarkModel(43,"Great Barrier", "Australia", "greatbarrier",false);
        LandmarkModel l44 = new LandmarkModel(44,"Twelve Apostle", "Australia", "twelveapostles",false);
        LandmarkModel l45 = new LandmarkModel(45,"Port Arthur", "Australia", "portarthur",false);
        LandmarkModel l46 = new LandmarkModel(46,"Kangaroo Island", "Australia", "kangarooisland",false);
        LandmarkModel l47 = new LandmarkModel(47,"Sydney Tower", "Australia", "sydneytower",false);
        LandmarkModel l48 = new LandmarkModel(48,"Uluru", "Australia", "uluru",false);

        LandmarkModel l49 = new LandmarkModel(49,"Great Sphinx", "Africa", "greatsphinx",false);
        LandmarkModel l50 = new LandmarkModel(50,"Luxor Temple", "Africa", "luxortemple",false);
        LandmarkModel l51 = new LandmarkModel(51,"Deadvlei", "Africa", "deadvlei",false);
        LandmarkModel l52 = new LandmarkModel(52,"Namib Desert", "Africa", "namibdesert",false);
        LandmarkModel l53 = new LandmarkModel(53,"Pyramids of Giza", "Africa", "pyramidsofgiza",false);
        LandmarkModel l54 = new LandmarkModel(54,"Ramesseum", "Africa", "ramesseum",false);
        LandmarkModel l55 = new LandmarkModel(55,"Hassan Tower", "Africa", "hassantower",false);
        LandmarkModel l56 = new LandmarkModel(56,"Victoria Falls", "Africa", "victoriafalls",false);

        landmarks.add(l1);
        landmarks.add(l2);
        landmarks.add(l3);
        landmarks.add(l4);
        landmarks.add(l5);
        landmarks.add(l6);
        landmarks.add(l7);
        landmarks.add(l8);
        landmarks.add(l9);
        landmarks.add(l10);
        landmarks.add(l11);
        landmarks.add(l12);
        landmarks.add(l13);
        landmarks.add(l14);
        landmarks.add(l15);
        landmarks.add(l16);
        landmarks.add(l17);
        landmarks.add(l18);
        landmarks.add(l19);
        landmarks.add(l20);
        landmarks.add(l21);
        landmarks.add(l22);
        landmarks.add(l23);
        landmarks.add(l24);

        landmarks.add(l25);
        landmarks.add(l26);
        landmarks.add(l27);
        landmarks.add(l28);
        landmarks.add(l29);
        landmarks.add(l30);
        landmarks.add(l31);
        landmarks.add(l32);
        landmarks.add(l33);
        landmarks.add(l34);
        landmarks.add(l35);
        landmarks.add(l36);
        landmarks.add(l37);
        landmarks.add(l38);
        landmarks.add(l39);
        landmarks.add(l40);
        landmarks.add(l41);
        landmarks.add(l42);
        landmarks.add(l43);
        landmarks.add(l44);
        landmarks.add(l45);
        landmarks.add(l46);
        landmarks.add(l47);
        landmarks.add(l48);
        landmarks.add(l49);
        landmarks.add(l50);
        landmarks.add(l51);
        landmarks.add(l52);
        landmarks.add(l53);
        landmarks.add(l54);
        landmarks.add(l55);
        landmarks.add(l56);
    }

    public void getQuestions(){
        for(int i=0; i<landmarks.size(); i++){
            if(landmarks.get(i).getContinent().equalsIgnoreCase(location.toString())){
                QuestionsModel q = new QuestionsModel(landmarks.get(i).getID(),false);
                questions.add(q);
                countQuestions++;
            }
        }
        if(questions.size()==0){
            checkContinent=true;
        }

        for(int i=0; i<questions.size(); i++){
            Log.v("Q", String.valueOf(questions.get(i).getLandmarkID()));
        }
    }

    public void loadQuestion(){
        r = new Random();

        if(skipSelected==false){
            Log.v("PUMASOK",String.valueOf(countQuestions));
            int index=r.nextInt(countQuestions);
            Log.v("q","index:" );
            Log.v("q", String.valueOf(index));
            question = questions.get(index);
            int landmarkno= question.getLandmarkID();
            Log.v("q","list of questions left:");
            for(int i=0; i<questions.size(); i++){
                Log.v("q", String.valueOf(questions.get(i).getLandmarkID()));
            }
            Log.v("q","Landmark no:" );
            Log.v("q", String.valueOf(landmarkno));
            for(int i=0; i<landmarks.size(); i++){
                if(landmarks.get(i).getID()==landmarkno && countQuestions>0){  // && question.getAnswered()==true add is not yet answered
                    currLandmark=landmarks.get(i).getID();
                    file=landmarks.get(i).getFilename();
                    landmark = landmarks.get(i).getName();
                    loadImage(file);
                }
            }
        }
        else{
            boolean repeat=true;
            int newLandmark=0;
            do{
                newLandmark = r.nextInt(countQuestions);
                if(currLandmark!=questions.get(newLandmark).getLandmarkID()){
                    repeat=false;
                }
            }while(repeat);
            newQuestion = questions.get(newLandmark);

            for(int i=0; i<landmarks.size();i++){
                if(landmarks.get(i).getID()==newQuestion.getLandmarkID() && countQuestions>0){
                    currLandmark=landmarks.get(i).getID();
                    file=landmarks.get(i).getFilename();
                    landmark = landmarks.get(i).getName();
                    loadImage(file);
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LandmarkInfo.info_code) {
            if(countQuestions==0 && countCorrect==8){
                getCompleted(); //get completed landmarks

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
                SharedPreferences sharedPreferences=  getSharedPreferences("userCred", Context.MODE_PRIVATE);

                String userID= sharedPreferences.getString(USERKEY_NUM_KEY, "");
                final int numcoins = coinCtr;
                final int highScore = score;
                Log.d("HS", "onActivityResult: " + score);

                mDatabase.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("highScore").setValue(highScore);
                        dataSnapshot.getRef().child("numCoins").setValue(numcoins);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("User", databaseError.getMessage());
                    }
                });

                Intent i = new Intent(GuessLandmark.this,EnterLocation.class);
                i.putExtra("completedContinent", location);
                i.putExtra("REQUEST_CODE_KEY",1);
                startActivityForResult(i, 1);
                Log.d("loadMap", "WORKING");

            }
            else {
                Log.v("q","aftr first if");
                resetWord(letterAnswers);
                loadQuestion();
                playGame();
                setButtonsVisible();
                info.setText("");
                info.setBackgroundColor(0x000000);

            }
        }
    }

    public void nextPage(){
        String file = null;
        for(int j=0; j<landmarks.size(); j++){
            if(landmarks.get(j).getName().equals(landmark)){
                file=landmarks.get(j).getFilename();
            }
        }
        Intent i = new Intent(GuessLandmark.this,LandmarkInfo.class);
        i.putExtra("landmark",landmark);
        i.putExtra("filename",file);
        startActivityForResult(i,LandmarkInfo.info_code);
    }

    public String generateFinalString(String str) {
        String finalStr = "";

        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetter(str.charAt(i))) {
                finalStr += str.charAt(i);
            }
        }

        return finalStr;
    }

    public char[] getStringCharacters(String str, int charCtr){
        int ctr = 0;

        char[] chars = new char[charCtr];

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetter(str.charAt(i))) {
                chars[i] = str.charAt(i);
            }
        }

        return chars;
    }

    public Boolean checkIntArray(int n, int arr[]) {
        Boolean exist = false;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == n) {
                exist = true;
                i = arr.length;
            }
        }

        return exist;
    }

    public char randomLetter() {
        String alphabet= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random randomizer = new Random();
        char letter = alphabet.charAt(randomizer.nextInt(26));

        return letter;
    }

    public String getWord(TextView[] letters) {
        String word = "";

        for (int i = 0; i < letters.length; i++) {
            word += letters[i].getText().toString();
        }

        return word;
    }

    public int checkArrayContent() {
        int ctr = 0;

        for (int i = 0; i < indexList.length; i++) {
            if (indexList[i] == 0) {
                ctr++;
            }
        }

        return ctr;
    }

    public void resetWord( TextView[] answer) {
        for (int i = 0; i < answer.length; i++) {
            if (answer[i].getText().toString().equals("[a-zA-Z]")) {
                answer[i].setText("");
            }
        }
        for(int i=0; i<letterAnswers.length; i++){
            letterAnswers[i].setVisibility(View.GONE);
        }
    }

    public void insertWord(String letter, int id) {
        if (id == 0) {
            id = -1;
        }

        for (int i = 0; i < letterAnswers.length; i++) {
            if (letterAnswers[i].getText().toString().equals("_")) {
                letterAnswers[i].setTypeface(null, Typeface.NORMAL);
                letterAnswers[i].setText(letter);
                indexList[i] = id;
                i = letterAnswers.length;
            }
        }

        setCursor();
    }


    public void reveal() {
        if (coinCtr >= 5) {
            final String finalString = generateFinalString(landmark.toUpperCase());

            for (int i = 0; i < letterAnswers.length; i++) {
                letterAnswers[i].setTypeface(letterAnswers[i].getTypeface(), Typeface.BOLD);
                letterAnswers[i].setText(finalString.charAt(i) + "");
            }
            /*

            question.setAnswered(true);
            //count++;
            clear();

            nextPage();*/
            info.setBackgroundColor(0x8C2ecc71);
            info.setText("CORRECT!");
            info.setTextSize(20);



            for(int i=0; i<landmarks.size(); i++){
                if(landmarks.get(i).getID()==currLandmark){  // && question.getAnswered()==true add is not yet answered
                    landmarks.get(i).setAnswered(true);
                }
            }
            for(int i=0; i<questions.size(); i++){
                if(questions.get(i).getLandmarkID()==currLandmark){
                    questions.remove(i);
                }
            }
            //question.setAnswered(true);
            final int bonus = computeScoreCoins();
            //info.setText("CORRECT!");
            countQuestions--;
            countCorrect++;
            if(countQuestions==1){
                skipBtn.setEnabled(false);
            }

            //ADD

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
            SharedPreferences sharedPreferences=  getSharedPreferences("userCred", Context.MODE_PRIVATE);

            String userID = sharedPreferences.getString(USERKEY_NUM_KEY, "");

            //Log. e("SCORE", String.valueOf(score));

            mDatabase.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    SharedPreferences sharedPreferences=  getSharedPreferences("userCred", Context.MODE_PRIVATE);
                    User user = dataSnapshot.getValue(User.class);
                    int hscore= user.highScore;

                    dataSnapshot.getRef().child("numCoins").setValue(coinCtr);
                    dataSnapshot.getRef().child("highScore").setValue(score);

                    Log.e("DB SCORE", String.valueOf("DATABASE" + user.highScore));
                    Log.e("DB SCORE", String.valueOf("GAME SCORE" + score));

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(NUMCOINS_NUM_KEY, coinCtr);
                    editor.putInt(HIGHSCORE_NUM_KEY,  score);
                    editor.commit();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("User", databaseError.getMessage());
                }
            });
            //ADD

            setButtonsEnabled();
            nextPage();

            coinCtr -= 5;
            refreshCoins();
        }
        else {
            Toast.makeText(this, "Not enough coins!", Toast.LENGTH_SHORT).show();
        }
    }

    public void insertWordHint() {
        if (coinCtr > 0) {
            final String finalString = generateFinalString(landmark.toUpperCase());

            String letterHint = "";
            Random randomizer = new Random();
            int index = 0;
            int randomIndex = 0;

            clear();

            do {
                randomIndex = randomizer.nextInt(finalString.length());
                letterHint = finalString.charAt(randomIndex) + "";
                index = checkDisabled(letterHint);
            }
            while (!letterAnswers[randomIndex].getText().toString().equals("_") || index == -1);

            Log.d("INDEX", randomIndex + "-" + letterHint + "-" + index);

            letterAnswers[randomIndex].setTypeface(letterAnswers[randomIndex].getTypeface(), Typeface.BOLD);
            letterAnswers[randomIndex].setText(letterHint);
            letterAnswers[randomIndex].setOnClickListener(null);

            letterOptions[index].setEnabled(false);
            if (letterOptions[index].getId() == 0) {
                indexList[randomIndex] = -1;
            }
            else {
                indexList[randomIndex] = letterOptions[index].getId();
            }

            setCursor();

            String str = getWord(letterAnswers);
            if (checkArrayContent() == 0) {
                if (checkWord(str)) {
                    /*final int bonus = computeScoreCoins();
                    info.setText("CORRECT!");
                    question.setAnswered(true);
                    clear();
                    setButtonsEnabled();
                    nextPage();*/
                    info.setBackgroundColor(0x8C2ecc71);
                    info.setText("CORRECT!");
                    info.setTextSize(20);


                    for(int i=0; i<landmarks.size(); i++){
                        if(landmarks.get(i).getID()==currLandmark){  // && question.getAnswered()==true add is not yet answered
                            landmarks.get(i).setAnswered(true);
                        }
                    }
                    for(int i=0; i<questions.size(); i++){
                        if(questions.get(i).getLandmarkID()==currLandmark){
                            questions.remove(i);
                        }
                    }
                    //question.setAnswered(true);
                    final int bonus = computeScoreCoins();
                    coinCtr += bonus;
                    info.setBackgroundColor(0x8C2ecc71);
                    info.setText("CORRECT!");
                    info.setTextSize(20);
                    countQuestions--;
                    countCorrect++;
                    if(countQuestions==1){
                        skipBtn.setEnabled(false);
                    }

                    //ADD

                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
                    SharedPreferences sharedPreferences=  getSharedPreferences("userCred", Context.MODE_PRIVATE);

                    String userID = sharedPreferences.getString(USERKEY_NUM_KEY, "");

                    //Log. e("SCORE", String.valueOf(score));

                    mDatabase.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            SharedPreferences sharedPreferences=  getSharedPreferences("userCred", Context.MODE_PRIVATE);
                            User user = dataSnapshot.getValue(User.class);
                            int hscore= user.highScore;

                            dataSnapshot.getRef().child("numCoins").setValue(coinCtr);
                            dataSnapshot.getRef().child("highScore").setValue(score);

                            Log.e("DB SCORE", String.valueOf("DATABASE" + user.highScore));
                            Log.e("DB SCORE", String.valueOf("GAME SCORE" + score));

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt(NUMCOINS_NUM_KEY, coinCtr);
                            editor.putInt(HIGHSCORE_NUM_KEY,  score);
                            editor.commit();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("User", databaseError.getMessage());
                        }
                    });
                    //ADD
                    setButtonsEnabled();
                    nextPage();


                } else {
                    info.setBackgroundColor(0x8Ce74c3c);
                    info.setText("WRONG!");
                    info.setTextSize(20);
                }
            }

            coinCtr -= 1;
            refreshCoins();
        }
        else {
            Toast.makeText(this, "Not enough coins!", Toast.LENGTH_SHORT).show();
        }
    }


    public int checkDisabled(String letter) {
        int index = -1;

        for (int i = 0; i < letterOptions.length; i++) {
            if (letterOptions[i].getText().toString().equals(letter) && letterOptions[i].isEnabled()) {
                index = i;
                i = letterOptions.length;
            }
        }

        return index;
    }

    public void setCursor() {
        for (int i = 0; i < letterAnswers.length; i++) {
            if (letterAnswers[i].getText().toString().equals("_")) {
                letterAnswers[i].setTypeface(null, Typeface.NORMAL);
            }
        }
        for (int i = 0; i < letterAnswers.length; i++) {
            if (letterAnswers[i].getText().toString().equals("_")) {
                letterAnswers[i].setTypeface(letterAnswers[i].getTypeface(), Typeface.BOLD);
                i = letterAnswers.length;
            }
        }
    }

    public void removeWord(int id) {
        int index = indexList[id];
        indexList[id] = 0;
        if (index == -1) {
            index = 0;
        }

        letterOptions[index].setEnabled(true);
    }

    public void removeLastWord() {
        int id = 0;

        for (int i = letterAnswers.length - 1; i >= 0; i--) {
            Log.d("TYPE", "removeLastWord: " + letterAnswers[i].getTypeface());
            if (!letterAnswers[i].getText().toString().equals("_") && letterAnswers[i].getTypeface() == null) {
                letterAnswers[i].setText("_");
                id = indexList[i];

                if (id == -1) {
                    id = 0;
                }
                indexList[i] = 0;
                i = -1;
            }
        }

        setCursor();

        letterOptions[id].setEnabled(true);
    }


    public boolean checkWord(String answer) {
        boolean check = false;

        if (getWord(letterAnswers).equals(answer)) {
            check = true;
        }

        return check;
    }

    public int computeScoreCoins () {
        int len = letterAnswers.length;
        int bonus = 2;
        score += len * 10;
        refreshCoins();

        return bonus;
    }


    public void onResume(){
        super.onResume();
    }
/*
    public void saveData(){
        sharedPreferences = getApplicationContext().getSharedPreferences("GuessLandmark", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        int count=0;

        for(int i=0; i<landmarks.size(); i++){
            if(landmarks.get(i).getAnswered()==true){
                editor.putString("answeredLandmarks"+i, landmarks.get(i).getID().toString());
                count++;
            }
        }
        editor.putString("count",String.valueOf(count));
        editor.apply();
        editor.commit();
    }
    public void onDestroy(){
        saveData();
        super.onDestroy();
    }*/


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        final String str = generateFinalString(landmark);
        Log.d("GAY", "onKeyDown: " + checkArrayContent());
        if (checkArrayContent() == str.length()) {
            if(keyCode== KeyEvent.KEYCODE_BACK){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(GuessLandmark.this);
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Are you sure you want to exit? Your progress will be lost.");
                alertDialog.setPositiveButton("CONFIRM",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                GuessLandmark.this.finish();
                                Intent i = new Intent(GuessLandmark.this,EnterLocation.class);
                                startActivity(i);
                            }
                        });
                alertDialog.setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return true;
            }

        }
        else {
            removeLastWord();
        }
        return false;
    }
    public void getCompleted(){
        for(int i=0; i<landmarks.size(); i++){
            if(landmarks.get(i).getAnswered()==true){
                completedID.add(landmarks.get(i).getID());
            }
        }
    }
}

