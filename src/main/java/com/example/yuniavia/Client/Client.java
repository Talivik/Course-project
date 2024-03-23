package com.example.yuniavia.Client;
import com.example.yuniavia.Server.Constants.Configs;
import com.example.yuniavia.Server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Client {
    private static Socket clientSocket;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    public static void startConnection() throws IOException {
        clientSocket = new Socket(Configs.SERVER_HOST, Configs.SERVER_PORT);
        in = new ObjectInputStream(clientSocket.getInputStream());
        out = new ObjectOutputStream(clientSocket.getOutputStream());

    }

    public static void sendToServer(List<Object> list) throws IOException {

        try {
            if (!clientSocket.isClosed()) {
                out.writeObject(list);
                out.flush();


            } else
                System.out.println("Socket is close");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Object> getFromServer() throws IOException {
        try {
            if (!clientSocket.isClosed()) {
                List<Object> getData = (List<Object>) in.readObject();
                return getData;
            } else {
                System.out.println("SOcket is close");
                return null;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void closeConnection() throws IOException {
        clientSocket.close();
        in.close();
        out.close();
    }
}