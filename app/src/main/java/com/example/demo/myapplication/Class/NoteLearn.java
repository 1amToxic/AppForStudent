package com.example.demo.myapplication.Class;

public class NoteLearn {
    private String title,content;
    private int id;
    public NoteLearn() {
    }

    public NoteLearn(int id,String title, String content) {
        this.title = title;
        this.content = content;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
