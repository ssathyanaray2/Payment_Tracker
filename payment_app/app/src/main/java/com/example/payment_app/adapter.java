package com.example.payment_app;

public class adapter {
    private String name;
    private String description;
    private String date;
    private String period;

    public adapter(){};



    public adapter(String name, String description, String date, String period) {
        this.name = name;
        this.description=description;
        this.date=date;
        this.period=period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriod() {
        return period;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
