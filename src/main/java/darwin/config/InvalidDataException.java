package darwin.config;

public class InvalidDataException extends RuntimeException {
    private final String field;

    public InvalidDataException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}