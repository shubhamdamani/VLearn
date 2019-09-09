package com.example.vlearn;

public class Post {

    private String title;
    private String author;
    private String date_updated;
    private String postURL;
    private String thumbnailURL;
    private String description;
    private String source;

    public Post(String title, String author, String date_updated, String postURL, String thumbnailURL, String description, String source) {
        this.title = title;
        this.author = author;
        this.date_updated = date_updated;
        this.postURL = postURL;
        this.thumbnailURL = thumbnailURL;
        this.description = description;
        this.source = source;
    }

    public Post(String title, String lastBuildDate, String link, String s) {

        this.title=title;
        this.date_updated=lastBuildDate;
        this.postURL=link;
        this.author=s;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(String date_updated) {
        this.date_updated = date_updated;
    }

    public String getPostURL() {
        return postURL;
    }

    public void setPostURL(String postURL) {
        this.postURL = postURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
