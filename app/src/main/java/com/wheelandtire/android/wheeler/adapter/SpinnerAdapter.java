package com.wheelandtire.android.wheeler.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wheelandtire.android.wheeler.model.VehicleMake;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<VehicleMake> {

    private List<VehicleMake> vehicleList;

    public SpinnerAdapter(Context context, int textViewResourceId,
                          List<VehicleMake> vehicleList) {
        super(context, textViewResourceId, vehicleList);
        this.vehicleList = vehicleList;
    }

    @Override
    public int getCount(){
        return vehicleList.size();
    }

    @Override
    public VehicleMake getItem(int position){
        return vehicleList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(vehicleList.get(position).getName());

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(vehicleList.get(position).getName());

        return label;
    }
}