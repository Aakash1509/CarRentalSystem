package admin;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.PrintWriter;

import java.util.*;

import system.CarRentalSystem;

import system.CarDetails;

public class AdminDashboard extends CarRentalSystem
{
    public synchronized void addCar(PrintWriter writeData, BufferedReader readData) throws IOException
    {
        writeData.println("\nFill up following details to add a car\nEnter Car ID: ");

        writeData.flush();

        var carId = readData.readLine();

        //Check if carId already exists

        boolean carExists = false;

        for(CarDetails car : cars)
        {
            if(car.getCarId().equals(carId))
            {
                carExists = true;

                break;
            }
        }

        if(carExists)
        {
            writeData.println("\nCar with this ID already exists. Please try again with another ID");

            writeData.flush();

            return;
        }

        writeData.println("Brand: ");

        writeData.flush();

        var carBrand = readData.readLine();

        writeData.println("Model: ");

        writeData.flush();

        var carModel = readData.readLine();

        writeData.println("Base Price Per Day: ");

        writeData.flush();

        var basePricePerDay = readData.readLine();

        if(carId.isEmpty() || carBrand.isEmpty() || carModel.isEmpty() || basePricePerDay.isEmpty())
        {
            writeData.println("\nPlease fill up all the required details to add the car..");

            return;
        }

        // Validate base price is a number
        double basePrice;

        try
        {
            basePrice = Double.parseDouble(basePricePerDay);
        }
        catch (NumberFormatException e)
        {
            writeData.println("\nInvalid input for Base Price Per Day. Please enter a valid number.");

            writeData.flush();
            return;
        }

        CarDetails car = new CarDetails(carId, carBrand, carModel, basePrice);

        cars.add(car);

        writeData.println("\nCar was successfully added");

        writeData.flush();
    }

    public synchronized void removeCar(PrintWriter writeData, BufferedReader readData) throws IOException
    {
        writeData.println("\nEnter the Id of the car you want to remove :\nEnter Car ID: ");

        writeData.flush();

        var carId = readData.readLine();

        //Cannot use forEach loop because it will throw ConcurrentModificationException , so need to use iterator

        Iterator<CarDetails> iterator = cars.iterator(); // Get the iterator

        boolean carRemoved = false;

        while (iterator.hasNext())
        {
            CarDetails car = iterator.next(); // Get the next car

            // Check if the car is available and matches the ID

            if (Objects.equals(car.getCarId(), carId))
            {
                if(!car.isAvailable())
                {
                    writeData.println("Cannot remove car with ID "+ carId + " because car is currently rented");

                    carRemoved = true;

                    break;
                }
                iterator.remove(); // Use iterator's remove method to safely remove the car

                writeData.println("\nCar with ID " + carId + " removed successfully.");

                carRemoved = true;

                break;
            }
        }
        if (!carRemoved)
        {
            writeData.println("No available car found with ID: " + carId);
        }
        writeData.flush();
    }

    public void viewRentedCars(PrintWriter writeData)
    {
        writeData.println("\n============= Rented Cars are ==============");

        writeData.flush();

        boolean flag = false;

        for (CarDetails car : cars)
        {
            if (!car.isAvailable())
            {
                flag = true;

                writeData.println(car.getCarId() + " - " + car.getCarBrand() + " " + car.getCarModel() + " is rented by "+ car.getRentedBy());
            }
        }
        if (!flag)
        {
            writeData.println("\nThere are no rented cars");
        }
        writeData.flush();
    }

    public synchronized void updateCarDetails(PrintWriter writeData, BufferedReader readData) throws IOException
    {
        writeData.println("\nEnter the ID of the car you want to update :\nCar ID: ");

        writeData.flush();

        var toUpdateCarId = readData.readLine();

        boolean carFound = false;

        for (CarDetails car : cars)
        {
            if (Objects.equals(car.getCarId(), toUpdateCarId))
            {
                if(!car.isAvailable())
                {
                    writeData.println("Cannot update car details with ID "+ toUpdateCarId + " because car is currently rented");

                    carFound = true;

                    break;
                }
                carFound = true;

                //Displaying current car details

                writeData.println("Current details of the car:");

                writeData.println("Car Brand:" + car.getCarBrand());

                writeData.println("Car Model:" + car.getCarModel());

                writeData.println("Car Rent per day:" + car.getBasePricePerDay());

                // Take new details as input

                writeData.println("\nEnter new details (leave blank to keep current value):");

                writeData.println("New Car Brand : ");

                writeData.flush();

                var newCarBrand = readData.readLine();

                if (!newCarBrand.isEmpty())
                {
                    car.setCarBrand(newCarBrand);
                }
                writeData.println("New Car Model : ");

                writeData.flush();

                var newCarModel = readData.readLine();

                if (!newCarModel.isEmpty())
                {
                    car.setCarModel(newCarModel);
                }
                writeData.println("New base price per day : ");

                writeData.flush();

                var newRentPrice = readData.readLine();

                if (!newRentPrice.isEmpty())
                {
                    try
                    {
                        var newBasePrice = Double.parseDouble(newRentPrice);

                        car.setBasePricePerDay(newBasePrice);
                    }
                    catch (NumberFormatException e)
                    {
                        writeData.println("Invalid Rent value. Keeping current rent");
                    }
                }
                writeData.println("Car details updated successfully");

                break;
            }
        }
        if (!carFound)
        {
            writeData.println("Car not found with ID: " + toUpdateCarId);
        }
        writeData.flush();
    }

    @Override

    public void showMenu(PrintWriter writeData, BufferedReader readData)
    {
        int choice = 0;

        do
        {
            writeData.println("\n====== Admin Menu ======");

            writeData.println("1. Add Car");

            writeData.println("2. Remove Car");

            writeData.println("3. Update Car Details");

            writeData.println("4. View Available Cars");

            writeData.println("5. View Rented Cars");

            writeData.println("6. Logout");

            writeData.println("Enter your choice: ");

            writeData.flush();

            try{
                choice = Integer.parseInt(readData.readLine());

                switch (choice)
                {
                    case 1:
                        addCar(writeData, readData);

                        break;

                    case 2:
                        removeCar(writeData, readData);

                        break;

                    case 3:
                        updateCarDetails(writeData, readData);

                        break;

                    case 4:
                        viewAvailableCars(writeData);

                        break;

                    case 5:
                        viewRentedCars(writeData);

                        break;

                    case 6:
                        writeData.println("Logging out.");

                        writeData.flush();

                        break;

                    default:
                        writeData.println("Invalid choice, please try again.");

                        writeData.flush();
                }
            }
            catch (Exception e)
            {
                writeData.println("Invalid data...Please enter a number between 1 to 6");

                writeData.flush();
            }
        } while (choice!=6);
    }
}

