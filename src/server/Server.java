package server;

import java.io.IOException;

import java.net.ServerSocket;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//This will replace Main.java
public class Server
{
    private final ServerSocket serverSocket; //for server

    private final ExecutorService executorService; //Thread pool

    public Server() throws IOException
    {
        serverSocket = new ServerSocket(9999);

        executorService = Executors.newCachedThreadPool();

        System.out.println("Server is ready to accept connections");

        System.out.println("Waiting");

    }

    public void start()
    {
        while (true)
        {
            try
            {
                Socket clientSocket = serverSocket.accept(); //Accept each client connection

                System.out.println("New client connected");

                //Submitting task to thread pool

                executorService.submit(new ClientHandler(clientSocket));
            }
            catch (Exception e)
            {
                System.out.println("An error occurred" + e.getMessage());
            }

            /*Creating new thread for each client (Without thread pool)

            ClientHandler clientHandler = new ClientHandler(clientSocket);

            new Thread(clientHandler).start(); //Start the thread to handle the client

             */
        }
    }

    public static void main(String[] args)
    {
        System.out.println("This is server...Server is going to start");

        try
        {
            Server server = new Server();

            server.start();
        }
        catch (IOException e)
        {
            System.out.println("An error occurred during starting the server : "+e.getMessage());
        }

    }
}
