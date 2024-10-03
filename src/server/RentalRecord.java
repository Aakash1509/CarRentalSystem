package server;

public class RentalRecord
{
    private final String rentalId;

    private final String username;

    private final String carId;

    private final String carBrand;

    private final String carModel;

    private final int rentalDuration;

    private final double totalCost;

    public RentalRecord(String rentalId,String username,String carId, String carBrand, String carModel, int rentalDuration, double totalCost)
    {
        this.rentalId = rentalId;

        this.username = username;

        this.carId = carId;

        this.carBrand = carBrand;

        this.carModel = carModel;

        this.rentalDuration = rentalDuration;

        this.totalCost = totalCost;
    }

    public String getRentalId()
    {
        return rentalId;
    }

    public String getUsername()
    {
        return username;
    }

    public String getCarBrand()
    {
        return carBrand;
    }

    public String getCarModel()
    {
        return carModel;
    }

    public int getRentalDuration()
    {
        return rentalDuration;
    }

    public String getCarId()
    {
        return carId;
    }

    public double getTotalCost()
    {
        return totalCost;
    }
}
