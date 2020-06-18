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

    @Autowired
    CallRepository cr;
    
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayed_PayComplete(@Payload Payed payed){

        if(payed.isMe()){
            Call call = new Call();
            call.setId(payed.getCallId());
            call.setEventType(payed.getEventType());
            call.setCallStatus("PAYED");

            cr.save(call);

        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDriverAssignFailed_DriverRequestFail(@Payload DriverAssignFailed driverAssignFailed){

        if(driverAssignFailed.isMe()){

            Call call = new Call();
            call.setId(driverAssignFailed.getCallId());
            call.setEventType(driverAssignFailed.getEventType());
            call.setCallStatus("CANCEL");

            cr.save(call);
        }
    }

}
