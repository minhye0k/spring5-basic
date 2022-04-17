package chap06;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.Client;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppContext.class);

        Client client = ctx.getBean(Client.class);
        client.send();

        ctx.close();
    }
}
