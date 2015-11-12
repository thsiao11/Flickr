package com.zeeh.flickr;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageFragment extends Fragment {
    int fragVal;
    static List<String> photoList;
    static Context mContext;

    static ImageFragment init(List<String> photos, int val, Context context) {
        mContext = context;
        photoList = photos;
        ImageFragment imageFragment = new ImageFragment();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        imageFragment.setArguments(args);
        return imageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragVal = getArguments() != null ? getArguments().getInt("val") : 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.activity_image, container,
                false);
        ImageView iv = (ImageView)layoutView.findViewById(R.id.image_vw);

            String urlStr =  photoList.get(fragVal);
            Uri uri = Uri.parse(urlStr);
            Picasso.with(mContext).load(uri).into(iv);
        return layoutView;
    }
}

