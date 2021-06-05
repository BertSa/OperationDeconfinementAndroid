package ca.bertsa.cal.operationdeconfinementandroid.models;

import java.util.List;

@SuppressWarnings("unused")
public class ErrorResponse {

    private String message;
    private List<String> details;

    public ErrorResponse() {
    }

    public ErrorResponse(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }

    @Override
    public String toString() {
        if (details == null) return message;
        return details.get(0);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}
