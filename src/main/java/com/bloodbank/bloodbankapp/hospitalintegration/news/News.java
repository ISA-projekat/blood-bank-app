package com.bloodbank.bloodbankapp.hospitalintegration.news;

public class News {

    public String reqTitle;
    public String reqBody;
    public String reqImage;

    public News(String title, String body, String image) {
        this.reqTitle = title;
        this.reqBody = body;
        this.reqImage = image;
    }
}
