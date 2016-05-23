package com.fstream.flickrstream;


import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.fstream.flickrstream.dtos.ImageDto;
import com.fstream.flickrstream.flickr.FlickrManager;
import com.fstream.flickrstream.flickr.FlickrURLBuilder;
import com.fstream.flickrstream.recyclerview.RecyclerItemClickListener;
import com.fstream.flickrstream.recyclerview.RecyclerViewAdapter;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String PREVIEW_URL_EXTRA = "previewURL";

//    @BindView(R.id.gridView)
//    GridView gridView;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private GridLayoutManager gridLayoutManager;

    private SearchView searchView;

    private String query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initImageLoader(getApplicationContext());

        handleIntent(getIntent());

        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    }

    private void initImageLoader(Context context) {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        boolean homeButtonVisible = false;
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            if (searchView != null) {
                searchView.clearFocus();
            }
            homeButtonVisible = true;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeButtonVisible);
        initRecyclerView();
        new LoadImagesFromFlickrTask().execute();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (position % 5) {
                    case 0:
                        return 2;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        return 1;
                }
                throw new IllegalStateException("internal error");
            }
        });

        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, SingleImageView.class);
                        intent.putExtra(PREVIEW_URL_EXTRA,
                                FlickrURLBuilder.buildSingleImageURL(recyclerViewAdapter.getItemList().get(position), "b"));
                        startActivity(intent);
                    }
                })
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ImageView overlay = (ImageView) findViewById(R.id.overlay);
                if (hasFocus) {
                    overlay.setImageResource(R.color.colorOverlay);
                } else {
                    overlay.setImageResource(R.color.transparent);
                }
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (query == null || query.isEmpty()) {
            super.onBackPressed();
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            query = null;
            startActivity(intent);
        }
    }

    class LoadImagesFromFlickrTask extends AsyncTask<String, Integer, List<ImageDto>> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading images from Flickr. Please wait...");
            progressDialog.show();
        }

        @Override
        protected List<ImageDto> doInBackground(String... params) {
            if (query == null || query.isEmpty()) {
                return FlickrManager.getListOfExplorePhotosURLs();
            } else {
                return FlickrManager.getListOfPhotosFoundURLs(query);
            }
        }

        @Override
        protected void onPostExecute(List<ImageDto> result) {
            progressDialog.dismiss();
            recyclerViewAdapter.setItemList(result);
            super.onPostExecute(result);
        }
    }
}
