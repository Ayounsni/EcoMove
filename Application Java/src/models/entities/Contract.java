package models.entities;

import models.enums.ContractStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Contract {
    private final UUID id;
    private Date startDate;
    private Date endDate;
    private BigDecimal specialRate;
    private String agreementConditions;
    private boolean renewable;
    private ContractStatus contractStatus;
    private UUID partnerId;
    private List<Promo> promos;
    private List<Ticket> tickets;

    public Contract(Date startDate, Date endDate, BigDecimal specialRate, String agreementConditions, boolean renewable, ContractStatus contractStatus, UUID partnerId) {
        this.id = UUID.randomUUID();
        this.startDate = startDate;
        this.endDate = endDate;
        this.specialRate = specialRate;
        this.agreementConditions = agreementConditions;
        this.renewable = renewable;
        this.contractStatus = contractStatus;
        this.partnerId = partnerId;
        this.promos = new ArrayList<>();
        this.tickets = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getSpecialRate() {
        return specialRate;
    }

    public void setSpecialRate(BigDecimal specialRate) {
        this.specialRate = specialRate;
    }

    public String getAgreementConditions() {
        return agreementConditions;
    }

    public void setAgreementConditions(String agreementConditions) {
        this.agreementConditions = agreementConditions;
    }

    public boolean isRenewable() {
        return renewable;
    }

    public void setRenewable(boolean renewable) {
        this.renewable = renewable;
    }

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

    public UUID getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(UUID partnerId) {
        this.partnerId = partnerId;
    }
    public List<Promo> getPromos() {
        return promos;
    }

    public void setPromos(List<Promo> promos) {
        this.promos = promos;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
