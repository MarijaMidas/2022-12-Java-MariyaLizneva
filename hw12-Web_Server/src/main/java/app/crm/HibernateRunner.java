package app.crm;

import app.crm.dbmigrations.MigrationsExecutorFlyway;
import app.crm.dbmigrations.core.repository.HibernateUtils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateRunner{

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static SessionFactory getSession(Class... classes){
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();
        return HibernateUtils.buildSessionFactory(configuration,classes);
    }

}
