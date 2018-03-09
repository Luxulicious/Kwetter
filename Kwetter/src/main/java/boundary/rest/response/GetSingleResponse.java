package boundary.rest.response;

public class GetSingleResponse<T> extends ResponseBase {

    private T Record;

    public T getRecord() {
        return Record;
    }

    public void setRecord(T Record) {
        this.Record = Record;
    }
}
