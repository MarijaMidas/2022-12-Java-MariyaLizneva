package app.servlet;

import app.crm.dbmigrations.core.repository.DataTemplate;
import app.crm.dbmigrations.core.repository.DataTemplateHibernate;
import app.crm.dbmigrations.core.sessionmanager.TransactionManagerHibernate;
import app.crm.model.Address;
import app.crm.model.Client;
import app.crm.model.Phone;
import app.crm.service.DbServiceClientCacheImpl;
import app.services.TemplateProcessor;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientServlet extends HttpServlet {

    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";
    private static final String TEMPLATE_ATTR_CLIENT = "tableClient";

    private final TemplateProcessor templateProcessor;
    private final DataTemplate clientTemplate;
    private final DbServiceClientCacheImpl dbServiceClient;

    public ClientServlet(TemplateProcessor templateProcessor, TransactionManagerHibernate transactionManager) {
        this.templateProcessor = templateProcessor;
        this.clientTemplate = new DataTemplateHibernate<>(Client.class);
        dbServiceClient = new DbServiceClientCacheImpl(transactionManager, clientTemplate);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        List<Client> clients = dbServiceClient.findAll();
        paramsMap.put(TEMPLATE_ATTR_CLIENT, clients);
        resp.setContentType("text/html");
        resp.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("clientName");
        String clientAddress = req.getParameter("clientAddress");
        Address address = new Address(null,clientAddress);
        String clientPhone = req.getParameter("clientPhone");
        List<Phone> phone = new ArrayList<>();
        phone.add(new Phone(null, clientPhone));
        Client client = new Client(null, name, address,phone );
        dbServiceClient.saveClient(client);
        resp.sendRedirect(req.getContextPath() + "/clients");
    }
}
