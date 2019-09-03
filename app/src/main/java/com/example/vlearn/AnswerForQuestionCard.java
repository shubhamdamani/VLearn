package com.example.vlearn;

public class AnswerForQuestionCard {

    String Answer, User_Id, UserName;

    public AnswerForQuestionCard(String answer, String user_Id, String userName) {
        Answer = answer;
        User_Id = user_Id;
        UserName = userName;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
