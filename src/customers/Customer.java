package customers;

import java.io.BufferedReader;

import java.io.PrintWriter;

import java.util.HashMap;

public class Customer
{
    private final String username;

    private final String password;

    private final String drivingLicenseNumber;

    //Made it static so that every object shares it

    private static final HashMap<String, String> customerCredentials = new HashMap<>();

    public Customer(String username, String password, String drivingLicenseNumber)
    {
        this.username = username;

        this.password = password;

        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    // Getters and other methods
    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getDrivingLicenseNumber()
    {
        return drivingLicenseNumber;
    }

    public synchronized void registerCustomer(String username, String password, PrintWriter writeData)
    {
        //Logic to check : is username unique or not

        if (!customerCredentials.containsKey(username))
        {
            customerCredentials.put(username, password); // Store username and password in HashMap

            writeData.println("Customer " + username + " registered successfully!");
        }
        else
        {
            writeData.println("This username already exists. Please register using other username");
        }

    }

    public synchronized boolean loginCustomer(String username, String password , PrintWriter writeData, BufferedReader readData)
    {
        if (customerCredentials.containsKey(username) && customerCredentials.get(username).equals(password))
        {
            writeData.println("Login successful! Welcome, " + username);

            //Need to create object of Customer Details as I need username in Customer Dashboard
            Customer customer = new Customer(username, password, drivingLicenseNumber);

            //After successful login , redirect to CustomerDashboard.java
            CustomerDashboard customerDashboard = new CustomerDashboard(customer);

            customerDashboard.showMenu(writeData, readData);

            return true;
        }
        else
        {
            writeData.println("Invalid username or password.");

            return false;
        }
    }

    public HashMap<String, String> getCustomerCredentials()
    {
        return customerCredentials;
    }
}
