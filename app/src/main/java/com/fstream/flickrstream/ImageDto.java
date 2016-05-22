package com.fstream.flickrstream;

/**
 * Created by Smarty on 18/05/16.
 */
public class ImageDto {
    private String id;
    private String secret;
    private String server;
    private String farm;
    private String title;
    private UserDto owner;

    public ImageDto(String id, String secret, String server, String farm, String title, UserDto owner) {
        this.id = id;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
        this.title = title;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }
}
