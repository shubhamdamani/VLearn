package com.example.vlearn;

public class Post_content {

    String Post_Id,Post_Title,Post_content,Post_Date,User_Id,Topic,UserName;
    Integer Upvotes,Downvotes;

    public Post_content(String post_Id, String post_Title, String post_content, String post_Date, String user_Id, String topic, String userName, Integer upvotes, Integer downvotes) {
        Post_Id = post_Id;
        Post_Title = post_Title;
        Post_content = post_content;
        Post_Date = post_Date;
        User_Id = user_Id;
        Topic = topic;
        UserName = userName;
        Upvotes = upvotes;
        Downvotes = downvotes;
    }

    public String getPost_Id() {
        return Post_Id;
    }

    public void setPost_Id(String post_Id) {
        Post_Id = post_Id;
    }

    public String getPost_Title() {
        return Post_Title;
    }

    public void setPost_Title(String post_Title) {
        Post_Title = post_Title;
    }

    public String getPost_content() {
        return Post_content;
    }

    public void setPost_content(String post_content) {
        Post_content = post_content;
    }

    public String getPost_Date() {
        return Post_Date;
    }

    public void setPost_Date(String post_Date) {
        Post_Date = post_Date;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public Integer getUpvotes() {
        return Upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        Upvotes = upvotes;
    }

    public Integer getDownvotes() {
        return Downvotes;
    }

    public void setDownvotes(Integer downvotes) {
        Downvotes = downvotes;
    }
}
