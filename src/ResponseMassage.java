enum ResponseMessage {
    SUCCESS("success\n"),
    DUPLICATE("duplicate-id\n"),
    NOT_FOUND("not-found\n"),
    ADMIN_STR("admin"),
    PERMISSION("permission-denied\n"),
    INVALID_PASS("invalid-pass\n"),
    NOT_ALLOWED("not-allowed\n");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}