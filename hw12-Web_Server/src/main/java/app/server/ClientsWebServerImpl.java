package app.server;

import app.crm.dbmigrations.core.repository.DataTemplateHibernate;
import app.crm.dbmigrations.core.sessionmanager.TransactionManagerHibernate;
import app.crm.model.Address;
import app.crm.model.Client;
import app.crm.model.Phone;
import app.crm.service.DbServiceClientCacheImpl;
import app.helpers.FileSystemHelper;
import app.services.AuthService;
import app.services.TemplateProcessor;
import app.servlet.AuthorizationFilter;
import app.servlet.ClientServlet;
import app.servlet.LoginServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ClientsWebServerImpl implements ClientsWebServer{
    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";

    protected final TemplateProcessor templateProcessor;
    private final Server server;
    private final AuthService authService;
    private final TransactionManagerHibernate transactionManager;

    public ClientsWebServerImpl(int port,  AuthService authService, TemplateProcessor templateProcessor,TransactionManagerHibernate transactionManager) {
        this.templateProcessor = templateProcessor;
        this.authService = authService;
        this.transactionManager = transactionManager;
        this.addClientsInBD();
        server = new Server(port);
    }

    @Override
    public void start() throws Exception {
        if (server.getHandlers().length == 0) {
            initContext();
        }
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

    private Server initContext() {

        ResourceHandler resourceHandler = createResourceHandler();
        ServletContextHandler servletContextHandler = createServletContextHandler();

        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(applySecurity(servletContextHandler, "/clients"));


        server.setHandler(handlers);
        return server;
    }

    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new ClientServlet(templateProcessor, transactionManager)), "/clients");
        return servletContextHandler;
    }

    private void addClientsInBD(){
        var clientTemplate = new DataTemplateHibernate<>(Client.class);;
        DbServiceClientCacheImpl dbServiceClient = new DbServiceClientCacheImpl(transactionManager, clientTemplate);
        List<Client> clients = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            clients.add(new Client(null, "Vasya" + i, new Address(null, "AnyStreet" + i),
                    List.of(new Phone(null, "13-555-22" + i))));
        }

        for (Client client:clients) {
            dbServiceClient.saveClient(client);
        }
    }
}
