package com.example.bloodbank;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RequestorDetails extends ArrayAdapter<DonorDetails> {
    private Activity context;
    private List<DonorDetails> donorList;

    public RequestorDetails(Activity context, List<DonorDetails> donorList){
        super(context, R.layout.view_activity,donorList);
        this.context = context;
        this.donorList = donorList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.view_activity,null,true);
        TextView textName = (TextView) listViewItem.findViewById(R.id.textName);
        TextView textBlood = (TextView) listViewItem.findViewById(R.id.textBlood);
        TextView textMobile = (TextView) listViewItem.findViewById(R.id.textMobile);
        TextView textCity = (TextView) listViewItem.findViewById(R.id.textCity);

        DonorDetails donor = donorList.get(position);

        textName.setText(donor.getDname());
        textBlood.setText(donor.getDblood());
        textMobile.setText(donor.getDmobile());
        textCity.setText(donor.getDcity());
        return listViewItem;

    }
}