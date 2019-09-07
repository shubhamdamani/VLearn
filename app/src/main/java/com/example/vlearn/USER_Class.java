package com.example.vlearn;

public class USER_Class {                                 /*Current User Logged In details*/
     static  String LOGGED_USER_NAME,LOGGED_USER_EMAIL;
    static  String LOGGED_USER_ID;
    //public static boolean loggedIn=false;

    public USER_Class(String LOGGED_USER_NAME, String LOGGED_USER_EMAIL, String LOGGED_USER_ID) {
        this.LOGGED_USER_NAME = LOGGED_USER_NAME;
        this.LOGGED_USER_EMAIL = LOGGED_USER_EMAIL;
        this.LOGGED_USER_ID = LOGGED_USER_ID;
    }

    public USER_Class() {
    }

    public static String getLoggedUserName() {
        return LOGGED_USER_NAME;
    }

    public static void setLoggedUserName(String loggedUserName) {
        LOGGED_USER_NAME = loggedUserName;
    }

    public static String getLoggedUserEmail() {
        return LOGGED_USER_EMAIL;
    }

    public static void setLoggedUserEmail(String loggedUserEmail) {
        LOGGED_USER_EMAIL = loggedUserEmail;
    }

    public static String getLoggedUserId() {
        return LOGGED_USER_ID;
    }

    public static void setLoggedUserId(String loggedUserId) {
        LOGGED_USER_ID = loggedUserId;
    }
}
