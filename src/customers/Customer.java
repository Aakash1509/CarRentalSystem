package customers;

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

    //This will be synchronized as I am checking here that is username unique or not

    public synchronized boolean registerCustomer(String username, String password)
    {
        //Logic to check : is username unique or not

        if (!customerCredentials.containsKey(username))
        {
            customerCredentials.put(username, password); // Store username and password in HashMap

            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean loginCustomer(String username, String password)
    {
        if (customerCredentials.containsKey(username) && customerCredentials.get(username).equals(password))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
