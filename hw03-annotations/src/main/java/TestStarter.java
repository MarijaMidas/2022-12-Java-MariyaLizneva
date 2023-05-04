import annotation.After;
import annotation.Before;
import annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestStarter {
    public static void main(String[] args)throws Exception {
        Class<MyTest> clazz = MyTest.class;
        int tests = 0;
        int failed = 0;
        var failMethod = new ArrayList<String>();
        Method[] methodsAll = clazz.getDeclaredMethods();
        ArrayList<Method> methodWithBefore = getMethodBefore(methodsAll,clazz);
        ArrayList<Method> methodWithAfter = getMethodAfter(methodsAll,clazz);
        ArrayList<Method> methodWithTest = getMethodTest(methodsAll,clazz);
       for(var method:methodWithTest) {
           var singleObject = ReflectionHelper.instantiate(MyTest.class);
           for (var methodBefore : methodWithBefore) {
                   ReflectionHelper.callMethod(singleObject, methodBefore.getName());
           }
           try {
               ReflectionHelper.callMethod(singleObject, method.getName());
           } catch (Exception e) {
               failMethod.add(method.getName());
               failed++;
           }
           for (var methodAfter : methodWithAfter) {
                   ReflectionHelper.callMethod(singleObject, methodAfter.getName());
           }
           tests++;
       }

           int passed = tests - failed;
           System.out.printf(" Test: %d \n Test failed: %d \n Test passed: %d \n", tests, failed, passed);
           failMethod.forEach((s) -> System.out.println("Name failed methods: " + s));
    }
    private static ArrayList<Method> getMethodBefore(Method[] methodsAll,Class<MyTest> clazz)throws Exception{
        var methodWithBefore = new ArrayList<Method>();
        for(Method method:methodsAll) {
            String methodName = method.getName();
            Method annotatedMethod = clazz.getMethod(methodName);
            Annotation[] annotations = annotatedMethod.getDeclaredAnnotations();
            for (Annotation simple : annotations) {
                if (simple.annotationType().equals(Before.class)) {
                    methodWithBefore.add(method);
                }
            }
        }
        return methodWithBefore;
    }
    private static ArrayList<Method> getMethodTest(Method[] methodsAll,Class<MyTest> clazz)throws Exception{
        var methodWithTest = new ArrayList<Method>();
                for(Method method:methodsAll){
                    String methodName = method.getName();
                    Method annotatedMethod = clazz.getMethod(methodName);
                    Annotation[] annotations = annotatedMethod.getDeclaredAnnotations();
                    for (Annotation simple:annotations) {
                        if(simple.annotationType().equals(Test.class)){
                            methodWithTest.add(method);
                        }
                    }
                }
                return methodWithTest;
        }
    private static ArrayList<Method> getMethodAfter(Method[] methodsAll,Class<MyTest> clazz)throws Exception {
        var methodWithAfter = new ArrayList<Method>();
                    for (Method method : methodsAll) {
                        String methodName = method.getName();
                        Method annotatedMethod = clazz.getMethod(methodName);
                        Annotation[] annotations = annotatedMethod.getDeclaredAnnotations();
                        for (Annotation simple : annotations) {
                            if (simple.annotationType().equals(After.class)) {
                                methodWithAfter.add(method);
                            }
                        }
                    }
                    return methodWithAfter;
                }
}