package com.fstream.flickrstream;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class SingleImageView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_image_view);

        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.PREVIEW_URL_EXTRA);

        ImageLoader imageLoader = ImageLoader.getInstance();
        ImageView imageView = (ImageView) findViewById(R.id.singleImageView);
        imageLoader.displayImage(url, imageView);
    }
}
