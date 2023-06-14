import java.util.ArrayList;
import java.util.List;

public class Start {
    public static void main(String[] args) {
        int balance = 0;
        Nominal[]money = {
                Nominal.FIFTY,
                Nominal.FIFTY,
                Nominal.FIVE_HUNDRED,
                Nominal.FIVE_HUNDRED,
                Nominal.ONE_THOUSAND,
                Nominal.FIVE_THOUSAND,
                Nominal.FIVE_THOUSAND};

    ATX atx = new ATX();
        List<Nominal> moneys = new ArrayList<>();
        for (Nominal nominal : money) {
            moneys.add(nominal);
            balance += nominal.getNominalValue();
        }
        atx.putMoney(moneys);
        System.out.println(atx.getBalance());
        atx.getMoney(11100);
        System.out.println(atx.getBalance());
        atx.getMoney(1000);
        System.out.println(atx.getBalance());
    }
}
