package customers;

import java.util.HashMap;

public class CustomerDetails
{
    private String username;
    private String password;
    private HashMap<String, String> customerCredentials = new HashMap<>();

    public CustomerDetails(String username, String password)
    {
        this.username = username;
        this.password = password;
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

    public void registerCustomer(String username, String password)
    {
//        Guest newGuest = new Guest(username, password);
        customerCredentials.put(username, password); // Store username and password in HashMap
        System.out.println("Customer" + username + " registered successfully!");
    }

    public boolean loginCustomer(String username, String password)
    {
        if (customerCredentials.containsKey(username) && customerCredentials.get(username).equals(password))
        {
            System.out.println("Login successful! Welcome, " + username);

            //After successful login , redirect to CustomerMenu.java
            CustomerMenu customerMenu = new CustomerMenu();
            customerMenu.showMenu();
            return true;
        } else
        {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    public HashMap<String, String> getCustomerCredentials()
    {
        return customerCredentials;
    }
}
