package com.candidateresult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Admin extends Person {

    static String username;
    static String password;
    public static  Long id;


    @Override
    public String showMyDetail(Long id) {
        System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"+id);
        
        return ConsoleColors.CYAN_BOLD_BRIGHT+"\t\tAdmin Details:\n\nid =>         " +id + "\nuserName =>  "  + username + "\n"+ConsoleColors.RESET;
    }

    
    




    public static String login() {
        //for login admin
        Scanner sc = new Scanner(System.in);
        System.out.println("" + ConsoleColors.CYAN_BOLD + "Enter you username: " + ConsoleColors.RESET);
        username = sc.next();

        System.out.println("" + ConsoleColors.CYAN_BOLD + "Enter you password: " + ConsoleColors.RESET);
        password = sc.next();

        String sql = "select * from admin where username=? and password=?";
        ResultSet rs = null;
        boolean em = true;
        try (Connection conn = DriverManager.getConnection(App.DB_URL, App.USER, App.PASS);
                PreparedStatement pst = conn.prepareStatement(sql);) {

            pst.setString(1, username);
            pst.setString(2, password);

            rs = pst.executeQuery();

            while (rs.next()) {
                em = false;
                //it means it verification of admin done successful
                id = rs.getLong(1);
                return ConsoleColors.CYAN_BOLD_BRIGHT + "Admin login done successful.";

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        if (em) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "wrong credential try again .....");
        }
        return null;

    }
    




    public static void createResult() {
        //creating results of candidates
        System.out.println("" + ConsoleColors.GREEN_BOLD + "Enter total candidates numbers to createResult: ");
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        int loop = sc.nextInt();
        for (int i = 0; i < loop; i++) {

            System.out
                    .println(ConsoleColors.BLUE
                            + "enter id,name, fatherName,dob(dd-mm-yyyy),standard (seperated by comma)"
                            + ConsoleColors.RESET);

            String[] details = sc2.nextLine().split(",");
            for (String s : details)
                s.trim();

            System.out.println(ConsoleColors.BLUE
                    + "enter Marks:\n\n PHYSICS,CHEMISTRY,MATHEMATICS,COMPUTER-SCIENCE,ENGLISH (seperated by comma)"
                    + ConsoleColors.RESET);
            String reply2 = sc2.nextLine();
            String[] marks = reply2.split(",");
            for (String s : marks)
                s.trim();

            String sql = "insert into candidate(id,name,fatherName,dob,standard,physics,chemistry,mathematics,computerScience,english) values(?,?,?,?,?,?,?,?,?,?)";
            ResultSet rs = null;
            boolean em = true;
            try (Connection conn = DriverManager.getConnection(App.DB_URL, App.USER, App.PASS);
                    PreparedStatement pst = conn.prepareStatement(sql);) {
                for (int j = 0; j < 5; j++) {
                    if (j == 0) {
                        pst.setLong(j + 1, Long.parseLong(details[j]));
                    } else {

                        pst.setString(j + 1, details[j]);
                    }
                }
                {
                    for (int j = 0; j < 5; j++) {
                        pst.setLong(j + 5 + 1, Long.parseLong(marks[j]));
                    }
                    int row = pst.executeUpdate();
                    System.out.println("" + ConsoleColors.GREEN_BOLD_BRIGHT + "rows affected in database: " + row);

                    if (row != 0) {
                        Candidate c1 = new Candidate(Long.parseLong(details[0]), details[1], details[2], details[3],
                                details[4]);
                        Result r1 = new Result(Float.parseFloat(marks[0]), Float.parseFloat(marks[1]),
                                Float.parseFloat(marks[2]), Float.parseFloat(marks[3]), Float.parseFloat(marks[4]));
                        System.out.println("_______________________________________________________________");

                        System.out.println(c1.showMyDetail(Long.parseLong(details[0])));
                        System.out.println(r1);
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Saving to database..."
                                + ConsoleColors.RESET);

                    }

                }
            } catch (SQLException e) {
                System.out.println("" + ConsoleColors.RED_BOLD_BRIGHT + "SQL State: " + e.getSQLState()
                        + e.getLocalizedMessage() + ConsoleColors.RESET);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
    






    public static String register() {
        System.out.println(ConsoleColors.BLUE + " ENTER ROOTPIN:  " + ConsoleColors.RESET);
        Scanner sc = new Scanner(System.in);
        int pin = sc.nextInt();

        
        
        if (pin == 20222022) {

            Scanner sc1 = new Scanner(System.in);
            System.out.println("" + ConsoleColors.GREEN_BOLD_BRIGHT + "Enter your Admin Registration details..." + ConsoleColors.RESET);
            System.out.println("" + ConsoleColors.CYAN_BOLD + "create you username(username should have one word atleast 3 letters only alpha-numeric characters and undesrcore '_' is allowed, no any space is allowed): " + ConsoleColors.RESET);
            
            while (true) {

                username = sc1.next();
                if (!username.matches("[\\w]{3,}")) {
                    System.out.println(
                            "" + ConsoleColors.RED_BOLD_BRIGHT
                                    + "username should have one word atleast 3 letters only alpha-numeric characters and undesrcore '_' is allowed, no any space is allowed, try Again..."
                                    + ConsoleColors.RESET);
                                    System.out.println(username);

                } else {
                    break;
                }
            }
            
    
            System.out.println("" + ConsoleColors.CYAN_BOLD + "create your password:(Minimum eight characters, at least one letter and one number: )" + ConsoleColors.RESET);
         
            while (true) {

                password = sc1.next();
                if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                    System.out.println(
                            "" + ConsoleColors.RED_BOLD_BRIGHT
                                    + "password should have Minimum eight characters, at least one letter and one number, try Again..."
                                    + ConsoleColors.RESET);

                } else {
                    break;
                }
            }

            //INSERT INTO database

            //------------------------------------------------------------------------------
            String sql = "insert into admin(userName,password) values(?,?)";
            String sql2 = "select adminId, userName from admin where userName = ?";
            ResultSet rs2 = null;
      
            try (Connection conn = DriverManager.getConnection(App.DB_URL, App.USER, App.PASS);
                    PreparedStatement pst = conn.prepareStatement(sql);PreparedStatement pst2 = conn.prepareStatement(sql2);) {
               
                        
                        pst.setString( 1, username);
                        pst.setString(2, password);
                        pst2.setString(1, username);
                    int row = pst.executeUpdate();
                    System.out.println("" + ConsoleColors.GREEN_BOLD_BRIGHT + "rows affected in database: " + row);

                    if (row != 0) {
                        
                        Admin admin = new Admin();
                        
                       
                         rs2 = pst2.executeQuery();
                        while (rs2.next()) {
                            id = rs2.getLong("adminId");
                            username = rs2.getString("userName");

                        }
                        System.out.println(admin.showMyDetail(id));
                       
                        System.out.println("_______________________________________________________________");
                      
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Saving to database..."
                                + ConsoleColors.RESET);

                    }

                
            } catch (SQLException e) {
                System.out.println("" + ConsoleColors.RED_BOLD_BRIGHT + "SQL State: " + e.getSQLState()
                        + e.getLocalizedMessage() + ConsoleColors.RESET);
            } catch (Exception e) {
                e.printStackTrace();
            }
//---------------------------------------------------------------------------------------------------------





            return ConsoleColors.GREEN_BOLD_BRIGHT + "Admin registration done successful.";

        }
        else {
            return ConsoleColors.RED + "Incorrect or Invalid ROOTPIN...";
        }





        
        
    }
}
