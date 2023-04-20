package racingcar.controller.dto;

public class ExceptionResponse {

    private final String message;

    public ExceptionResponse(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
