package com.example.anbmapp;

public class Bands_uploads_details {
    private String caption, url_upload;

    public Bands_uploads_details() {
    }

    public Bands_uploads_details(String caption, String url_upload) {
        this.caption = caption;
        this.url_upload = url_upload;

    }

    public String getCaption() {
        return caption;
    }

    public String getUrl_upload() {
        return url_upload;
    }

}
