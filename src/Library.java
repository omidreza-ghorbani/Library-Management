import java.util.ArrayList;

class Library {
    final static ArrayList<String> libraries = new ArrayList<>();

    static public void addLibrary(String data) {
        String[] details = data.split("\\|");
        String adminName = details[0];
        String adminPassword = details[1];
        String id = details[2];

        if (!User.userExists(adminName)) {
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
        if (libraries.contains(id)) {
            System.out.print(ResponseMessage.DUPLICATE);
            return;
        }

        libraries.add(id);
        System.out.print(ResponseMessage.SUCCESS);
    }
}