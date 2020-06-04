package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Quantity extends AppCompatActivity {
    SQLiteDatabase db;
    String blood, place;
    DatabaseReference viewRef;
    ListView listViewDonors;
    List<DonorDetails> donorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        listViewDonors = (ListView) findViewById(R.id.listViewDonors);

        donorList = new ArrayList<>();
        blood = RequestActivity.blood();
        place = RequestActivity.city();

        viewRef = FirebaseDatabase.getInstance().getReference("Donor");


        viewRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                donorList.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    DonorDetails dd = ds.getValue(DonorDetails.class);
                    donorList.add(dd);
                }
                RequestorDetails adapter = new RequestorDetails(Quantity.this, donorList);
                listViewDonors.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    //        db = openOrCreateDatabase("BloodBank", Context.MODE_PRIVATE, null);
//        Cursor c = db.rawQuery("SELECT Name,bloodgroup,city,mobile FROM donor WHERE bloodgroup = '" + blood + "' AND city = '" + place + "'", null);
//        StringBuffer buffer = new StringBuffer();
//        while (c.moveToNext()) {
//            buffer.append("Name   : " + c.getString(0) + "\n");
//            buffer.append("Blood  : " + c.getString(1) + "\n");
//            buffer.append("Place  : " + c.getString(2) + "\n");
//            buffer.append("Mobile : " + c.getString(3) + "\n\n\n");
//
//        }
//        putmatches(buffer.toString());
//}
//    public void putmatches (String details){
//        match.setText(details);
//
//
//    }

}
