package app.crm.repository;

import app.crm.model.Client;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;


public interface ClientRepository extends ListCrudRepository<Client, Long> {

    List<Client> findAll();
}
