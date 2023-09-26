package app.crm.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Table(name = "address")
@NoArgsConstructor
@Entity

public class Address {
    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "address_id")
    private Long id;

    @Column(name = "address_street")
    private String street;

    public Address(Long id,String street){
        this.id = id;
        this.street = street;
    }
}
