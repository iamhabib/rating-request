package com.iamhabib.ratingrequestlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
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

        private Button btn_agree, btn_done, btn_later;
        SharedPreferences settings;
        ClickListener listener;
        Context context;
        private static long delayTime = 1000;
        private static int scheduleAfter = 5;
        private boolean isCancelable=true;
        View v;
        static Handler dismissHandler = new Handler();
        static Runnable dialogRunnable;

        public Builder(final Context context) {
            this.context = context;
            v = LayoutInflater.from(context).inflate(R.layout.dialog_view, null);
            btn_agree = (Button) v.findViewById(R.id.btn_yes);
            btn_done = (Button) v.findViewById(R.id.btn_done);
            btn_later = (Button) v.findViewById(R.id.btn_later);
            settings = context.getSharedPreferences("ReviewDialogPref", Context.MODE_PRIVATE);
        }

        private String getNextDate(int days) {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, days);
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

        public Builder agreeButtonText(String yesButtonText) {
            btn_agree.setText(yesButtonText);
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

        public Builder backgroundResource(int res) {
            LinearLayout layout = (LinearLayout) v.findViewById(R.id.lay_full);
            layout.setBackgroundResource(res);
            return this;
        }

        public Builder agreeButtonSeletor(@DrawableRes int seletor) {
            btn_agree.setBackgroundResource(seletor);
            return this;
        }

        public Builder doneButtonSeletor(@DrawableRes int seletor) {
            btn_done.setBackgroundResource(seletor);
            return this;
        }

        public Builder laterButtonSeletor(@DrawableRes int seletor) {
            btn_later.setBackgroundResource(seletor);
            return this;
        }

        public Builder agreeButtonTextColor(int color) {
            btn_agree.setTextColor(color);
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

        public Builder cancelable(boolean isCancelable){
            this.isCancelable=isCancelable;
            return this;
        }

        public Builder listener(ClickListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder register() {
            if (settings.getBoolean("isLaterEnable", false) && getTodayDate().equalsIgnoreCase(settings.getString("later_date", ""))) {
                dismissHandler.postDelayed(dialogRunnable, delayTime);
            } else if (settings.getBoolean("isFirstTime", true)) {
                dismissHandler.postDelayed(dialogRunnable, delayTime);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("isFirstTime", false);
                editor.putBoolean("isLaterEnable", true);
                editor.commit();
            }
            return this;
        }

        private void initRunnable(){
            dialogRunnable = new Runnable() {
                @Override
                public void run() {
                    if (((Activity) context).isFinishing()) {
                        return;
                    }

                    final AlertDialog ratingDialog = new AlertDialog.Builder(context)
                            .setView(v)
                            .setCancelable(isCancelable)
                            .create();
                    ratingDialog.show();

                    btn_agree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("isLaterEnable", true);
                            editor.putString("later_date", getNextDate(1));
                            editor.commit();
                            context.startActivity(intent);
                            ratingDialog.dismiss();
                            listener.onAgreeButtonClick();
                        }
                    });

                    btn_done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("isLaterEnable", false);
                            editor.commit();
                            ratingDialog.dismiss();
                            listener.onDoneButtonClick();
                        }
                    });

                    btn_later.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("isLaterEnable", true);
                            editor.putString("later_date", getNextDate(scheduleAfter));
                            editor.commit();
                            ratingDialog.dismiss();
                            listener.onLaterButtonClick();
                        }
                    });
                }
            };
        }
    }

    public interface ClickListener {
        public void onAgreeButtonClick();

        public void onDoneButtonClick();

        public void onLaterButtonClick();
    }
}
