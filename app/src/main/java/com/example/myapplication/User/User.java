package com.example.myapplication.User;

public class User {
    private static String email="";

    public static void setEmail(String email){
        User.email=email;
    }

    public static String getUserEmail(){
        return email;
    }
}
