import loger.TestLogging;
import loger.TestLoggingInterface;

public class Demo {
    public static void main(String[] args) throws Exception {
        TestLoggingInterface myClass = Ioc.createMyClass();
        myClass.calculation(6);
        myClass.calculation(10,"Привет");
        myClass.calculation(1001);
        myClass.calculation(50,5000.6451);
    }
}
