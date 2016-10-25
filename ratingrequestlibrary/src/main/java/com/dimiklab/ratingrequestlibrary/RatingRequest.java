package com.dimiklab.ratingrequestlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by HABIB on 10/25/2016.
 */

public class RatingRequest {

    public static Builder with(Context context) {
        return new Builder(context);
    }

    public static class Builder {
        Button btn_yes;
        Button btn_done;
        Button btn_later;
        SharedPreferences settings;
        Context context;
        public static long delayTime = 100;
        private static int scheduleAfter = 5;
        View v;
        static Handler dismissHandler = new Handler();
        static Runnable dismissRunnable;

        public Builder(final Context context) {
            this.context = context;

            v = LayoutInflater.from(context).inflate(R.layout.dialog_view, null);
            btn_yes = (Button) v.findViewById(R.id.btn_yes);
            btn_done = (Button) v.findViewById(R.id.btn_done);
            btn_later = (Button) v.findViewById(R.id.btn_later);
            settings = context.getSharedPreferences("ReviewDialogPref", Context.MODE_PRIVATE);
            dismissRunnable = new Runnable() {
                @Override
                public void run() {
                    if (((Activity) context).isFinishing()) {
                        return;
                    }
                    Button btn_yes = (Button) v.findViewById(R.id.btn_yes);
                    Button btn_done = (Button) v.findViewById(R.id.btn_done);
                    Button btn_later = (Button) v.findViewById(R.id.btn_later);

                    final AlertDialog ratingDialog = new AlertDialog.Builder(context)
                            .setView(v)
                            .setCancelable(false)
                            .create();
                    ratingDialog.show();

                    btn_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("isEnableDialog", true);
                            editor.commit();
                            context.startActivity(intent);
                            ratingDialog.dismiss();
                        }
                    });

                    btn_done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("isEnableDialog", false);
                            editor.commit();
                            ratingDialog.dismiss();
                        }
                    });

                    btn_later.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("isEnableDialog", false);
                            editor.putBoolean("isLaterEnable", true);
                            editor.putString("later_date", getNextDate());
                            editor.commit();
                            ratingDialog.dismiss();
                        }
                    });
                }
            };
        }

        private String getNextDate() {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, scheduleAfter);
            return format1.format(calendar.getTime());
        }

        private String getTodayDate() {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            return format1.format(calendar.getTime());
        }

        public Builder message(String message) {
            TextView title = (TextView) v.findViewById(R.id.tv_title);
            title.setText(message);
            return this;
        }

        public Builder scheduleAfter(int days) {
            this.scheduleAfter = days;
            return this;
        }

        public Builder yesButtonText(String yesButtonText) {
            btn_yes.setText(yesButtonText);
            return this;
        }

        public Builder doneButtonText(String doneButtonText) {
            btn_done.setText(doneButtonText);
            return this;
        }

        public Builder laterButtonText(String laterButtonText) {
            btn_later.setText(laterButtonText);
            return this;
        }

        public Builder backgroundColor(int color) {
            LinearLayout layout = (LinearLayout) v.findViewById(R.id.lay_full);
            layout.setBackgroundColor(color);
            return this;
        }

        public Builder yesButtonSeletor(int seletor) {
            btn_yes.setBackgroundResource(seletor);
            return this;
        }

        public Builder doneButtonSeletor(int seletor) {
            btn_done.setBackgroundResource(seletor);
            return this;
        }

        public Builder laterButtonSeletor(int seletor) {
            btn_later.setBackgroundResource(seletor);
            return this;
        }

        public Builder yesButtonTextColor(int color) {
            btn_yes.setTextColor(color);
            return this;
        }

        public Builder doneButtonTextColor(int color) {
            btn_done.setTextColor(color);
            return this;
        }

        public Builder laterButtonTextColor(int color) {
            btn_later.setTextColor(color);
            return this;
        }

        public Builder delay(long timeInMillis) {
            this.delayTime = timeInMillis;
            return this;
        }

        public Builder register() {
            if (settings.getBoolean("isEnableDialog", true)) {
                dismissHandler.postDelayed(dismissRunnable, delayTime);
            } else if (settings.getBoolean("isLaterEnable", false) && getTodayDate().equalsIgnoreCase(settings.getString("later_date", ""))) {
                dismissHandler.postDelayed(dismissRunnable, delayTime);
            }
            return this;
        }

    }
}
