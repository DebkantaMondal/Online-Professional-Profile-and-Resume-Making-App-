package com.example.resumeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

class MyPagerAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private int[]layouts;
    private Context context;

    public MyPagerAdapter(int[] layouts, Context context) {
        this.layouts = layouts;
        this.context = context;
    }


    public int getCount() {
        return layouts.length;
    }


    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }


    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(layouts[position], container, false);
        container.addView(v);
        return v;
    }


    public void destroyItem(ViewGroup container, int position, Object object) {
        View v = (View)object;
        container.removeView(v);
    }

}
