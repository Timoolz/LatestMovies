package com.olamide.latestmovies.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.olamide.latestmovies.R;

public class RecyclerViewUtils {

    private static final String TAG = RecyclerViewUtils.class.getSimpleName();




    public static int getSpanCount(final RecyclerView recyclerView, final float cardWidth ){

        WindowManager wm = (WindowManager) recyclerView.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;


        int newSpanCount = (int) Math.floor(width / cardWidth);
        int spanCount = newSpanCount;
        Log.e(TAG, "width  " +width);
        Log.e(TAG, "cardWidth  " +cardWidth);
        Log.e(TAG, "Spancount  " +spanCount);

        return spanCount;
    }



}
