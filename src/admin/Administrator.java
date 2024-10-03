package admin;

public class Administrator
{
    private static String username;

    private static String password;

    public static synchronized boolean registerAdmin(String newUsername, String newPassword)
    {
        if(username == null && password == null)
        {
            username = newUsername;

            password = newPassword;

            return true;
        }
        return false;
    }

    public static boolean loginAdmin(String loginUsername, String loginPassword)
    {
        if(username != null && password !=null)
        {
            return username.equals(loginUsername) && password.equals(loginPassword);
        }
        return false;
    }
}
