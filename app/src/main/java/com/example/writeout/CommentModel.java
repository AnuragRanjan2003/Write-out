package com.example.writeout;

public class CommentModel {
    String comment;
    String sender;

    public CommentModel() {
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CommentModel(String sender, String comment) {
        this.sender = sender;
        this.comment = comment;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
