package admin;

import system.CarDetails;

import java.util.Iterator;

import java.util.List;

import java.util.Objects;

public class AdminService
{
    //Processing adding a car

    public void addCarProcess(List<CarDetails> cars,String carId, String carBrand, String carModel, double basePricePerDay)
    {
        CarDetails car = new CarDetails(carId, carBrand, carModel, basePricePerDay);

        cars.add(car);
    }

    //Processing removing a car

    public String removeCarProcess(List<CarDetails>cars, String carId)
    {
        synchronized (cars)
        {
            try
            {
                //Cannot use forEach loop because it will throw ConcurrentModificationException , so need to use iterator
                Iterator<CarDetails> iterator = cars.iterator(); // Get the iterator

                while (iterator.hasNext())
                {
                    CarDetails car = iterator.next(); // Get the next car

                    // Check if the car is available and matches the ID

                    if (Objects.equals(car.getCarId(), carId))
                    {
                        if(!car.isAvailable())
                        {
                            return "Cannot remove car with ID "+ carId + " because car is currently rented";
                        }
                        iterator.remove(); // Use iterator's remove method to safely remove the car

                        return "\nCar with ID " + carId + " removed successfully.";
                    }
                }
                return "No available car found with ID: " + carId;
            }
            catch (Exception e)
            {
                return "An error occurred while removing the car"+e.getMessage();
            }

        }
    }

    //Processing updating a car

    public String updateCarProcess(CarDetails car, String newCarBrand, String newCarModel, String newRentPrice)
    {
        boolean isUpdated = false;

        StringBuilder result = new StringBuilder("Car details updated successfully");

        if (!newCarBrand.isEmpty())
        {
            car.setCarBrand(newCarBrand);

            isUpdated = true;
        }

        if (!newCarModel.isEmpty())
        {
            car.setCarModel(newCarModel);

            isUpdated = true;
        }

        if (!newRentPrice.isEmpty())
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

}
