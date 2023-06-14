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

        DemoInvocationHandler(TestLoggingInterface myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(DemoInvocationHandler.isRequired(method)) {
                System.out.println("executed method:" + method.getName() + " parameter: " + Arrays.toString(args).replaceAll("\\[|\\]", ""));}
            return method.invoke(myClass, args);
        }

        private static boolean isRequired(Method IocMethod){
            Class<TestLogging> clazzTest = TestLogging.class;
            Method[] methodsAllTest = clazzTest.getDeclaredMethods();
            for(Method method:methodsAllTest) {
                for (Annotation annotation : method.getDeclaredAnnotations()) {
                    if (annotation.annotationType().equals(Log.class) && method==IocMethod) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
