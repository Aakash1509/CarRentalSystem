//Will be from Customer Side
public class CustomerDetailsAfterLogin {
    private String customerId;
    private String customerName;
    private String licenseNumber;

    public CustomerDetailsAfterLogin(String customerId, String customerName, String licenseNumber) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.licenseNumber = licenseNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }
}
