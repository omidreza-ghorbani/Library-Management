public class Staff extends User {
    @Override
    protected void addUserImpl() {
        System.out.println("Adding staff user...");
    }
}
