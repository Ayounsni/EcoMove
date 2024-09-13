package dao.interfaces;

import models.entities.Trajet;

import java.util.List;
import java.util.UUID;

public interface ITrajetDao {
    Trajet create(Trajet trajet);
    Trajet findById(UUID id);
    List<Trajet> findAll();
    boolean update(Trajet trajet);
    boolean delete(UUID id);
}

