public abstract class User {
    public final void addUser(String data) {
        validateUser(data);
        addUserImpl(data);
    }

    protected void validateUser(String data) {
        String[] detail = data.split("\\|");

    }

    abstract void addUserImpl(String data);
}
