package chap06;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import spring.Client;

public class MainWithPrototype {
    public static void main(String[] args) {
        AbstractApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppCtxWithPrototype.class);


        Client client1 = ctx.getBean("client", Client.class);
        Client client2 = ctx.getBean("client", Client.class);

        ctx.close();
        System.out.println(client1 == client2);
    }
}
