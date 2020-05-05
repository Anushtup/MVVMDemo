package com.example.mvvmdemo.view;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvvmdemo.R;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

public class Util {
    public static void loadImage(ImageView view, String url, CircularProgressDrawable progressDrawable)
    {
        RequestOptions request = new RequestOptions()
                                .placeholder(progressDrawable)
                                .error(R.mipmap.ic_launcher_round);

        /**
         * Glide has a caching mechanism so, it caches the images and uses the cached images whenever we reload the images
         */
        Glide.with(view.getContext())
                  .setDefaultRequestOptions(request)
                  .load(url)
                  .into(view);
    }

    public static CircularProgressDrawable getCircularProgressDrawable(Context context)
    {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(10f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();

        return circularProgressDrawable;
    }


}
