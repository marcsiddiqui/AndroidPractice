package com.example.apr;

import java.util.Date;

public class Notification {

    public Notification(String title, String description){
        this.Title = title;
        this.Description = description;
        Date date = new Date();
        this.CreatedOn = date;
        this.IsRead = false;
    }

    public Notification(){
    }

    int Id;
    String Title;
    String Description;
    Date CreatedOn;
    Boolean IsRead;
}
