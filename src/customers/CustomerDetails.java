package customers;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class CustomerDetails
{
    private String username;
    private String password;
    private String drivingLicenseNumber;

    //Made it static so that every object shares it

    private static HashMap<String, String> customerCredentials = new HashMap<>();

    public CustomerDetails(String username, String password, String drivingLicenseNumber)
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
//        Guest newGuest = new Guest(username, password);

        //Logic to check : is username unique or not
//        System.out.println(customerCredentials.size());

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
            CustomerDetails customer = new CustomerDetails(username, password, drivingLicenseNumber);

            //After successful login , redirect to CustomerMenu.java
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
