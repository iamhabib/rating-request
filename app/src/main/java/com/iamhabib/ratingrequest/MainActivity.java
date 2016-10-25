package com.iamhabib.ratingrequest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.iamhabib.ratingrequestlibrary.RatingRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RatingRequest.with(MainActivity.this)
                .scheduleAfter(7)
                .yesButtonText("Sure!")
                .delay(20*1000)
                .register();
    }
}
