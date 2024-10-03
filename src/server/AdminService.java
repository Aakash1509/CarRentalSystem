package server;

import system.CarDetails;

import system.CarRentalSystem;

import java.util.ArrayList;

import java.util.List;

import static system.CarRentalSystem.cars;

public class AdminService
{
    //Processing adding a car

    public void addCarProcess(String carId, String carBrand, String carModel, double basePricePerDay)
    {
        synchronized (cars)
        {
            cars.add(new CarDetails(carId, carBrand, carModel, basePricePerDay));
        }
    }

    //Processing removing a car

    public String removeCarProcess(String carId)
    {
        synchronized (cars)
        {
            try
            {
                CarDetails car = CarRentalSystem.getCarById(carId);

                if(car==null)
                {
                    return "No available car found with ID: " + carId;
                }
                if(!car.isAvailable())
                {
                    return "Cannot remove car with ID "+ carId + " because car is currently rented";
                }
                cars.remove(car);

                return "Car with ID " + carId + " removed successfully.";
            }
            catch (Exception e)
            {
                return "An error occurred while removing the car"+e.getMessage();
            }
        }
    }

    //Processing updating a car

    public String updateCarProcess(String carID, String newCarBrand, String newCarModel, String newRentPrice)
    {
        synchronized (cars)
        {
            var car = CarRentalSystem.getCarById(carID);

            if(car!=null)
            {
                boolean isUpdated = false;

                StringBuilder result = new StringBuilder("Car details updated successfully");

                if (!"null".equalsIgnoreCase(newCarBrand))
                {
                    car.setCarBrand(newCarBrand);

                    isUpdated = true;
                }

                if (!"null".equalsIgnoreCase(newCarModel))
                {
                    car.setCarModel(newCarModel);

                    isUpdated = true;
                }

                if (!"null".equalsIgnoreCase(newRentPrice))
                {
                    try
                    {
                        var newBasePrice = Double.parseDouble(newRentPrice);

                        car.setBasePricePerDay(newBasePrice);

                        isUpdated = true;
                    }
                    catch (NumberFormatException e)
                    {
                        result.append(", but Invalid Rent value so keeping current rent");
                    }
                }
                if(!isUpdated)
                {
                    return "Keeping values as it is";
                }
                return result.toString();
            }
            return "No available car found with ID: "+carID;
        }

    }

    public List<CarDetails> rentedCars()
    {
        List<CarDetails> records = new ArrayList<>();

        for(CarDetails car : cars)
        {
            if(!car.isAvailable())
            {
                records.add(car);
            }
        }
        return records;
    }

}
