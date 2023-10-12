package app.crm.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "client_id")
    private Long id;

    @Column(name = "client_name")
    private String name;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "address_id_fk")
    private Address address;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phones;


    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name,Address address,List<Phone>phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        List<Phone>allPhones = new ArrayList<>();
        phones.forEach(e -> allPhones.add(new Phone(e.getId(),e.getNumber(),this)));
        this.phones = allPhones;
    }


    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Client clone() {
        Client clone = null;
        try {
            clone = (Client) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        clone.setAddress(this.address);
        clone.setPhones(new ArrayList<>(phones));
        return clone;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
