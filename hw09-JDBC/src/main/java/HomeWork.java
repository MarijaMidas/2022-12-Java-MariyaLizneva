import mapper.*;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.core.repository.executor.DbExecutorImpl;
import otus.core.sessionmanager.TransactionRunnerJdbc;
import otus.crm.datasource.DriverManagerDataSource;
import otus.crm.model.Client;
import otus.crm.model.Manager;
import otus.crm.service.DbServiceClientImpl;
import otus.crm.service.DbServiceManagerImpl;

import javax.sql.DataSource;

public class HomeWork {
    private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static final Logger log = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
// Подключение к базе данных
        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);
        var transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();

// Работа с клиентом
        EntityClassMetaData<Client> entityClassMetaDataClient = new EntityClassMetaDataImpl<>(){};
        EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl<>(entityClassMetaDataClient);
        var dataTemplateClient = new DataTemplateJdbc<Client>(dbExecutor, entitySQLMetaDataClient,entityClassMetaDataClient);

        var dbServiceClient = new DbServiceClientImpl(transactionRunner, dataTemplateClient);
        dbServiceClient.saveClient(new Client("dbServiceFirst"));

        var clientSecond = dbServiceClient.saveClient(new Client("dbServiceSecond"));
        var clientSecondSelected = dbServiceClient.getClient(clientSecond.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        log.info("clientSecondSelected:{}", clientSecondSelected);

        EntityClassMetaData<Manager> entityClassMetaDataManager = new EntityClassMetaDataImpl<>(){};
        EntitySQLMetaData entitySQLMetaDataManager = new EntitySQLMetaDataImpl<>(entityClassMetaDataManager);
        var dataTemplateManager = new DataTemplateJdbc<Manager>(dbExecutor, entitySQLMetaDataManager,entityClassMetaDataManager);

        var dbServiceManager = new DbServiceManagerImpl(transactionRunner, dataTemplateManager);
        dbServiceManager.saveManager(new Manager("ManagerFirst"));

        var managerSecond = dbServiceManager.saveManager(new Manager("ManagerSecond"));
        var managerSecondSelected = dbServiceManager.getManager(managerSecond.getNo())
                .orElseThrow(() -> new RuntimeException("Manager not found, id:" + managerSecond.getNo()));
        log.info("managerSecondSelected:{}", managerSecondSelected);
    }

    private static void flywayMigrations(DataSource dataSource) {
        log.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        log.info("db migration finished.");
        log.info("***");
    }
}
