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
            parameterMethodHashSet.addAll(Arrays.asList(methodsAllTest));
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(DemoInvocationHandler.isRequired(method,args)) {
                System.out.println("Method:" + method.getName() + " with parameter: " + Arrays.toString(args).replaceAll("\\[|\\]", ""));}
            return method.invoke(myClass, args);
        }

        private static boolean isRequired(Method iocMethod,Object[] args){
            for(Method method:parameterMethodHashSet){
                Parameter[] parameters = method.getParameters();
            if(method.isAnnotationPresent(Log.class)
                   && iocMethod.getName().equals(method.getName())
                   && parameters.length == args.length) {
                        for (int i = 0; i < parameters.length; i++){
                            String argsName = (args[i].getClass().getSimpleName()).toLowerCase();
                            String paramName = parameters[i].getType().toString();
                            if(argsName.contains(paramName)){
                                return true;
                            }
                        }
                }
            }
            return false;
        }
    }
}
