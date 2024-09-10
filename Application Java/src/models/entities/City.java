package models.entities;

public class City {

    private int id;
    private String cityName;

    public City() {
    }


    public City(String cityName) {
        this.cityName = cityName;
    }

    // Getter pour id, pas de setter
    public int getId() {
        return id;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

}

