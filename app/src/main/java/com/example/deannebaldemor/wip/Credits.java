package com.example.deannebaldemor.wip;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class Credits extends Activity {
    private List<Source> sources = new ArrayList<>();
    private RecyclerView recyclerView;
    private CreditsAdapter creditsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_credits);

        setupUI();
        createList();
    }
    public void setupUI(){
        recyclerView = findViewById(R.id.creditsRecyclerView);
        creditsAdapter = new CreditsAdapter(sources);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(creditsAdapter);
    }

    public void createList(){
        Log.v("TAG","pumasok");
        Source s1 = new Source("https://www.educationaltravelfinder.com/");
        sources.add(s1);
        Source s2 = new Source("http://www.airpano.com/Photogallery-AirPano.php");
        sources.add(s2);
        Source s3 = new Source("http://johnsigrid.blogspot.com/2014/02/s-americaantarctica-12302013-danco-coast.html");
        sources.add(s3);
        Source s4 = new Source("https://ingerhogstrom.wordpress.com/2014/09/06/storm-at-saunders-island-the-falkland-islands/");
        sources.add(s4);
        Source s5 = new Source("https://myitchytravelfeet.com/half-moon-island-antarctica/");
        sources.add(s5);
        Source s6 = new Source("http://wildtoronto.com/my-antarctica/");
        sources.add(s6);
        Source s7 = new Source("http://www.ukaht.org/visit/visiting-port-lockroy/");
        sources.add(s7);
        Source s8 = new Source("https://www.britannica.com/place/Ross-Ice-Shelf");
        sources.add(s8);
        Source s9 = new Source("https://www.clean2antarctica.nl/en/blog-updates");
        sources.add(s9);


        Source s10 = new Source("https://victoriafallstourism.org/victoria-falls-photos/");
        sources.add(s10);
        Source s11 = new Source("http://www.pinsdaddy.com/ramesseum-luxor-temple_zgjCuMdEHH%7CUkWzo3gaeWxLoSroJN2KAcuTav2hPy*E/");
        sources.add(s11);
        Source s12 = new Source("https://www.pexels.com/photo/sand-desert-statue-pyramid-2359/");
        sources.add(s12);
        Source s13 = new Source("https://unsplash.com/search/photos/namib-desert-namibia");
        sources.add(s13);
        Source s14 = new Source("https://goluxortours.com/egypt-travel-guide/luxor-attractions/");
        sources.add(s14);
        Source s15 = new Source("https://www.askideas.com/55-most-incredible-hassan-tower-pictures-and-photos/ ");
        sources.add(s15);
        Source s16 = new Source("https://www.pexels.com/photo/sand-desert-statue-pyramid-2359/");
        sources.add(s16);
        Source s17 = new Source("https://unsplash.com/photos/sYZ1TFtZ3Cw");
        sources.add(s17);


        Source s18 = new Source("http://www.sciencemag.org/news/2016/09/some-relief-great-barrier-reef");
        sources.add(s18);
        Source s19 = new Source("https://tripuniq.com/sydney/");
        sources.add(s19);
        Source s20 = new Source("https://www.australia.com/en/places/adelaide-and-surrounds/guide-to-kangaroo-island.html");
        sources.add(s20);
        Source s21 = new Source("https://www.atlasobscura.com/places/port-arthur-penal-colony");
        sources.add(s21);
        Source s22 = new Source("http://www.comparestudio.com/best-places-to-visit-in-australia/");
        sources.add(s22);
        Source s23 = new Source("https://www.klook.com/activity/2754-tower-buffet-sydney/");
        sources.add(s23);
        Source s24 = new Source("http://foundtheworld.com/how-were-the-twelve-apostles-formed/");
        sources.add(s24);
        Source s25 = new Source("https://www.scoop.it/t/uluru-perception-protection-of-places-stage-2/p/4062458448/2016/04/12/uluru-kata-tjuta-national-park-parks-australia");
        sources.add(s25);

        Source s26 = new Source("https://www.ourbreathingplanet.com/angel-falls/");
        sources.add(s26);
        Source s27 = new Source("https://unsplash.com/search/photos/christ-the-redeemer-brazil");
        sources.add(s27);
        Source s28 = new Source("https://travel.trythis.co/10-most-beautiful-places-in-the-world-to-visit/iguazu-falls-argentina-brazil-border/");
        sources.add(s28);
        Source s29 = new Source("https://unsplash.com/search/photos/cusco%2C-peru");
        sources.add(s29);
        Source s30 = new Source("http://mapio.net/pic/p-15554967/");
        sources.add(s30);
        Source s31 = new Source("https://www.lorenzoexpeditions.com/machupicchu-tours/sacred-valley.html");
        sources.add(s31);
        Source s32 = new Source("http://www.vivowallpaper.com/wallpaper/mayo-88344");
        sources.add(s32);
        Source s33 = new Source("http://www.capital.cl/vida-y-estilo/2015/06/03/109058/5-salares-mas-grandes-del-mundo/");
        sources.add(s33);
        Source s34 = new Source("https://skyrisecities.com/database/projects/arena-central");
        sources.add(s34);
        Source s35 = new Source("https://unsplash.com/search/photos/chichen-itza-mexico");
        sources.add(s35);
        Source s36 = new Source("http://the1709blog.blogspot.com/2018/03/");
        sources.add(s36);
        Source s37 = new Source("https://www.localtalks.ca/");
        sources.add(s37);
        Source s38 = new Source("https://www.pexels.com/photo/nature-national-park-arches-national-park-62600/");
        sources.add(s38);
        Source s39 = new Source("https://unsplash.com/search/photos/mount-rushmore");
        sources.add(s39);
        Source s40 = new Source("http://embassysuitesniagara.com/falls-cam.php");
        sources.add(s40);

        Source s41 = new Source("https://pixabay.com/en/photos/space%20needle/");
        sources.add(s41);
        Source s42 = new Source("https://pixabay.com/en/photos/cambodia/?cat=religion");
        sources.add(s42);
        Source s43 = new Source("https://www.havehalalwilltravel.com/blog/the-only-itinerary-youll-need-for-your-5d4n-stay-in-yogyakarta/");
        sources.add(s43);
        Source s44 = new Source("https://iphonexpapers.com/nn96-disney-world-castle-sky/");
        sources.add(s44);
        Source s45 = new Source("https://asiacitytours.com/location/halong-bay/");
        sources.add(s45);
        Source s46 = new Source("http://justacarryon.com/2018/03/petra/");
        sources.add(s46);

        Source s47 = new Source("https://whc.unesco.org/en/list/642");
        sources.add(s47);
        Source s48 = new Source("https://steemit.com/history/@neeeeraj/red-fort-delhi-india");
        sources.add(s48);
        Source s49 = new Source("https://unsplash.com/search/photos/taj-mahal");
        sources.add(s49);
        Source s50 = new Source("http://deaconmarty.com/our-lord-jesus-christ-king-of-the-universe-by-deacon-marty-mcindoe/");
        sources.add(s50);
        Source s51 = new Source("https://www.pexels.com/search/colosseum/");
        sources.add(s51);
        Source s52 = new Source("https://unsplash.com/photos/4B6x7ESZIvs");
        sources.add(s52);
        Source s53 = new Source("http://www.visionsoftravel.org/national-pantheon-lisbon-portugal/");
        sources.add(s53);
        Source s54 = new Source("https://sonomotors.com/test-drives.html/");
        sources.add(s54);
        Source s55 = new Source("http://www.tiberlimo.com/shore-excursions-ports-italy/shore-excursions-from-livorno-port/pisa-and-lucca-shore-excursion/");
        sources.add(s55);
        Source s56 = new Source("https://tomcottrell.wordpress.com/2011/05/02/deception-island-dare/");
        sources.add(s56);
        Source s57 = new Source("https://pixabay.com/en/photos/trevi%20fountain/");
        sources.add(s57);

        Source s58 = new Source("http://johnsigrid.blogspot.com/2014/02/s-americaantarctica-12302013-danco-coast.html");
        sources.add(s58);
        Source s59= new Source("http://www.antarcticimages.com/");
        sources.add(s59);

        creditsAdapter.notifyDataSetChanged();
    }
}
