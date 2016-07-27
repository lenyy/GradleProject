package com.example.jokedisplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class JokeDisplayActivity extends AppCompatActivity {

    private static final String STRING_INTENT = "joke";

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joke_display_activity);
        Intent intent = getIntent();
        TextView textView = (TextView) findViewById(R.id.joke);
        if ( intent != null)
        {
            textView.setText(intent.getStringExtra(STRING_INTENT));
        }
    }
}
