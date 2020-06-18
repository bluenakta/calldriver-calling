package calldriver;

import calldriver.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayed_PayComplete(@Payload Payed payed){

        if(payed.isMe()){
            System.out.println("##### listener PayComplete : " + payed.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDriverAssignFailed_DriverRequestFail(@Payload DriverAssignFailed driverAssignFailed){

        if(driverAssignFailed.isMe()){
            System.out.println("##### listener DriverRequestFail : " + driverAssignFailed.toJson());
        }
    }

}
