import annotation.After;
import annotation.Before;
import annotation.Test;

public class MyTest {
    @Before
    public void beforeALL(){
        System.out.println("Before");
        System.out.println("Object testing class: " + Integer.toHexString(hashCode()));
    }
    @Test
    public void Test3(){
        System.out.println("Object testing class: " + Integer.toHexString(hashCode()));
        String string1 = "Привет!";
        String string2 = "Привет!";
        string1.equals(string2);
        int fail = 2/0;

    }
    @Test
    public void Test1(){
        System.out.println("Object testing class: " + Integer.toHexString(hashCode()));
    }
    @Test
    public void Test2(){
        System.out.println("Object testing class: " + Integer.toHexString(hashCode()));
    }
    @After
    public void methodString(){
        System.out.println("This is method String");
    }
    @Test
    public void Test4(){
        System.out.println("Object testing class: " + Integer.toHexString(hashCode()));
        String string1 = "Привет!";
        String string2 = "Привет!";
        string1.equals(string2);
        int fail = 2/0;

    }
    @After
    public void afterALL(){
        System.out.println("After");
    }
}
