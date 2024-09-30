package customers;

import system.CarDetails;

import java.util.List;

import java.util.concurrent.atomic.AtomicInteger;

public class CustomerService
{
    private static final AtomicInteger rentalIdCounter = new AtomicInteger(0);

    private final Customer customer;

    // Constructor
    public CustomerService(Customer customer)
    {
        this.customer = customer;
    }

    public RentalRecord rentCarProcess(String carId , int rentalDuration , List<CarDetails>cars) throws Exception
    {
        CarDetails selectedCar = null;

        //Find selected car

        for (CarDetails car : cars)
        {
            if (car.getCarId().equals(carId) && car.isAvailable())
            {
                selectedCar = car;

                car.setRentedBy(customer.getUsername());

                break;
            }
        }
        if (selectedCar==null)
        {
            throw new Exception(" Car is not available or invalid Car ID");
        }
        else
        {
            // Calculate the total cost
            var totalCost = rentalDuration * selectedCar.getBasePricePerDay();

            // Mark the car as rented
            selectedCar.setAvailable(false);

            // Generate Rental ID
            var rentalId = "R" + rentalIdCounter.incrementAndGet();

            // Create an object of RentalRecord class

            return new RentalRecord(rentalId, customer.getUsername(), selectedCar.getCarId(), selectedCar.getCarBrand(), selectedCar.getCarModel(), rentalDuration, totalCost);
        }
    }

    public String returnCarProcess(List<CarDetails>cars,String rentalId, String username, List<RentalRecord> rentalRecords) throws Exception
    {
        RentalRecord selectedRental = null;

        /*Using streamAPI
         RentalRecord selectedRental = rentalRecords.stream()
                .filter(record -> record.getRentalId().equals(rentalId) && record.getUsername().equals(username))
                .findFirst().orElse(null);
         */

        for (RentalRecord record : rentalRecords)
        {
            if (record.getRentalId().equals(rentalId) && record.getUsername().equals(username))
            {
                selectedRental = record;
            }
        }

        if (selectedRental==null)
        {
//            return "Invalid Rental ID . Please try again";
            throw new Exception(" Invalid Rental ID . Please try again");
        }

        //Need to update Car status also

        CarDetails rentedCar = findCarById(selectedRental.getCarId(),cars);

        if (rentedCar!=null)
        {
            synchronized (cars)
            {
                rentedCar.setAvailable(true);
            }
            //Removing from rental records also (Synchronized as multiple clients can work on rentalRecords arraylist)
            synchronized (rentalRecords)
            {
                rentalRecords.remove(selectedRental);
            }
            return "Car returned successfully. Rental ID: " + rentalId;
        }
        else
        {
            return "Unable to find the car associated with this rental.";
        }
    }

    private CarDetails findCarById(String carId, List<CarDetails>cars)
    {
        for (CarDetails car : cars)
        {
            if (car.getCarId().equals(carId))
            {
                return car;
            }
        }
        return null;
    }

}
