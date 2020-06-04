package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class RequestActivity extends AppCompatActivity implements View.OnClickListener{
    Button submit;
    EditText name, dob, mobile, aadhar;
    Spinner blood,city;
    SQLiteDatabase db;
    static String b;
    static String p;
    private AwesomeValidation awesomeValidation;
    DatabaseReference rootRef,demorRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        submit = findViewById(R.id.Rsubmit);
        name = findViewById(R.id.Rname);
        dob = findViewById(R.id.Rdob);
        blood = findViewById(R.id.Rblood);
        city = findViewById(R.id.Rcity);
        mobile =  findViewById(R.id.Rmobile);
        aadhar = findViewById(R.id.Raadhar);
        submit.setOnClickListener(this);
        rootRef = FirebaseDatabase.getInstance().getReference("BloodBank");
        demorRef = rootRef.child("Donee");

        db=openOrCreateDatabase("BloodBank", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS donee(name VARCHAR, dob DATE, bloodgroup VARCHAR, mobile NUMBER,city VARCHAR, aadhar VARCHAR, appointDate VARCHAR, appointTime VARCHAR)");
    }

    @Override
    public void onClick(View view) {
        if(view == submit){
            awesomeValidation.addValidation(this, R.id.Rname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
            awesomeValidation.addValidation(this, R.id.Rmobile, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
            awesomeValidation.addValidation(this, R.id.Rdob, "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.dateerror);
            if(awesomeValidation.validate() && cityvali() && bloodvali()){
                if(aadhar.getText().length()==16){
                    b = blood.getSelectedItem().toString();
                    p = city.getSelectedItem().toString();

//                    RequestorDetails rd = new RequestorDetails();
//                    rd.setRname(name.getText().toString());
//                    rd.setRblood(blood.getSelectedItem().toString());
//                    rd.setRcity(city.getSelectedItem().toString());
//                    rd.setRdob(dob.getText().toString());
//                    rd.setRmobile(mobile.getText().toString());
//                    String fbID = demorRef.push().getKey();
//                    demorRef.child(fbID).setValue(rd);
//
//                    db.execSQL("INSERT INTO donee(name,dob,bloodgroup,mobile,city,aadhar) VALUES('" + name.getText() + "','" + dob.getText() + "','" + blood.getSelectedItem().toString() + "','" + mobile.getText() + "','"+ city.getSelectedItem().toString() +"','" + aadhar.getText() + "')");
                    Intent i = new Intent(RequestActivity.this, Quantity.class);
                    startActivity(i);
                }
                else {
                    aadhar.setError("Enter 16 digit Aadhar number");
                }
            }
            else{
                Toast.makeText(getApplicationContext(),"Please fill everything",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private Boolean bloodvali(){
        if(blood.getSelectedItem().toString().equals("--select your blood type--")){
            Toast.makeText(getApplicationContext(),"Select blood type",Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }
    private boolean cityvali(){
        if(city.getSelectedItem().toString().equals("--select your city--")){
            Toast.makeText(getApplicationContext(),"Select city",Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }
    public static String blood(){
        return b;
    }
    public static String city(){
        return p;
    }
}
