package customers;

import java.io.BufferedReader;

import java.io.PrintWriter;

import java.util.ArrayList;

import java.util.List;

import system.CarRentalSystem;

public class CustomerDashboard extends CarRentalSystem
{
    //To keep rental Id thread safe
    private final List<RentalRecord> rentalRecords = new ArrayList<>();

    private final Customer customer;

    private final CustomerService customerService;

    public CustomerDashboard(Customer customer)
    {
        this.customer = customer;

        this.customerService = new CustomerService(customer);
    }

    //Instead of making whole methods synchronized I have used synchronized block so that I/O can be done

    public void rentCar(PrintWriter writeData, BufferedReader readData) throws Exception
    {
        viewAvailableCars(writeData);

        writeData.println("\nEnter Car Id to rent: ");

        writeData.flush();

        var carId = readData.readLine();

        writeData.println("Enter rental duration (in days): ");

        writeData.flush(); // Ensure the prompt is sent immediately

        try
        {
            var rentalDuration = Integer.parseInt(readData.readLine());

            RentalRecord record;

            synchronized (cars)
            {
                record = customerService.rentCarProcess(carId,rentalDuration,cars);

                // Adding rental record in ArrayList
                rentalRecords.add(record);
            }

            writeData.println("\nCar rented successfully...");

            writeData.println("Rental Details :");

            writeData.println("Car: " + record.getCarBrand() + " " + record.getCarModel());

            writeData.println("Rental Duration: " + rentalDuration + " days");

            writeData.println("Total Cost: $ " + record.getTotalCost());
        }
        catch (NumberFormatException e)
        {
            writeData.println("Invalid input for rental duration. Please enter a valid number.");
        }
        writeData.flush(); // Ensure all output is sent to the client

    }

    public void returnCar(PrintWriter writeData, BufferedReader readData) throws Exception
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
            writeData.println("\nYou have not rented any cars");

            return;
        }

        //Select which car to return based on Rental Id
        viewRentedCars(writeData);

        writeData.println("\nEnter Rental Id of the car you want to return : ");

        writeData.flush();

        var rentalId = readData.readLine();

        var result = customerService.returnCarProcess(cars,rentalId,username,rentalRecords);

        writeData.println(result);

        writeData.flush();

    }

    public void viewRentedCars(PrintWriter writeData)
    {
        var username = customer.getUsername();

        writeData.println("\n=========== Rented Cars by : " + username + " ===========");

        writeData.flush();

        boolean hasRentedCars = false;

        for (RentalRecord record : rentalRecords)
        {
            if (record.getUsername().equals(username))
            {

                writeData.println("Rental Id :" + record.getRentalId());

                writeData.println("Car Rented :" + record.getCarBrand() + " " + record.getCarModel());

                writeData.println("Rental Duration :" + record.getRentalDuration());

                writeData.println("Total Cost :" + record.getTotalCost());

                writeData.println("-----------------------");

                hasRentedCars = true;

            }
        }
        if (!hasRentedCars)
        {
            writeData.println("\nYou have not rented any cars");
        }
        writeData.flush();
    }

    @Override
    public void showDashboard(PrintWriter writeData, BufferedReader readData)
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
            catch (NumberFormatException e)
            {
                writeData.println("Invalid data...Please enter a number between 1 to 5");
            }
            catch (Exception e)
            {
                writeData.println("An Error occurred " + e.getMessage());
            }
        } while (choice!=5);
    }
}