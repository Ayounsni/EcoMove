package services.interfaces;

import models.entities.City;

import java.util.List;

public interface ICityService {
    City findById(int id);
    List<City> findAll();


}
