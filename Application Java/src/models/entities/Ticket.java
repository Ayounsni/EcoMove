package models.entities;

import models.enums.TicketStatus;
import models.enums.TransportType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Ticket {
    private final UUID id;
    private TransportType transportType;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private Date saleDate;
    private TicketStatus ticketStatus;
    private UUID contractId;

    public Ticket(TransportType transportType, BigDecimal purchasePrice, BigDecimal salePrice, Date saleDate, TicketStatus ticketStatus, UUID contractId) {
        this.id = UUID.randomUUID();
        this.transportType = transportType;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.saleDate = saleDate;
        this.ticketStatus = ticketStatus;
        this.contractId = contractId;
    }

    public UUID getId() {
        return id;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public UUID getContractId() {
        return contractId;
    }

    public void setContractId(UUID contractId) {
        this.contractId = contractId;
    }
}
