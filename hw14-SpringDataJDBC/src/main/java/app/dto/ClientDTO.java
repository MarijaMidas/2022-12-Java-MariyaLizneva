package app.dto;

import app.crm.model.Client;
import app.crm.model.Phone;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Setter
@Getter
public class ClientDTO {

    private Long id;
    private String name;
    private String address;
    private Set<String> phones = new HashSet<>();
    public ClientDTO(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.address = client.getAddress().getStreet();
        this.phones = client.getPhones().stream().map(Phone::getNumber).collect(Collectors.toSet());
    }
}
