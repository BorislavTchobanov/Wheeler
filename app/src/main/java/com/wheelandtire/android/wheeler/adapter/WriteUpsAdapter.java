package com.wheelandtire.android.wheeler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wheelandtire.android.wheeler.R;
import com.wheelandtire.android.wheeler.model.Wheel;

import java.util.List;

public class WriteUpsAdapter extends RecyclerView.Adapter<WriteUpsAdapter.WriteUpsViewHolder> {

    private List<Wheel> wheelList;

    public WriteUpsAdapter(List<Wheel> wheelList) {
        this.wheelList = wheelList;
    }

    @NonNull
    @Override
    public WriteUpsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.write_ups_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new WriteUpsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WriteUpsViewHolder writeUpsViewHolder, int position) {
        Wheel wheel = wheelList.get(position);

        String tireSizeFront = wheel.getFront().getTire();
        String tireSizeRear = wheel.getRear().getTire();
        String tireSize = tireSizeRear != null && !tireSizeRear.isEmpty() ?
                tireSizeFront.concat("\n Rear: \n" + tireSizeRear) : tireSizeFront;
        writeUpsViewHolder.setHeadlineTv(tireSize);

        String rimSizeFront = wheel.getFront().getRim();
        String rimSizeRear = wheel.getRear().getRim();
        String rimSize = rimSizeRear != null && !rimSizeRear.isEmpty() ?
                rimSizeFront.concat("\n Rear: \n" + rimSizeRear) : rimSizeFront;
        writeUpsViewHolder.setContentTv(rimSize);


        String tirePressureFront = (wheel.getFront().getTirePressure()) != null ?
                String.valueOf(wheel.getFront().getTirePressure().getBar()) : "";
        String tirePressureRear = (wheel.getRear().getTirePressure()) != null ?
                String.valueOf(wheel.getRear().getTirePressure().getBar()) : "";
        String tirePressure = tirePressureRear.isEmpty() ?
                tirePressureFront : tirePressureFront.concat("/" + tirePressureRear);
        writeUpsViewHolder.setTirePressureFront(tirePressure);
    }

    @Override
    public int getItemCount() {
        if (wheelList == null) {
            return 0;
        }

        return wheelList.size();
    }

    class WriteUpsViewHolder extends RecyclerView.ViewHolder {
        private TextView headlineTv;
        private TextView contentTv;

        WriteUpsViewHolder(View itemView) {
            super(itemView);

            headlineTv = itemView.findViewById(R.id.headlineTv);
            contentTv = itemView.findViewById(R.id.contentTv);
        }

        void setHeadlineTv(String tireFront) {
            headlineTv.setText(tireFront);
        }

        void setContentTv(String rimFront) {
            contentTv.setText(rimFront);
        }

    }
}
