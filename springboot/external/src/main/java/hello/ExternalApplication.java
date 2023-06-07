package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExternalApplication {

    //--url=devdb --username=dev_user --password=dev_pw mode=on
    public static void main(String[] args) {
        SpringApplication.run(ExternalApplication.class, args);
    }

}
