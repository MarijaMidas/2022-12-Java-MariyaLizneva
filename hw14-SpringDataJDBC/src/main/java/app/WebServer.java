package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    // ��������� ��������

    http://localhost:8080/clients

*/

@SpringBootApplication
public class WebServer {
    public static void main(String[] args) {
        SpringApplication.run(WebServer.class,args);
    }
}
