package server;

import admin.Administrator;

import customers.Customer;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.io.PrintWriter;

import java.net.Socket;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import java.util.UUID;

import customers.Database;

import system.CarRentalSystem;

public class ClientHandler implements Runnable
{
    //for client
    private final Socket clientSocket;

    //To read data
    private BufferedReader readData;

    //To write data
    private PrintWriter writeData;
    
    //Hashmap which stores token and Customer object as key value pair
    private static final Map<String, Customer> tokenStorage = new HashMap<>();

    private final CustomerService customerService;

    private final AdminService adminService;

    public ClientHandler(Socket clientSocket)
    {
        this.clientSocket = clientSocket;

        this.customerService = new CustomerService();

        this.adminService = new AdminService();
    }

    public void run()
    {
        try
        {
            readData = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            writeData = new PrintWriter(clientSocket.getOutputStream(), true); //If we pass true , auto flush is enabled

            String clientMessage;

            while((clientMessage = readData.readLine())!=null)
            {
                try
                {
                    //Splitting client message
                    String[] parts = clientMessage.split(" ");

                    String command = parts[0];

                    var username = "";

                    var register = true;

                    var login = true;

                    var token = "";

                    var response = "";

                    switch (command)
                    {
                        case "REGISTER_ADMIN":
                            try
                            {
                                register = Administrator.registerAdmin(parts[1],parts[2]);

                                writeData.println(register ? "true" : "false");
                            }
                            catch (Exception e)
                            {
                                writeData.println("Fields cannot be empty. Please try again.");
                            }

                            break;

                        case "REGISTER_CUSTOMER":
                            try
                            {
                                //return the result to the client
                                register = Database.registerCustomer(parts[1], parts[2], parts[3]);

                                writeData.println(register ? "true" : "false");
                            }
                            catch (Exception e)
                            {
                                writeData.println("Fields cannot be empty. Please try again.");
                            }

                            break;

                        case "CUSTOMER_EXIST":
                            try
                            {
                                register = Database.exist(parts[1]);

                                writeData.println(register ? "true" : "false");
                            }
                            catch (Exception e)
                            {
                                writeData.println("Fields cannot be empty. Please try again.");
                            }
                            break;

                        case "LOGIN_ADMIN":
                            try
                            {
                                login = Administrator.loginAdmin(parts[1],parts[2]);

                                if(login)
                                {
                                    token = generateToken();

                                    tokenStorage.put(token,null);

                                    writeData.println("LOGIN_SUCCESS token:"+token);
                                }
                                else
                                {
                                    writeData.println("LOGIN_FAILED");
                                }
                            }
                            catch (Exception e)
                            {
                                writeData.println("Fields cannot be empty. Please try again.");
                            }

                            break;

                        case "LOGIN_CUSTOMER":
                            try
                            {
                                Customer customer = Database.loginCustomer(parts[1],parts[2]);

                                if(customer!=null)
                                {
                                    token = generateToken();

                                    tokenStorage.put(token,customer);

                                    writeData.println("LOGIN_SUCCESS token:"+token);
                                }
                                else
                                {
                                    writeData.println("LOGIN_FAILED");
                                }
                            }
                            catch (Exception e)
                            {
                                writeData.println("Fields cannot be empty. Please try again.");
                            }

                            break;

                        case "GET_CUSTOMER_DETAILS":
                            Customer customer1 = tokenStorage.get(parts[1]);

                            if(customer1 != null)
                            {
                                response = customer1.getUsername() + " " + customer1.getPassword() + " " + customer1.getDrivingLicenseNumber();

                                writeData.println(response);
                            }
                            else
                            {
                                writeData.println("INVALID_TOKEN");
                            }
                            break;

                        case "VIEW_AVAILABLE_CARS":
                            try
                            {
                                response = CarRentalSystem.viewAvailableCars();

                                writeData.println(response);

                                writeData.println("END_OF_LIST");
                            }
                            catch (Exception e)
                            {
                                writeData.println("An error occurred : "+e.getMessage());
                            }
                            break;

                        case "RENT_CAR":
                            try
                            {
                                try
                                {
                                    RentalRecord record = customerService.rentCarProcess(parts[2],Integer.parseInt(parts[3]),tokenStorage.get(parts[1]).getUsername());

                                    if(record != null)
                                    {
                                        writeData.println("RENTAL_SUCCESS : "+"Rental ID : "+record.getRentalId()+ ", Car Brand : "+record.getCarBrand()+", Car Model : "+record.getCarModel()+", Rental Duration : "+record.getRentalDuration()+", Total Cost : "+record.getTotalCost()+" $");
                                    }
                                    else
                                    {
                                        writeData.println("Car is unavailable or invalid Car ID. Please try again");
                                    }
                                }
                                catch (Exception e)
                                {
                                    writeData.println("RENTAL_FAILED: "+e.getMessage());
                                }
                            }
                            catch (Exception e)
                            {
                                writeData.println("Fields cannot be empty. Please try again.");
                            }
                            break;

                        case "VIEW_RENTED_CARS":
                            try
                            {
                                username = tokenStorage.get(parts[1]).getUsername();

                                List<RentalRecord> recordList = customerService.rentedCars(username);

                                if(recordList.isEmpty())
                                {
                                    writeData.println("You have not rented any cars");

                                    writeData.println("END_OF_LIST");
                                }
                                else
                                {
                                    for(RentalRecord record : recordList)
                                    {
                                        writeData.println("Rental ID: "+record.getRentalId()+", Car Rented: "+record.getCarBrand()+" "+record.getCarModel()+", Rental Duration: "+record.getRentalDuration()+ ", Total Cost: "+record.getTotalCost()+" $");
                                    }
                                    writeData.println("END_OF_LIST");
                                }
                            }
                            catch (Exception e)
                            {
                                writeData.println("An error occurred : "+e.getMessage());
                            }

                            break;

                        case "VIEW_RENTED_CARS_ADMIN":
                            try
                            {
                                var recordList = adminService.rentedCars();

                                if(recordList.isEmpty())
                                {
                                    writeData.println("There are not rented any cars");

                                    writeData.println("END_OF_LIST");
                                }
                                else
                                {
                                    for(String record : recordList)
                                    {
                                        writeData.println(record);
                                    }
                                    writeData.println("END_OF_LIST");
                                }
                            }
                            catch (Exception e)
                            {
                                writeData.println("An error occurred : "+e.getMessage());
                            }

                            break;

                        case "RETURN_CAR":
                            try
                            {
                                username = tokenStorage.get(parts[1]).getUsername();

                                response = customerService.returnCarProcess(parts[2],username);

                                writeData.println(response);
                            }
                            catch (Exception e)
                            {
                                writeData.println("Fields cannot be empty. Please try again.");
                            }
                            break;

                        case "ADD_CAR":
                            try
                            {
                                if(CarRentalSystem.carExists(parts[2]))
                                {
                                    writeData.println("Car with this ID already exists. Please try again with another ID");
                                }
                                else
                                {
                                    adminService.addCarProcess(parts[2],parts[3],parts[4],Double.parseDouble(parts[5]));

                                    writeData.println("Car was successfully added");
                                }
                            }
                            catch (Exception e)
                            {
                                writeData.println("Fields cannot be empty. Please try again.");
                            }

                            break;

                        case "REMOVE_CAR":
                            try
                            {
                                var result = adminService.removeCarProcess(parts[2]);

                                writeData.println(result);
                            }
                            catch (Exception e)
                            {
                                writeData.println("Fields cannot be empty. Please try again.");
                            }
                            break;

                        case "CAR_EXIST":
                            try
                            {
                                var result = CarRentalSystem.carExists(parts[2]);

                                writeData.println(result ? "true" : "false");
                            }
                            catch (Exception e)
                            {
                                writeData.println("Fields cannot be empty. Please try again.");
                            }

                            break;

                        case "CAR_AVAILABLE":
                            try
                            {
                                var result = CarRentalSystem.isCarAvailable(parts[2]);

                                if(result == null)
                                {
                                    writeData.println("false");
                                }
                                else
                                {
                                    writeData.println("Current Details : "+"Car Brand : "+result.getCarBrand()+" Car Model : "+result.getCarModel()+" Base Price : "+result.getBasePricePerDay()+" $");
                                }
                            }
                            catch (Exception e)
                            {
                                writeData.println("Fields cannot be empty. Please try again.");
                            }

                            break;

                        case "UPDATE_CAR":
                            try
                            {
                                response = adminService.updateCarProcess(parts[2],parts[3],parts[4],parts[5]);

                                writeData.println(response);

                            }
                            catch (Exception e)
                            {
                                writeData.println("Fields cannot be empty. Please try again.");
                            }

                            break;

                        case "LOGOUT":
                            try
                            {
                                if(tokenStorage.containsKey(parts[1]))
                                {
                                    tokenStorage.remove(parts[1]);

                                    writeData.println("LOGGED OUT SUCCESSFULLY");
                                }
                            }
                            catch (Exception e)
                            {
                                writeData.println("An error occurred : "+e.getMessage());
                            }

                        default:

                            writeData.println("ERROR. No such command "+command);
                    }
                }
                catch (Exception e)
                {
                    System.out.println("An exception occurred here : "+e.getMessage());
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error occurred : "+e.getMessage());
        }
        finally
        {
            closeConnection();
        }
    }
    
    //Function to generate token
    private String generateToken()
    {
        return UUID.randomUUID().toString();
    }

    private void closeConnection()
    {
        try
        {
            if(readData!=null){readData.close();}

            if(writeData!=null){writeData.close();}

            if(clientSocket!=null){clientSocket.close();}
        }
        catch (Exception e)
        {
            System.out.println("Error while closing resources : "+e.getMessage());
        }
        finally
        {
            System.out.println("Connection closed");
        }
    }
}
