package system;

//Will be from Admin side

//Details of the car

public class CarDetails
{
    private String carId;

    private String carBrand;

    private String carModel;

    private double basePricePerDay;

    private boolean isAvailable;

    private String rentedBy;

    public CarDetails(String carId, String carBrand, String carModel, double basePricePerDay)
    {
        this.carId = carId;

        this.carBrand = carBrand;

        this.carModel = carModel;

        this.basePricePerDay = basePricePerDay;

        this.isAvailable = true;

        this.rentedBy = null;
    }

    public String getCarId()
    {
        return carId;
    }

    public String getCarBrand()
    {
        return carBrand;
    }

    public String getCarModel()
    {
        return carModel;
    }

    public boolean isAvailable()
    {
        return isAvailable;
    }

    public String getRentedBy()
    {
        return rentedBy;
    }

    public void setAvailable(boolean available)
    {
        this.isAvailable = available;
    }

    public double getBasePricePerDay()
    {
        return basePricePerDay;
    }

    public void setCarId(String carId)
    {
        this.carId = carId;
    }

    public void setCarBrand(String carBrand)
    {
        this.carBrand = carBrand;
    }

    public void setCarModel(String carModel)
    {
        this.carModel = carModel;
    }

    public void setBasePricePerDay(double basePricePerDay)
    {
        this.basePricePerDay = basePricePerDay;
    }

    public void setRentedBy(String username)
    {
        this.rentedBy = username;
    }
}
