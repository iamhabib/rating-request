package com.dimiklab.ratingrequestlibrary;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mukesh.tinydb.TinyDB;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by HABIB on 10/24/2016.
 */

public class RatingDialog {
    TinyDB tinyDB;
    Context context;
    View v;
    private int scheduleAfter=5;

    public RatingDialog(Context context) {
        this.context=context;
        v= LayoutInflater.from(context).inflate(R.layout.dialog_view, null);
        tinyDB=new TinyDB(context);
    }

    public void register(){
        if(!isDisableDialog()){
            showDialog(v);
        }else if(isLaterEnable()){
            if(getTodayDate().equalsIgnoreCase(tinyDB.getString("later_date"))){
                showDialog(v);
            }
        }

    }

    public void setYesButtonText(String yesButtonText){
        Button yesBtn=(Button)v.findViewById(R.id.btn_yes);
        yesBtn.setText(yesButtonText);
    }

    public void setDoneButtonText(String doneButtonText) {
        Button doneBtn=(Button)v.findViewById(R.id.btn_done);
        doneBtn.setText(doneButtonText);
    }

    public void setLaterButtonText(String laterButtonText) {
        Button laterBtn=(Button)v.findViewById(R.id.btn_later);
        laterBtn.setText(laterButtonText);
    }

    public void setTitleText(String titleText) {
        TextView title=(TextView)v.findViewById(R.id.tv_title);
        title.setText(titleText);
    }

    public void setScheduleAfter(int scheduleAfter) {
        this.scheduleAfter = scheduleAfter;
    }

    public void setBackgroundColor(int color){
        LinearLayout layout=(LinearLayout)v.findViewById(R.id.lay_full);
        layout.setBackgroundColor(color);
    }

    public void setYesButtonSeletor(int seletor){
        Button btn_yes=(Button)v.findViewById(R.id.btn_yes);
        btn_yes.setBackgroundResource(seletor);
    }

    public void setDoneButtonSeletor(int seletor){
        Button btn_done=(Button)v.findViewById(R.id.btn_done);
        btn_done.setBackgroundResource(seletor);
    }

    public void setLaterButtonSeletor(int seletor){
        Button btn_later=(Button)v.findViewById(R.id.btn_later);
        btn_later.setBackgroundResource(seletor);
    }

    private boolean isDisableDialog(){
        return tinyDB.getBoolean("isDisableDialog");
    }

    private boolean isLaterEnable(){
        return tinyDB.getBoolean("isLaterEnable");
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

    private void showDialog(View view){
        Button btn_yes=(Button)v.findViewById(R.id.btn_yes);
        Button btn_done=(Button)v.findViewById(R.id.btn_done);
        Button btn_later=(Button)v.findViewById(R.id.btn_later);

        final AlertDialog ratingDialog=new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(false)
                .create();
        ratingDialog.show();

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id="+context.getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
                ratingDialog.dismiss();
                tinyDB.putBoolean("isDisableDialog", false);
            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tinyDB.putBoolean("isDisableDialog", true);
                ratingDialog.dismiss();
            }
        });

        btn_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tinyDB.putBoolean("isDisableDialog", true);
                tinyDB.putBoolean("isLaterEnable", true);
                tinyDB.putString("later_date", getNextDate());
                ratingDialog.dismiss();
            }
        });
    }
}
