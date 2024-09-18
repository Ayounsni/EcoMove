package services.implementations;

import dao.implementations.ReservationDao;
import dao.interfaces.IReservationDao;
import models.entities.Reservation;
import services.interfaces.IReservationService;

import java.util.UUID;

public class ReservationService implements IReservationService {

    private final IReservationDao reservationDao;

    public ReservationService() {
        this.reservationDao = new ReservationDao();
    }

    @Override
    public void reserveTicket(Reservation reservation) {
        reservationDao.add(reservation);
    }

    @Override
    public Reservation getReservationById(UUID reservationId) {
        return reservationDao.getById(reservationId);
    }
}
