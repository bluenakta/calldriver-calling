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


    @PostPersist
    public void onPostPersist(){

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


        if("PAYED".equals(this.callStatus)) {

            DriverRequested driverRequested = new DriverRequested();

            BeanUtils.copyProperties(this, driverRequested);

            System.out.println("-------------------------------");
            System.out.println("------------------------------- " + this.callStatus);
            System.out.println("-------------------------------");
            driverRequested.publishAfterCommit();

        } else if("NODRIVER".equals(this.callStatus)) {

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

}
