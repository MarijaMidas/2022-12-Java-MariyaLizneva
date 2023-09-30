package app.crm.service;

import app.cache.Cache;
import app.cache.MyCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import app.core.repository.DataTemplate;
import app.core.sessionmanager.TransactionManager;
import app.crm.model.Client;

import java.util.List;
import java.util.Optional;

public class DbServiceClientCacheImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientCacheImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final Cache<String,Client> cache = new MyCache<>();

    public DbServiceClientCacheImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            if (client.getId() == null) {
                var savedClient = clientDataTemplate.insert(session, client);
                log.info("created client: {}", client);
                cache.put(savedClient.getId().toString(),savedClient);
                return savedClient;
            }
            var savedClient = clientDataTemplate.update(session, client);
            log.info("updated client: {}", savedClient);
            cache.put(savedClient.getId().toString(),savedClient);
            return savedClient;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        if(cache.get(id+"")!=null){
            var clientOptional = Optional.of(cache.get(id+""));
            log.info("client ex cache: {}", clientOptional);
            return clientOptional;
        }
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientOptional = clientDataTemplate.findById(session, id);
            log.info("client ex DB: {}", clientOptional);
            return clientOptional;
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            return clientList;
        });
    }
}
