package com.zeeh.flickr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends FragmentActivity {
    MyAdapter mAdapter;
    ViewPager mPager;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);

        Intent p = getIntent();
        position = p.getExtras().getInt("position");
        List<String> photoListStr = (ArrayList)p.getStringArrayListExtra("photoListStr");

        mAdapter = new MyAdapter(getSupportFragmentManager(), photoListStr,position, this);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(position);

    }

    public static class MyAdapter extends FragmentStatePagerAdapter {
        List<String> photoList;
        int pos;
        Context mContext;

        public MyAdapter(FragmentManager fragmentManager, List<String> photos, int position, Context context) {
            super(fragmentManager);
            photoList = photos;
            pos = position;
            mContext = context;
        }

        @Override
        public int getCount() {
            return photoList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return ImageFragment.init(photoList, position, mContext);
        }
    }

}