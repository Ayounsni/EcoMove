package services.implementations;

import dao.interfaces.IClientDao;
import models.entities.Client;
import services.interfaces.IClientService;

public class ClientService implements IClientService {
    private final IClientDao clientDao;

    public ClientService(IClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public String registerClient(Client client) {
        return clientDao.addClient(client);
    }

    @Override
    public Client getClientByEmail(String email) {
        return clientDao.findClientByEmail(email);
    }

    @Override
    public boolean updateClient(Client client) {
        Client existingClient = clientDao.findClientByEmail(client.getEmail());

        if (existingClient == null) {
            return false;
        }

        existingClient.setFirstname(client.getFirstname());
        existingClient.setLastname(client.getLastname());
        existingClient.setPhone(client.getPhone());

        return clientDao.updateClient(existingClient);
    }

}
