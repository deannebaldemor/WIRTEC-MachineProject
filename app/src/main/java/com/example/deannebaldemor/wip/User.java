package com.example.deannebaldemor.wip;

/**
 * Created by Deanne Baldemor on 02/04/2018.
 */

public class User implements Comparable<User> {

    public String username;
    public int numCoins;
    public int highScore;



    public User(String username, int numCoins, int highScore){
        this.username=username;
        this.numCoins=numCoins;
        this.highScore=highScore;
    }

    public String getUsername(){return username;}
    public int getNumCoins(){return numCoins;}
    public int getHighScore(){return highScore;}


    @Override
    public int compareTo(User user) {
        int compareScore = user.getHighScore();
        return (compareScore - this.highScore);
    }

    //ADD
    public User(){}
    //ADD
}
