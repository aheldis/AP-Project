package IDK;

public enum ErrorType {
    USER_NAME_NOT_FOUND("User name not found"),
    PASSWORD_DOESNT_MATCH("Password doesn't match"),
    USER_NAME_ALREADY_EXIST("User name already exist"),

    private String message;

    public String getMessage() {
        return message;
    }

    ErrorType(String message) {
        this.message = message;
    }
}
