package dao;

import db.DbFunctions;
import models.entities.Ticket;
import models.enums.TicketStatus;
import models.enums.TransportType;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.UUID;

public class TicketDao {
    private final DbFunctions db;

    public TicketDao() {
        this.db = DbFunctions.getInstance();
    }


    public void addTicket(Ticket ticket) {
        String query = "INSERT INTO ticket (id, transportType, purchasePrice, salePrice, saleDate, ticketStatus, contractId) VALUES (?, ?::transporttype, ?, ?, ?, ?::ticketstatus, ?)";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, ticket.getId());
            stmt.setObject(2, ticket.getTransportType().name(), java.sql.Types.OTHER);
            stmt.setBigDecimal(3, ticket.getPurchasePrice());
            stmt.setBigDecimal(4, ticket.getSalePrice());
            stmt.setDate(5, new java.sql.Date(ticket.getSaleDate().getTime()));
            stmt.setObject(6, ticket.getTicketStatus().name(), java.sql.Types.OTHER);
            stmt.setObject(7, ticket.getContractId());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new ticket was inserted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while inserting the ticket: " + e.getMessage());
        }
    }


    public void updateTicket(UUID ticketId, TransportType transportType, BigDecimal purchasePrice, BigDecimal salePrice, Date saleDate, TicketStatus ticketStatus) {
        String query = "UPDATE ticket SET transportType = ?::transporttype, purchasePrice = ?, salePrice = ?, saleDate = ?, ticketStatus = ?::ticketstatus WHERE id = ?";
        try {
            int rowsUpdated = getRowsUpdated(ticketId, transportType, purchasePrice, salePrice, saleDate, ticketStatus, query);
            if (rowsUpdated > 0) {
                System.out.println("Ticket updated successfully!");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while updating the ticket: " + e.getMessage());
        }
    }


    public void deleteTicket(UUID ticketId) {
        String query = "DELETE FROM ticket WHERE id = ?";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, ticketId);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("The ticket was deleted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while deleting the ticket: " + e.getMessage());
        }
    }


    public void displayTickets() {
        String query = "SELECT * FROM ticket";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                String transportType = rs.getString("transportType");
                BigDecimal purchasePrice = rs.getBigDecimal("purchasePrice");
                BigDecimal salePrice = rs.getBigDecimal("salePrice");
                Date saleDate = rs.getDate("saleDate");
                String ticketStatus = rs.getString("ticketStatus");
                UUID contractId = (UUID) rs.getObject("contractId");

                System.out.println("Ticket ID: " + id);
                System.out.println("Transport Type: " + transportType);
                System.out.println("Purchase Price: " + purchasePrice);
                System.out.println("Sale Price: " + salePrice);
                System.out.println("Sale Date: " + saleDate);
                System.out.println("Ticket Status: " + ticketStatus);
                System.out.println("Contract ID: " + contractId);
                System.out.println("--------------------------");
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while retrieving tickets: " + e.getMessage());
        }
    }


    private int getRowsUpdated(UUID ticketId, TransportType transportType, BigDecimal purchasePrice, BigDecimal salePrice, Date saleDate, TicketStatus ticketStatus, String query) throws SQLException {
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, transportType.name(), java.sql.Types.OTHER);
            stmt.setBigDecimal(2, purchasePrice);
            stmt.setBigDecimal(3, salePrice);
            stmt.setDate(4, new java.sql.Date(saleDate.getTime()));
            stmt.setObject(5, ticketStatus.name(), java.sql.Types.OTHER);
            stmt.setObject(6, ticketId);
            return stmt.executeUpdate();
        }
    }
}
