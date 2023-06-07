import java.util.List;

public interface ATXInterface {
    int getBalance();

    void putMoney(List<Nominal> moneys);

    void getMoney(int money);
}
