package app.crm.service;

import app.crm.model.Client;

import java.util.List;
import java.util.Optional;

public interface DBServiceClient {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    List<Client> findAll();
}