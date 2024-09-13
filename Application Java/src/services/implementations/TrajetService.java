package services.implementations;

import dao.interfaces.ITrajetDao;
import models.entities.Trajet;
import services.interfaces.ITrajetService;

import java.util.List;
import java.util.UUID;

public class TrajetService implements ITrajetService {

    private final ITrajetDao trajetDao;

    public TrajetService(ITrajetDao trajetDao) {
        this.trajetDao = trajetDao;
    }

    @Override
    public Trajet create(Trajet trajet) {
        return trajetDao.create(trajet);
    }

    @Override
    public Trajet findById(UUID id) {
        return trajetDao.findById(id);
    }

    @Override
    public List<Trajet> findAll() {
        return trajetDao.findAll();
    }

    @Override
    public boolean update(Trajet trajet) {
        return trajetDao.update(trajet);
    }

    @Override
    public boolean delete(UUID id) {
        return trajetDao.delete(id);
    }
}
