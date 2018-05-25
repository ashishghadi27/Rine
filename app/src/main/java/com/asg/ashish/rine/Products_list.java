package com.asg.ashish.rine;

public class Products_list {
    private String title;
    private String id;
    private String img;



    public Products_list(String title, String id, String img) {
        this.title = title;
        this.id = id;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getImg() {
        return img;
    }
}

