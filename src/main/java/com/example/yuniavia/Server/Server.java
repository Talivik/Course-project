package com.example.yuniavia.Server;

import com.example.yuniavia.Server.Constants.Configs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static int numConnections = 0;
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(Configs.SERVER_PORT);
            System.out.println("Сервер запущен!");
            while (true) {
                Socket client = server.accept();
                Thread t = new MonoThreadClientHandler(client);
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}