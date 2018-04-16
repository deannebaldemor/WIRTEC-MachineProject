package com.example.deannebaldemor.machineproject;

/**
 * Created by Deanne Baldemor on 10/03/2018.
 */

public class LandmarkModel {
    private Integer ID;
    private String name;
    private String continent;
    private String panoramaFile;
    private String filename;
    private Boolean answered;
    public LandmarkModel(Integer ID, String name, String continent, String filename,Boolean answered){
        this.ID=ID;
        this.name=name;
        this.continent=continent;
        this.filename=filename;
        this.answered=answered;
    }

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getContinent() {
        return continent;
    }
    public String getFilename() {
        return filename;
    }

    public Boolean getAnswered() {
        return answered;
    }

    public void setAnswered(Boolean answered) {
        this.answered = answered;
    }
}
