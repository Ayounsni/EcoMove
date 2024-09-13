package dao.implementations;

import dao.interfaces.IClientDao;
import db.DbFunctions;
import models.entities.Client;

import java.sql.*;
import java.util.UUID;

public class ClientDao implements IClientDao {

    private final DbFunctions db;

    public ClientDao() {

        this.db = DbFunctions.getInstance();
    }

    @Override
    public String addClient(Client client) {
        String query = "INSERT INTO clients (id, firstname, lastname, email, phone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setObject(1, client.getId());
            stmt.setString(2, client.getFirstname());
            stmt.setString(3, client.getLastname());
            stmt.setString(4, client.getEmail());
            stmt.setString(5, client.getPhone());

            stmt.executeUpdate();
            return client.getEmail();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Client findClientByEmail(String email) {
        String query = "SELECT * FROM clients WHERE email = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Client client = new Client();
                client.setId(UUID.fromString(rs.getString("id")));
                client.setFirstname(rs.getString("firstname"));
                client.setLastname(rs.getString("lastname"));
                client.setEmail(rs.getString("email"));
                client.setPhone(rs.getString("phone"));
                return client;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateClient(Client client) {
        String query = "UPDATE clients SET firstname = ?, lastname = ?, phone = ? WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, client.getFirstname());
            stmt.setString(2, client.getLastname());
            stmt.setString(3, client.getPhone());
            stmt.setObject(4, client.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}
