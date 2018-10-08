package com.example.tejas.admin;

/**
 * Created by Tejas on 22-02-2018.
 */

public class ImageUploadModel {

    public String imageName;

    public String imageURL;

    public ImageUploadModel() {

    }

    public ImageUploadModel(String name, String url) {

        this.imageName = name;
        this.imageURL= url;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageURL() {
        return imageURL;
    }
}