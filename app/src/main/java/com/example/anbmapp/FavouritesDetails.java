package com.example.anbmapp;

public class FavouritesDetails {
    String Band_Id;
    String Band_Name;

    public FavouritesDetails() {
    }

    public FavouritesDetails(String Band_Id, String Band_Name) {
        this.Band_Id = Band_Id;
        this.Band_Name = Band_Name;
    }

    //getter

    public String getBand_Id() {
        return Band_Id;
    }

    public String getBand_Name() {
        return Band_Name;
    }
}
