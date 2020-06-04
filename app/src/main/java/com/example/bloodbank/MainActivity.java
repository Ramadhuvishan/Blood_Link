package com.example.bloodbank;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    Button StatusBt, RequestBt,DonorBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

//        StatusBt = (Button) findViewById(R.id.status);
//        StatusBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MainActivity.this, Quantity.class);
//                startActivity(i);
//
//            }
//        });

        RequestBt = (Button) findViewById(R.id.request);
        RequestBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,RequestActivity.class);
                startActivity(i);
            }
        });

        DonorBt = (Button) findViewById(R.id.donate);
        DonorBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,DonorLogin.class);
                startActivity(i);
            }
        });
    }

}
