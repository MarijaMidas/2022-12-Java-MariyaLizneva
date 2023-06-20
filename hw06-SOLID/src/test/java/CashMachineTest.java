import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CashMachineTest {
    private CashMachine atx;
    private int balance;

    @BeforeEach
    void before() {
        atx = new MyCashMachine();
        Map<Nominal, Integer> startMoney = new HashMap<>();
        startMoney.put(Nominal.FIFTY, 40);
        startMoney.put(Nominal.FIVE_HUNDRED, 3);
        startMoney.put(Nominal.ONE_THOUSAND, 4);
        startMoney.put(Nominal.FIVE_THOUSAND, 3);
        startMoney.put(Nominal.TWO_HUNDRED, 3);
        atx.putMoney(startMoney);
        balance = atx.getBalance();
    }

    @DisplayName("Добавление денег")
    @Test
    void putMoney(){
        Map<Nominal, Integer> addMoney = new HashMap<>();
        addMoney.put(Nominal.FIFTY, 4);
        addMoney.put(Nominal.FIVE_HUNDRED, 3);

        int expected = 0;
        for(Map.Entry<Nominal,Integer> m: addMoney.entrySet()){
            expected += m.getKey().getNominalValue() * m.getValue();
        }

        atx.putMoney(addMoney);

        assertEquals(balance + expected, atx.getBalance());
    }

    @DisplayName("Запрос остатка в банкомате")
    @Test
    void balance(){
        assertEquals(balance,atx.getBalance());
    }

    @DisplayName("Выдача суммы достаточного номинала")
    @Test
    void correctSum(){
        String expected = "2 по 50 рублей." +
                "1 по 1000 рублей.";
        StringBuilder actual = new StringBuilder();

        for(Map.Entry<Integer,Integer> m: atx.takeMoneyOut(1100).entrySet()){
            if(m.getValue()!=0) {
                String out = m.getValue() + " по " + m.getKey() + " рублей.";
                actual.append(out);
            }
        }

        assertEquals(expected, actual.toString());


    }

    @DisplayName("Выдача суммы недостаточного номинала")
    @Test
    void sumWithUncorrectedNominal(){
        int money = 30000;

        assertThrows(TakeMoneyOutException.class,()->atx.takeMoneyOut(money));
    }

    @DisplayName("Выдача некорректной суммы")
    @Test
    void uncorrectedSum() {
        int money = 1;

        assertThrows(TakeMoneyOutException.class,()->atx.takeMoneyOut(money));
    }
}
