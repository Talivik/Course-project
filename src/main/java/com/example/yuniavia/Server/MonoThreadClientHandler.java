package com.example.yuniavia.Server;



import com.example.yuniavia.Client.ClientHandlerClasses.Account;
import com.example.yuniavia.Client.ClientHandlerClasses.Flight;
import com.example.yuniavia.Client.ClientHandlerClasses.Runway;
import com.example.yuniavia.Server.Constants.Configs;
import com.example.yuniavia.Server.Constants.Requests;
import com.example.yuniavia.db.DBAdapter;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MonoThreadClientHandler extends Thread {
    Socket clientDialog;
//    DBAdapter dbAdapter = new DBAdapter();
    public MonoThreadClientHandler(Socket client) {
        this.clientDialog = client;
    }
    @Override
    public void run() {
        super.run();
        System.out.println("Подключение принято от " +
                clientDialog.getInetAddress().toString().substring(1) + ":"
                + clientDialog.getPort());
        Server.numConnections++;
        System.out.println("Клиентов подключено: " + Server.numConnections);
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(clientDialog.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(clientDialog.getInputStream());



            while (true){

                treatment(inputStream, outputStream);

            }
            }
        catch (IOException ex) {
            Server.numConnections--;
            throw new RuntimeException(ex);
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void treatment(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException, SQLException {
        List<Object> getData = (List<Object>) inputStream.readObject();
        String comand = getData.get(0).toString();

        System.out.println(comand);
        if(getData.size()!=1){
            System.out.println(getData.get(1).toString());
        }


        switch (comand) {
            case Requests.FLUSH: {
                actionFeedback(outputStream, Configs.SUCCESS_ACTION);
                break;
            }
            case Requests.REQUEST_REGISTRATION: {
                registration(getData, outputStream);
                break;

            }
            case Requests.REQUEST_LOGIN: {
                authorization(outputStream, getData);
                break;
            }
            case Requests.REQUEST_SHOW_RUNWAYS: {
                showRunways(outputStream);
                break;

            }
            case Requests.REQUEST_UPDATE_RUNWAY_STATE: {
                 updateRunwayState(outputStream, getData);
                break;
            }
            case Requests.REQUEST_SHOW_FLIGHTS: {
                showFlights(outputStream);
                break;
            }
            case Requests.REQUEST_UPDATE_RUNWAY_OCCUPATION: {
                updateRunwayOccupation(outputStream, getData);
                break;
            }
            case Requests.REQUEST_SHOW_ACCOUNTS: {
                showAccounts(outputStream);
                break;
            }
            case Requests.REQUEST_UPDATE_ACCOUNT: {
                updateAccounts(outputStream, getData);
                break;
            }
            case Requests.REQUEST_DELETE_ACCOUNT: {
                deleteAccount(outputStream, getData);
                break;
            }
        }
    }

    private void deleteAccount(ObjectOutputStream outputStream, List<Object> getData) throws IOException, SQLException {
        Account account= (Account) getData.get(1);
        DBAdapter.deleteAccount(account);
        actionFeedback(outputStream,Configs.SUCCESS_ACTION);

    }

    private void updateAccounts(ObjectOutputStream outputStream, List<Object> getData) throws IOException, SQLException {
        Account new_account= (Account) getData.get(1);
        Account old_account= (Account) getData.get(2);
        DBAdapter.updateAccount(new_account,old_account);
        actionFeedback(outputStream,Configs.SUCCESS_ACTION);

    }

    private void showAccounts(ObjectOutputStream outputStream) throws IOException, SQLException, ClassNotFoundException {
        ArrayList<Account> accounts=DBAdapter.getAllAccounts();
        List<Object> list=List.of(accounts);
        outputStream.writeObject(list);
        outputStream.flush();
    }

    private void updateRunwayOccupation(ObjectOutputStream outputStream, List<Object> getData) throws IOException, SQLException {
        Runway runway=(Runway) getData.get(1);
        DBAdapter.updateRunwayOccupation(runway);
        actionFeedback(outputStream,Configs.SUCCESS_ACTION);

    }

    private void showFlights(ObjectOutputStream outputStream) throws SQLException, ClassNotFoundException, IOException {
        ArrayList<Flight> flights=DBAdapter.getAllFlights();
        List<Object> list=List.of(flights);
        outputStream.writeObject(list);
        outputStream.flush();
    }
    private void updateRunwayState(ObjectOutputStream outputStream, List<Object> getData) throws IOException, SQLException {
        Runway runway=(Runway) getData.get(1);
        DBAdapter.updateRunwayState(runway);
        actionFeedback(outputStream,Configs.SUCCESS_ACTION);






    }
    private void showRunways(ObjectOutputStream outputStream) throws SQLException, ClassNotFoundException, IOException {
        ArrayList<Runway> runways=DBAdapter.getAllRunwaysWithFlights();
       List<Object> list=List.of(runways);
        outputStream.writeObject(list);
       outputStream.flush();
    }
    public void authorization(ObjectOutputStream outputStream, List<Object> getData) throws SQLException, ClassNotFoundException, IOException {
        Account account=(Account) getData.get(1);
        ArrayList<Account> accounts=DBAdapter.getAllAccounts();
        Boolean flag_of_search=false;
        for(Account temp_acc: accounts){
            if (temp_acc.getPhone_number().equals(account.getPhone_number()) &&
            temp_acc.getPassword().equals(account.getPassword()))
            {

                flag_of_search=true;
                List<Object> sendData = List.of(Configs.SUCCESS_ACTION, temp_acc);
                outputStream.writeObject(sendData);
                outputStream.flush();
                break;
            }
        }
        if (!flag_of_search){
            actionFeedback(outputStream, Configs.UNSUCCESS_ACTION);
        }


    }
    public void actionFeedback(ObjectOutputStream outputStream, String string) throws IOException {
        List<Object> sendData = List.of(string);
        outputStream.writeObject(sendData);
        outputStream.flush();
    }
    public void registration(List<Object> getData, ObjectOutputStream outputStream) throws IOException, SQLException, ClassNotFoundException {
        Account account= (Account) getData.get(1);
        if (DBAdapter.uniquePhoneNumberValidation(account.getPhone_number())){
            DBAdapter.createAccount(account);
            actionFeedback(outputStream, Configs.SUCCESS_ACTION);
        }
        else {
            actionFeedback(outputStream, Configs.UNSUCCESS_ACTION);
        }
    }

  /*      switch(comand){
            case "LOGIN":{
                Map<String, String> map = (Map<String, String>) getData.get(1);
                ArrayList<User> users = dbAdapter.getUsers();
                int count = 0;
                for(User u : users){
                    System.out.println(u.toString());
                    if(map.get("login").equals(u.getLogin()) && map.get("password").equals(u.getPassword())){
                        List<Object> sendData = List.of(u.getIdUsers(), u.getLogin(), u.getPassword(), u.getRole());
                        outputStream.writeObject(sendData);
                        outputStream.flush();
                        System.out.println("Data sent!");
                        count++;
                    }
                }
                if(count==0) {
                    List<Object> sendData = List.of("User doesnt exist!");
                    outputStream.writeObject(sendData);
                    outputStream.flush();
                    System.out.println("Data sent!");
                }
                clientDialog.close();
                Server.numConnections--;
                System.out.println("Клиент отключился: " +
                        clientDialog.getInetAddress().toString().substring(1) + ":"
                        + clientDialog.getPort());
                System.out.println("Клиентов подключено: " +
                        Server.numConnections);
                break;
            }

        }*/
    }
