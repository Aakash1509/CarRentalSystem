package customers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import system.CarDetails;
import system.CarRentalSystem;

public class CustomerDashboard extends CarRentalSystem
{
    //To keep rental Id thread safe
    private static final AtomicInteger rentalIdCounter = new AtomicInteger(0);

    private List<RentalRecord> rentalRecords = new ArrayList<>();

    private Customer customer;

    public CustomerDashboard(Customer customer)
    {
        this.customer = customer;
    }

    public synchronized void rentCar(PrintWriter writeData, BufferedReader readData) throws IOException
    {
        viewAvailableCars(writeData);

        writeData.println("\nEnter Car Id to rent: ");

        writeData.flush();

        var carId = readData.readLine();

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
            writeData.println("Car is not available or invalid car ID");
        }
        else
        {
            writeData.println("Enter rental duration (in days) : ");

            writeData.flush();

            var rentalDuration = Integer.parseInt(readData.readLine());

            //Total Cost

            var totalCost = rentalDuration * selectedCar.getBasePricePerDay();

            //Mark the car as rented

            selectedCar.setAvailable(false);

            //Generating Rental ID

            var rentalId = "R" + rentalIdCounter.incrementAndGet();

            //Create object of RentalRecord class

            RentalRecord record = new RentalRecord(rentalId, customer.getUsername(), selectedCar.getCarId(), rentalDuration, totalCost);

            //Adding rental record in arraylist

            rentalRecords.add(record);

            writeData.println("\nCar rented successfully...");

            //Displaying Car Details

            writeData.println("Rental Details :");

            writeData.println("Car :" + selectedCar.getCarBrand() + " " + selectedCar.getCarModel());

            writeData.println("Rental Duration :" + rentalDuration + " days");

            writeData.println("Total Cost: $ " + totalCost);

            writeData.flush();
        }
    }

    public synchronized void returnCar(PrintWriter writeData, BufferedReader readData) throws IOException
    {
        var username = customer.getUsername();

        /* boolean hasRentedCars = rentalRecords.stream().anyMatch(record -> record.getUsername().equals(username));*/
        boolean hasRentedCars = false;

        for (RentalRecord record : rentalRecords)
        {
            if (record.getUsername().equals(username))
            {
                hasRentedCars = true;

                break;
            }
        }
        if (!hasRentedCars)
        {
            writeData.println("\nYou have not rented any cars...");

            return;
        }
        //To display user's rented cars
        viewRentedCars(writeData);

        //Select which car to return based on Rental Id

        writeData.println("\nEnter Rental Id of the car you want to return : ");

        writeData.flush();

        var rentalId = readData.readLine();

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
            writeData.println("Invalid Rental ID . Please try again");

            return;
        }

        //Need to update Car status also

        CarDetails rentedCar = findCarById(selectedRental.getCarId());

        if (rentedCar!=null)
        {
            rentedCar.setAvailable(true);

            writeData.println("Car returned successfully");

            //Removing from rental records also

            rentalRecords.remove(selectedRental);

            writeData.println("Rental Id : " + selectedRental.getRentalId() + " has been successfully returned");

            writeData.flush();
        }
        else
        {
            writeData.println("Unable to find the car associated with this rental.");
        }
    }

    public synchronized void viewRentedCars(PrintWriter writeData)
    {
        var username = customer.getUsername();

        writeData.println("\n=========== Rented Cars by : " + username + " ===========");

        writeData.flush();

        boolean hasRentedCars = false;

        for (RentalRecord record : rentalRecords)
        {
            if (record.getUsername().equals(username))
            {
                CarDetails car = findCarById(record.getCarId());

                if (car!=null)
                {
                    writeData.println("Rental Id :" + record.getRentalId());

                    writeData.println("Car Rented :" + car.getCarBrand() + " " + car.getCarModel());

                    writeData.println("Rental Duration :" + record.getRentalDuration());

                    writeData.println("Total Cost :" + record.getTotalCost());

                    writeData.println("-----------------------");

                    hasRentedCars = true;
                }

            }
        }
        if (!hasRentedCars)
        {
            writeData.println("\nYou have not rented any cars");
        }
        writeData.flush();
    }

    private CarDetails findCarById(String carId)
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

    @Override
    public void showMenu(PrintWriter writeData, BufferedReader readData)
    {
        int choice = 0;

        do
        {
            writeData.println("\n====== Customer Menu ======");

            writeData.println("1. Rent a Car");

            writeData.println("2. Return a Car");

            writeData.println("3. View Available Cars");

            writeData.println("4. View Rented Cars");

            writeData.println("5. Logout");

            writeData.println("Enter your choice: ");

            try
            {
                choice = Integer.parseInt(readData.readLine());

                switch (choice)
                {

                    case 1:
                        rentCar(writeData, readData);

                        break;

                    case 2:
                        returnCar(writeData, readData);

                        break;

                    case 3:
                        viewAvailableCars(writeData);

                        break;

                    case 4:
                        viewRentedCars(writeData);

                        break;

                    case 5:
                        writeData.println("Logging out.");

                        break;

                    default:
                        writeData.println("Invalid choice, please try again.");
                }
            }
            catch (Exception e)
            {
                writeData.println("Invalid data...Please enter a number between 1 to 5");
            }
        } while (choice!=5);
    }
}