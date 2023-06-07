import annotation.Log;
import loger.TestLogging;
import loger.TestLoggingInterface;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Starter {
    public static void main(String[] args)throws Exception {
        TestLoggingInterface myClass = Ioc.createMyClass();

        Class<TestLogging> clazzTest = TestLogging.class;
        Method[] methodsAllTest = clazzTest.getDeclaredMethods();
        Method methodLog = null;
        for(Method method:methodsAllTest){
            String methodName = method.getName();
            Method annotatedMethod = clazzTest.getMethod(methodName);
            Annotation[] annotations = annotatedMethod.getDeclaredAnnotations();
            for (Annotation simple:annotations) {
                if (simple.annotationType().equals(Log.class)) {
                    methodLog = method;
                }
            }
        }
        System.out.println(methodLog.getName());
//        Class<Demo> clazz = Demo.class;
//        Method[] methodsAll = clazz.getDeclaredMethods();
//        var demoObject = ReflectionHelper.instantiate(Demo.class);
//        for(Method methodDemo:methodsAll){
//            if(methodLog.equals(methodDemo)) {
//                ReflectionHelper.callMethod(demoObject, methodDemo.getName());
//            }
//        }
    }
}
