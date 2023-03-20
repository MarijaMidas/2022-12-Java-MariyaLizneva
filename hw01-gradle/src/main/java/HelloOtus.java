import static com.google.common.base.Objects.equal;

public class HelloOtus {
    public static void main(String[] args) {
        int a = 5;
        System.out.println(equal(a,null));
        System.out.println(equal(a,a));
    }
}
