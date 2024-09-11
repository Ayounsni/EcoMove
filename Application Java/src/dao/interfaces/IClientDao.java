package dao.interfaces;

import models.entities.Client;

public interface IClientDao {
    String addClient(Client client);
    Client findClientByEmail(String email);
    String deleteClient(String email); // If needed for deletion
}

