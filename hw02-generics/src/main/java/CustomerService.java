import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    static TreeMap<Customer, String> map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    public Map.Entry<Customer, String> getSmallest() {
        return map.firstEntry();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return map.higherEntry(customer);
    }

    public void add(Customer customer, String data) {
        map.put(customer,data);
    }
}
