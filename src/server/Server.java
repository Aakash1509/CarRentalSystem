package server;

import admin.Administrator;
import customers.CustomerDetails;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//This will be replaced with Main.java
public class Server
{
    ServerSocket serverSocket; //for server

    Socket clientSocket; //for client

    //To read data
    BufferedReader readData;

    //To write data
    PrintWriter writeData;

    //For Admin
    Administrator adminReg = Administrator.getInstance();

    //For Customer
    CustomerDetails customerReg;

    public Server() throws IOException
    {
        serverSocket = new ServerSocket(9999);

        System.out.println("Server is ready to accept connection");

        System.out.println("Waiting");

        clientSocket = serverSocket.accept();

        System.out.println("Client connected");

        readData = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        writeData = new PrintWriter(clientSocket.getOutputStream(), true); //If we pass true , auto flush is enabled

        listenClientRequests();
    }

    private void listenClientRequests() throws IOException
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

                handleClientRequest(choice);
            }
            catch (Exception e)
            {
                writeData.println("Invalid data...Please enter a number between 1 to 5");
            }
        } while (choice!=5);

        //Close connection when choice is 5

        closeConnection();
    }

    private void handleClientRequest(int choice) throws IOException
    {
        switch (choice)
        {
            case 1:
                // Admin Registration
                writeData.println("Enter Admin Details:\nUsername: ");

                writeData.flush();

//                writeData.println("Username: ");
//
//                writeData.flush();

                var adminUsername = readData.readLine();

                writeData.println("Password: ");

                writeData.flush();

                var adminPassword = readData.readLine();

                if (!adminUsername.isEmpty() && !adminPassword.isEmpty())
                {
                    adminReg = Administrator.getInstance();

                    adminReg.registerAdmin(adminUsername, adminPassword, writeData);
                }
                else
                {
                    writeData.println("Don't keep any field empty...Please register again");

                    writeData.flush();
                }
                break;

            case 2:
                // Guest Registration
                writeData.println("Enter Customer Details:\nUsername: ");

                writeData.flush();

                var customerUsername = readData.readLine();

                writeData.println("Password: ");

                writeData.flush();

                var customerPassword = readData.readLine();

                writeData.println("Driver license number: ");

                writeData.flush();

                var drivingLicenseNumber = readData.readLine();

                if (!customerUsername.isEmpty() && !customerPassword.isEmpty() && !drivingLicenseNumber.isEmpty())
                {
                    customerReg = new CustomerDetails(customerUsername, customerPassword, drivingLicenseNumber);

                    customerReg.registerCustomer(customerUsername, customerPassword, writeData);
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

                var adminLoginUsername = readData.readLine();

                writeData.println("Admin Password: ");

                writeData.flush();

                var adminLoginPassword = readData.readLine();

                try
                {
                    if (!adminLoginUsername.isEmpty() && !adminLoginPassword.isEmpty())
                    {
                        adminReg.loginAdmin(adminLoginUsername, adminLoginPassword, writeData, readData);
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
                // Guest Login
                writeData.println("Customer Username: ");

                writeData.flush();

                var customerLoginUsername = readData.readLine();

                writeData.print("Customer Password: ");

                writeData.flush();

                var customerLoginPassword = readData.readLine();

                try
                {
                    if (!customerLoginUsername.isEmpty() && !customerLoginPassword.isEmpty())
                    {
                        customerReg.loginCustomer(customerLoginUsername, customerLoginPassword, writeData, readData);
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

    private void closeConnection() throws IOException
    {
        try
        {
            if(readData!=null){readData.close();}

            if(writeData!=null){writeData.close();}

            if(clientSocket!=null){clientSocket.close();}

            if(serverSocket!=null){serverSocket.close();}
        }
        catch (IOException e)
        {
            System.out.println("Error while closing resources : "+e.getMessage());
        }
        finally
        {
            System.out.println("Connection closed");
        }
    }

    public static void main(String[] args) throws IOException
    {
        System.out.println("This is server...Server is going to start");
        new Server();
    }
}
