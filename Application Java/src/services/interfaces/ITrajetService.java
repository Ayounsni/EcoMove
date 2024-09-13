package services.interfaces;

import models.entities.Trajet;

import java.util.List;
import java.util.UUID;

public interface ITrajetService {

    Trajet create(Trajet trajet);

    Trajet findById(UUID id);

    List<Trajet> findAll();

    boolean update(Trajet trajet);

    boolean delete(UUID id);
}
