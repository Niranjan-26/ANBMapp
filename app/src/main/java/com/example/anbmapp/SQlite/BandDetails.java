package com.example.anbmapp.SQlite;

public class BandDetails {
    private String Band_Name;
    private String Genre;
    private String Based_City;
    private String Address;
    private String Email;
    private String key_Id;

    public BandDetails() {
    }

    public BandDetails(String band_Name, String genre, String based_City, String address, String email, String key_Id) {
        Band_Name = band_Name;
        Genre = genre;
        Based_City = based_City;
        Address = address;
        Email = email;
        this.key_Id = key_Id;
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

    public String getAddress() {
        return Address;
    }

    public String getEmail() {
        return Email;
    }

    public String getKey_Id() {
        return key_Id;
    }

    public void setBand_Name(String band_Name) {
        Band_Name = band_Name;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public void setBased_City(String based_City) {
        Based_City = based_City;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setKey_Id(String key_Id) {
        this.key_Id = key_Id;
    }
}
