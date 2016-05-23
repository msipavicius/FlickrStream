package com.fstream.flickrstream.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fstream.flickrstream.R;

public class RecyclerViewHolders extends RecyclerView.ViewHolder {

    public ImageView photo;
    public ImageView buddyicon;
    public TextView title;
    public TextView owner;
    public RelativeLayout wrapper;

    public RecyclerViewHolders(View itemView) {
        super(itemView);

        photo = (ImageView) itemView.findViewById(R.id.imageView);
        buddyicon = (ImageView) itemView.findViewById(R.id.buddyIcon);
        title = (TextView) itemView.findViewById(R.id.title);
        owner = (TextView) itemView.findViewById(R.id.owner);
        wrapper = (RelativeLayout) itemView;
    }
}
