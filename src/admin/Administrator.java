package admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import java.io.PrintWriter;

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

    public void registerAdmin(String username, String password, PrintWriter writeData)
    {
//        Admin newAdmin = new Admin(username,password);
        if (adminCredentials.isEmpty())
        {
            adminCredentials.put(username, password); // Store username and password in HashMap

            writeData.println("Admin " + username + " registered successfully!");
        }
        else
        {
            writeData.println("An admin is already registered. Registration is not allowed");
        }

    }

    public boolean loginAdmin(String username, String password, PrintWriter writeData, BufferedReader readData) throws IOException
    {
        if (adminCredentials.containsKey(username) && adminCredentials.get(username).equals(password))
        {
            writeData.println("Login successful! Welcome, " + username);
//            AdminMenu adminMenu = new AdminMenu();

            //After successful login , redirect to AdminMenu.java
            AdminDashboard adminDashboard = new AdminDashboard();

            adminDashboard.showMenu(writeData, readData);

            return true;
        }
        else
        {
            writeData.println("Invalid username or password. Login again");
            return false;
        }
    }

    public HashMap<String, String> getAdminCredentials()
    {
        return adminCredentials;
    }
}
