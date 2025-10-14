package client.asta_lsi2;

import client.asta_lsi2.models.Apprenti;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AstaLsi2Application {

    public static void main(String[] args) {
        System.out.println("tata");
        Apprenti a=new Apprenti();
        SpringApplication.run(AstaLsi2Application.class, args);
    }

}
