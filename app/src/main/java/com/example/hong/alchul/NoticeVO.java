package com.example.hong.alchul;

public class NoticeVO {

    private String id;
    private String title;
    private String content;
    private String time;

    public NoticeVO(){}

    public NoticeVO(String id, String title, String content, String time) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() { return title; }

    public String getTime() {
        return time;
    }
}