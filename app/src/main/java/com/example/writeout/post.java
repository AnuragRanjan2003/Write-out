package com.example.writeout;

public class post {
    String Article, Author,Category,Title,AuthorName;
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

    public post(String article, String author, String category, String title, String authorName) {
        Article = article;
        Author = author;
        Category = category;
        Title = title;
        AuthorName=authorName;
    }
    public post(String article,  String category, String title) {
        Article = article;
        Category = category;
        Title = title;
    }
}
