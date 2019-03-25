package com.wheelandtire.android.wheeler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wheelandtire.android.wheeler.R;
import com.wheelandtire.android.wheeler.model.Generation;
import com.wheelandtire.android.wheeler.model.Vehicle;
import com.wheelandtire.android.wheeler.utility.ImageHandler;

import java.util.List;
import java.util.Random;

public class FitmentAdapter extends RecyclerView.Adapter<FitmentAdapter.VehicleGenerationViewHolder> {

    private List<Vehicle> vehicleList;
    private final ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public FitmentAdapter(List<Vehicle> vehicleList, ListItemClickListener listener) {
        mOnClickListener = listener;
        this.vehicleList = vehicleList;
    }

    @NonNull
    @Override
    public VehicleGenerationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.fitment_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new VehicleGenerationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleGenerationViewHolder vehicleGenerationViewHolder, int position) {
        Vehicle vehicle = vehicleList.get(position);

        final Generation generation = vehicleList.get(position).getGeneration();
        int vehicleImagesListSize = generation.getBodies().size();
        vehicleGenerationViewHolder.setVehicleImage(generation.getBodies()
                .get(new Random().nextInt(vehicleImagesListSize))
                .getImage());
        vehicleGenerationViewHolder.setVehicleGenerationName(generation.getName());
        vehicleGenerationViewHolder.setVehicleTrim(vehicle.getTrim());
        vehicleGenerationViewHolder.setVehicleBoltPattern(vehicle.getBoltPattern());
    }

    @Override
    public int getItemCount() {
        if (vehicleList == null) {
            return 0;
        }

        List<Vehicle> vehicleListTrimmed = vehicleList;
        if (vehicleList != null) {
            for (int j = vehicleList.size() - 1; j >= 0; j--) {
                Vehicle vehicle = vehicleList.get(j);
                if (vehicle.getTrim() == null) {
                    vehicleListTrimmed.remove(j);
                }
            }
        }

        return vehicleListTrimmed.size();
    }

    class VehicleGenerationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView generationNameTv;
        private TextView trimTv;
        private TextView boltPatternTv;

        VehicleGenerationViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.vehicleImage);
            itemView.setOnClickListener(this);
            generationNameTv = itemView.findViewById(R.id.generationTv);
            trimTv = itemView.findViewById(R.id.trimTv);
            boltPatternTv = itemView.findViewById(R.id.boltPatternTv);
        }

        void setVehicleImage(final String imageUrl) {
            ImageHandler.loadImage(imageUrl, imageView);
        }

        void setVehicleGenerationName(String generationName) {
            generationNameTv.setText(generationName);
        }

        void setVehicleTrim(String vehicleTrim) {
            trimTv.setText(vehicleTrim);
        }

        void setVehicleBoltPattern(String boltPattern) {
            boltPatternTv.setText(boltPattern);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
