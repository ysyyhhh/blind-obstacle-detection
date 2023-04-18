package cc.yysy.servicedatasource.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@RabbitListener(queues = "dealFile")
public class Receiver {
    static Logger logger = Logger.getLogger("Receiver log");

    @RabbitHandler
    public void process(String url){
        logger.info("Receiver:" + url);
//        System.out.println("Receiver:" + url);
    }
}