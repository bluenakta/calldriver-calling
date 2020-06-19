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

            cr.findById(payed.getCallId()).ifPresent(call -> {

                call.setId(payed.getCallId());
                call.setCallStatus("PAYED");

                cr.save(call);
            });

        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDriverAssignFailed_DriverRequestFail(@Payload DriverAssignFailed driverAssignFailed){

        if(driverAssignFailed.isMe()){

            cr.findById(driverAssignFailed.getCallId()).ifPresent(call -> {

                call.setId(driverAssignFailed.getCallId());
                call.setCallStatus("NODRIVER");

                cr.save(call);
            });

        }
    }

}
