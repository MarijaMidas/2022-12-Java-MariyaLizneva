package app.crm.model;

import jakarta.annotation.Nonnull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;


@Table(name = "phone")
public class Phone {
    @Id
    private final Long id;

    @Nonnull
    private final String number;

    public Phone(String number) {
        this(null, number);
    }

    @PersistenceCreator
    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }


    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "clientId=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}
