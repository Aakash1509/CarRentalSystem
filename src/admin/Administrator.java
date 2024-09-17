package admin;

import java.util.HashMap;

//This will be a singleton class as I want to create only 1 object of this class

public class Administrator
{
    private String username;
    private String password;
    private HashMap<String, String> adminCredentials = new HashMap<>();

    //Private static instance of Singleton

    private static Administrator instance;

    //Private constructor

    private Administrator()
    {

    }

    /*
    public AdminDetails(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
     */

    //Public static method to provide single instance

    public static Administrator getInstance()
    {
        if (instance==null)
        {
            instance = new Administrator();
        }
        return instance;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public void registerAdmin(String username, String password)
    {
//        Admin newAdmin = new Admin(username,password);
        if (adminCredentials.isEmpty())
        {
            adminCredentials.put(username, password); // Store username and password in HashMap
            System.out.println("admin " + username + " registered successfully!");
        } else
        {
            System.out.println("An admin is already registered. Registration is not allowed");
        }

    }

    public boolean loginAdmin(String username, String password)
    {
        if (adminCredentials.containsKey(username) && adminCredentials.get(username).equals(password))
        {
            System.out.println("Login successful! Welcome, " + username);
//            AdminMenu adminMenu = new AdminMenu();

            //After successful login , redirect to AdminMenu.java
            AdminDashboard adminDashboard = new AdminDashboard();
            adminDashboard.showMenu();
            return true;
        } else
        {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    public HashMap<String, String> getAdminCredentials()
    {
        return adminCredentials;
    }
}
