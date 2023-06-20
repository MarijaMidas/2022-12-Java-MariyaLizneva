import java.util.Map;

public interface CashMachine {

    int getBalance();

    void putMoney(Map<Nominal,Integer>moneys);

    Map<Integer,Integer> takeMoneyOut(int money);
}
