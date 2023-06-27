import java.util.Map;

public interface Case {

    Map<Integer, Integer> getMoney(int money);

    void put(Nominal nominal);

    int getBalance();
}
