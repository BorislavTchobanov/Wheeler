package com.wheelandtire.android.wheeler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.wheelandtire.android.wheeler.R;
import com.wheelandtire.android.wheeler.model.VehicleMake;

import java.util.List;

public class VehicleMakeAdapter extends RecyclerView.Adapter<VehicleMakeAdapter.VehicleMakeViewHolder> {


    private final List<VehicleMake> vehicleMakeList;
//    private final ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public VehicleMakeAdapter(List<VehicleMake> vehicleMakeList){
//        mOnClickListener = listener;
        this.vehicleMakeList = vehicleMakeList;
    }

    @NonNull
    @Override
    public VehicleMakeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.custom_row;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new VehicleMakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleMakeViewHolder vehicleMakeViewHolder, int position) {
        final VehicleMake vehicleMake = vehicleMakeList.get(position);
        vehicleMakeViewHolder.setVehicleMake(vehicleMake.getName());
    }

    @Override
    public int getItemCount() {
        if (vehicleMakeList == null) {
            return 0;
        }
        return vehicleMakeList.size();
    }

    class VehicleMakeViewHolder extends RecyclerView.ViewHolder {
        final TextView reviewAuthor;

        VehicleMakeViewHolder(View itemView) {
            super(itemView);

            reviewAuthor = itemView.findViewById(R.id.title);
        }

        void setVehicleMake(final String vehicleName) {
            reviewAuthor.setText(vehicleName);
        }

    }
}
