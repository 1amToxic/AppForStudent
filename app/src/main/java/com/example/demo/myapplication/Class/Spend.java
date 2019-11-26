package com.example.demo.myapplication.Class;

public class Spend {
    private String spend,money;
    private int id,moneyInt;

    public Spend() {
    }

    public int getMoneyInt() {
        return moneyInt;
    }

    public void setMoneyInt(int moneyInt) {
        this.moneyInt = moneyInt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Spend(int id, String spend, String money) {
        this.id = id;
        this.spend = spend;
        this.money = money;
    }

    public String getSpend() {
        return spend;
    }

    public void setSpend(String spend) {
        this.spend = spend;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
