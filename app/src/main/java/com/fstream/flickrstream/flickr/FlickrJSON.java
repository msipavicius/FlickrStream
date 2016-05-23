package com.fstream.flickrstream.flickr;

import com.fstream.flickrstream.dtos.ImageDto;
import com.fstream.flickrstream.dtos.UserDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FlickrJSON {
    public static List<ImageDto> getPhotos(String jsonString) {
        List<ImageDto> photoDtoList = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(jsonString.replace("jsonFlickrApi(", "").replace(")", ""));
            JSONArray photos = root.getJSONObject("photos").getJSONArray("photo");
            for (int i = 0; i < photos.length(); i++) {
                JSONObject photo = photos.getJSONObject(i);
                UserDto userDto = new UserDto(
                        photo.getString("owner"),
                        photo.getString("ownername"),
                        photo.getString("iconfarm"),
                        photo.getString("iconserver"));

                ImageDto imageDto = new ImageDto(
                        photo.getString("id"),
                        photo.getString("secret"),
                        photo.getString("server"),
                        Integer.toString(photo.getInt("farm")),
                        photo.getString("title"),
                        userDto);
                photoDtoList.add(imageDto);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photoDtoList;
    }
}
