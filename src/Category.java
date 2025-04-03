import java.util.HashMap;

class Category {
    final static HashMap<String, String> categories = new HashMap<>();

    static public void addCategory(String data) {
        String[] details = data.split("\\|");
        String adminName = details[0];
        String adminPassword = details[1];
        String id = details[2];
        String parentCategoryId = details[4];

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
        if (categories.containsKey(id)) {
            System.out.print(ResponseMessage.DUPLICATE);
            return;
        }
        if (!parentCategoryId.equals("null") && !categories.containsKey(parentCategoryId)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            return;
        }

        categories.put(id, parentCategoryId);
        System.out.print(ResponseMessage.SUCCESS);
    }
}
