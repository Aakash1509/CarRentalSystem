import server.ClientHandler;

import java.net.ServerSocket;

import java.net.Socket;

import java.util.concurrent.ExecutorService;

import java.util.concurrent.Executors;

public class Main
{
    private final ServerSocket serverSocket; //for server

    private final ExecutorService executorService; //Thread pool

    public Main() throws Exception
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
        System.out.println("Hello This is server...Server is going to start");

        try
        {
            var server = new Main();

            server.start();
        }
        catch (Exception e)
        {
            System.out.println("An error occurred during starting the server : "+e.getMessage());
        }

    }
}
