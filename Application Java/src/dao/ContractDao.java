package dao;

import db.DbFunctions;
import models.entities.Contract;
import models.enums.ContractStatus;


import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.UUID;

public class ContractDao {
    private final DbFunctions db;

    public ContractDao() {
        this.db = DbFunctions.getInstance();
    }


    public void addContract( Contract contract) {

        String query = "INSERT INTO contract (id, startDate, endDate, specialRate, agreementConditions, renewable, contractStatus, partnerId) VALUES (?, ?, ?, ?, ?, ?, ?::contractstatus, ?)";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, contract.getId());
            stmt.setDate(2, new java.sql.Date(contract.getStartDate().getTime()));
            stmt.setDate(3, new java.sql.Date(contract.getEndDate().getTime()));
            stmt.setBigDecimal(4, contract.getSpecialRate());
            stmt.setString(5, contract.getAgreementConditions());
            stmt.setBoolean(6, contract.isRenewable());
            stmt.setObject(7, contract.getContractStatus(), java.sql.Types.OTHER);
            stmt.setObject(8, contract.getPartnerId());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new contract was inserted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while inserting the contract: " + e.getMessage());
        }
    }
    public void updateContract(UUID contractId, Date startDate, Date endDate, BigDecimal specialRate, String agreementConditions, boolean renewable, ContractStatus contractStatus) {
        String query = "UPDATE contract SET startDate = ?, endDate = ?, specialRate = ?, agreementConditions = ?, renewable = ?, contractStatus = ?::contractstatus WHERE id = ?";
        try {
            int rowsUpdated = getRowsUpdated(contractId, startDate, endDate, specialRate, agreementConditions, renewable, contractStatus, query);
            if (rowsUpdated > 0) {
                System.out.println("Contract updated successfully!");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while updating the contract: " + e.getMessage());
        }
    }
    public void deleteContract(UUID contractId) {
        String query = "DELETE FROM contract WHERE id = ?";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, contractId);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("The contract was deleted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while deleting the contract: " + e.getMessage());
        }
    }
    public void displayContracts() {
        String query = "SELECT * FROM contract";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                Date startDate = rs.getDate("startDate");
                Date endDate = rs.getDate("endDate");
                BigDecimal specialRate = rs.getBigDecimal("specialRate");
                String agreementConditions = rs.getString("agreementConditions");
                boolean renewable = rs.getBoolean("renewable");
                String contractStatus = rs.getString("contractStatus");
                UUID partnerId = (UUID) rs.getObject("partnerId");

                System.out.println("Contract ID: " + id);
                System.out.println("Start Date: " + startDate);
                System.out.println("End Date: " + endDate);
                System.out.println("Special Rate: " + specialRate);
                System.out.println("Agreement Conditions: " + agreementConditions);
                System.out.println("Renewable: " + renewable);
                System.out.println("Contract Status: " + contractStatus);
                System.out.println("Partner ID: " + partnerId);
                System.out.println("--------------------------");
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while retrieving contracts: " + e.getMessage());
        }
    }
    private int getRowsUpdated(UUID contractId, Date startDate, Date endDate, BigDecimal specialRate, String agreementConditions, boolean renewable, ContractStatus contractStatus, String query) throws SQLException {
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(startDate.getTime()));
            stmt.setDate(2, new java.sql.Date(endDate.getTime()));
            stmt.setBigDecimal(3, specialRate);
            stmt.setString(4, agreementConditions);
            stmt.setBoolean(5, renewable);
            stmt.setObject(6, contractStatus.name(), java.sql.Types.OTHER);
            stmt.setObject(7, contractId);
            return stmt.executeUpdate();
        }
    }
    public Contract getContractById(UUID contractId) {
        String query = "SELECT * FROM contract WHERE id = ?";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, contractId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Date startDate = rs.getDate("startDate");
                    Date endDate = rs.getDate("endDate");
                    BigDecimal specialRate = rs.getBigDecimal("specialRate");
                    String agreementConditions = rs.getString("agreementConditions");
                    boolean renewable = rs.getBoolean("renewable");
                    ContractStatus contractStatus = ContractStatus.valueOf(rs.getString("contractStatus").toUpperCase());
                    UUID partnerId = UUID.fromString(rs.getString("partnerId"));

                    return new Contract(startDate, endDate, specialRate, agreementConditions, renewable, contractStatus, partnerId);
                }
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while retrieving the contract: " + e.getMessage());
        }
        return null;
    }





}
