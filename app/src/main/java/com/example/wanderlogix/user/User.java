package com.example.wanderlogix.user;

public class User {
    public static User user=new User();
    public String Id;
    public String Name;

    public void setId(String id){
        user.Id=id;
    }

    public String getId(){
        return user.Id;
    }

    public void setName(String name){
        user.Name=name;
    }

    public String getName(){
        return user.Name;
    }
}
