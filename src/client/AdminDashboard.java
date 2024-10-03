package client;

public class AdminDashboard extends Client
{
    String token;

    public AdminDashboard(String token)
    {
        this.token = token;
    }

    public void addCar()
    {
        try
        {
            System.out.println("\nFill up following details to add a car\nEnter Car ID: ");

            var carId = scanner.nextLine();

            System.out.println("Brand: ");

            var carBrand = scanner.nextLine();

            System.out.println("Model: ");

            var carModel = scanner.nextLine();

            System.out.println("Base Price Per Day: ");

            var basePricePerDay = scanner.nextLine();

            if(carId.isEmpty() || carBrand.isEmpty() || carModel.isEmpty() || basePricePerDay.isEmpty())
            {
                System.out.println("\nPlease fill up all the required details to add the car..");

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
                System.out.println("\nInvalid input for Base Price Per Day. Please enter a valid number.");

                return;
            }
            initializeConnection();

            serverOutput.println("ADD_CAR "+token+" "+carId+" "+carBrand+" "+carModel+" "+basePrice);

            System.out.println(serverInput.readLine());
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

    public void removeCar()
    {
        try
        {
            System.out.println("\nEnter the Id of the car you want to remove :\nEnter Car ID: ");

            var carId = scanner.nextLine();

            initializeConnection();

            serverOutput.println("REMOVE_CAR "+token+" "+carId);

            System.out.println(serverInput.readLine());
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

    public void updateCar()
    {
        var response = "";

        try
        {
            System.out.println("\nEnter the ID of the car you want to update :\nCar ID: ");

            var carID = scanner.nextLine();

            if(carID.isEmpty())
            {
                System.out.println("Fields cannot be empty");

                return;
            }

            initializeConnection();

            serverOutput.println("CAR_AVAILABLE "+token+" "+carID);

            response = serverInput.readLine();

            if("false".equalsIgnoreCase(response))
            {
                System.out.println("Currently car is rented or Car with ID not found , can't update the details.");

                return;
            }
            else
            {
                System.out.println(response);

                closeConnection();
            }

            System.out.println("\nEnter new details (leave blank to keep current value):");

            System.out.println("New Car Brand : ");

            var newCarBrand = scanner.nextLine();

            if (newCarBrand.isEmpty())
            {
                newCarBrand = "null";
            }

            System.out.println("New Car Model : ");

            var newCarModel = scanner.nextLine();

            if (newCarModel.isEmpty())
            {
                newCarModel = "null";
            }

            System.out.println("New base price per day : ");

            var newRentPrice = scanner.nextLine();

            if (newRentPrice.isEmpty())
            {
                newRentPrice = "null";
            }

            initializeConnection();

            serverOutput.println("UPDATE_CAR "+token+" "+carID+" "+newCarBrand+" "+newCarModel+" "+newRentPrice);

            System.out.println(serverInput.readLine());
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

            serverOutput.println("VIEW_RENTED_CARS_ADMIN "+token);

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

    public void showDashboard()
    {
        try
        {
            int choice = 0;

            do
            {
                System.out.println("\n====== Admin Menu ======");

                System.out.println("1. Add a Car");

                System.out.println("2. Remove a Car");

                System.out.println("3. Update Car Details");

                System.out.println("4. View Available Cars");

                System.out.println("5. View Rented Cars");

                System.out.println("6. Logout");

                System.out.println("Enter your choice: ");

                try
                {
                    choice = Integer.parseInt(scanner.nextLine());

                    switch (choice)
                    {

                        case 1:
                            addCar();

                            break;

                        case 2:
                            removeCar();

                            break;

                        case 3:
                            updateCar();

                            break;

                        case 4:

                            viewAvailableCars();

                            break;

                        case 5:
                            viewRentedCars();

                            break;

                        case 6:
                            try
                            {
                                initializeConnection();

                                serverOutput.println("LOGOUT "+token); //Need to remove token after logging out

                                System.out.println(serverInput.readLine());
                            }
                            catch (Exception e)
                            {
                                System.out.println("An error occurred : "+e.getMessage());
                            }
                            finally
                            {
                                closeConnection();
                            }

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
            } while (choice!=6);
        }
        catch (Exception e)
        {
            System.out.println("An error occurred : "+e.getMessage());
        }
    }
}
