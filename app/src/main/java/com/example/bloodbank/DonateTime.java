package com.example.bloodbank;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class DonateTime extends AppCompatActivity implements View.OnClickListener {
    Button book;
    SQLiteDatabase db;
    TimePicker tp;
    String sname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_time);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        db=openOrCreateDatabase("BloodBank", Context.MODE_PRIVATE, null);
        book = findViewById(R.id.book);
        tp = findViewById(R.id.time);
        book.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        if(view == book) {
            if (tp.getHour() > 9 && tp.getHour() < 17) {
                db.execSQL("UPDATE donor SET appointTime = '" + String.format("%02d",tp.getHour()) + ":" + String.format("%02d",tp.getMinute()) + "' WHERE Name = '" + DonorLogin.getName() + "'");
                sname = DonorLogin.getName();
                Cursor c = db.rawQuery("SELECT Name,bloodgroup,appointDate,appointTime FROM donor WHERE Name = '" + sname + "'", null);
                StringBuffer buffer = new StringBuffer();
                while (c.moveToNext()) {
                    buffer.append("Name : " + c.getString(0) + "\n\n");
                    buffer.append("Blood : " + c.getString(1) + "\n\n");
                    buffer.append("Date: " + c.getString(2) + "\n\n");
                    buffer.append("Time  : " + c.getString(3) + "\n\n");
                }
                showMessage("Your Appointment", buffer.toString());
            }
            else {
                Toast.makeText(getApplicationContext(),"Available hours :: 10:00 - 17:00",Toast.LENGTH_SHORT).show();
            }
        }

        }
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}

