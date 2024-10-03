package client;

import system.CarRentalSystem;

public class CustomerDashboard extends Client
{
    String token;

    public CustomerDashboard(String token)
    {
        this.token = token;
    }

    public void rentCar()
    {
        try
        {
            viewAvailableCars();

            System.out.println("\nEnter Car Id to rent: ");

            var carId = scanner.nextLine();

            System.out.println("Enter rental duration (in days): ");

            try
            {
//                var rentalDuration = Integer.parseInt(scanner.nextLine());
                var rentalDuration = scanner.nextLine();

                if(carId.isEmpty() || rentalDuration.isEmpty())
                {
                    System.out.println("Fields cannot be empty. Please try again.");

                    return;
                }
                rentalDuration = String.valueOf(Integer.parseInt(rentalDuration));

                initializeConnection();

                serverOutput.println("RENT_CAR "+token+" "+carId+" "+rentalDuration);

                System.out.println(serverInput.readLine());
            }
            catch (Exception e)
            {
                System.out.println("Invalid input for rental duration. Please enter a valid input");
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid input for rental duration. Please enter a valid number.");
        }
        finally
        {
            if(!clientSocket.isClosed())
            {
                closeConnection();
            }
        }
    }

    public void returnCar()
    {
        try
        {
            System.out.println("\n=========== Rented Cars ===========");

            initializeConnection();

            serverOutput.println("VIEW_RENTED_CARS " + token);

            // Use the return value to check if there are any rented cars
            if (!processRent())
            {
                return;  // Exit early if no cars are rented
            }

            closeConnection();

            System.out.println("\nEnter the Rental ID of the car you want to return: ");

            var rentalID = scanner.nextLine();

            // Handle the return process
            initializeConnection();

            serverOutput.println("RETURN_CAR " + token + " " + rentalID);

            System.out.println(serverInput.readLine());
        }
        catch (Exception e)
        {
            System.out.println("An error occurred : "+e.getMessage());
        }
        finally
        {
            if(!clientSocket.isClosed())
            {
                closeConnection();
            }
        }
    }

    public void viewAvailableCars()
    {
        try
        {
            initializeConnection();

            serverOutput.println("VIEW_AVAILABLE_CARS "+token);

            String response;

            while((response = serverInput.readLine())!=null)
            {
                if("END_OF_LIST".equals(response))
                {
                    break;
                }
                System.out.println(response);
            }
        }
        catch (Exception e)
        {
            System.out.println("An error occurred : "+e.getMessage());
        }
        finally
        {
            closeConnection();
        }
    }

    public void viewRentedCars()
    {
        try
        {
            System.out.println("\n=========== Rented Cars ===========");

            initializeConnection();

            serverOutput.println("VIEW_RENTED_CARS " + token);

            processRent();
        }
        catch (Exception e)
        {
            System.out.println("An error occurred: " + e.getMessage());
        }
        finally
        {
            closeConnection();
        }
    }

    public boolean processRent()
    {
        String response;

        boolean hasRentedCars = false;

        try
        {
            while((response = serverInput.readLine())!=null)
            {
                if("You have not rented any cars".equals(response))
                {
                    System.out.println(response);

                    return false;
                }
                if("END_OF_LIST".equals(response))
                {
                    break;
                }
                System.out.println(response);

                hasRentedCars = true;
            }
        }
        catch (Exception e)
        {
            System.out.println("An error occurred : "+e.getMessage());
        }
        return hasRentedCars;
    }


    public void showCustomerDashboard()
    {
        try
        {
            int choice = 0;

            do
            {
                System.out.println("\n====== Customer Menu ======");

                System.out.println("1. Rent a Car");

                System.out.println("2. Return a Car");

                System.out.println("3. View Available Cars");

                System.out.println("4. View Rented Cars");

                System.out.println("5. Logout");

                System.out.println("Enter your choice: ");

                try
                {
                    choice = Integer.parseInt(scanner.nextLine());

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
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Invalid data...Please enter a number between 1 to 5");
                }
                catch (Exception e)
                {
                    System.out.println("An error occurred " + e.getMessage());
                }
            } while (choice!=5);
        }
        catch (Exception e)
        {
            System.out.println("An error occurred : "+e.getMessage());
        }
    }
}
