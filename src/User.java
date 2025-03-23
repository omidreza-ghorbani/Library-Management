import java.util.HashMap;
abstract public class User {
    private String id;
    private String password;
    private String firstName;
    private String lastName;
    private String nationalCode;
    private String birthDate;
    private String address;
    final static HashMap<String, User> users = new HashMap<>();

    public User(String id, String password, String firstName, String lastName, String nationalCode, String birthDate, String address) {
        this.id = id;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.birthDate = birthDate;
        this.address = address;
    }

    public String getId() {return id;}

    static void addUser(String data, String type) {
        String[] detail = data.split("\\|");
        String adminName = detail[0];
        String adminPassword = detail[1];
        String id = detail[2];
        String userPassword = detail[3];
        String firstName = detail[4];
        String lastName = detail[5];
        String nationalCode = detail[6];
        String birthDate = detail[7];
        String address = detail[8];
        String library = "null";
        if (type.equals("manager")) {
            library = detail[9];
        }

        if (!userExists(adminName)) {
            System.out.print(Main.NOT_FOUND);return;}

        if(!LibraryManager.libraries.containsKey(library) && !library.equals("null")) {
            System.out.print(Main.NOT_FOUND);return;
        }

        if (!adminName.equals(Main.ADMIN_STR)) {
            System.out.print(Main.PERMISSION);return;}

        if (!checkPassword(adminName, adminPassword)) {
            System.out.print(Main.INVALID_PASS);return;}

        if (userExists(id)) {
            System.out.print(Main.DUPLICATE);return;}

        if (type.equals("student")) {
            User user = new Student(detail[2],detail[3],detail[4],detail[5],detail[6],detail[7],detail[8]);
            users.put(id, user);
        } else if (type.equals("staff")) {
            if(detail[9].equals("staff")){
                User staff = new Staff(detail[2],detail[3],detail[4],detail[5],detail[6],detail[7],detail[8]);
                users.put(id, staff);
            }else {
                User professor = new Professor(detail[2],detail[3],detail[4],detail[5],detail[6],detail[7],detail[8]);
                users.put(id, professor);
            }
        } else if (type.equals("manager")) {
            if (!LibraryManager.libraries.containsKey(detail[9])) {
                System.out.print(Main.NOT_FOUND);return;
            }
            User manager = new Manager(detail[2],detail[3],detail[4],detail[5],detail[6],detail[7],detail[8],detail[9]);
            users.put(id, manager);
        }
        System.out.print(Main.SUCCESS);return;
    }

    public static boolean userExists(String key) {
        return users.containsKey(key);}

    public static boolean checkPassword(String name, String password) {
        return users.get(name).password.equals(password);}

    public static void removeUser(String data) {
        String[] detail = data.split("\\|");
        String adminName = detail[0];
        String adminPassword = detail[1];
        String id = detail[2];

        if (!userExists(adminName)) {
           System.out.print(Main.NOT_FOUND);return;}

        if (!userExists(id)) {
            System.out.print(Main.NOT_FOUND);return;}

        if (!adminName.equals(Main.ADMIN_STR)) {
            System.out.print(Main.PERMISSION);return;}

        if (!User.checkPassword(adminName, adminPassword)) {
            System.out.print(Main.INVALID_PASS);return;}


        users.remove(id);

        System.out.print(Main.SUCCESS);return;
    }
}

