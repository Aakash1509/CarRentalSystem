package admin;

import system.CarDetails;

import java.util.Iterator;

import java.util.List;

import java.util.Objects;

public class AdminService
{
    public boolean carExists(String carId, List<CarDetails> cars)
    {
        //Check if carId already exists

        for(CarDetails car : cars)
        {
            if(car.getCarId().equals(carId))
            {
                return true;
            }
        }
        return false;
    }

    public CarDetails getCarById(String carId, List<CarDetails>cars)
    {
        for(CarDetails car : cars)
        {
            if(Objects.equals(car.getCarId(),carId))
            {
                return car;
            }
        }
        return null; //As car is not found with passed ID
    }

    public void addCarProcess(List<CarDetails> cars,String carId, String carBrand, String carModel, double basePricePerDay) throws Exception
    {
        CarDetails car = new CarDetails(carId, carBrand, carModel, basePricePerDay);

        cars.add(car);
    }

    public String removeCarProcess(List<CarDetails>cars, String carId)
    {
        synchronized (cars)
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
    }

    public String updateCarProcess(CarDetails car, String newCarBrand, String newCarModel, String newRentPrice)
    {
        if(!car.isAvailable())
        {
            return "Cannot update car details with ID "+ car.getCarId() + " because car is currently rented";
        }

        if (!newCarBrand.isEmpty())
        {
            car.setCarBrand(newCarBrand);
        }

        if (!newCarModel.isEmpty())
        {
            car.setCarModel(newCarModel);
        }

        if (!newRentPrice.isEmpty())
        {
            try
            {
                var newBasePrice = Double.parseDouble(newRentPrice);

                car.setBasePricePerDay(newBasePrice);
            }
            catch (NumberFormatException e)
            {
                return "Invalid Rent value. Keeping current rent";
            }
        }
        return "Car details updated successfully";
    }

}
