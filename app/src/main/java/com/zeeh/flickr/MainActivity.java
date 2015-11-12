package com.zeeh.flickr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zeeh.flickr.API.FlickrService;
import com.zeeh.flickr.model.Flickrmodel;
import com.zeeh.flickr.model.Photo;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class MainActivity extends Activity {

    final String method = "flickr.photos.search";
    final String api_key = "2e0e30218c38cfs044bcf217ef9724aa";
    final String format = "json";
    final String per_page = "20";
    final String media = "photos";
    final String nojsoncallback = "1";
    private List<Photo> photoList;

    private RecyclerView.LayoutManager layoutManager;
    public static View.OnClickListener myOnClickListener;
    private static RecyclerView recyclerView;
    private static MyRecyclerViewAdapter myAdapter;

    Button click;
    TextView tv;
    EditText edit_tags;
    ProgressBar pbar;
    String metadataAPI = "https://api.flickr.com";
    String imageAPI = "https://farm";


/////
    ArrayList<String> photoListStr;
/////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myOnClickListener = new MyOnClickListener();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);

        click = (Button) findViewById(R.id.button);
        tv = (TextView) findViewById(R.id.tv);
        edit_tags = (EditText) findViewById(R.id.edit);
        pbar = (ProgressBar) findViewById(R.id.pb);
        pbar.setVisibility(View.INVISIBLE);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_tags.setEnabled(false);
                edit_tags.setEnabled(true);
                if (edit_tags.getText() == null) {
                    edit_tags.setText("Android");
                }
                String tags = edit_tags.getText().toString();
                pbar.setVisibility(View.VISIBLE);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(metadataAPI)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                FlickrService flickrService = retrofit.create(FlickrService.class);

                Call call = flickrService.getFlickr(method, api_key, tags, format, per_page, media, nojsoncallback);


                call.enqueue(new Callback<Flickrmodel>() {
                    @Override
                    public void onResponse(Response<Flickrmodel> response) {
                        Flickrmodel model = response.body();

                        if (model == null) {
                            //404 or the response cannot be converted to User.
                            ResponseBody responseBody = response.errorBody();
                            if (responseBody != null) {
                                try {
                                    tv.setText("responseBody = " + responseBody.string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                tv.setText("responseBody = null");
                            }
                        } else {
                            //200
                            tv.setText("Photos :" + model.getPhotos().getPhoto().get(0).getTitle() + "\nStat :" + model.getStat());
                            photoList = model.getPhotos().getPhoto();
                            getFlickrResults();
                        }
                        pbar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        tv.setText("t = " + t.getMessage());
                        pbar.setVisibility(View.INVISIBLE);
                    }
                });

            }
        });
    }

    private void getFlickrResults() {
        myAdapter = new MyRecyclerViewAdapter(this, photoList);
        recyclerView.setAdapter(myAdapter);

///// fill the photoListStr with urls of photos
        photoListStr = new ArrayList<>();
        int i = 0;
        while (i < photoList.size()) {
            photoListStr.add(getThumbImageUrl(photoList.get(i)));
            i++;
        }
/////
    }

    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int selectedItemPosition = recyclerView.getChildPosition(v);
            String url = getThumbImageUrl(photoList.get(selectedItemPosition));
            Intent intent = new Intent(MainActivity.this, ImageActivity.class);

///// send position and the entire list of photo urls
            intent.putExtra("position", selectedItemPosition);
            intent.putStringArrayListExtra("photoListStr", photoListStr);
/////
            startActivity(intent);
        }
    }

    private String getThumbImageUrl(Photo photo) {

        String urlStr = imageAPI + photo.getFarm()+".staticflickr.com/"
                +photo.getServer()+"/"+photo.getId()+"_"+photo.getSecret()+"_z.jpg";

        return urlStr;
    }

}
