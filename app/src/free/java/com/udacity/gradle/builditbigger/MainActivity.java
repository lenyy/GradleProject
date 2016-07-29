package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity implements EndpointsAsyncTask.AsyncResponse{

    InterstitialAd mInterstitialAd;

    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        requestNewInterstitial();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        new EndpointsAsyncTask(this).execute(this);
        if ( Looper.myLooper() == Looper.getMainLooper() ) {
            if ( mInterstitialAd.isLoaded() && result.isEmpty() )
                mInterstitialAd.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        result = "";
        requestNewInterstitial();
    }

    /**
     * Only used to test the AsyncTask response.
     * @return The AsyncTask result.
     */
    public String getResult()
    {
        return result;
    }


    @Override
    public void deliverTestResult( String result ) {
        this.result = result;
    }


    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("B4F381F93F645F0BF22C75C2CFA88F84")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
}
