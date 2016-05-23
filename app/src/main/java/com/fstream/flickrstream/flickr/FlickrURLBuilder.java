package com.fstream.flickrstream.flickr;

import com.fstream.flickrstream.dtos.ImageDto;
import com.fstream.flickrstream.dtos.UserDto;

public class FlickrURLBuilder {
    private static final String FLICKR_BASE_URL = "https://api.flickr.com/services/rest/?method=";

    // Param names
    private static final String KEY_PARAM = "&api_key=";
    private static final String TEXT_PARAM = "&text=";
    private static final String SORT_PARAM = "&sort=";
    private static final String FORMAT_PARAM = "&format=";
    private static final String PER_PAGE_PARAM = "&per_page=";
    private static final String MEDIA_PARAM = "&media=";
    private static final String EXTRAS_PARAM = "&extras=";

    // Param values
    private static final String API_KEY = "b6523d622b1d7c02a8ed39c65e07b404";
    private static final String SORT_STRATEGY = "relevance";
    private static final int NUMBER_OF_PHOTOS = 50;
    private static final String FORMAT = "json";
    private static final String MEDIA_TYPE = "photos";
    private static final String EXTRAS = "owner_name,icon_server";

    // Methods
    private static final String FLICKR_EXPLORE_PHOTOS_METHOD = "flickr.interestingness.getList";
    private static final String FLICKR_SEARCH_PHOTOS_METHOD = "flickr.photos.search";

    public static String buildSearchURL(String query) {
        String formatedQuery = query.trim().replace(" ", "+");
        return FLICKR_BASE_URL
                + FLICKR_SEARCH_PHOTOS_METHOD
                + KEY_PARAM + API_KEY
                + TEXT_PARAM + formatedQuery
                + SORT_PARAM + SORT_STRATEGY
                + FORMAT_PARAM + FORMAT
                + PER_PAGE_PARAM + NUMBER_OF_PHOTOS
                + MEDIA_PARAM + MEDIA_TYPE
                + EXTRAS_PARAM + EXTRAS;
    }

    public static String buildExploreURL() {
        return FLICKR_BASE_URL
                + FLICKR_EXPLORE_PHOTOS_METHOD
                + KEY_PARAM + API_KEY
                + FORMAT_PARAM + FORMAT
                + PER_PAGE_PARAM + NUMBER_OF_PHOTOS
                + EXTRAS_PARAM + EXTRAS;
    }

    /**
     *
     * @param image image DTO
     * @param size see https://www.flickr.com/services/api/misc.urls.html for possible values
     * @return url to photo of desired size
     */
    public static String buildSingleImageURL(ImageDto image, String size) {
        // https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_{size char}.jpg
        if (!size.isEmpty()) {
            size = "_" + size;
        }
        return String.format("https://farm%s.staticflickr.com/%s/%s_%s%s.jpg",
                image.getFarm(), image.getServer(), image.getId(),image.getSecret(), size);
    }

    public static String buildSingleImageURL(ImageDto image) {
        return buildSingleImageURL(image, "");
    }

    public static String buildBuddyIconURL(ImageDto image) {
        // https://farm{icon-farm}.staticflickr.com/{icon-server}/buddyicons/{nsid}.jpg
        UserDto user = image.getOwner();
        return String.format("http://farm%s.staticflickr.com/%s/buddyicons/%s.jpg",
                user.getIconFarm(), user.getIconServer(), user.getId());
    }
}
