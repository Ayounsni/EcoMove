package models.entities;

import models.enums.TicketStatus;
import models.enums.TransportType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Ticket {
    private final UUID id;
    private TransportType transportType;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private LocalDate saleDate;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private TicketStatus ticketStatus;
    private UUID contractId;
    private Trajet trajet;



    public Ticket(TransportType transportType, BigDecimal purchasePrice, BigDecimal salePrice, LocalDate saleDate, LocalDate departureDate, LocalTime departureTime, UUID contractId, Trajet trajet) {
        this.id = UUID.randomUUID();
        this.transportType = transportType;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.saleDate = saleDate;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.ticketStatus = TicketStatus.PENDING;
        this.contractId = contractId;
        this.trajet = trajet;
    }
    public Ticket(TransportType transportType, BigDecimal purchasePrice, BigDecimal salePrice, LocalDate saleDate, LocalDate departureDate, LocalTime departureTime, UUID contractId,TicketStatus ticketStatus, Trajet trajet, UUID id) {
        this.id = id;
        this.transportType = transportType;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.saleDate = saleDate;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.ticketStatus = ticketStatus;
        this.contractId = contractId;
        this.trajet = trajet;
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

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
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


    public Trajet getTrajet() {
        return trajet;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }
}
