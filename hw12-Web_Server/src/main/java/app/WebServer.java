package app;

import app.crm.HibernateRunner;
import app.crm.dbmigrations.core.sessionmanager.TransactionManagerHibernate;
import app.crm.model.Address;
import app.crm.model.Client;
import app.crm.model.Phone;
import app.dao.AdminDao;
import app.dao.InMemoryAdminDao;
import app.server.ClientsWebServerImpl;
import app.services.AuthService;
import app.services.AuthServiceImpl;
import app.services.TemplateProcessor;
import app.services.TemplateProcessorImpl;

// Стартовая страница
//    http://localhost:8080

public class WebServer {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        AdminDao adminDao = new InMemoryAdminDao();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        AuthService authService = new AuthServiceImpl(adminDao);
        var transactionManager = new TransactionManagerHibernate(HibernateRunner.getSession(Client.class, Address.class, Phone.class));

        ClientsWebServerImpl usersWebServer = new ClientsWebServerImpl(WEB_SERVER_PORT,
                authService, templateProcessor,transactionManager);

        usersWebServer.start();
        usersWebServer.join();
    }
}
