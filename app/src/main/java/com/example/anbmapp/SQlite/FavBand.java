package com.example.anbmapp.SQlite;

public class FavBand {

    private String _fav_Band_Name;
    private String _fav_Band_Genre;
    private String _fav_Band_Based_City;
    private String _fav_Band_Email;
    private String _fav_Status;
//    private String _fav_Band_id;

    public FavBand() {
    }

    public FavBand(String _fav_Band_Name, String _fav_Band_Genre, String _fav_Band_Based_City, String _fav_Band_Email, String _fav_Status) {
        this._fav_Band_Name = _fav_Band_Name;
        this._fav_Band_Genre = _fav_Band_Genre;
        this._fav_Band_Based_City = _fav_Band_Based_City;
        this._fav_Band_Email = _fav_Band_Email;
        this._fav_Status = _fav_Status;
//        this._fav_Band_id = _fav_Band_id;
    }

    public String get_fav_Band_Name() {
        return _fav_Band_Name;
    }

    public String get_fav_Band_Genre() {
        return _fav_Band_Genre;
    }

    public String get_fav_Band_Based_City() {
        return _fav_Band_Based_City;
    }

    public String get_fav_Band_Email() {
        return _fav_Band_Email;
    }

    public String get_fav_Status() {
        return _fav_Status;
    }

//    public String get_fav_Band_id() {
//        return _fav_Band_id;
//    }

    public void set_fav_Band_Name(String _fav_Band_Name) {
        this._fav_Band_Name = _fav_Band_Name;
    }

    public void set_fav_Band_Genre(String _fav_Band_Genre) {
        this._fav_Band_Genre = _fav_Band_Genre;
    }

    public void set_fav_Band_Based_City(String _fav_Band_Based_City) {
        this._fav_Band_Based_City = _fav_Band_Based_City;
    }

    public void set_fav_Band_Email(String _fav_Band_Email) {
        this._fav_Band_Email = _fav_Band_Email;
    }

    public void set_fav_Status(String _fav_Status) {
        this._fav_Status = _fav_Status;
    }

//    public void set_fav_Band_id(String _fav_Band_id) {
//        this._fav_Band_id = _fav_Band_id;
//    }
}
