package com.example.time.budget;

public class JiZhang_ShouRu_ZhiZhu_listview_Item_Message {
    private String name;
    private int imaged;
    private String money;
    private String time;

    public JiZhang_ShouRu_ZhiZhu_listview_Item_Message(String name, int imaged, String money, String time) {
        this.name = name;
        this.imaged = imaged;
        this.money = money;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImaged() {
        return imaged;
    }

    public void setImaged(int imaged) {
        this.imaged = imaged;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

