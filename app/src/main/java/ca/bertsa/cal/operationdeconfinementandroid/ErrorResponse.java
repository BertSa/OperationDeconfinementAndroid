package ca.bertsa.cal.operationdeconfinementandroid;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {

    private String message;
    private List<String> details;

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
}
