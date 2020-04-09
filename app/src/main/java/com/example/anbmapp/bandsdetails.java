package com.example.anbmapp;

import java.io.Serializable;

public class bandsdetails implements Serializable {
    private String Band_Name;
    private String Genre;
    private String Based_City;
    private String Email;
    private String Band_Id;
    private String Phone;


    public bandsdetails(){

    }
    public bandsdetails(String band_Name, String genre, String based_City, String email, String band_Id, String phone){
        Band_Name = band_Name;
        Genre = genre;
        Based_City = based_City;
        Email = email;
        Band_Id = band_Id;
        Phone = phone;


    }
    //getter


    public String getPhone() {
        return Phone;
    }

    public String getBand_Id() {
        return Band_Id;
    }

    public String getBand_Name() {
        return Band_Name;
    }

    public String getGenre() {
        return Genre;
    }

    public String getBased_City() {
        return Based_City;
    }

    public String getEmail() {
        return Email;
    }
    //setter

}
