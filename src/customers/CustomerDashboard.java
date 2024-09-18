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

            rentalRecords.add(record);

            System.out.println("\nCar rented successfully...");

            //Displaying Car Details

            System.out.println("Rental Details : ");

            System.out.println("Car : " + selectedCar.getCarBrand() + " " + selectedCar.getCarModel());

            System.out.println("Rental Duration : " + rentalDuration + " days");

            System.out.println("Total Cost: $" + totalCost);


        }
    }

    public void returnCar()
    {

    }

    @Override
    public void showMenu()
    {
        int choice;
        /*
        CarDetails car1 = new CarDetails("C001","Ford","Mustang",1500);
        CarDetails car2 = new CarDetails("C002","Mahindra","Thar",2000);
        CarDetails car3 = new CarDetails("C003","Tata","Nexon",1500);

         */
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
                    /*
                case 4:
                    viewRentedCars();
                    break;

                     */

                case 5:
                    System.out.println("Logging out.");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");

            }

        } while (choice!=5);

    }
}
