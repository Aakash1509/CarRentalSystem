package client;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.io.PrintWriter;

import java.net.Socket;

import java.util.Scanner;

public class Client
{
    protected Socket clientSocket;

    //To read data from server
    protected BufferedReader serverInput;

    //To write data to server
    protected PrintWriter serverOutput;

    //To read input from user
    protected Scanner scanner;

    private String token = null;

    public Client()
    {
        scanner = new Scanner(System.in);
    }

    public void startClient()
    {
        listenClientRequests();
    }

    protected void initializeConnection()
    {
        try
        {
                clientSocket = new Socket("localhost",9999);

                serverOutput = new PrintWriter(clientSocket.getOutputStream(),true);

                serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (Exception e)
        {
                System.out.println("An exception occurred : "+e.getMessage());
        }
    }

    private void listenClientRequests()
    {
        int choice = 0;

        System.out.println("====== Welcome to Car Rental System ======");

        do
        {
            System.out.println();

            System.out.println("1. Register Admin");

            System.out.println("2. Register Customer");

            System.out.println("3. Login Admin");

            System.out.println("4. Login Customer");

            System.out.println("5. Exit");

            System.out.println("Enter your choice: ");

            try
            {
                choice = Integer.parseInt(scanner.nextLine());

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
                System.out.println("Invalid data...Please enter a number between 1 to 5");
            }
        } while (choice!=5);
    }

    private void handleClientRequest(int choice)
    {
        var username = "";

        var password = "";

        switch (choice)
        {
            case 1:
                //Admin registration

                System.out.print("Enter Admin Username: ");

                username = scanner.nextLine();

                System.out.print("Enter Admin Password: ");

                password = scanner.nextLine();

                if(username.isEmpty() || password.isEmpty())
                {
                    System.out.println("Fields cannot be empty. Please try again.");

                    return;
                }
                try
                {
                    initializeConnection();

                    //Send registration request

                    serverOutput.println("REGISTER_ADMIN " + username + " " + password);

                    //Read server response

                    String response = serverInput.readLine();

                    if("true".equalsIgnoreCase(response))
                    {
                        System.out.println("Admin registered successfully");
                    }
                    else
                    {
                        System.out.println("Admin registration failed. An admin might already be registered.");
                    }
                }
                catch (Exception e)
                {
                    System.out.println("An exception occurred : "+e.getMessage());
                }
                finally
                {
                    closeConnection();
                }
                break;
            case 2:
                //Customer registration
                System.out.print("Enter Customer Username: ");

                username = scanner.nextLine();

                System.out.print("Enter Customer Password: ");

                password = scanner.nextLine();

                System.out.print("Enter driving license number: ");

                String drivingLicenseNumber = scanner.nextLine();

                if (!username.isEmpty() && !password.isEmpty() && !drivingLicenseNumber.isEmpty())
                {
                    try
                    {
                        initializeConnection();

                        // Send registration request

                        serverOutput.println("REGISTER_CUSTOMER " + username + " " + password + " " + drivingLicenseNumber);

                        // Read server response
                        String response = serverInput.readLine();

                        if ("true".equalsIgnoreCase(response))
                        {
                            System.out.println("Customer registered successfully!");
                        }
                        else
                        {
                            System.out.println("This username already exists. Please register using other username");
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("Error connecting to the server: " + e.getMessage());
                    }
                    finally
                    {
                        closeConnection();
                    }
                }
                else
                {
                    System.out.println("Don't keep any field empty...Please register again");
                }
                break;
            case 3:
                //Admin Login
                System.out.print("Enter Admin Username: ");

                username = scanner.nextLine();

                System.out.print("Enter Admin Password: ");

                password = scanner.nextLine();

                if(username.isEmpty() || password.isEmpty())
                {
                    System.out.println("Fields cannot be empty. Please try again.");

                    return;
                }
                try
                {
                    initializeConnection();

                    //Send registration request

                    serverOutput.println("LOGIN_ADMIN " + username + " " + password);

                    //Read server response

                    String response = serverInput.readLine();

                    if(response.startsWith("LOGIN_SUCCESS"))
                    {
                        token = response.split("token:")[1];

                        System.out.println("Admin Login successful !");

                        closeConnection();

                        AdminDashboard admin = new AdminDashboard(token);

                        admin.showDashboard();
                    }
                    else
                    {
                        System.out.println("Login failed. Invalid username or password");
                    }
                }
                catch (Exception e)
                {
                    System.out.println("An exception occurred during login : "+e.getMessage());
                }
                finally
                {
                    if(!clientSocket.isClosed())
                    {
                        closeConnection();
                    }
                }
                break;
            case 4:
                //Customer login

                System.out.print("Enter Customer Username: ");

                username = scanner.nextLine();

                System.out.print("Enter Customer Password: ");

                password = scanner.nextLine();

                if(username.isEmpty() || password.isEmpty())
                {
                    System.out.println("Fields cannot be empty. Please try again.");

                    return;
                }
                try
                {
                    initializeConnection();

                    serverOutput.println("LOGIN_CUSTOMER " + username + " " + password);

                    //Read server response

                    String response = serverInput.readLine();

                    if(response.startsWith("LOGIN_SUCCESS"))
                    {

                            token = response.split("token:")[1];

                            System.out.println("Customer Login successful !");

                            closeConnection();

                            CustomerDashboard customerDashboard = new CustomerDashboard(token);

                            customerDashboard.showCustomerDashboard();
                    }
                    else
                    {
                        System.out.println("Login failed. Invalid username or password");
                    }
                }
                catch (Exception e)
                {
                    System.out.println("An exception occurred : "+e.getMessage());
                }
                finally
                {
                    if(!clientSocket.isClosed())
                    {
                        closeConnection();
                    }
                }
                break;
            case 5:
                System.out.println("Exiting the system.");

                break;

            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    //Closing resources

    protected void closeConnection()
    {
        try
        {
            if(serverInput!=null) serverInput.close();

            if(serverOutput!=null) serverOutput.close();

            if(clientSocket!=null && !clientSocket.isClosed()) clientSocket.close();

            System.out.println("Connection closed");
        }
        catch (Exception e)
        {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }

    public static void main(String[] args)
    {
        System.out.println("Client started...");

        var client = new Client();

        client.startClient();
    }

}
