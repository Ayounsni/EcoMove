package models.entities;

import java.util.UUID;

public class Trajet {

    private UUID id;
    private int dure;
    private City cityD;
    private City cityA;


    public Trajet() {
    }


    public Trajet(int dure, City cityD, City cityA) {
        this.id = UUID.randomUUID();
        this.dure = dure;
        this.cityD = cityD;
        this.cityA = cityA;
    }



    // Getters et Setters
    public UUID getId() {
        return id;
    }



    public int getDuree() {
        return dure;
    }

    public void setDuree(int dure) {
        this.dure = dure;
    }

    public City getCityDepart() {
        return cityD;
    }

    public void setCityDepart(City cityD) {
        this.cityD = cityD;
    }

    public City getCityA() {
        return cityA;
    }

    public void setCityA(City cityA) {
        this.cityA = cityA;
    }
}

