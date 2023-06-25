package loger;

import annotation.Log;

import java.io.Serial;

public class TestLogging implements TestLoggingInterface {

        @Log
        @Override
        public void calculation(int param) {
                System.out.println("Метод с аннотацией Log " + param);
        }

        @Override
        public void calculation(int param, String param2){
                System.out.println("Метод без логирования  -  "+param+" "+param2);
        }
        @Override
        public void calculation(int param, double param2){
                System.out.println("Метод без логирования  -  "+param+" "+param2);
        }
}
