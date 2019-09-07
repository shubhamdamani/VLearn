package com.example.vlearn;

public class Post_content {

    String post_user,artical;
    int upvote,downvotes;

    public Post_content(String post_user, String artical, int upvote, int downvotes) {
        this.post_user = post_user;
        this.artical = artical;
        this.upvote = upvote;
        this.downvotes = downvotes;
    }

    public String getPost_user() {
        return post_user;
    }

    public void setPost_user(String post_user) {
        this.post_user = post_user;
    }

    public String getArtical() {
        return artical;
    }

    public void setArtical(String artical) {
        this.artical = artical;
    }

    public int getUpvote() {
        return upvote;
    }

    public void setUpvote(int upvote) {
        this.upvote = upvote;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }
}
