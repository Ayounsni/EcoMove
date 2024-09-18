package dao.interfaces;

import models.entities.Client;

import java.util.UUID;

public interface IClientDao {
    String addClient(Client client);
    Client findClientByEmail(String email);
    boolean updateClient(Client client);
    Client getById(UUID id);

}

