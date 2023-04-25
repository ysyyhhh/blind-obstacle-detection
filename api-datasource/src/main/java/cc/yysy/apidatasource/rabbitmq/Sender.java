package cc.yysy.apidatasource.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.logging.Logger;

@Component
public class Sender {
    static Logger logger = Logger.getLogger("Sender log");
    @Autowired
    private AmqpTemplate rabbitmqTemplate;
    private static final String RAGER_NAME = "Radar";

    public void send(Map<String,Object> map){
//        logger.info("Sender:" + url);
//        String content = "hello" + new Date();
//        System.out.println("Sender:" +content);
        this.rabbitmqTemplate.convertAndSend(RAGER_NAME, map);
    }
}