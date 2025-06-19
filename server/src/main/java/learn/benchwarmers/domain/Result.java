package learn.benchwarmers.domain;

public class Result <T> extends Response {
    private T payload;
    private ResultType type = ResultType.SUCCESS;

    public T getPayload(){
        return payload;
    }

    public void setPayload(T payload){
        this.payload = payload;
    }

    public ResultType getType() {
        return type;
    }
}
