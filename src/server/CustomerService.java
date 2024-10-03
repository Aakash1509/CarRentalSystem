package server;

import system.CarDetails;

import system.CarRentalSystem;

import java.util.ArrayList;

import java.util.List;

import java.util.concurrent.atomic.AtomicInteger;

import static system.CarRentalSystem.cars;

public class CustomerService
{
    private static final AtomicInteger rentalIdCounter = new AtomicInteger(0);

    private static final List<RentalRecord> rentalRecords = new ArrayList<>();

    public RentalRecord rentCarProcess(String carId , int rentalDuration,String username)
    {
        synchronized (cars)
        {
            CarDetails selectedCar;

            //Find selected car

            selectedCar = CarRentalSystem.getCarById(carId);

            if(selectedCar == null || !selectedCar.isAvailable())
            {
                return null;
            }
            else
            {
                // Calculate the total cost
                var totalCost = rentalDuration * selectedCar.getBasePricePerDay();

                // Mark the car as rented
                selectedCar.setAvailable(false);

                selectedCar.setRentedBy(username);

                // Generate Rental ID
                var rentalId = "R" + rentalIdCounter.incrementAndGet();

                // Create an object of RentalRecord class

                RentalRecord record = new RentalRecord(rentalId,username,selectedCar.getCarId(), selectedCar.getCarBrand(), selectedCar.getCarModel(), rentalDuration, totalCost);

                rentalRecords.add(record);

                return record;
            }
        }
    }

    public String returnCarProcess(String rentalId, String username)
    {
        /*Using streamAPI
         RentalRecord selectedRental = rentalRecords.stream()
                .filter(record -> record.getRentalId().equals(rentalId) && record.getUsername().equals(username))
                .findFirst().orElse(null);
         */
        RentalRecord selectedRental = null;

        for (RentalRecord record : rentalRecords)
        {
            if (record.getRentalId().equals(rentalId) && record.getUsername().equals(username))
            {
                selectedRental = record;
            }
        }

        if (selectedRental==null)
        {
            return "Unable to find the car associated with this rental.";
        }

        //Need to update Car status also

        CarDetails rentedCar = CarRentalSystem.getCarById(selectedRental.getCarId());

        if (rentedCar!=null)
        {
            synchronized (cars)
            {
                rentedCar.setAvailable(true);

                rentedCar.setRentedBy(null);
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

    public List<RentalRecord> rentedCars(String username)
    {
        List<RentalRecord> records = new ArrayList<>();

        for(RentalRecord record : rentalRecords)
        {
            if(record.getUsername().equals(username))
            {
                records.add(record);
            }
        }
        return records;
    }

}
