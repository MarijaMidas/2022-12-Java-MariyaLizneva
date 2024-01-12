package app.crm.service;

import app.crm.model.Client;
import java.util.List;

public interface DBServiceClient {

    Client saveClient(Client client);

    List<Client> findAll();
}
