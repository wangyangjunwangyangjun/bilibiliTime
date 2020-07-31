package com.example.time.dataBase;

public class DataBaseItem {
    private String name;
    private String type;
    private String time;
    private int  costicon;
    private double cost;
    private String costdate;
    private String place;
    private String note;
    private int image;
    public DataBaseItem() {
    }
    public DataBaseItem(String name, String type, String time, int costicon, double cost, String costdate, String place, String note, int image) {
        this.name = name;
        this.type = type;
        this.time = time;
        this.costicon = costicon;
        this.cost = cost;
        this.costdate = costdate;
        this.place = place;
        this.note = note;
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCosticon(int costicon) {
        this.costicon = costicon;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setCostdate(String costdate) {
        this.costdate = costdate;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public int getCosticon() {
        return costicon;
    }

    public double getCost() {
        return cost;
    }

    public String getCostdate() {
        return costdate;
    }

    public String getPlace() {
        return place;
    }

    public String getNote() {
        return note;
    }

    public int getImage() {
        return image;
    }

}