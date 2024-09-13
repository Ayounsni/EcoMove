package models.entities;

import java.util.ArrayList;
import java.util.List;

public class City {

    private int id;
    private String cityName;
    private List<Trajet> trajets;

    public City() {
    }
    public City(int id, String cityName) {
        this.id = id;
        this.cityName = cityName;
        this.trajets = new ArrayList<>();
    }


    public City(String cityName) {
        this.cityName = cityName;
        this.trajets = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }
    public List<Trajet> getTrajets() {
        return trajets;
    }

}

