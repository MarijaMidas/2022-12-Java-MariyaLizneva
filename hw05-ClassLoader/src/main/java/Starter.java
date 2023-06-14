import annotation.Log;
import loger.TestLogging;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Starter {
    public static void main(String[] args)throws Exception {
        Class<TestLogging> clazzTest = TestLogging.class;
        Method[] methodsAllTest = clazzTest.getDeclaredMethods();
        System.out.println(Arrays.toString(methodsAllTest));
        Method[] methodLog = null;
        for(Method method:methodsAllTest){
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation simple:annotations) {
                System.out.println("Все аннотации" + simple.toString());
                if (simple.annotationType().equals(Log.class)) {
                    System.out.println("Проверка на наличие аннотации" + simple);
                }
            }
        }
       // System.out.println(methodLog.getName());
    }
}
