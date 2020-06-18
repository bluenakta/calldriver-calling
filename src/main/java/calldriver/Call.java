package calldriver;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Call_table")
public class Call {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String callStatus;

    @Transient
    private String eventType;

    @PrePersist
    public void onPrePersist(){

        CallReceived callReceived = new CallReceived();
        BeanUtils.copyProperties(this, callReceived);
        callReceived.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        calldriver.external.Pay pay = new calldriver.external.Pay();
        // mappings goes here
        pay.setCallId(this.id);
        pay.setPayStatus("PAYED");
        Application.applicationContext.getBean(calldriver.external.PayService.class)
            .payRequest(pay);

    }

    @PostUpdate
    public void onPostUpdate() {

        if("payed".equals(this.eventType)) {

            DriverRequested driverRequested = new DriverRequested();
            BeanUtils.copyProperties(this, driverRequested);
            driverRequested.publishAfterCommit();

        } else {
            Canceled canceled = new Canceled();
            BeanUtils.copyProperties(this, canceled);
            canceled.publishAfterCommit();
        }


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
