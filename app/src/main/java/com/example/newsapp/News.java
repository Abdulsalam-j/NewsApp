package com.example.newsapp;

public class News {

    private String section;
    private String author;
    private String title;
    private String publishDate;
    private String url;

    public News(String section, String author, String title, String publishDate, String url) {
        this.section = section;
        this.author = author;
        this.title = title;
        this.publishDate = publishDate;
        this.url = url;
    }

    public String getSection() {
        return section;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getUrl() {
        return url;
    }
}
