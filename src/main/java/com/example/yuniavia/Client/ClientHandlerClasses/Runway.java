package com.example.yuniavia.Client.ClientHandlerClasses;

import java.io.Serializable;

public class Runway implements Serializable {
    private int flight_id;
    private String state;
    private String picture_path;
    private String number_of_runway;
    private String occupation;





    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }



    public String getNumber_of_runway() {
        return number_of_runway;
    }

    public void setNumber_of_runway(String number_of_runway) {
        this.number_of_runway = number_of_runway;
    }



    public Runway(int flight_id, String state, String picture_path, String number_of_runway) {
        this.flight_id = flight_id;
        this.state = state;
        this.picture_path = picture_path;
        this.number_of_runway=number_of_runway;
    }
    public Runway(int flight_id, String state, String picture_path, String number_of_runway, String occupation) {
        this.flight_id = flight_id;
        this.state = state;
        this.picture_path = picture_path;
        this.number_of_runway=number_of_runway;
        this.occupation=occupation;
    }

    public Runway() {

    }


    @Override
    public String toString() {
        return "Runway{" +
                "flight_id=" + flight_id +
                ", state='" + state + '\'' +
                ", picture_path='" + picture_path + '\'' +
                ", number_of_runway='" + number_of_runway + '\'' +
                '}';
    }

    public int getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPicture_path() {
        return picture_path;
    }

    public void setPicture_path(String picture_path) {
        this.picture_path = picture_path;
    }
}
