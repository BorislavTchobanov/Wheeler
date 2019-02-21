package com.wheelandtire.android.wheeler.utility;

import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.wheelandtire.android.wheeler.R;


public class ImageHandler {

    public static void loadImage(String imageUrl, final ImageView imageView) {
        if (imageUrl != null && imageUrl.trim().isEmpty()) {
            imageUrl = null;
        }
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_menu_camera)
                .error(R.drawable.ic_menu_gallery)
                .into(imageView);
    }
}
