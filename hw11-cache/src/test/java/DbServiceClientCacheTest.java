import app.crm.model.Address;
import app.crm.model.Client;
import app.crm.model.Phone;

import base.AbstractHibernateTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class DbServiceClientCacheTest extends AbstractHibernateTest {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceClientCacheTest.class);

    @Test
    @DisplayName("при использовании кэша чтение должно происходить быстрее")
    void shouldBeFasterWithCache() {
        //given
        List<Client> clients = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            clients.add(new Client(null, "Vasya" + i, new Address(null, "AnyStreet" + i),
                    List.of(new Phone(null, "13-555-22" + i), new Phone(null, "14-666-333" + i))));
        }
        for (Client client:clients) {
            dbServiceClient.saveClient(client);
        }

        //when
        //with cache
        var start = System.currentTimeMillis();
        for (Client client:clients) {
            dbServiceClient.getClient(client.getId());
        }
        var end = System.currentTimeMillis();
        var timeWithCache = end - start;
        logger.info("Time Exit Cache:{}",timeWithCache);

        //without cached
        start = System.currentTimeMillis();
            dbServiceClient.findAll();
        end = System.currentTimeMillis();
        var timeWithOutCache = end - start;
        logger.info("Time Exit DB:{}",timeWithOutCache);

        //then
        assertThat(timeWithOutCache).isGreaterThan(timeWithCache);
    }

}
