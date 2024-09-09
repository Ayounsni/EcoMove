package dao;

import db.DbFunctions;
import models.entities.Promo;
import models.enums.DiscountType;
import models.enums.OfferStatus;

import java.sql.*;
import java.util.Date;
import java.util.UUID;

public class PromoDao {
    private final DbFunctions db;

    public PromoDao() {
        this.db = DbFunctions.getInstance();
    }

    // Méthode pour ajouter une nouvelle promotion
    public void addPromo(Promo promo) {
        String query = "INSERT INTO promo (id, offerName, description, startDate, endDate, discountType, conditions, offerStatus, contractId) VALUES (?, ?, ?, ?, ?, ?::discounttype, ?, ?::offerstatus, ?)";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, promo.getId());
            stmt.setString(2, promo.getOfferName());
            stmt.setString(3, promo.getDescription());
            stmt.setDate(4, new java.sql.Date(promo.getStartDate().getTime()));
            stmt.setDate(5, new java.sql.Date(promo.getEndDate().getTime()));
            stmt.setObject(6, promo.getDiscountType().name(), java.sql.Types.OTHER);
            stmt.setString(7, promo.getConditions());
            stmt.setObject(8, promo.getOfferStatus().name(), java.sql.Types.OTHER);
            stmt.setObject(9, promo.getContractId());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new promo was inserted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while inserting the promo: " + e.getMessage());
        }
    }

    // Méthode pour mettre à jour une promotion existante
    public void updatePromo(UUID promoId, String offerName, String description, Date startDate, Date endDate, DiscountType discountType, String conditions, OfferStatus offerStatus) {
        String query = "UPDATE promo SET offerName = ?, description = ?, startDate = ?, endDate = ?, discountType = ?::discounttype, conditions = ?, offerStatus = ?::offerstatus WHERE id = ?";
        try {
            int rowsUpdated = getRowsUpdated(promoId, offerName, description, startDate, endDate, discountType, conditions, offerStatus, query);
            if (rowsUpdated > 0) {
                System.out.println("Promo updated successfully!");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while updating the promo: " + e.getMessage());
        }
    }

    // Méthode pour supprimer une promotion
    public void deletePromo(UUID promoId) {
        String query = "DELETE FROM promo WHERE id = ?";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, promoId);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("The promo was deleted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while deleting the promo: " + e.getMessage());
        }
    }

    // Méthode pour afficher toutes les promotions
    public void displayPromos() {
        String query = "SELECT * FROM promo";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                String offerName = rs.getString("offerName");
                String description = rs.getString("description");
                Date startDate = rs.getDate("startDate");
                Date endDate = rs.getDate("endDate");
                String discountType = rs.getString("discountType");
                String conditions = rs.getString("conditions");
                String offerStatus = rs.getString("offerStatus");
                UUID contractId = (UUID) rs.getObject("contractId");

                System.out.println("Promo ID: " + id);
                System.out.println("Offer Name: " + offerName);
                System.out.println("Description: " + description);
                System.out.println("Start Date: " + startDate);
                System.out.println("End Date: " + endDate);
                System.out.println("Discount Type: " + discountType);
                System.out.println("Conditions: " + conditions);
                System.out.println("Offer Status: " + offerStatus);
                System.out.println("Contract ID: " + contractId);
                System.out.println("--------------------------");
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while retrieving promos: " + e.getMessage());
        }
    }

    // Méthode privée pour gérer la mise à jour
    private int getRowsUpdated(UUID promoId, String offerName, String description, Date startDate, Date endDate, DiscountType discountType, String conditions, OfferStatus offerStatus, String query) throws SQLException {
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, offerName);
            stmt.setString(2, description);
            stmt.setDate(3, new java.sql.Date(startDate.getTime()));
            stmt.setDate(4, new java.sql.Date(endDate.getTime()));
            stmt.setObject(5, discountType.name(), java.sql.Types.OTHER);
            stmt.setString(6, conditions);
            stmt.setObject(7, offerStatus.name(), java.sql.Types.OTHER);
            stmt.setObject(8, promoId);
            return stmt.executeUpdate();
        }
    }
}

