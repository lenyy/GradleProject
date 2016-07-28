package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.jokedisplay.JokeDisplayActivity;
import com.example.prrosa.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;


class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    private static MyApi myApiService = null;
    private Context context;

    private static final String STRING_INTENT = "joke";

    public interface AsyncResponse {
        void deliverTestResult(String result);
    }

    public AsyncResponse delegate = null;

    public EndpointsAsyncTask(AsyncResponse response) {
        delegate = response;
    }

    @Override
    protected String doInBackground(Context... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://smartnotes.ddns.net:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0];

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            Log.e("EndpintsAsyncTask", " error : " + e.getMessage());
        }
        return "No Message";
    }


    @Override
    protected void onPostExecute(String result) {
        delegate.deliverTestResult(result);
        Intent intent = new Intent(context, JokeDisplayActivity.class);
        intent.putExtra(STRING_INTENT, result);
        context.startActivity(intent);
    }
}
