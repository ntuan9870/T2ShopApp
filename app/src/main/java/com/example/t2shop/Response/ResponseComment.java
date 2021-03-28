package com.example.t2shop.Response;

import com.example.t2shop.Model.Comment;

import java.util.List;

public class ResponseComment {
    private List<Comment> comments;

    public ResponseComment() {
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
