package dao.interfaces;

import models.entities.City;

import java.util.List;

public interface ICityDao {

    City findById(int id);
    List<City> findAll();

}
