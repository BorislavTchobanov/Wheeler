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

    private List<String> titleList;
    private List<String> contentList;

    public WriteUpsAdapter(List<String> titleList, List<String> contentList) {
        this.titleList = titleList;
        this.contentList = contentList;
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


        writeUpsViewHolder.setHeadlineTv(titleList.get(position));
        writeUpsViewHolder.setContentTv(contentList.get(position));
    }

    @Override
    public int getItemCount() {
        if (titleList == null) {
            return 0;
        }

        return titleList.size();
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
