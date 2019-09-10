package com.example.vlearn.object;

public class Lboard_user {
    String UserName,UserEmail,UserId;



    public Lboard_user(String userName, String userEmail, String userId) {
        UserName = userName;
        UserEmail = userEmail;
        UserId = userId;
    }
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
