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

    //Public static method to provide single instance

    public static synchronized Administrator getInstance()
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

    public synchronized void registerAdmin(String username, String password, PrintWriter writeData)
    {
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

    public synchronized boolean loginAdmin(String username, String password, PrintWriter writeData, BufferedReader readData) throws IOException
    {
        if (adminCredentials.containsKey(username) && adminCredentials.get(username).equals(password))
        {
            writeData.println("Login successful! Welcome, " + username);

            //After successful login , redirect to AdminDashboard.java
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
