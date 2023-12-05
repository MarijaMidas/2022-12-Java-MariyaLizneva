
/*
В классе AppComponentsContainerImpl реализовать обработку, полученной в конструкторе конфигурации,
основываясь на разметке аннотациями из пакета appcontainer. Так же необходимо реализовать методы getAppComponent.
В итоге должно получиться работающее приложение. Менять можно только класс AppComponentsContainerImpl.
Можно добавлять свои исключения.


*/

import appcontainer.AppComponentsContainerImpl;
import appcontainer.api.AppComponentsContainer;
import config.AppConfig;
import services.GameProcessor;
import services.GameProcessorImpl;

public class App {

    public static void main(String[] args) throws Exception {
        AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig.class);

        GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
        //GameProcessor gameProcessor = container.getAppComponent(GameProcessorImpl.class);
        //GameProcessor gameProcessor = container.getAppComponent("gameProcessor");

        gameProcessor.startGame();
    }
}
