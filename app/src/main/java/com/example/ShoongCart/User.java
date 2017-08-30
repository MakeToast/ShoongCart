package com.example.ShoongCart;

/**
 * Created by choeyujin on 2017. 8. 25..
 */

public class User {
    public String username;
    public String password;
    public String email;
    public boolean sex; // 0 : male, 1 : female
    public int age; // 0 : 10, 1 : 20, 2 : 30, 3 : 40, 4 : 50, 5 : 6이상

    public User()
    {

    }
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
