package com.fstream.flickrstream.flickr;

import com.fstream.flickrstream.dtos.ImageDto;

import java.util.List;

public class FlickrManager {

    public static List<ImageDto> getListOfPhotosFoundURLs(String query) {
        String url = FlickrURLBuilder.buildSearchURL(query);
        return getPhotos(url);
    }

    public static List<ImageDto> getListOfExplorePhotosURLs() {
        String url = FlickrURLBuilder.buildExploreURL();
        return getPhotos(url);
    }

    private static List<ImageDto> getPhotos(String url) {
        String jsonString = HTTPRequester.getRequest(url).toString();
        List<ImageDto> photos = FlickrJSON.getPhotos(jsonString);
        return photos;
    }

}
