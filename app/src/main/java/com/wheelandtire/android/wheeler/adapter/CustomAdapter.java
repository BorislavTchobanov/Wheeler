package com.wheelandtire.android.wheeler.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.wheelandtire.android.wheeler.R;
import com.wheelandtire.android.wheeler.model.Body;
import com.wheelandtire.android.wheeler.model.Vehicle;
import com.wheelandtire.android.wheeler.model.VehicleMake;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<VehicleMake> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private List<VehicleMake> vehicleList;

    public CustomAdapter(Context context, int textViewResourceId,
                         List<VehicleMake> vehicleList) {
        super(context, textViewResourceId, vehicleList);
        this.context = context;
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


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(vehicleList.get(position).getName());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(vehicleList.get(position).getName());

        return label;
    }
}