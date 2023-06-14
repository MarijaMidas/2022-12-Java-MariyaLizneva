package loger;

import annotation.Log;

import java.io.Serial;

public class TestLogging implements TestLoggingInterface {

        @Override
        public void calculation(int param) {
                System.out.println("calculation param: " + param);
        }

        @Log
        @Override
        public void calculation(int param, String param2){
                System.out.println("My method  -  "+param+param2);
        }
}
