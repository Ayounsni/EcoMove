package dao.interfaces;

import models.entities.Client;

public interface IClientDao {
    String addClient(Client client);
    Client findClientByEmail(String email);
    boolean updateClient(Client client);

}

