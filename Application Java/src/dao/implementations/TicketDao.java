package dao.implementations;

import dao.interfaces.ITicketDao;
import db.DbFunctions;
import models.entities.City;
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
    @Override
    public List<Ticket> findAvailableTickets(String departureCity, String arrivalCity, LocalDate departureDate) {
        List<Ticket> tickets = new ArrayList<>();
        String query = "SELECT t.id, t.transporttype, t.purchaseprice, t.saleprice, t.saledate, " +
                "t.datedepart, t.horaire, t.ticketstatus, t.contractid, t.idtrajet, " +
                "tr.duree, cD.cityname AS cityD, cA.cityname AS cityA " +
                "FROM tickets t " +
                "JOIN trajets tr ON t.idtrajet = tr.id " +
                "JOIN cities cD ON tr.idcityd = cD.id " +
                "JOIN cities cA ON tr.idcitya = cA.id " +
                "WHERE cD.cityname = ? AND cA.cityname = ? AND t.datedepart = ? AND t.ticketstatus = 'PENDING'";


        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, departureCity);
            stmt.setString(2, arrivalCity);
            stmt.setDate(3, Date.valueOf(departureDate));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket(
                        TransportType.valueOf(rs.getString("transporttype")),
                        rs.getBigDecimal("purchaseprice"),
                        rs.getBigDecimal("saleprice"),
                        rs.getTimestamp("saledate").toLocalDateTime().toLocalDate(),
                        rs.getDate("datedepart").toLocalDate(),
                        rs.getTime("horaire").toLocalTime(),
                        UUID.fromString(rs.getString("contractid")),
                        TicketStatus.valueOf(rs.getString("ticketstatus")),
                        new Trajet(
                                rs.getInt("duree"),
                                new City(rs.getString("cityD")),
                                new City(rs.getString("cityA")),
                                UUID.fromString(rs.getString("idtrajet"))
                        ),
                        UUID.fromString(rs.getString("id"))
                );
                tickets.add(ticket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
    @Override
    public Ticket getById(UUID ticketId) {
        String query = "SELECT * FROM tickets WHERE id = ?";
        try (Connection connection = db.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, ticketId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Extraction des champs depuis le ResultSet
                UUID id = UUID.fromString(rs.getString("id"));
                TransportType transportType = TransportType.valueOf(rs.getString("transport_type"));
                BigDecimal purchasePrice = rs.getBigDecimal("purchase_price");
                BigDecimal salePrice = rs.getBigDecimal("sale_price");
                LocalDate saleDate = rs.getDate("sale_date").toLocalDate();
                LocalDate departureDate = rs.getDate("departure_date").toLocalDate();
                LocalTime departureTime = rs.getTime("departure_time").toLocalTime();
                TicketStatus ticketStatus = TicketStatus.valueOf(rs.getString("ticket_status"));
                UUID contractId = UUID.fromString(rs.getString("contract_id"));

                // On peut également récupérer Trajet via une autre méthode
                UUID trajetId = UUID.fromString(rs.getString("trajet_id"));
                Trajet trajet = trajetDao.findById(trajetId); // Vous devrez avoir un TrajetDao avec une méthode getById()

                // Retourner un objet Ticket
                return new Ticket(transportType, purchasePrice, salePrice, saleDate, departureDate, departureTime, contractId, ticketStatus, trajet, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
