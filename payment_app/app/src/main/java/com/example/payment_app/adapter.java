package com.example.payment_app;

public class adapter {
    private String name;
    private String description;
    private String date;
    private String period;
    private String edate;
    private String eventid;
    private String dmy;
    private String img;
    private Double amount;
    private int crdr;
    public adapter(){};



    public adapter(String name, String description, String date, String period,String edate,String eventid,String dmy,String img,Double amount,int crdr) {
        this.name = name;
        this.description=description;
        this.date=date;
        this.period=period;
        this.edate=edate;
        this.eventid=eventid;
        this.dmy=dmy;
        this.img=img;
        this.amount=amount;
        this.crdr=crdr;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public Double getAmount() {
        return amount;
    }

    public int getCrdr() {
        return crdr;
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

    public String getEdate() {
        return edate;
    }

    public String getEventid() {
        return eventid;
    }

    public String getDmy() {
        return dmy;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public void setDmy(String dmy) {
        this.dmy = dmy;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setCrdr(int crdr) {
        this.crdr = crdr;
    }

}
