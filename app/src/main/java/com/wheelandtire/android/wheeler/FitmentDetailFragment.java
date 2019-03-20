package com.wheelandtire.android.wheeler;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wheelandtire.android.wheeler.adapter.FitmentDetailAdapter;
import com.wheelandtire.android.wheeler.model.Vehicle;
import com.wheelandtire.android.wheeler.utility.ImageHandler;

import java.net.URLConnection;
import java.util.HashMap;
import java.util.Random;

import static com.wheelandtire.android.wheeler.FitmentActivity.EXTRA_VEHICLE;

public class FitmentDetailFragment extends Fragment {

    private boolean mTwoPane;
    private Vehicle vehicle;
    private TextView tireTv;
    private TextView rimTv;
    private TextView tirePressureTv;

    public FitmentDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fitment_detail_view, container, false);

//        if (getActivity() instanceof FitmentDetailActivity) {
//            ((FitmentDetailActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }

        assert getArguments() != null;
        if (getArguments().containsKey(EXTRA_VEHICLE)) {
            vehicle = (Vehicle) getArguments().getSerializable(EXTRA_VEHICLE);
            if (vehicle != null) {



                RecyclerView recyclerView = rootView.findViewById(R.id.fitmentDetailRecyclerView);

                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);

                FitmentDetailAdapter fitmentDetailAdapter = new FitmentDetailAdapter(vehicle.getWheels());
                    recyclerView.setAdapter(fitmentDetailAdapter);


            }
        }


        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


//        assert getArguments() != null;
//        if (getArguments().containsKey(EXTRA_TWO_PANE)) {
//            mTwoPane = getArguments().getBoolean(EXTRA_TWO_PANE);
//        }
//        if (mTwoPane) return;
//        try {
//            mListener = (OnStepNavButtonClickListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString() + " must implement OnStepNavButtonClickListener");
//        }
    }



}