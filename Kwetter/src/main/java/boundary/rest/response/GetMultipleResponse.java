
import boundary.rest.response.ResponseBase;
import java.util.List;

public class GetMultipleResponse<T> extends ResponseBase {

    private List<T> Records;

    public List<T> getRecords() {
        return Records;
    }

    public void setRecords(List<T> Records) {
        this.Records = Records;
    }
}
