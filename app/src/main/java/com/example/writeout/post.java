package com.example.writeout;

public class post {
    String Article;
    String Author;
    String Category;
    String Title;
    String AuthorName;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    String Date;
    public post() {}

    public String getArticle() {
        return Article;
    }

    public void setArticle(String article) {
        Article = article;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    public void setAuthorName(String authorName) {
        AuthorName = authorName;
    }

    public post(String article, String author, String category, String title, String authorName,String date) {
        Article = article;
        Author = author;
        Category = category;
        Title = title;
        AuthorName=authorName;
        Date=date;
    }
    public post(String article,  String category, String title,String date) {
        Article = article;
        Category = category;
        Title = title;
        Date=date;
    }
}
