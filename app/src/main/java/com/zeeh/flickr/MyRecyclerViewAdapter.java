package com.zeeh.flickr;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zeeh.flickr.model.Photo;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    List<Photo> data = Collections.emptyList();
    private LayoutInflater inflater;
    String imageAPI = "https://farm";

    public MyRecyclerViewAdapter(Context context, List<Photo> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_layout, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        Photo current = data.get(i);

        String urlStr = imageAPI + current.getFarm()+".staticflickr.com/"
                +current.getServer()+"/"+current.getId()+"_"+current.getSecret()+"_n.jpg";

        Uri uri = Uri.parse(urlStr);
        Context context = myViewHolder.imageView.getContext();
        Picasso.with(context).load(uri)
                .into(myViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.imageView2);
            v.setOnClickListener(MainActivity.myOnClickListener);
        }
    }
}


