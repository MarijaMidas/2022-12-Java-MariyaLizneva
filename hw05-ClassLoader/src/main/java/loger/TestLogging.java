package loger;

import annotation.Log;

public class TestLogging implements TestLoggingInterface {
        @Log
        @Override
        public void calculation(int param) {
                System.out.println("calculation param: " + param);
        }
        public void calculation(int param, String param2){
                System.out.println("My method  -  "+param+param2);
        }
}
