package cc.yysy.serviceai.rabbitmq;

import cc.yysy.serviceai.service.impl.AiServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

@Component
@RabbitListener(queues = "Radar")
public class RadarReceiver {
    static Logger logger = Logger.getLogger("Receiver log");

//    @Autowired
//    private Sender sender;

    @Autowired
    private AiServiceImpl aiService;

    @RabbitHandler
    public void process(Map<String,Object> params){
        Date date = (Date) params.get("date");
        String dataSourceId = (String) params.get("dataSourceId");
        Integer num = (Integer) params.get("num");
//        logger.info("Receiver:" + params.toString());
        aiService.dealRadar(date,num,dataSourceId);

//        sender.send("deal "+url);
//        System.out.println("Receiver:" + url);
    }
}