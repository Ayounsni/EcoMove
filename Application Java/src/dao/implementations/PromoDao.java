package dao.implementations;

import db.DbFunctions;
import models.entities.Promo;
import models.enums.ContractStatus;
import models.enums.DiscountType;
import models.enums.OfferStatus;

import java.sql.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class PromoDao {
    private final DbFunctions db;

    public PromoDao() {
        this.db = DbFunctions.getInstance();
    }

    public void addPromo(Promo promo) {
        String query = "INSERT INTO promos (id, offerName, description, startDate, endDate, discountType, discountValue, conditions, offerStatus, contractId) VALUES (?, ?, ?, ?, ?, ?::discounttype, ?, ?, ?::offerstatus, ?)";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, promo.getId());
            stmt.setString(2, promo.getOfferName());
            stmt.setString(3, promo.getDescription());
            stmt.setDate(4, new java.sql.Date(promo.getStartDate().getTime()));
            stmt.setDate(5, new java.sql.Date(promo.getEndDate().getTime()));
            stmt.setObject(6, promo.getDiscountType().name(), java.sql.Types.OTHER);
            stmt.setBigDecimal(7, promo.getDiscountValue());
            stmt.setString(8, promo.getConditions());
            stmt.setObject(9, promo.getOfferStatus().name(), java.sql.Types.OTHER);
            stmt.setObject(10, promo.getContractId());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new promo was inserted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while inserting the promo: " + e.getMessage());
        }
    }


    public void updatePromo(UUID promoId, String offerName, String description, Date startDate, Date endDate, DiscountType discountType, BigDecimal discountValue, String conditions, OfferStatus offerStatus) {
        String query = "UPDATE promos SET offerName = ?, description = ?, startDate = ?, endDate = ?, discountType = ?::discounttype, discountValue = ?, conditions = ?, offerStatus = ?::offerstatus WHERE id = ?";
        try {
            int rowsUpdated = getRowsUpdated(promoId, offerName, description, startDate, endDate, discountType, discountValue, conditions, offerStatus, query);
            if (rowsUpdated > 0) {
                System.out.println("Promo updated successfully!");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while updating the promo: " + e.getMessage());
        }
    }

    public void deletePromo(UUID promoId) {
        String query = "DELETE FROM promos WHERE id = ?";
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

    public void displayPromos() {
        String query = "SELECT * FROM promos";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                String offerName = rs.getString("offerName");
                String description = rs.getString("description");
                Date startDate = rs.getDate("startDate");
                Date endDate = rs.getDate("endDate");
                DiscountType discountType = DiscountType.valueOf(rs.getString("discountType").toUpperCase());

                BigDecimal discountValue = rs.getBigDecimal("discountValue");
                String conditions = rs.getString("conditions");
                OfferStatus offerStatus = OfferStatus.valueOf(rs.getString("offerStatus").toUpperCase());
                UUID contractId = (UUID) rs.getObject("contractId");

                System.out.println("Promo ID: " + id);
                System.out.println("Offer Name: " + offerName);
                System.out.println("Description: " + description);
                System.out.println("Start Date: " + startDate);
                System.out.println("End Date: " + endDate);
                System.out.println("Discount Type: " + discountType);
                System.out.println("Discount Value: " + discountValue);
                System.out.println("Conditions: " + conditions);
                System.out.println("Offer Status: " + offerStatus);
                System.out.println("Contract ID: " + contractId);
                System.out.println("--------------------------");
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while retrieving promos: " + e.getMessage());
        }
    }

    private int getRowsUpdated(UUID promoId, String offerName, String description, Date startDate, Date endDate, DiscountType discountType, BigDecimal discountValue, String conditions, OfferStatus offerStatus, String query) throws SQLException {
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, offerName);
            stmt.setString(2, description);
            stmt.setDate(3, new java.sql.Date(startDate.getTime()));
            stmt.setDate(4, new java.sql.Date(endDate.getTime()));
            stmt.setObject(5, discountType.name(), java.sql.Types.OTHER);
            stmt.setBigDecimal(6, discountValue);
            stmt.setString(7, conditions);
            stmt.setObject(8, offerStatus.name(), java.sql.Types.OTHER);
            stmt.setObject(9, promoId);
            return stmt.executeUpdate();
        }
    }
}
