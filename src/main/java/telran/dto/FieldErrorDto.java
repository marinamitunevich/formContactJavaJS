package telran.dto;

public class FieldErrorDto {
    public String field;
    public String errorMessage;

    public FieldErrorDto(String field, String errorMessage) {
        this.field = field;
        this.errorMessage = errorMessage;
    }

    public FieldErrorDto() {
    }
}
