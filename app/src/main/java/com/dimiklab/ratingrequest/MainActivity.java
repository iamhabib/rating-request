package com.dimiklab.ratingrequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dimiklab.ratingrequestlibrary.RatingDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RatingDialog ratingDialog=new RatingDialog(MainActivity.this);
        ratingDialog.displayDialog();
    }
}
