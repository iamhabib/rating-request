package com.dimiklab.ratingrequest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dimiklab.ratingrequestlibrary.RatingRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RatingRequest.with(MainActivity.this)
                .message("Hello !!!")
                .scheduleAfter(1)
                .yesButtonText("OK")
                .yesButtonTextColor(Color.RED)
                .delay(20*1000)
                .register();
    }

    @Override
    public void onBackPressed() {
       // RatingRequest.cancel();
        super.onBackPressed();
    }
}
