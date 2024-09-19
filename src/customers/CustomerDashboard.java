package customers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import system.CarDetails;
import system.CarRentalSystem;

public class CustomerDashboard extends CarRentalSystem
{
    List<RentalRecord> rentalRecords = new ArrayList<>();

    private CustomerDetails customer;

    private Scanner scanner = new Scanner(System.in);

    public CustomerDashboard(CustomerDetails customer)
    {
        this.customer = customer;
    }

    public void rentCar()
    {
        viewAvailableCars();

        System.out.print("\nEnter Car Id to rent : ");

        String carId = scanner.nextLine();

        CarDetails selectedCar = null;

        //Find selected car

        for (CarDetails car : cars)
        {
            if (car.getCarId().equals(carId) && car.isAvailable())
            {
                selectedCar = car;

                break;
            }
        }

        if (selectedCar==null)
        {
            System.out.println("Car is not available or invalid car ID");
        } else
        {
            System.out.print("Enter rental duration (in days) : ");

            int rentalDuration = scanner.nextInt();

            //Total Cost

            double totalCost = rentalDuration * selectedCar.getBasePricePerDay();

            //Mark the car as rented

            selectedCar.setAvailable(false);

            //Create object of RentalRecord class

            RentalRecord record = new RentalRecord("R" + (rentalRecords.size() + 1), customer.getUsername(), selectedCar.getCarId(), rentalDuration, totalCost);

            //Adding rental record in arraylist

            rentalRecords.add(record);

            System.out.println("\nCar rented successfully...");

            //Displaying Car Details

            System.out.println("Rental Details : ");

            System.out.println("Car : " + selectedCar.getCarBrand() + " " + selectedCar.getCarModel());

            System.out.println("Rental Duration : " + rentalDuration + " days");

            System.out.println("Total Cost: $ " + totalCost);
        }
    }

    public void returnCar()
    {
        var username = customer.getUsername();

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
            System.out.println("\nYou have not rented any cars...");

            return;
        }

        //To display user's rented cars
        viewRentedCars();

        //Select which car to return based on Rental Id

        System.out.print("\nEnter Rental Id of the car you want to return : ");

        var rentalId = scanner.nextLine();

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
            System.out.println("Invalid Rental ID . Please try again");

            return;
        }

        //Need to update Car status also

        CarDetails rentedCar = findCarById(selectedRental.getCarId());

        if (rentedCar!=null)
        {
            rentedCar.setAvailable(true);

            System.out.println("Car returned successfully");

            //Removing from rental records also

            rentalRecords.remove(selectedRental);

            System.out.println("Rental Id : " + selectedRental.getRentalId() + " has been successfully returned");
        }
        else
        {
            System.out.println("Unable to find the car associated with this rental.");
        }


    }

    public void viewRentedCars()
    {
        var username = customer.getUsername();

        System.out.println("\n=========== Rented Cars by : " + username + " ===========");

        boolean hasRentedCars = false;

        for (RentalRecord record : rentalRecords)
        {
            if (record.getUsername().equals(username))
            {
                CarDetails car = findCarById(record.getCarId());

                if (car!=null)
                {
                    System.out.println("Rental Id : " + record.getRentalId());

                    System.out.println("Car Rented : " + car.getCarBrand() + " " + car.getCarModel());

                    System.out.println("Rental Duration : " + record.getRentalDuration());

                    System.out.println("Total Cost : " + record.getTotalCost());

                    System.out.println("-----------------------");

                    hasRentedCars = true;
                }

            }
        }

        if (!hasRentedCars)
        {
            System.out.println("\nYou have not rented any cars");
        }
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
    public void showMenu()
    {
        int choice;

        do
        {
            System.out.println("\n====== Customer Menu ======");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. View Available Cars");
            System.out.println("4. View Rented Cars");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice)
            {

                case 1:
                    rentCar();

                    break;

                case 2:
                    returnCar();

                    break;

                case 3:
                    viewAvailableCars();

                    break;

                case 4:
                    viewRentedCars();

                    break;

                case 5:
                    System.out.println("Logging out.");

                    break;

                default:
                    System.out.println("Invalid choice, please try again.");

            }

        } while (choice!=5);

    }


}