package customers;

public class Customer
{
    private final String username;

    private final String password;

    private final String drivingLicenseNumber;

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

}
