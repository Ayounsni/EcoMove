package services.interfaces;

import models.entities.Client;

public interface IClientService {
    String registerClient(Client client);
    Client getClientByEmail(String email);
    boolean updateClient(Client client);
}

