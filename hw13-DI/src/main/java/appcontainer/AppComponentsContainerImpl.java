package appcontainer;

import appcontainer.api.AppComponent;
import appcontainer.api.AppComponentsContainer;
import appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        Method[] methods = configClass.getDeclaredMethods();
        var methodsList = new ArrayList<Method>();
        int count = 0;

        while (count <= methods.length){
            for (var method:methods) {
                if (method.getDeclaredAnnotation(AppComponent.class).order() == count) {
                    methodsList.add(method);
                }
            }
            count++;
        }

        try {
            for (Method method : methodsList) {
                Object configClassInstance = configClass.getDeclaredConstructor().newInstance();
                Object[] args = new Object[method.getParameterTypes().length];
                for(int i = 0; i < args.length; i++){
                    args[i] = getAppComponent(method.getParameterTypes()[i]);
                }
                var bean = method.invoke(configClassInstance,args);
                appComponents.add(bean);
                var beanName = method.getDeclaredAnnotation(AppComponent.class).name();
                appComponentsByName.put(beanName,bean);
                if(appComponents.size()!=appComponentsByName.size()){
                    throw new RuntimeException("В контексте компоненто с одинаковым именем");
                }
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<Object> beans = new ArrayList<>(1);
        for (Object b : appComponents) {
            if (componentClass.isAssignableFrom(b.getClass())) {
                beans.add(b);
            }
        }
        if (beans.size() != 1) throw new RuntimeException("Попытка достать из контекста отсутствующий или дублирующийся компонент");
        return (C)beans.get(0);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        Optional<C> componentOptional = Optional.ofNullable((C) appComponentsByName.get(componentName));
        return componentOptional.orElseThrow(() ->
                new RuntimeException(String.format("Failed to determine bean in %s", componentName))
        );
    }
}
