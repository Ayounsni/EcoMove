package dao.interfaces;

import models.entities.Reservation;

import java.util.UUID;

public interface IReservationDao {
    void add(Reservation reservation);
    Reservation getById(UUID reservationId);
}
