import java.util.Stack;

public class CustomerReverseOrder {
    Stack<Customer> stack = new Stack<>();
    public void add(Customer customer) {
        stack.push(customer);
    }
    public Customer take() {

        return stack.pop();
    }
}
