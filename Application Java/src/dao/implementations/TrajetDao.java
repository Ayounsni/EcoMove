package dao.implementations;

import dao.interfaces.ICityDao;
import dao.interfaces.ITrajetDao;
import db.DbFunctions;
import models.entities.City;
import models.entities.Trajet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TrajetDao implements ITrajetDao {

    private final DbFunctions db;
    private ICityDao cityDao;

    public TrajetDao() {
        this.db = DbFunctions.getInstance();
        this.cityDao = new CityDao();
    }

    @Override
    public Trajet create(Trajet trajet) {
        try (Connection connection = db.getConnection()) {
            String query = "INSERT INTO trajets (id, duree, idcityD, idcityA) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, trajet.getId());
            statement.setInt(2, trajet.getDuree());
            statement.setInt(3, trajet.getCityDepart().getId());
            statement.setInt(4, trajet.getCityA().getId());
            statement.executeUpdate();
            return trajet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Trajet findById(UUID id) {
        try (Connection connection = db.getConnection()) {
            String query = "SELECT * FROM trajets WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int cityDId = resultSet.getInt("idcityD");
                int cityAId = resultSet.getInt("idcityA");


                City cityD = cityDao.findById(cityDId);
                City cityA = cityDao.findById(cityAId);

                return new Trajet( resultSet.getInt("duree"), cityD, cityA,id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Trajet> findAll() {
        List<Trajet> trajets = new ArrayList<>();
        try (Connection connection = db.getConnection()) {
            String query = "SELECT * FROM trajets";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                UUID id = (UUID) resultSet.getObject("id");
                int cityDId = resultSet.getInt("idcityD");
                int cityAId = resultSet.getInt("idcityA");

                City cityD = cityDao.findById(cityDId);
                City cityA = cityDao.findById(cityAId);


                Trajet trajet = new Trajet(resultSet.getInt("duree"), cityD, cityA, id);

                trajets.add(trajet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trajets;
    }


    @Override
    public boolean update(Trajet trajet) {
        try (Connection connection = db.getConnection()) {
            String query = "UPDATE trajets SET duree = ?, idcityD = ?, idcityA = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, trajet.getDuree());
            statement.setInt(2, trajet.getCityDepart().getId());
            statement.setInt(3, trajet.getCityA().getId());
            statement.setObject(4, trajet.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(UUID id) {
        try (Connection connection = db.getConnection()) {
            String query = "DELETE FROM trajets WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
