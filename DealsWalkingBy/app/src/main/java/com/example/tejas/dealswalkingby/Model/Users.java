package com.example.tejas.dealswalkingby.Model;

/**
 * Created by Tejas on 17-02-2018.
 */

public class Users {

    private String user;
    private String email;
    private String photo_url;
    private String UID;

    public Users(String s, String email, String photo_url, String uid){
        this.user = user;
        this.email = this.email;
        this.photo_url = this.photo_url;
        this.UID = UID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(){
        this.photo_url = photo_url;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
