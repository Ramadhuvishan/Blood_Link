package com.example.bloodbank;

public class DonorDetails {
    private String Name,Blood,DoB,City,Mobile,Password;
    public DonorDetails(){

    }
    public String getDname(){
        return Name;
    }
    public void setDname(String name){
        this.Name = name;
    }
    public String getDblood(){
        return Blood;
    }
    public void setDblood(String blood){
        this.Blood = blood;
    }
    public String getDdob(){
        return DoB;
    }
    public void setDdob(String dob){
        this.DoB= dob;
    }
    public String getDmobile(){
        return Mobile;
    }
    public void setDmobile(String mobile){
        this.Mobile = mobile;
    }
    public String getDcity(){
        return City;
    }
    public void setDcity(String city){
        this.City= city;
    }
    public  String getDpass(){
        return Password;
    }
    public void setDpass(String pass){
        this.Password = pass;
    }
}
