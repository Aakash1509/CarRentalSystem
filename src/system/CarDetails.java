package system;

//Will be from Admin side
public class CarDetails
{
    private String carId;
    private String carBrand;
    private String carModel;
    private double basePricePerDay;
    private boolean isAvailable;

    public CarDetails(String carId, String carBrand, String carModel, double basePricePerDay)
    {
        this.carId = carId;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
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

    public double getBasePricePerDay()
    {
        return basePricePerDay;
    }

    public void rentCar()
    {
        isAvailable = false;
    }

    public void returnCar()
    {
        isAvailable = true;
    }

    public double calculateRentPrice(int rentalDays)
    {
        return basePricePerDay * rentalDays;
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
}
