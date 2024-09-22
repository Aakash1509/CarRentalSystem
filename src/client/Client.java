package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client
{
    private Socket clientSocket;

    //To read data from server
    private BufferedReader serverInput;

    //To write data to server
    private PrintWriter serverOutput;

    //To read input from user
    private BufferedReader userInput;

    public Client()
    {
        try
        {
            System.out.println("Sending request to server");

            clientSocket = new Socket("localhost", 9999);

            System.out.println("Connection done");

            serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            serverOutput = new PrintWriter(clientSocket.getOutputStream());

            //To take input from user
            userInput = new BufferedReader(new InputStreamReader(System.in));

            interactWithServer();
        }
        catch (Exception e)
        {
            System.out.println("Error in client: " + e.getMessage());
        }
        finally
        {
            closeConnection();
        }
    }

    private void interactWithServer()
    {
        try
        {
            String serverResponse;

            String userInputString;

            while (true)
            {
                serverResponse = serverInput.readLine();

                if (serverResponse==null || serverResponse.equalsIgnoreCase("exit"))
                {
                    break;
                }

                System.out.println(serverResponse);

                if (serverResponse.endsWith(": "))
                {
                    System.out.println("Your input: ");

                    userInputString = userInput.readLine();

                    serverOutput.println(userInputString);

                    serverOutput.flush();

                    // If client types "exit", break the loop
                    if (userInputString.equalsIgnoreCase("exit"))
                    {
                        break;
                    }
                }
            }


        }
        catch (IOException e)
        {
            System.out.println("Error during communication : " + e.getMessage());
        }
    }

    private void closeConnection()
    {
        try
        {
            if(serverInput!=null) serverInput.close();

            if(serverOutput!=null) serverOutput.close();

            if(userInput!=null) userInput.close();

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
        new Client();
    }
}
