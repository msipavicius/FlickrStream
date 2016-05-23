package com.fstream.flickrstream.dtos;

/**
 * Created by Smarty on 22/05/16.
 */
public class UserDto {
    private String id;
    private String name;
    private String iconFarm;
    private String iconServer;

    public UserDto(String id, String name, String iconFarm, String iconServer) {
        this.id = id;
        this.name = name;
        this.iconFarm = iconFarm;
        this.iconServer = iconServer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconFarm() {
        return iconFarm;
    }

    public void setIconFarm(String iconFarm) {
        this.iconFarm = iconFarm;
    }

    public String getIconServer() {
        return iconServer;
    }

    public void setIconServer(String iconServer) {
        this.iconServer = iconServer;
    }
}
