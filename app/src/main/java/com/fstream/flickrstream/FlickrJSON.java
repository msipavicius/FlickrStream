package com.fstream.flickrstream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smarty on 18/05/16.
 */
public class FlickrJSON {
    public static List<ImageDto> getPhotos(String jsonString) {
        List<ImageDto> photoDtoList = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(jsonString.replace("jsonFlickrApi(", "").replace(")", ""));
            JSONArray photos = root.getJSONObject("photos").getJSONArray("photo");
            for (int i = 0; i < photos.length(); i++) {
                JSONObject photo = photos.getJSONObject(i);
                ImageDto imageDto = new ImageDto(
                        photo.getString("id"),
                        photo.getString("secret"),
                        photo.getString("server"),
                        Integer.toString(photo.getInt("farm")),
                        photo.getString("title"),
                        getUser(photo.getString("owner")));
                photoDtoList.add(imageDto);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photoDtoList;
    }

    private static UserDto getUser(String userId) throws JSONException {
        String response = HTTPRequester.getRequest(FlickrURLBuilder.buildUserInfoURL(userId)).toString();
        JSONObject root = new JSONObject(response.replace("jsonFlickrApi(", "").replace(")", ""));
        JSONObject personJson = root.getJSONObject("person");
        String name;
        if (personJson.has("realname")) {
            name = personJson.getJSONObject("realname").getString("_content");
        } else {
            name = personJson.getJSONObject("username").getString("_content");
        }
        return new UserDto(
                personJson.getString("nsid"),
                name,
                Integer.toString(personJson.getInt("iconfarm")),
                Integer.toString(personJson.getInt("iconserver")));
    }
}
