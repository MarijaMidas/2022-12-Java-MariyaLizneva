import annotation.Log;
import loger.TestLogging;
import loger.TestLoggingInterface;

import java.lang.annotation.Annotation;
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

        private static int countMethods = 0;
        DemoInvocationHandler(TestLoggingInterface myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(DemoInvocationHandler.isRequired(method)) {
                System.out.println("Method:" + method.getName() + " with parameter: " + Arrays.toString(args).replaceAll("\\[|\\]", ""));}
            return method.invoke(myClass, args);
        }

        private static boolean isRequired(Method IocMethod){
            Class<TestLogging> clazzTest = TestLogging.class;
            Method[] methodsAllTest = clazzTest.getDeclaredMethods();
            if(countMethods == methodsAllTest.length){countMethods = 0;}
            for(int i = countMethods; i< methodsAllTest.length;) {
                Method method = methodsAllTest[i];
                for (Annotation annotation : method.getDeclaredAnnotations()) {
                    if (annotation.annotationType().equals(Log.class)
                            && IocMethod.getName().equals(method.getName())) {
                        countMethods++;
                        return true;
                    }
                }
                countMethods++;
                return false;
            }
            countMethods++;
            return false;
        }
    }
}
