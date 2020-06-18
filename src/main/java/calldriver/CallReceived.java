package calldriver;

public class CallReceived extends AbstractEvent {

    private Long id;
    private String callStatus;

    public CallReceived(){
        super();
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
