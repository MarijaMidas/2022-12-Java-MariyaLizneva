package app.web.controllers;

import app.crm.model.Client;
import app.crm.service.DBServiceClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class ClientController {

    private final DBServiceClient dbServiceClient;

    public ClientController(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @GetMapping({"/clients"})
    public String clientsListView(Model model) {
        if (model.getAttribute("client") == null) {
            model.addAttribute("client", new Client());
        }
        List<Client> clients = dbServiceClient.findAll();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @PostMapping("/clients")
    public RedirectView saveClient(Client client) {
        dbServiceClient.saveClient(client);
        return new RedirectView("/clients", true);
    }

}
