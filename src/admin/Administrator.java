package admin;

import java.util.HashMap;

//This will be a singleton class as I want to create only 1 object of this class

public class Administrator
{
    private String username;

    private String password;

    private final static HashMap<String, String> adminCredentials = new HashMap<>();

    //Private static instance of Singleton

    private static Administrator instance;

    //Private constructor

    private Administrator()
    {

    }

    //Public static method to provide single instance , synchronized so that at one time only one instance can be created

    public static Administrator getInstance()
    {
        if (instance==null)
        {
            instance = new Administrator();
        }
        return instance;
    }

    public static synchronized void registerAdmin(String username, String password)
    {
            adminCredentials.put(username, password); // Store username and password in HashMap
    }

    public boolean loginAdmin(String username, String password)
    {
        if (adminCredentials.containsKey(username) && adminCredentials.get(username).equals(password))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean exist()
    {
        return !adminCredentials.isEmpty();
    }
}
