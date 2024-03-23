package com.example.yuniavia.db;



import java.sql.*;
import java.util.ArrayList;

import com.example.yuniavia.Client.ClientHandlerClasses.Account;
import com.example.yuniavia.Client.ClientHandlerClasses.Flight;
import com.example.yuniavia.Client.ClientHandlerClasses.Runway;
import com.example.yuniavia.Client.ClientHandlerClasses.RunwayWithFlightInfo;
import com.example.yuniavia.Server.Constants.Configs;

public class DBAdapter {
    static Connection c;
    static Statement statement;



    public static void getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        c = DriverManager.getConnection("jdbc:mysql://localhost:3306/YuniAvia", Configs.dbUser ,Configs.dbPass);
        statement = c.createStatement();

    }
    static {
        try {
            getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Runway> getAllRunwaysWithFlights() throws SQLException, ClassNotFoundException {
        ArrayList<Runway> runways = getAllRunways();

        ArrayList<Runway> runwaysWithFlightInfo=new ArrayList<>();
        Flight flight;
        for (Runway runway: runways){
            if (runway.getFlight_id()!=0)
            {
                flight=getFlightForRunway(runway.getFlight_id());

                 RunwayWithFlightInfo runwayWithFlightInfo=new RunwayWithFlightInfo(
                        runway.getFlight_id(),  runway.getState(),
                        runway.getPicture_path(),runway.getNumber_of_runway(),Configs.OCCUPATED_RUNWAY, flight.getFlight_number(),
                        flight.getDeparture_time());

                runwaysWithFlightInfo.add(runwayWithFlightInfo);

            }
            else {
                runwaysWithFlightInfo.add(runway);
            }
        }

        return runwaysWithFlightInfo;
    }
    public static ArrayList<Runway> getAllRunways() throws SQLException, ClassNotFoundException {
        ArrayList<Runway> runways=new ArrayList<>();
        String sql = "SELECT * FROM runways";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            runways.add(new Runway(rs.getInt("flight_id"), rs.getString("state"),
                    rs.getString("picture_path"), rs.getString("number_of_runway"),Configs.NOT_OCCUPATED_RUNWAY));



        }
        return runways;
    }
    public static Flight getFlightForRunway(int flight_id) throws SQLException {
        String sql = "SELECT * FROM flights WHERE flight_id="+flight_id;
        ResultSet rs = statement.executeQuery(sql);
        Flight flight = null;
        while (rs.next()){
                flight=new Flight(rs.getInt("flight_id"),rs.getString("departure_city"),
                        rs.getString("arrival_city"),rs.getString("departure_time"),
                        rs.getString("duration"),rs.getString("arrival_time"),
                        rs.getString("note"), rs.getString("flight_number"));

            }
        return flight;
        }
    public static ArrayList<Flight> getAllFlights() throws SQLException {
        ArrayList<Flight> flights=new ArrayList<>();

        String sql = "SELECT * FROM flights";
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
            flights.add(new Flight(rs.getInt("flight_id"),rs.getString("departure_city"),
                    rs.getString("arrival_city"),rs.getString("departure_time"),
                    rs.getString("duration"),rs.getString("arrival_time"),
                    rs.getString("note"), rs.getString("flight_number")));

        }
        return flights;
    }



    public static boolean uniquePhoneNumberValidation(String new_phone_number) throws SQLException, ClassNotFoundException {
        ArrayList<String> accounts_phone_numbers = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            if (new_phone_number.equals(rs.getString("phone_number"))){
                return false;
            }
        }
        return true;
    }
    public static ArrayList<Account> getAllAccounts() throws SQLException, ClassNotFoundException {
        ArrayList<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            accounts.add(new Account(rs.getString("first_name"),
                    rs.getString("second_name"), rs.getString("phone_number"),
                     rs.getString("password"),
                    rs.getString("role")));
        }
    return accounts;
    }
    public static void createAccount(Account account) throws SQLException, ClassNotFoundException {

        String insert = "INSERT INTO accounts (first_name, second_name, phone_number, role, password )" +
                " VALUES ('"+account.getFirst_name()+"', '"+account.getSecond_name()+"', '"+account.getPhone_number()+"'," +
                " '"+account.getRole()+"', '"+account.getPassword()+"')";
         statement.executeUpdate(insert);

        }

    public static void updateRunwayState(Runway runway) throws SQLException {
        String insert = "UPDATE runways SET state='"+runway.getState()+"' WHERE picture_path='"+runway.getPicture_path()+"'";
        statement.executeUpdate(insert);

    }

    public static void updateRunwayOccupation(Runway runway) throws SQLException {
        if (runway.getOccupation().equals(Configs.OCCUPATED_RUNWAY)) {
            String insert = "UPDATE runways SET occupation='" + runway.getOccupation() + "', flight_id='" + runway.getFlight_id() + "' WHERE picture_path='" + runway.getPicture_path() + "'";
            System.out.println(insert);
            statement.executeUpdate(insert);
        }
        else {
            String insert = "UPDATE runways SET occupation='" + runway.getOccupation() + "', flight_id=NULL WHERE picture_path='" + runway.getPicture_path() + "'";
            System.out.println(insert);
            statement.executeUpdate(insert);
        }
    }

    public static void updateAccount(Account new_account, Account old_account) throws SQLException {
        String insert = "UPDATE accounts SET first_name='"+new_account.getFirst_name()+"'," +
                "second_name='"+new_account.getSecond_name()+"'," +
                "password='"+new_account.getPassword()+"'," +
                "role='"+new_account.getRole()+"' WHERE phone_number='"+old_account.getPhone_number()+"'";
        statement.executeUpdate(insert);
    }

    public static void deleteAccount(Account account) throws SQLException {
        String insert = "DELETE FROM accounts WHERE phone_number='"+account.getPhone_number()+"'";
        statement.executeUpdate(insert);
    }
}


   /* public void createUser(String userName, String password, String role) throws SQLException, ClassNotFoundException {
        ArrayList<Member> members = getMembers();
        int Members_id = members.get(members.size()-1).getIdMembers();
        String insert = "INSERT INTO users (login, password, role, Members_id) VALUES ('"+userName+"', '"+password+"', '"+role+"', '"+Members_id+"')";
        int rowsInserted = statement.executeUpdate(insert);
        if (rowsInserted > 0) {
            System.out.println("A new user was inserted successfully!");
        }
    }
    public void createMember(String memberName, String gender, String age, String relation) throws SQLException, ClassNotFoundException {
        ArrayList<Family> families = getFamilies();
        int Families_id = families.get(families.size()-1).getIdFamilies();
        String insert = "INSERT INTO members (name, gender, age, relation, Families_id) VALUES ('"+memberName+"', '"+gender+"', '"+Integer.parseInt(age)+"', '"+relation+"', '"+Families_id+"')";
        int rowsInserted = statement.executeUpdate(insert);
        if (rowsInserted > 0) {
            System.out.println("A new user was inserted successfully!");
        }
    }
    public void createFamily(String familyName) throws SQLException {
        String insert = "INSERT INTO families (name, budget) VALUES ('"+familyName+"', '"+0+"')";
        int rowsInserted = statement.executeUpdate(insert);
        if (rowsInserted > 0) {
            System.out.println("A new family was inserted successfully!");
        }
    }
    public ArrayList<User> getUsers() throws SQLException, ClassNotFoundException {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            int idUsers = rs.getInt("idUsers");
            String login = rs.getString("login");
            String password = rs.getString("password");
            String role = rs.getString("role");
            User user = new User(idUsers, login, password, role);
            users.add(user);
        }
        return users;
    }
    public ArrayList<Family> getFamilies() throws SQLException, ClassNotFoundException {
        ArrayList<Family> families = new ArrayList<>();
        String sql = "SELECT * FROM families";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            int idFamily = rs.getInt("idFamilies");
            String name = rs.getString("name");
            double budget = rs.getDouble("budget");
            Family family = new Family(idFamily, name, budget);
            families.add(family);
        }
        return families;
    }
    public ArrayList<Member> getMembers() throws SQLException, ClassNotFoundException {
        ArrayList<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            int idMembers = rs.getInt("idMembers");
            String name = rs.getString("name");
            String gender = rs.getString("gender");
            int  age = rs.getInt("age");
            String relation = rs.getString("relation");
            int Families_id = rs.getInt("Families_id");
            int Limits_id = rs.getInt("Limits_id");
            Member member = new Member(idMembers, name, gender, Integer.toString(age), relation, Families_id, Limits_id);
            members.add(member);
        }
        return members;
    }
*/




