import java.util.Map;

public interface CaseInterface {

    Map<Integer, Cell> getMoney(int money);

    void put(Nominal nominal);

    int getBalance();
}
