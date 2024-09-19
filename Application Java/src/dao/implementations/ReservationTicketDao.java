package dao.implementations;

import dao.interfaces.IReservationTicketDao;
import dao.interfaces.ITicketDao;
import dao.interfaces.IReservationDao;
import dao.interfaces.ITrajetDao;
import db.DbFunctions;
import models.entities.*;
import models.enums.ReservationStatus;
import models.enums.TicketStatus;
import models.enums.TransportType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReservationTicketDao implements IReservationTicketDao {

    private final DbFunctions db;
    private final ITicketDao ticketDao;
    private final IReservationDao reservationDao;
    private final ITrajetDao trajetDao;

    public ReservationTicketDao() {
        this.db = DbFunctions.getInstance();
        this.ticketDao = new TicketDao();
        this.reservationDao = new ReservationDao();
        this.trajetDao = new TrajetDao();
    }

    @Override
    public void add(ReservationTicket reservationTicket) {
        String query = "INSERT INTO reservationtickets (idTicket, idReservation) VALUES (?, ?)";
        try (Connection connection = db.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, reservationTicket.getTicket().getId());
            stmt.setObject(2, reservationTicket.getReservation().getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ReservationTicket getByTicketId(UUID ticketId) {
        String query = "SELECT * FROM reservation_tickets WHERE ticket_id = ?";
        try (Connection connection = db.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, ticketId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UUID ticketUUID = UUID.fromString(rs.getString("ticket_id"));
                UUID reservationUUID = UUID.fromString(rs.getString("reservation_id"));

                Ticket ticket = ticketDao.getById(ticketUUID);
                Reservation reservation = reservationDao.getById(reservationUUID);

                ReservationTicket reservationTicket = new ReservationTicket();
                reservationTicket.setTicket(ticket);
                reservationTicket.setReservation(reservation);

                return reservationTicket;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ReservationTicket> getReservationsByClient(Client client) {
        List<ReservationTicket> reservationTickets = new ArrayList<>();

        String query = """
        SELECT r.id AS reservation_id, r.datereservation, r.prix AS reservation_prix, r.reservationstatus, 
               t.id AS ticket_id, t.transporttype, t.purchaseprice, t.saleprice, t.saledate, 
               t.datedepart, t.horaire, t.ticketstatus AS ticket_status, t.contractid, t.idtrajet
        FROM reservations r
        JOIN reservationtickets rt ON r.id = rt.idreservation
        JOIN tickets t ON rt.idticket = t.id
        WHERE r.clientid = ? AND r.reservationstatus = 'CONFIRMED'
    """;

        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setObject(1, client.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                UUID reservationId = UUID.fromString(rs.getString("reservation_id"));
                Timestamp dateReservation = rs.getTimestamp("datereservation");
                BigDecimal reservationPrice = rs.getBigDecimal("reservation_prix");
                ReservationStatus reservationStatus = ReservationStatus.valueOf(rs.getString("reservationstatus"));

                Reservation reservation = new Reservation();
                reservation.setId(reservationId);
                reservation.setDateReservation(dateReservation);
                reservation.setPrice(reservationPrice);
                reservation.setReservationStatus(reservationStatus);
                reservation.setClient(client);

                UUID ticketId = UUID.fromString(rs.getString("ticket_id"));
                TransportType transportType = TransportType.valueOf(rs.getString("transporttype"));
                BigDecimal purchasePrice = rs.getBigDecimal("purchaseprice");
                BigDecimal salePrice = rs.getBigDecimal("saleprice");
                LocalDate departureDate = LocalDate.parse(rs.getString("datedepart"));
                LocalTime departureTime = LocalTime.parse(rs.getString("horaire"));
                TicketStatus ticketStatus = TicketStatus.valueOf(rs.getString("ticket_status"));
                UUID trajetId = UUID.fromString(rs.getString("idtrajet"));

                Trajet trajet = trajetDao.findById(trajetId);



                Ticket ticket = new Ticket();
                ticket.setId(ticketId);
                ticket.setTransportType(transportType);
                ticket.setPurchasePrice(purchasePrice);
                ticket.setSalePrice(salePrice);
                ticket.setDepartureDate(departureDate);
                ticket.setDepartureTime(departureTime);
                ticket.setTicketStatus(ticketStatus);
                ticket.setTrajet(trajet);

                ReservationTicket reservationTicket = new ReservationTicket(ticket, reservation);

                reservationTickets.add(reservationTicket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reservationTickets;
    }

}

