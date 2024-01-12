package app.crm.model;

import jakarta.annotation.Nonnull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table(name = "clients")
public class Client {

    @Id
    private final Long id;

    @Nonnull
    private final String name;

    @MappedCollection(idColumn = "clients_id")
    private final Address address;

    @MappedCollection(idColumn = "clients_id")
    private final List<Phone> phones;


    public Client() {
        this.id = null;
        this.name = null;
        this.address = null;
        this.phones = null;
    }

    public Client(String name, Address address, List<Phone> phones) {
        this(null, name, address, phones);
    }

    @PersistenceCreator
    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }
}
