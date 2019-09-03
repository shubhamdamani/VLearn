package com.example.vlearn;

public class questionfetch {

    private String Topic,User_Id,Q_Id,Question;

    public questionfetch(String topic, String user_Id, String q_Id, String question) {
        Topic = topic;
        User_Id = user_Id;            //Actually UserName
        Q_Id = q_Id;
        Question = question;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getQ_Id() {
        return Q_Id;
    }

    public void setQ_Id(String q_Id) {
        Q_Id = q_Id;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }
}
