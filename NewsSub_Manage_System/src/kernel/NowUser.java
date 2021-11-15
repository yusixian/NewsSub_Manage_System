package kernel;

public class NowUser {
    private static int id;          // 用户编号
    private static String username; // 用户名
    private static String type;     // 用户类型

    public static int getId() { return id; }

    public static void setId(int id) { NowUser.id = id; }

    public static String getUsername() { return username; }

    public static void setUsername(String username) { NowUser.username = username; }

    public static String getType() { return type; }

    public static void setType(String type) { NowUser.type = type; }
}
