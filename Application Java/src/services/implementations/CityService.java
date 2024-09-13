package services.implementations;

import dao.interfaces.ICityDao;
import models.entities.City;
import services.interfaces.ICityService;

import java.util.List;

public class CityService implements ICityService {

    private final ICityDao cityDao;

    public CityService(ICityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public City findById(int id) {
        return cityDao.findById(id);
    }

    @Override
    public List<City> findAll() {
        return cityDao.findAll();
    }


}
