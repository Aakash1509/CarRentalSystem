package customers;

import java.util.Scanner;

import system.CarRentalSystem;

public class CustomerMenu extends CarRentalSystem
{
    private Scanner scanner = new Scanner(System.in);

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
                /*
                case 1:
                    addCar();
                    break;

                case 2:
                    removeCar();
                    break;

                case 3:
                    updateCarDetails();
                    break;

                case 4:
                    viewAvailableCars();
                    break;

                case 5:
                    viewRentedCars();
                    break;


                 */
                case 6:
                    System.out.println("Logging out.");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");

            }

        } while (choice!=6);

    }
}
