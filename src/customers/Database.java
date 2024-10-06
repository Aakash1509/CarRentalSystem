package customers;

import java.util.HashMap;

public class Database
{
    //Made it static so that every object shares it

    private static final HashMap<String, Customer> customers = new HashMap<>();

    //This will be synchronized as I am checking here that is username unique or not

    public static synchronized boolean registerCustomer(String username, String password, String drivingLicenseNumber)
    {
        //Logic to check : is username unique or not

        if (exist(username))
        {
            return false;
        }
        Customer customer = new Customer(username,password,drivingLicenseNumber);

        customers.put(username,customer);

        return true;
    }

    public static Customer loginCustomer(String username, String password)
    {
        if (customers.containsKey(username))
        {
            Customer customer = customers.get(username);

            if(customer.getPassword().equals(password))
            {
                return customer;
            }
        }
        return null;

    }
    public static boolean exist(String username)
    {
        return customers.containsKey(username);
    }

}
