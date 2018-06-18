package com.example.deannebaldemor.wip;

/**
 * Created by Deanne Baldemor on 12/03/2018.
 */

public class QuestionsModel {
    private int landmarkID;
    public boolean answered;
    public QuestionsModel(int landmarkID, boolean answered){
        this.landmarkID=landmarkID;
        this.answered=answered;
        answered=true;
    }

    public int getLandmarkID() {
        return landmarkID;
    }

    public boolean getAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }
}
