package models.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Trajet {

    private UUID id;
    private int dure;
    private City cityD;
    private City cityA;
    private List<Ticket> tickets;


    public Trajet() {
    }


    public Trajet(int dure, City cityD, City cityA) {
        this.id = UUID.randomUUID();
        this.dure = dure;
        this.cityD = cityD;
        this.cityA = cityA;
        this.tickets = new ArrayList<>();
    }

    public Trajet( int dure, City cityD, City cityA , UUID id) {
        this.dure = dure;
        this.cityD = cityD;
        this.cityA = cityA;
        this.id = id;
        this.tickets = new ArrayList<>();
    }


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
    public List<Ticket> getTickets() {
        return tickets;
    }
}

