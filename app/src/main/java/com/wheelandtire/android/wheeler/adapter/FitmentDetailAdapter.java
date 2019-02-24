package com.wheelandtire.android.wheeler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wheelandtire.android.wheeler.R;
import com.wheelandtire.android.wheeler.model.Generation;
import com.wheelandtire.android.wheeler.model.Wheel;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FitmentDetailAdapter extends RecyclerView.Adapter<FitmentDetailAdapter.FitmentDetailViewHolder> {

    private List<Wheel> wheelList;

    public FitmentDetailAdapter(List<Wheel> wheelList) {
        this.wheelList = wheelList;
    }

    @NonNull
    @Override
    public FitmentDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.fitment_detail_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new FitmentDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FitmentDetailViewHolder fitmentDetailViewHolder, int position) {
        Wheel wheel = wheelList.get(position);

        String tireSizeFront = wheel.getFront().getTire();
        String tireSizeRear = wheel.getRear().getTire();
        String tireSize = tireSizeRear != null && !tireSizeRear.isEmpty() ?
                tireSizeFront.concat("\n Rear: \n" + tireSizeRear) : tireSizeFront;
        fitmentDetailViewHolder.setTireFront(tireSize);

        String rimSizeFront = wheel.getFront().getRim();
        String rimSizeRear = wheel.getRear().getRim();
        String rimSize = rimSizeRear != null && !rimSizeRear.isEmpty() ?
                rimSizeFront.concat("\n Rear: \n" + rimSizeRear) : rimSizeFront;
        fitmentDetailViewHolder.setRimFront(rimSize);


        String tirePressureFront = (wheel.getFront().getTirePressure()) != null ?
                String.valueOf(wheel.getFront().getTirePressure().getBar()) : "";
        String tirePressureRear = (wheel.getRear().getTirePressure()) != null ?
                String.valueOf(wheel.getRear().getTirePressure().getBar()) : "";
        String tirePressure = tirePressureRear.isEmpty() ?
                tirePressureFront : tirePressureFront.concat("/" + tirePressureRear);
        fitmentDetailViewHolder.setTirePressureFront(tirePressure);
    }

    @Override
    public int getItemCount() {
        if (wheelList == null) {
            return 0;
        }

        return wheelList.size();
    }

    class FitmentDetailViewHolder extends RecyclerView.ViewHolder {
        private TextView tireTv;
        private TextView rimTv;
        private TextView tirePressureTv;

        FitmentDetailViewHolder(View itemView) {
            super(itemView);

            tireTv = itemView.findViewById(R.id.tireTv);
            rimTv = itemView.findViewById(R.id.rimTv);
            tirePressureTv = itemView.findViewById(R.id.tirePressure);
        }

        void setTireFront(String tireFront) {
            tireTv.setText(tireFront);
        }

        void setRimFront(String rimFront) {
            rimTv.setText(rimFront);
        }

        void setTirePressureFront(String tirePressureFront) {
            tirePressureTv.setText(tirePressureFront);
        }

    }
}
