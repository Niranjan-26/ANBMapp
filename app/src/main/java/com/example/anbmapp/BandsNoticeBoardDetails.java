package com.example.anbmapp;

public class BandsNoticeBoardDetails {
    private String Date, Notice_Title, Start_Time, End_Time, Venue, Description;

    public BandsNoticeBoardDetails() {
    }

    public BandsNoticeBoardDetails(String date, String notice_Title, String start_Time, String end_Time, String venue, String description) {
        Date = date;
        Notice_Title = notice_Title;
        Start_Time = start_Time;
        End_Time = end_Time;
        Venue = venue;
        Description = description;
    }

    public String getDate() {
        return Date;
    }

    public String getNotice_Title() {
        return Notice_Title;
    }

    public String getStart_Time() {
        return Start_Time;
    }

    public String getEnd_Time() {
        return End_Time;
    }

    public String getVenue() {
        return Venue;
    }

    public String getDescription() {
        return Description;
    }
}
