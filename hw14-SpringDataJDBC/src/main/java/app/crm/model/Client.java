package app.crm.model;

import app.dto.ClientDTO;
import jakarta.annotation.Nonnull;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Table(name = "client")
public class Client {

    @Id
    private final Long id;

    @Nonnull
    private final String name;

    @MappedCollection(idColumn = "client_key")
    private final Address address;

    @MappedCollection(idColumn = "client_key")
    private final Set<Phone> phones;


    public Client(ClientDTO dto) {
        this(dto.getId(),
                dto.getName(),
                new Address(dto.getAddress()),
                dto.getPhones().stream().map(Phone::new).collect(Collectors.toSet()));
    }

    public Client(String name, Address address, Set<Phone> phones) {
        this(null, name, address, phones);
    }

    @PersistenceCreator
    public Client(Long id, String name, Address address, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
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
