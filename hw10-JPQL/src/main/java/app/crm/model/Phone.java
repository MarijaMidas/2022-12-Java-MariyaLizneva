package app.crm.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "phone")
@Entity
public class Phone {
    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "phone_id")
    private Long id;

    @Column(name = "phone_number")
    private String number;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

//    @ManyToOne
//    @JoinTable(name = "clients", joinColumns= @JoinColumn(name = "client_id"))
//    private Client client;

    public Phone(Long id,String number){
        this.id = id;
        this.number = number;

    }


}
