import java.util.HashMap;

abstract public class User {
    private String id;
    private String password;
    private String firstName;
    private String lastName;
    private String nationalCode;
    private String birthDate;
    private String address;
    final static private HashMap<String, User> users = new HashMap<>();

    public User(String id, String password, String firstName, String lastName, String nationalCode, String birthDate, String address) {
        this.id = id;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.birthDate = birthDate;
        this.address = address;
    }

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

        if (!adminName.equals(Main.ADMIN_STR)) {
            System.out.println(Main.NOT_FOUND);
        }

        if (adminName.equals(Main.ADMIN_STR)) {
            System.out.println(Main.PERMISSION);
        }

        if (!adminPassword.equals(Main.ADMIN_PASS)) {
            System.out.println(Main.INVALID_PASS);
        }

        if (userExists(id)) {
            System.out.println(Main.DUPLICATE);
        }

        if (type.equals("student")) {
            User user = new Student(detail[2],detail[3],detail[4],detail[5],detail[6],detail[7],detail[8]);
            users.put(id, user);
        } else if (type.equals("staff")) {
            User user = new Staff(detail[2],detail[3],detail[4],detail[5],detail[6],detail[7],detail[8]);
            users.put(id, user);
        } else if (type.equals("manager")) {
            if (!LibraryManager.libraries.containsKey(detail[9])) {
                System.out.println(Main.NOT_FOUND);
            }
            User user = new Manager(detail[2],detail[3],detail[4],detail[5],detail[6],detail[7],detail[8],detail[9]);
            users.put(id, user);
        }
        System.out.println(Main.SUCCESS);
    }

    public static boolean userExists(String key) {
        return users.containsKey(key);
    }
}

