package cc.yysy.serviceai.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class Sender {
    static Logger logger = Logger.getLogger("Sender log");
    @Autowired
    private AmqpTemplate rabbitmqTemplate;
    private static final String DEAL_FILE_NAME = "dealFile";

    public void send(String url){
        logger.info("Sender:" + url);
//        String content = "hello" + new Date();
//        System.out.println("Sender:" +content);
        this.rabbitmqTemplate.convertAndSend(DEAL_FILE_NAME, url);
    }
}