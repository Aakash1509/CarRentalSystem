package server;

import admin.AdminDashboard;

import admin.Administrator;

import customers.Customer;

import customers.CustomerDashboard;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.io.PrintWriter;

import java.net.Socket;

public class ClientHandler implements Runnable
{
    //for client
    private final Socket clientSocket;

    //To read data
    private BufferedReader readData;

    //To write data
    private PrintWriter writeData;

    //For Admin
    Administrator admin;

    //For Customer
    Customer customer;

    public ClientHandler(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public void run()
    {
        try
        {
            readData = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            writeData = new PrintWriter(clientSocket.getOutputStream(), true); //If we pass true , auto flush is enabled

            listenClientRequests();
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

    private void listenClientRequests()
    {
        int choice = 0;

        writeData.println("====== Welcome to Car Rental System ======");

        do
        {
            writeData.println();

            writeData.println("1. Register Admin");

            writeData.println("2. Register Customer");

            writeData.println("3. Login Admin");

            writeData.println("4. Login Customer");

            writeData.println("5. Exit");

            writeData.println("Enter your choice: ");

            //writeData.flush(); //Bcoz print doesn't use automatic flush

            try
            {
                choice = Integer.parseInt(readData.readLine());

                try
                {
                    handleClientRequest(choice);
                }
                catch (Exception e)
                {
                    System.out.println("Error occurred : "+e.getMessage());
                }

            }
            catch (Exception e)
            {
                writeData.println("Invalid data...Please enter a number between 1 to 5");
            }
        } while (choice!=5);
    }

    private void handleClientRequest(int choice) throws Exception
    {
        var username = "";

        var password = "";

        var login = true;

        var register = true;

        switch (choice)
        {
            case 1:
                // Admin Registration

                writeData.println("Enter Admin Details:\nUsername: ");

                writeData.flush();

                username = readData.readLine();

                writeData.println("Password: ");

                writeData.flush();

                password = readData.readLine();

                if (!username.isEmpty() && !password.isEmpty())
                {
                        admin = Administrator.getInstance();

                        if(!admin.exist())
                        {
                            Administrator.registerAdmin(username, password);

                            writeData.println("Admin " + username + " registered successfully!");
                        }
                        else
                        {
                            writeData.println("An admin is already registered. Registration is not allowed");

                            writeData.flush();
                        }
                }
                else
                {
                    writeData.println("Don't keep any field empty...Please register again");

                    writeData.flush();
                }
                break;

            case 2:
                // Customer Registration

                writeData.println("Enter Customer Details:\nUsername: ");

                writeData.flush();

                username = readData.readLine();

                writeData.println("Password: ");

                writeData.flush();

                password = readData.readLine();

                writeData.println("Driver license number: ");

                writeData.flush();

                var drivingLicenseNumber = readData.readLine();

                if (!username.isEmpty() && !password.isEmpty() && !drivingLicenseNumber.isEmpty())
                {
                    customer = new Customer(username, password, drivingLicenseNumber);

                    register = customer.registerCustomer(username, password);

                    if(register)
                    {
                        writeData.println("Customer " + username + " registered successfully!");
                    }
                    else
                    {
                        writeData.println("This username already exists. Please register using other username");
                    }
                }
                else
                {
                    writeData.println("Don't keep any field empty...Please register again");
                }
                break;

            case 3:
                // Admin Login

                writeData.println("Admin Username: ");

                writeData.flush();

                username = readData.readLine();

                writeData.println("Admin Password: ");

                writeData.flush();

                password = readData.readLine();

                try
                {
                    if (!username.isEmpty() && !password.isEmpty())
                    {
                        login = admin.loginAdmin(username, password);

                        if(login)
                        {
                            writeData.println("Login successful! Welcome, " + username);

                            //After successful login , redirect to AdminDashboard

                            AdminDashboard adminDashboard = new AdminDashboard();

                            adminDashboard.showDashboard(writeData, readData);
                        }
                        else
                        {
                            writeData.println("Invalid username or password. Login again");
                        }
                    }
                    else
                    {
                        writeData.println("Don't keep any field empty...Please login again");
                    }
                }
                catch (Exception e)
                {
                    writeData.println("No username found, please register yourself first");
                }

                break;

            case 4:
                // Customer Login

                writeData.println("Customer Username: ");

                writeData.flush();

                username = readData.readLine();

                writeData.println("Customer Password: ");

                writeData.flush();

                password = readData.readLine();

                try
                {
                    if (!username.isEmpty() && !password.isEmpty())
                    {
                        login = customer.loginCustomer(username, password);

                        if(login)
                        {
                            writeData.println("Login successful! Welcome, " + username);

                            //After successful login , redirect to CustomerDashboard

                            CustomerDashboard customerDashboard = new CustomerDashboard(customer);

                            customerDashboard.showDashboard(writeData, readData);
                        }
                        else
                        {
                            writeData.println("Invalid username or password.");
                        }
                    }
                    else
                    {
                        writeData.println("Don't keep any field empty...Please login again");
                    }
                }
                catch (Exception e)
                {
                    writeData.println("No username found, please register yourself first");
                }

                break;

            case 5:
                writeData.println("Exiting the system.");

                writeData.println("exit");

                break;

            default:
                writeData.println("Invalid choice. Please try again.");
        }
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
