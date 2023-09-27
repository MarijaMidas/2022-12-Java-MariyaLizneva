import app.cache.Cache;
import app.cache.MyCache;
import app.crm.model.*;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

class HomeworkTest {

    private StandardServiceRegistry serviceRegistry;
    private Metadata metadata;
    private SessionFactory sessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(HomeworkTest.class);
    private final List<Client> clients = new ArrayList<>();
    private final Cache<String, Client> clientsCache = new MyCache<>();


    @BeforeEach
    public void setUp() {
        makeTestDependencies();
    }

    @AfterEach
    public void tearDown() {
        sessionFactory.close();
    }


    @Test
    public void testHomeworkForCache() {
        clients.add(new Client(null, "Vasya", new Address(null, "AnyStreet"),
            List.of(new Phone(null, "13-555-22"), new Phone(null, "14-666-333"))));
        clients.add(new Client(null, "Misha", new Address(null, "AnyStreet"),
                List.of(new Phone(null, "14-555-22"), new Phone(null, "15-666-333"))));
        clients.add(new Client(null, "Igor", new Address(null, "AnyStreet"),
                List.of(new Phone(null, "15-555-22"), new Phone(null, "16-666-333"))));
        clients.add(new Client(null, "Lev", new Address(null, "AnyStreet"),
                List.of(new Phone(null, "16-555-22"), new Phone(null, "17-666-333"))));
        clients.add(new Client(null, "Lena", new Address(null, "AnyStreet"),
                List.of(new Phone(null, "7-555-22"), new Phone(null, "18-666-333"))));

        try (var session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            for (Client client:clients) {
                session.persist(client);
            }
            session.getTransaction().commit();
            session.clear();
            var timeStartDB = System.currentTimeMillis();
            for (long i = 1; i <= clients.size(); i++) {
                var client = session.find(Client.class, i);
                clientsCache.put(client.getId().toString(),client);
                logger.info("Select client:{}, in time{}",client,System.currentTimeMillis()-timeStartDB);
            }
            var timeExitDB = System.currentTimeMillis()- timeStartDB;
            logger.info("Time Exit DB:{}",timeExitDB);

            System.out.println("-----------------------------------------------------------------------");

            var timeStartCache = System.currentTimeMillis();
            for (long i = 1; i <= clients.size(); i++) {
                var client = clientsCache.get(i+"");
                logger.info("Select client ex cache:{}, in time{}",client.toString(),System.currentTimeMillis()-timeStartCache);
            }
            var timeExitCache = System.currentTimeMillis()- timeStartCache;
            logger.info("Time Exit Cache:{}",timeExitCache);

            System.gc();

            logger.info("Cash in 1 position: {}",clientsCache.get("1"));
            logger.info("Cash in 2 position: {}",clientsCache.get("2"));
            logger.info("Cash in 3 position: {}",clientsCache.get("3"));
            logger.info("Cash in 4 position: {}",clientsCache.get("4"));
            logger.info("Cash in 5 position: {}",clientsCache.get("5"));
        }
    }

    private void makeTestDependencies() {
        var cfg = new Configuration();

        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        cfg.setProperty("hibernate.connection.driver_class", "org.h2.Driver");

        cfg.setProperty("hibernate.connection.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

        cfg.setProperty("hibernate.connection.username", "sa");
        cfg.setProperty("hibernate.connection.password", "");

        cfg.setProperty("hibernate.show_sql", "true");
        cfg.setProperty("hibernate.format_sql", "false");
        cfg.setProperty("hibernate.generate_statistics", "true");

        cfg.setProperty("hibernate.hbm2ddl.auto", "create");
        cfg.setProperty("hibernate.enable_lazy_load_no_trans", "false");

        serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties()).build();


        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(Phone.class);
        metadataSources.addAnnotatedClass(Address.class);
        metadataSources.addAnnotatedClass(Client.class);
        metadata = metadataSources.getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }
}