package com.example.tejas.dealswalkingby;

import android.location.Location;
import java.lang.String;

/**
 * Created by Tejas on 22-02-2018.
 */

public class DealStructure {

    String deal_image_name;
    String deal_image;
    String deal_title;
    String deal_description;
    String deal_longitude;
    String deal_latitude;
    String deal_validity;
    String deal_validity_day;
    String deal_validity_month;
    String deal_validity_year;
    String deal_promo_code;

    public DealStructure(){}

    public DealStructure(String deal_image_name,
                         String deal_image,
                         String deal_title,
                         String deal_description,
                         String deal_longitude,
                         String deal_latitude,
                         String deal_validity,
                         String deal_validity_day,
                         String deal_validity_month,
                         String deal_validity_year,
                         String deal_promo_code) {
        this.deal_image_name = deal_image_name;
        this.deal_image = deal_image;
        this.deal_title = deal_title;
        this.deal_description = deal_description;
        this.deal_longitude = deal_longitude;
        this.deal_latitude = deal_latitude;
        this.deal_validity = deal_validity;
        this.deal_validity_day = deal_validity_day;
        this.deal_validity_month = deal_validity_month;
        this.deal_validity_year = deal_validity_year;
        this.deal_promo_code = deal_promo_code;
    }

    public String getDeal_image_name() {
        return deal_image_name;
    }

    public void setDeal_image_name(String deal_image_name) {
        this.deal_image_name = deal_image_name;
    }

    public String getDeal_image() {
        return deal_image;
    }

    public void setDeal_image(String deal_image) {
        this.deal_image = deal_image;
    }

    public String getDeal_title() {
        return deal_title;
    }

    public void setDeal_title(String deal_title) {
        this.deal_title = deal_title;
    }

    public String getDeal_description() {
        return deal_description;
    }

    public void setDeal_description(String deal_description) {
        this.deal_description = deal_description;
    }

    public String getDeal_longitude() {
        return deal_longitude;
    }

    public void setDeal_longitude(String deal_longitude) {
        this.deal_longitude = deal_longitude;
    }

    public String getDeal_latitude() {
        return deal_latitude;
    }

    public void setDeal_latitude(String deal_latitude) {
        this.deal_latitude = deal_latitude;
    }

    public String getDeal_validity() {
        return deal_validity;
    }

    public void setDeal_validity(String deal_validity) {
        this.deal_validity = deal_validity;
    }

    public String getDeal_validity_day() {
        return deal_validity_day;
    }

    public void setDeal_validity_day(String deal_validity_day) {
        this.deal_validity_day = deal_validity_day;
    }

    public String getDeal_validity_month() {
        return deal_validity_month;
    }

    public void setDeal_validity_month(String deal_validity_month) {
        this.deal_validity_month = deal_validity_month;
    }

    public String getDeal_validity_year() {
        return deal_validity_year;
    }

    public void setDeal_validity_year(String deal_validity_year) {
        this.deal_validity_year = deal_validity_year;
    }

    public String getDeal_promo_code() {
        return deal_promo_code;
    }

    public void setDeal_promo_code(String deal_promo_code) {
        this.deal_promo_code = deal_promo_code;
    }
}

