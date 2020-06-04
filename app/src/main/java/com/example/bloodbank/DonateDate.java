package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;


public class DonateDate extends AppCompatActivity implements View.OnClickListener{
    Button proceed;
    DatePicker dp;
    SQLiteDatabase db;
    String date,mon,year;
    int cd,cm,cy,bd,bm,by;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_date);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        db=openOrCreateDatabase("BloodBank", Context.MODE_PRIVATE, null);
        proceed =findViewById(R.id.next);
        dp = findViewById(R.id.datepick);
        proceed.setOnClickListener(this);
        cy = Calendar.getInstance().get(Calendar.YEAR);
        cm = Calendar.getInstance().get(Calendar.MONTH)+1;
        cd = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);



    }

    @Override
    public void onClick(View view) {
        if(view == proceed){
            date = String.valueOf(dp.getDayOfMonth());
            mon = String.valueOf(dp.getMonth() + 1);
            year = String.valueOf(dp.getYear());
            if(Integer.parseInt(year) >= Calendar.getInstance().get(Calendar.YEAR) && (Integer.parseInt(mon) >= (Calendar.getInstance().get(Calendar.MONTH) + 1)) && (Integer.parseInt(date) > (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)))) {
                db.execSQL("UPDATE donor SET appointDate = '" + date + "/" + mon + "/" + year + "' WHERE Name = '" + DonorLogin.getName() + "'");
                Intent i = new Intent(DonateDate.this, DonateTime.class);
                startActivity(i);
            }
            else {
                Toast.makeText(getApplicationContext(),"Invalid Date for appointment",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
