package com.example.tejas.dealswalkingby;

/**
 * Created by Tejas on 25-01-2018.
 */

public class UserInformation {

    public String name;
    public String surname;
    public String phone;
    public String billingAddress;

    public UserInformation(){

    }

    public UserInformation(String name,String surname,String phone,String billingAddress){
        this.name = name;
        this.phone = phone;
        this.surname = surname;
        this.billingAddress = billingAddress;
    }

}
