package dao.implementations;

import db.DbFunctions;
import models.entities.Partner;
import models.enums.PartnerStatus;
import models.enums.TransportType;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class PartnershipDao {
    private final DbFunctions db;

    public PartnershipDao() {
        this.db = DbFunctions.getInstance();
    }


    public void addPartner(Partner partner) {
        String query = "INSERT INTO partners (id, companyName, businessContact, transportType, geographicZone, specialConditions, partnerStatus, creationDate) VALUES (?, ?, ?, ?::transporttype, ?, ?, ?::partnerstatus, ?)";
        try {
            int rowsInserted = getRowsInserted(partner, query);
            if (rowsInserted > 0) {
                System.out.println("A new partner was inserted successfully!");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while inserting the partner: " + e.getMessage());
        }
    }


    public void modifyPartner(UUID partnerId, String companyName, String businessContact, TransportType transportType, String geographicZone, String specialConditions, PartnerStatus partnerStatus, Date creationDate) {
        String query = "UPDATE partners SET companyName = ?, businessContact = ?, transportType = ?::transporttype, geographicZone = ?, specialConditions = ?, partnerStatus = ?::partnerstatus, creationDate = ? WHERE id = ?";
        try {
            int rowsUpdated = getRowsUpdated(partnerId, companyName, businessContact, transportType, geographicZone, specialConditions, partnerStatus, creationDate, query);
            if (rowsUpdated > 0) {
                System.out.println("models.entities.Partner updated successfully!");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while updating the partner: " + e.getMessage());
        }
    }


    public void deletePartner(UUID partnerId) {
        String query = "DELETE FROM partners WHERE id = ?";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, partnerId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("models.entities.Partner deleted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while deleting the partner: " + e.getMessage());
        }
    }


    public List<Partner> findAll() {
        String query = "SELECT * FROM partners";
        List<Partner> partners = new ArrayList<>();
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Partner partner = new Partner(
                        (UUID) rs.getObject("id"),
                        rs.getString("companyName"),
                        rs.getString("businessContact"),
                        TransportType.valueOf(rs.getString("transportType")),
                        rs.getString("geographicZone"),
                        rs.getString("specialConditions"),
                        PartnerStatus.valueOf(rs.getString("partnerStatus")),
                        rs.getDate("creationDate")
                );
                partners.add(partner);
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while retrieving partners: " + e.getMessage());
        }
        return partners;
    }

    private int getRowsInserted(Partner partner, String query) throws SQLException {
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, partner.getId());
            stmt.setString(2, partner.getCompanyName());
            stmt.setString(3, partner.getBusinessContact());
            stmt.setObject(4, partner.getTransportType(), java.sql.Types.OTHER);
            stmt.setString(5, partner.getGeographicZone());
            stmt.setString(6, partner.getSpecialConditions());
            stmt.setObject(7, partner.getPartnerStatus(), java.sql.Types.OTHER);
            stmt.setDate(8, new java.sql.Date(partner.getCreationDate().getTime()));


            return stmt.executeUpdate();
        }
    }

    private int getRowsUpdated(UUID partnerId, String companyName, String businessContact, TransportType transportType, String geographicZone, String specialConditions, PartnerStatus partnerStatus, Date creationDate, String query) throws SQLException {
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, companyName);
            stmt.setString(2, businessContact);
            stmt.setObject(3, transportType, java.sql.Types.OTHER);
            stmt.setString(4, geographicZone);
            stmt.setString(5, specialConditions);
            stmt.setObject(6, partnerStatus, java.sql.Types.OTHER);
            stmt.setDate(7, new java.sql.Date(creationDate.getTime()));
            stmt.setObject(8, partnerId);
            return stmt.executeUpdate();
        }
    }

    public Partner getPartnerById(UUID partnerId) {
        String query = "SELECT * FROM partners WHERE id = ?";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, partnerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String companyName = rs.getString("companyName");
                    String businessContact = rs.getString("businessContact");
                    TransportType transportType = TransportType.valueOf(rs.getString("transportType"));
                    String geographicZone = rs.getString("geographicZone");
                    String specialConditions = rs.getString("specialConditions");
                    PartnerStatus partnerStatus = PartnerStatus.valueOf(rs.getString("partnerStatus"));
                    Date creationDate = rs.getDate("creationDate");

                    return new Partner(companyName, businessContact, transportType, geographicZone, specialConditions, partnerStatus, creationDate);
                }
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while retrieving the partner: " + e.getMessage());
        }
        return null;
    }
}