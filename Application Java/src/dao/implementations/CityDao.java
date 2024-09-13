package dao.implementations;

import dao.interfaces.ICityDao;
import db.DbFunctions;
import models.entities.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDao implements ICityDao {

    private final DbFunctions db;

    public CityDao() {
        this.db = DbFunctions.getInstance();
    }


    @Override
    public City findById(int id) {
        try (Connection connection = db.getConnection()) {
            String query = "SELECT * FROM cities WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new City(resultSet.getInt("id"), resultSet.getString("cityName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<City> findAll() {
        List<City> cities = new ArrayList<>();
        try (Connection connection = db.getConnection()) {
            String query = "SELECT * FROM cities";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                cities.add(new City(resultSet.getInt("id"), resultSet.getString("cityName")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }




}
