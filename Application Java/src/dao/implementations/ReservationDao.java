package dao.implementations;


import dao.interfaces.IClientDao;
import dao.interfaces.IReservationDao;
import db.DbFunctions;
import models.entities.Client;
import models.entities.Reservation;
import models.enums.ReservationStatus;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.UUID;

public class ReservationDao implements IReservationDao {

    private final DbFunctions db;
    private final IClientDao clientDao;


    public ReservationDao() {
        this.db = DbFunctions.getInstance();
        this.clientDao = new ClientDao();
    }

    @Override
    public void add(Reservation reservation) {
        String query = "INSERT INTO reservations (id, datereservation, prix, reservationstatus, clientId) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = db.getConnection() ; PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, reservation.getId());
            stmt.setTimestamp(2, reservation.getDateReservation());
            stmt.setBigDecimal(3, reservation.getPrice());
            stmt.setObject(4, reservation.getReservationStatus().name(), java.sql.Types.OTHER);
            stmt.setObject(5, reservation.getClient().getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reservation getById(UUID reservationId) {
        String query = "SELECT * FROM reservations WHERE id = ?";
        try (Connection connection = db.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, reservationId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                Timestamp dateReservation = rs.getTimestamp("date_reservation");
                BigDecimal price = rs.getBigDecimal("price");
                ReservationStatus status = ReservationStatus.valueOf(rs.getString("reservation_status"));

                UUID clientId = UUID.fromString(rs.getString("client_id"));
                Client client = clientDao.getById(clientId);

                Reservation reservation = new Reservation();
                reservation.setId(id);
                reservation.setDateReservation(dateReservation);
                reservation.setPrice(price);
                reservation.setReservationStatus(status);
                reservation.setClient(client);

                return reservation;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
