package models.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;
import models.enums.ReservationStatus;

public class Reservation {

    private UUID id;
    private Timestamp dateReservation;
    private BigDecimal price;
    private ReservationStatus reservationStatus;
    private Client client;

    public Reservation() {
    }


    public Reservation(BigDecimal price, ReservationStatus reservationStatus, Client client) {
        this.id = UUID.randomUUID();
        this.dateReservation = new Timestamp(System.currentTimeMillis());
        this.price = price;
        this.reservationStatus = reservationStatus;
        this.client = client;
    }

    // Getters et Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Timestamp getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Timestamp dateReservation) {
        this.dateReservation = dateReservation;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
}

