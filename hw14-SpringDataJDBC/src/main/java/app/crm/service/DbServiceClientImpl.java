package app.crm.service;

import app.crm.model.Client;
import app.crm.repository.ClientRepository;
import app.sessionmanager.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final TransactionManager transactionManager;
    private final ClientRepository clientRepository;

    public DbServiceClientImpl(TransactionManager transactionManager, ClientRepository clientRepository) {
        this.transactionManager = transactionManager;
        this.clientRepository = clientRepository;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(() -> {
            var savedClient = clientRepository.save(client);
            log.info("saved client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInTransaction(() -> {
            var clientList = new ArrayList<Client>();
            clientRepository.findAll().forEach(clientList::add);
            log.info("clientList:{}", clientList);
            return clientList;
        });
    }
}
