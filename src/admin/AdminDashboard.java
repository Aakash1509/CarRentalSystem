package admin;

import java.io.BufferedReader;

import java.io.PrintWriter;

import system.CarRentalSystem;

import system.CarDetails;

public class AdminDashboard extends CarRentalSystem
{
    private final AdminService adminService;

    public AdminDashboard()
    {
        adminService = new AdminService();
    }

    //Instead of making whole methods synchronized I have used synchronized block so that I/O can be done

    // Need to use synchronized block , as I can't modify arraylist while other thread is modifying it or doing operations on it

    public void addCar(PrintWriter writeData, BufferedReader readData) throws Exception
    {
        writeData.println("\nFill up following details to add a car\nEnter Car ID: ");

        writeData.flush();

        var carId = readData.readLine();

        synchronized (cars)
        {
            if(adminService.carExists(carId,cars))
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

            adminService.addCarProcess(cars,carId,carBrand,carModel,basePrice);

            writeData.println("\nCar was successfully added");
        }

        writeData.flush();
    }

    //Synchronization required on arraylist as if client is trying to rent a car , admin can't remove it simultaneously

    public void removeCar(PrintWriter writeData, BufferedReader readData) throws Exception
    {
        writeData.println("\nEnter the Id of the car you want to remove :\nEnter Car ID: ");

        writeData.flush();

        var carId = readData.readLine();

        var result = adminService.removeCarProcess(cars,carId);

        writeData.println(result);

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

    //Synchronization required if client is renting a car , admin tries to update car details which is not rented can throw ConcurrentModificationException

    public void updateCarDetails(PrintWriter writeData, BufferedReader readData) throws Exception
    {
        writeData.println("\nEnter the ID of the car you want to update :\nCar ID: ");

        writeData.flush();

        var toUpdateCarId = readData.readLine();

        CarDetails car = null;

        car = adminService.getCarById(toUpdateCarId,cars);

        if(car == null)
        {
            writeData.println("Car not found with ID : "+toUpdateCarId);

            return;
        }
        if(!car.isAvailable())
        {
            writeData.println("Cannot update car details with ID "+ car.getCarId() + " because car is currently rented");

            return;
        }

        //Displaying current car details

        writeData.println("Current details of the car:");

        writeData.println("Car Brand:" + car.getCarBrand());

        writeData.println("Car Model:" + car.getCarModel());

        writeData.println("Car Rent per day: $" + car.getBasePricePerDay());

        writeData.flush();

        // Take new details as input

        writeData.println("\nEnter new details (leave blank to keep current value):");

        writeData.println("New Car Brand : ");

        writeData.flush();

        var newCarBrand = readData.readLine();

        writeData.println("New Car Model : ");

        writeData.flush();

        var newCarModel = readData.readLine();

        writeData.println("New base price per day : ");

        writeData.flush();

        var newRentPrice = readData.readLine();

        String result = "";

        synchronized (cars)
        {
           result = adminService.updateCarProcess(car,newCarBrand,newCarModel,newRentPrice);
        }

        writeData.println(result);

        writeData.flush();
    }

    @Override

    public void showDashboard(PrintWriter writeData, BufferedReader readData)
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
            catch (NumberFormatException e)
            {
                writeData.println("Invalid data...Please enter a number between 1 to 6");
            }
            catch (Exception e)
            {
                writeData.println("An Error occurred " + e.getMessage());
            }
        } while (choice!=6);
    }
}

