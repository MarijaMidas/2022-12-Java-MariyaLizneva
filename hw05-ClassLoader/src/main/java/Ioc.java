import annotation.Log;
import loger.TestLogging;
import loger.TestLoggingInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

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
        private static Method[] methodsAllTest;
        private static int countMethods;

        DemoInvocationHandler(TestLoggingInterface myClass) {
            countMethods = 0;
            this.myClass = myClass;
            Class<TestLogging> clazzTest = TestLogging.class;
            methodsAllTest = clazzTest.getDeclaredMethods();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(DemoInvocationHandler.isRequired(method,methodsAllTest)) {
                System.out.println("Method:" + method.getName() + " with parameter: " + Arrays.toString(args).replaceAll("\\[|\\]", ""));}
            return method.invoke(myClass, args);
        }

        private static boolean isRequired(Method IocMethod,Method[] methodsAllTest){
            if(countMethods == methodsAllTest.length){countMethods = 0;}
            for(int i = countMethods; i< methodsAllTest.length;) {
                Method method = methodsAllTest[i];
                if (method.isAnnotationPresent(Log.class)
                        && IocMethod.getName().equals(method.getName())) {
                    countMethods++;
                    return true;
                }
                countMethods++;
                return false;
            }
            countMethods++;
            return false;
        }
    }
}
