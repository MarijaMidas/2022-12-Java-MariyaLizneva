import java.util.Map;

public interface CaseInterface {

    Map<Integer, Integer> getMoney(int money);

    void put(Nominal nominal);

    int getBalance();
}
