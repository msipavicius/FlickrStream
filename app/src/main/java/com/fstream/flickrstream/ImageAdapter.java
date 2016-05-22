package com.fstream.flickrstream;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.util.List;

/**
 * Created by Smarty on 18/05/16.
 */
public class ImageAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ImageDto> images;
    private Context context;

    public ImageAdapter(Context context, List<ImageDto> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images == null ? 0 : images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
        } else {
            imageView = (ImageView) convertView;
        }

        ImageLoader imageLoader = ImageLoader.getInstance();
        ImageAware imageAware = new ImageViewAware(imageView, false);
        String url = FlickrURLBuilder.buildSingleImageURL(images.get(position));
        imageLoader.displayImage(url , imageAware);
        return imageView;
    }
}
