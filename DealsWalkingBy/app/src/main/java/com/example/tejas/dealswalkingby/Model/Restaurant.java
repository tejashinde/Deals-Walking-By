package com.example.tejas.dealswalkingby.Model;

/**
 * Created by Tejas on 25-02-2018.
 */

public class Restaurant {

    private String name;
    private String address;
    private String rating;
    private String cost;
    private String imageUrl;
    private String currency;
    private double longitude;
    private double latitude;
    private double distance;
    private String votes;

    public Restaurant() {
    }

    public Restaurant(String name, String address, String rating,
                      String cost, String imageUrl, String currency,
                      double longitude, double latitude, double distance,
                      String votes) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.cost = cost;
        this.imageUrl = imageUrl;
        this.currency = currency;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
        this.votes = votes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }
}
