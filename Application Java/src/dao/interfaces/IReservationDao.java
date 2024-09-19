package dao.interfaces;

import models.entities.Reservation;
import models.enums.ReservationStatus;

import java.util.UUID;

public interface IReservationDao {
    void add(Reservation reservation);
    Reservation getById(UUID reservationId);
    boolean updateReservationStatus(UUID reservationId, ReservationStatus reservationStatus);
}
