package com.asg.ashish.rine;

public class Products_list {
    private String title;
    private String id;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String description;
    private String sale_price;
    private String regular_price;




    public Products_list(String title, String id, String img1, String img2, String img3, String img4, String description, String sale_price, String regular_price) {
        this.title = title;
        this.id = id;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.img4 = img4;
        this.description = description;
        this.sale_price = sale_price;
        this.regular_price = regular_price;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getImg1() {
        return img1;
    }

    public String getImg2() {
        return img2;
    }

    public String getImg3() {
        return img3;
    }

    public String getImg4() {
        return img4;
    }

    public String getDescription() {
        return description;
    }

    public String getSale_price() {
        return sale_price;
    }

    public String getRegular_price() {
        return regular_price;
    }
}

