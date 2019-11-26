package com.example.demo.myapplication.Class;

public class Work {
    private String work,dateDL,timeDL;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Work() {
    }

    public Work(int id,String work, String dateDL, String timeDL) {
        this.id = id;
        this.work = work;
        this.dateDL = dateDL;
        this.timeDL = timeDL;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getDateDL() {
        return dateDL;
    }

    public void setDateDL(String dateDL) {
        this.dateDL = dateDL;
    }

    public String getTimeDL() {
        return timeDL;
    }

    public void setTimeDL(String timeDL) {
        this.timeDL = timeDL;
    }
}
