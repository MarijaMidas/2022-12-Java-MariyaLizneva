import annotation.Log;
import loger.TestLogging;
import loger.TestLoggingInterface;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashSet;

class Ioc {

    private Ioc() {
    }

    static TestLoggingInterface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface myClass;
        private static HashSet<Method>parameterMethodHashSet;

        DemoInvocationHandler(TestLoggingInterface myClass) {
            parameterMethodHashSet = new HashSet<>();
            this.myClass = myClass;
            Class<TestLogging> clazzTest = TestLogging.class;
            var methodsAllTest = clazzTest.getDeclaredMethods();
            Class<TestLoggingInterface> clazzInterface = TestLoggingInterface.class;
            var methodsAllTestInterface = clazzInterface.getDeclaredMethods();
            for(Method method:methodsAllTest){
                for(Method interfaceMethod:methodsAllTestInterface) {
                    if (method.isAnnotationPresent(Log.class)
                            && interfaceMethod.getName().contains(method.getName())
                            && Arrays.equals(interfaceMethod.getParameterTypes(), method.getParameterTypes())) {
                        parameterMethodHashSet.add(interfaceMethod);
                    }
                }
            }
            for(Method method:parameterMethodHashSet){
                System.out.println(method.getName());
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(parameterMethodHashSet.contains(method)) {
                System.out.println("Method:" + method.getName() + " with parameter: " + Arrays.toString(args).replaceAll("\\[|\\]", ""));}
            return method.invoke(myClass, args);
        }
    }
}
