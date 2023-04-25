package cc.yysy.serviceai.rabbitmq;

import cc.yysy.serviceai.service.impl.AiServiceImpl;
import cc.yysy.utilscommon.utils.FormatUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.logging.Logger;

@Component
@RabbitListener(queues = "rawImage")
public class ImageReceiver {
    static Logger logger = Logger.getLogger("ImageReceiver log");

//    @Autowired
//    private Sender sender;

    @Autowired
    private AiServiceImpl aiService;
    @RabbitHandler
    public void process(Map<String,Object> params){
        String url = (String) params.get("url");
        String dataSourceId = (String) params.get("dataSourceId");
        if (dataSourceId == null || url == null){
            return ;
        }
        logger.info("Receiver:" + url);
        if(FormatUtils.judgeImageFileName(url)){
            aiService.dealRawFile(url,dataSourceId);
        }
        else{
            logger.info("Receiver: not format matched file");
        }
//        aiService.dealRawFile(url);

//        sender.send("deal "+url);
//        System.out.println("Receiver:" + url);
    }
}