package cc.yysy.serviceai.rabbitmq;

import cc.yysy.serviceai.service.impl.AiServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@RabbitListener(queues = "rawFile")
public class Receiver {
    static Logger logger = Logger.getLogger("Receiver log");

//    @Autowired
//    private Sender sender;

    @Autowired
    private AiServiceImpl aiService;
    @RabbitHandler
    public void process(String url){
        logger.info("Receiver:" + url);
        aiService.dealRawFile(url);

//        sender.send("deal "+url);
//        System.out.println("Receiver:" + url);
    }
}