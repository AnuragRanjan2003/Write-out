package com.example.writeout;

public class model {
    String date, category, title, authorName;

    public model() {
    }

    public model(String date, String category, String title) {
        this.date = date;
        this.category = category;
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public model(String date, String category, String title, String authorName) {
        this.date = date;
        this.category = category;
        this.title = title;
        this.authorName = authorName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
