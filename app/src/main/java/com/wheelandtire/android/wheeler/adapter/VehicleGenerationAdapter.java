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

public class VehicleGenerationAdapter extends RecyclerView.Adapter<VehicleGenerationAdapter.VehicleGenerationViewHolder> {

    private List<Vehicle> vehicleList;
    private final ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public VehicleGenerationAdapter(List<Vehicle> vehicleList, ListItemClickListener listener) {
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
        final Generation generation = vehicleList.get(position).getGeneration();
        vehicleGenerationViewHolder.setRecipeImage(generation.getBodies().get(0).getImage());
        vehicleGenerationViewHolder.setRecipeName(generation.getName());
//        vehicleGenerationViewHolder.setNumOfServings(generation.getBodies().g);
    }

    @Override
    public int getItemCount() {
        if (vehicleList == null) {
            return 0;
        }
        return vehicleList.size();
    }

    class VehicleGenerationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView generationNameTv;
        private TextView trimTv;

        VehicleGenerationViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.recipe_image);
            itemView.setOnClickListener(this);
            generationNameTv = itemView.findViewById(R.id.recipe_name);
            trimTv = itemView.findViewById(R.id.recipe_num_of_servings);
        }

        void setRecipeImage(final String imageUrl) {
            ImageHandler.loadImage(imageUrl, imageView);
        }

        void setRecipeName(String recipeName) {
            generationNameTv.setText(recipeName);
        }

        void setNumOfServings(int numOfServings) {
            trimTv.setText(String.valueOf(numOfServings));
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
