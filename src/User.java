import java.util.HashMap;

abstract class User {
    private final String id;
    private final String password;
    private final String firstName;
    private final String lastName;
    private boolean penalized = false;
    final static HashMap<String, User> users = new HashMap<>();

    public boolean getPenalized() {
        return penalized;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setPenalized(boolean penalized) {
        this.penalized = penalized;
    }

    public User(String id, String password, String firstName, String lastName) {
        this.id = id;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    static void addUser(String data, String type) {
        String[] detail = data.split("\\|");
        String adminName = detail[0];
        String adminPassword = detail[1];
        String id = detail[2];
        String library = "null";

        if (type.equals("manager")) {
            library = detail[9];
        }

        if (!userExists(adminName)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            return;
        }
        if (!Library.libraries.contains(library) && !library.equals("null")) {
            System.out.print(ResponseMessage.NOT_FOUND);
            return;
        }
        if (!adminName.equals(ResponseMessage.ADMIN_STR.toString())) {
            System.out.print(ResponseMessage.PERMISSION);
            return;
        }
        if (isInvalidPassword(adminName, adminPassword)) {
            System.out.print(ResponseMessage.INVALID_PASS);
            return;
        }
        if (userExists(id)) {
            System.out.print(ResponseMessage.DUPLICATE);
            return;
        }

        switch (type) {
            case "student" -> {
                User user = new Student(detail[2], detail[3], detail[4], detail[5]);
                users.put(id, user);
            }
            case "staff" -> {
                if (detail[9].equals("staff")) {
                    User staff = new Staff(detail[2], detail[3], detail[4], detail[5]);
                    users.put(id, staff);
                } else {
                    User professor = new Professor(detail[2], detail[3], detail[4], detail[5]);
                    users.put(id, professor);
                }
            }
            case "manager" -> {
                if (!Library.libraries.contains(detail[9])) {
                    System.out.print(ResponseMessage.NOT_FOUND);
                    return;
                }
                User manager = new Manager(detail[2], detail[3], detail[4], detail[5], detail[9]);
                users.put(id, manager);
            }
        }
        System.out.print(ResponseMessage.SUCCESS);
    }

    public static void removeUser(String data) {
        String[] detail = data.split("\\|");
        String adminName = detail[0];
        String adminPassword = detail[1];
        String id = detail[2];

        if (!userExists(adminName)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            return;
        }
        if (!adminName.equals(ResponseMessage.ADMIN_STR.toString())) {
            System.out.print(ResponseMessage.PERMISSION);
            return;
        }
        if (User.isInvalidPassword(adminName, adminPassword)) {
            System.out.print(ResponseMessage.INVALID_PASS);
            return;
        }
        if (!userExists(id)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            return;
        }
        if (User.users.get(id).getPenalized()) {
            System.out.print(ResponseMessage.NOT_ALLOWED);
            return;
        }
        String userKeyPrefix = id + "_";
        boolean hasBorrowed = Borrow.borrows.keySet().stream().anyMatch(k -> k.startsWith(userKeyPrefix));
        if (hasBorrowed) {
            System.out.print(ResponseMessage.NOT_ALLOWED);
            return;
        }

        users.remove(id);
        System.out.print(ResponseMessage.SUCCESS);
    }

    public static boolean userExists(String key) {
        return users.containsKey(key);
    }

    public static boolean isInvalidPassword(String name, String password) {
        return !users.get(name).password.equals(password);
    }
}
