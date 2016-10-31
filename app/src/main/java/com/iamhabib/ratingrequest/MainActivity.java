package com.iamhabib.ratingrequest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.iamhabib.ratingrequestlibrary.RatingRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RatingRequest.with(this)
                .scheduleAfter(7) // invoke when later button click, default 5
                .agreeButtonText("Sure!")
                .laterButtonSeletor(R.drawable.button_accept)
                .laterButtonText("Later")
                .doneButtonText("Already Done")
                .backgroundResource(R.color.colorPrimary)
                .message("Are you enjoying our app?\n Please give us a review")
                .listener(new RatingRequest.ClickListener() {
                    @Override
                    public void onAgreeButtonClick() {

                    }

                    @Override
                    public void onDoneButtonClick() {
                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onLaterButtonClick() {

                    }
                })
                .cancelable(false) // default true
                .delay(10 * 1000) // after 10 second dialog will be shown, default 1000 milliseconds
                .register();
    }
}
