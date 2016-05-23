package com.fstream.flickrstream.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fstream.flickrstream.R;
import com.fstream.flickrstream.dtos.ImageDto;
import com.fstream.flickrstream.flickr.FlickrURLBuilder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<ImageDto> itemList;
    private Context context;

    public RecyclerViewAdapter(Context context) {
        this.itemList = new ArrayList<>();
        this.context = context;
    }

    public List<ImageDto> getItemList() {
        return itemList;
    }

    public void setItemList(List<ImageDto> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);
        this.notifyItemRangeInserted(0, itemList.size() - 1);
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new RecyclerViewHolders(layoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        TextView title = holder.title;
        TextView owner = holder.owner;
        ImageView buddyicon = holder.buddyicon;
        RelativeLayout wrapper = holder.wrapper;

        title.setText(itemList.get(position).getTitle());
        owner.setText(R.string.by + itemList.get(position).getOwner().getName());

        String url = FlickrURLBuilder.buildSingleImageURL(itemList.get(position));
        loadImage(url, holder.photo);

        switch (position % 5) {
            case 0:
                title.setTextSize(TypedValue.COMPLEX_UNIT_PX, 65);
                setMargins(title, 36, 0, 0, 100);
                owner.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
                wrapper.getLayoutParams().height = 608;
                String iconUrl = FlickrURLBuilder.buildBuddyIconURL(itemList.get(position));
                DisplayImageOptions iconOptions = new DisplayImageOptions.Builder()
                        .displayer(new RoundedBitmapDisplayer(60))
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .build();
                loadImage(iconUrl, buddyicon, iconOptions);
                buddyicon.setVisibility(View.VISIBLE);
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                title.setTextSize(TypedValue.COMPLEX_UNIT_PX, 45);
                setMargins(title, 36, 0, 0, 90);
                owner.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
                wrapper.getLayoutParams().height = 540;
                buddyicon.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private static void loadImage(String url, ImageView view) {
        loadImage(url, view, null);
    }

    private static void loadImage(String url, ImageView view, DisplayImageOptions options) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        ImageAware imageAware = new ImageViewAware(view, false);
        if (options != null) {
            imageLoader.displayImage(url, imageAware, options);
        } else {
            imageLoader.displayImage(url , imageAware);
        }
    }

    private static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
