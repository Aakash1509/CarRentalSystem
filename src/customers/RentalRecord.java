package customers;

public class RentalRecord
{
    private String rentalId;

    private String username;

    private String carId;

    private int rentalDuration;

    private double totalCost;

    public RentalRecord(String rentalId, String username, String carId, int rentalDuration, double totalCost)
    {
        this.rentalId = rentalId;

        this.username = username;

        this.carId = carId;

        this.rentalDuration = rentalDuration;

        this.totalCost = totalCost;
    }


}
