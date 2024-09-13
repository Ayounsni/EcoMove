package dao.implementations;

import dao.interfaces.ITicketDao;
import db.DbFunctions;
import models.entities.Ticket;
import models.entities.Trajet;
import models.enums.TicketStatus;
import models.enums.TransportType;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketDao implements ITicketDao {

    private final DbFunctions db;
    private final TrajetDao trajetDao;

    public TicketDao() {
        this.db = DbFunctions.getInstance();
        this.trajetDao = new TrajetDao();
    }

    @Override
    public Ticket create(Ticket ticket) {

        if (ticket.getTicketStatus() == null) {
            ticket.setTicketStatus(TicketStatus.PENDING);
        }
        try (Connection connection = db.getConnection()) {
            String query = "INSERT INTO tickets (id, transporttype, purchaseprice, saleprice, saledate, ticketstatus, contractid, datedepart, horaire, idtrajet) " +
                    "VALUES (?, ?::transporttype, ?, ?, ?, ?::ticketstatus, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, ticket.getId());
            statement.setObject(2, ticket.getTransportType().name(), java.sql.Types.OTHER);
            statement.setBigDecimal(3, ticket.getPurchasePrice());
            statement.setBigDecimal(4, ticket.getSalePrice());
            statement.setDate(5, Date.valueOf(ticket.getSaleDate()));
            statement.setObject(6, ticket.getTicketStatus().name(), java.sql.Types.OTHER);
            statement.setObject(7, ticket.getContractId());
            statement.setDate(8, Date.valueOf(ticket.getDepartureDate()));
            statement.setTime(9, Time.valueOf(ticket.getDepartureTime()));
            statement.setObject(10, ticket.getTrajet() != null ? ticket.getTrajet().getId() : null);
            statement.executeUpdate();
            return ticket;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Ticket findById(UUID id) {
        try (Connection connection = db.getConnection()) {
            String query = "SELECT * FROM tickets WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToTicket(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = db.getConnection()) {
            String query = "SELECT * FROM tickets";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                tickets.add(mapResultSetToTicket(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public boolean updateTicketStatus(UUID ticketId, TicketStatus ticketStatus) {
        try (Connection connection = db.getConnection()) {
            String query = "UPDATE tickets SET ticketStatus = ?::ticketstatus WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, ticketStatus.name(), java.sql.Types.OTHER);
            statement.setObject(2, ticketId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean delete(UUID id) {
        try (Connection connection = db.getConnection()) {
            String query = "DELETE FROM tickets WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Ticket mapResultSetToTicket(ResultSet resultSet) throws SQLException {
        UUID id = (UUID) resultSet.getObject("id");
        TransportType transportType = TransportType.valueOf(resultSet.getString("transportType"));
        BigDecimal purchasePrice = resultSet.getBigDecimal("purchasePrice");
        BigDecimal salePrice = resultSet.getBigDecimal("salePrice");
        LocalDate saleDate = resultSet.getDate("saleDate").toLocalDate();
        LocalDate departureDate = resultSet.getDate("dateDepart").toLocalDate();
        LocalTime departureTime = resultSet.getTime("horaire").toLocalTime();
        TicketStatus ticketStatus = TicketStatus.valueOf(resultSet.getString("ticketStatus"));
        UUID contractId = (UUID) resultSet.getObject("contractId");
        UUID trajetId = (UUID) resultSet.getObject("idTrajet");
        Trajet trajet = trajetId != null ? trajetDao.findById(trajetId) : null;

        return new Ticket(transportType, purchasePrice, salePrice, saleDate, departureDate, departureTime,  contractId,ticketStatus, trajet, id);
    }
}
