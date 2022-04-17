package chap06;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.Client;

@Configuration
public class AppContext {

    @Bean
    public Client client() {
        Client client = new Client();
        client.setHost("host");
        return client;
    }
}
