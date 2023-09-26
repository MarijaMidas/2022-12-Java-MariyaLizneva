package app.crm.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "phone")
@Entity
public class Phone {
    @Id
    @SequenceGenerator(name = "phone_gen", sequenceName = "phone_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_gen")
    @Column(name = "phone_id")
    private Long id;

    @Column(name = "phone_number")
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public Phone(Long id,String number){
        this.id = id;
        this.number = number;
    }

    public Phone(Long id,String number,Client client){
        this.id = id;
        this.number = number;
        this.client = client;
    }
}
