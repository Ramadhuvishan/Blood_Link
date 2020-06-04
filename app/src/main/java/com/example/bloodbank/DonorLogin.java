package com.example.bloodbank;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.app.AlertDialog.Builder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import androidx.appcompat.app.AppCompatActivity;

public class DonorLogin extends AppCompatActivity implements View.OnClickListener {
    Button register,login;
    EditText uname, pwd, name, dob, mobile, password, confirm;
    Spinner blood,city;
    SQLiteDatabase db;
    static String n,p,m;
    DatabaseReference rootRef,demoRef,userRef,bloodRef,loginRef;
    private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        uname = findViewById(R.id.username);
        pwd = findViewById(R.id.password);
        name = findViewById(R.id.name);
        dob = findViewById(R.id.dob);
        mobile = findViewById(R.id.mobile);
        blood = findViewById(R.id.bloodgroup);
        city = findViewById(R.id.city);
        password = findViewById(R.id.password2);
        confirm = findViewById(R.id.c_password);
        register = findViewById(R.id.newDonor);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        rootRef = FirebaseDatabase.getInstance().getReference("BloodBank");
        demoRef = rootRef.child("Donor");
        loginRef = rootRef.child("D Login");
        db=openOrCreateDatabase("BloodBank", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS donor(Name VARCHAR, dob DATE, bloodgroup VARCHAR, mobile NUMBER, city VARCHAR, password VARCHAR, confirm VARCHAR, appointDate VARCHAR, appointTime VARCHAR)");

    }
    @Override
    public void onClick(View view) {
        if (view == register) {
            awesomeValidation.addValidation(this, R.id.name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
            awesomeValidation.addValidation(this, R.id.mobile, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
            awesomeValidation.addValidation(this, R.id.dob, "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.dateerror);
            if (awesomeValidation.validate() && validatePassword(password) && validatePassword(confirm) && bloodvali() && cityvali())  {
                if (password.getText().toString().equals(confirm.getText().toString())) {

//                        db.execSQL("INSERT INTO donor(Name,dob,bloodgroup,mobile,city,password,confirm) VALUES('" + name.getText() + "','" + dob.getText() + "','" + blood.getSelectedItem().toString() + "','" + mobile.getText() + "','"+city.getSelectedItem().toString()+"','" + password.getText() + "','" + confirm.getText() + "')");
                    userRef = demoRef.child(city.getSelectedItem().toString());
                    DonorDetails dd = new DonorDetails();
                    dd.setDname(name.getText().toString());
                    dd.setDblood(blood.getSelectedItem().toString());
                    dd.setDcity(city.getSelectedItem().toString());
                    dd.setDdob(dob.getText().toString());
                    dd.setDmobile(mobile.getText().toString());
                    dd.setDpass(password.getText().toString());
                    DonorDetails dl = new DonorDetails();
                    dl.setDmobile(mobile.getText().toString());
                    dl.setDpass(password.getText().toString());

                        String fbID = demoRef.push().getKey();
//                    userRef.child(blood.getSelectedItem().toString()).child(mobile.getText().toString()).setValue(dd);
                    demoRef.child(fbID).setValue(dd);
                    loginRef.child(mobile.getText().toString()).setValue(dl);
//                    Intent i = new Intent(DonorLogin.this, DonateDate.class);
//                    startActivity(i);
                    Toast.makeText(getApplicationContext(),"Registration Successful! \n Login with your name and password to continue",Toast.LENGTH_LONG).show();
                } else {
                    confirm.setError("Password mismatch");
                }
            }
        }
        if (view == login) {
            awesomeValidation.addValidation(this, R.id.username, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
            if (awesomeValidation.validate() && validatePassword(pwd)) {
                Cursor c = db.rawQuery("SELECT Name,password FROM donor WHERE Name = '" + uname.getText().toString() + "'", null);
                if (c != null && c.moveToFirst()) {
                    p = c.getString(c.getColumnIndex("password"));
                    m = c.getString(c.getColumnIndex("Name"));
                    c.close();
                }
               if(pwd.getText().toString().equals(p) && uname.getText().toString().equals(m)){

                    Cursor d = db.rawQuery("SELECT appointDate,appointTime FROM donor WHERE Name = '" + uname.getText().toString() + "'",null);
                    if(d != null && d.moveToFirst()){
                        n = uname.getText().toString();
                        if(d.getString(d.getColumnIndex("appointDate")) == null || d.getString(d.getColumnIndex("appointTime")) == null) {
                            Intent j = new Intent(DonorLogin.this, DonateDate.class);
                            startActivity(j);
                            d.close();
                        }
                        else{
                            Cursor e=db.rawQuery("SELECT Name,bloodgroup,appointDate,appointTime FROM donor WHERE Name = '"+uname.getText().toString()+"'", null);
                            StringBuffer buffer=new StringBuffer();
                            while(e.moveToNext())
                            {
                                buffer.append("Name : "+e.getString(0)+"\n\n");
                                buffer.append("Blood : "+e.getString(1)+"\n\n");
                                buffer.append("Date: "+e.getString(2)+"\n\n");
                                buffer.append("Time  : "+e.getString(3)+"\n\n");
                            }
                            showMessage("Your Appointment", buffer.toString());

                        }
                    }
               }
               else {
                   uname.setError("Check your username");
                   pwd.setError("Check your password");
                   Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_SHORT).show();
               }
            }
        }
    }

    public void showMessage(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public static String getName(){
            return n;
    }
    private Boolean validatePassword(EditText name) {
        String val = name.getText().toString();

        if (val.isEmpty()) {
            name.setError("Field cannot be empty");
            return false;
        }
        else {
            name.setError(null);
            return true;
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

}
