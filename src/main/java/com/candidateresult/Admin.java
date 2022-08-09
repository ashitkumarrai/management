package com.candidateresult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class Admin extends Person{

    @Override
    public String showMyDetail(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String updateMyDetail() {
        // TODO Auto-generated method stub
        return null;
    }
    




    public static String login() {
        //for login admin
        Scanner sc = new Scanner(System.in);
        System.out.println("" + ConsoleColors.CYAN_BOLD + "Enter you username: " + ConsoleColors.RESET);
        String username = sc.next();

        System.out.println("" + ConsoleColors.CYAN_BOLD + "Enter you password: " + ConsoleColors.RESET);
        String password = sc.next();

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
        System.out.println( ""+ConsoleColors.GREEN_BOLD+"Enter total candidates numbers to createResult: ");
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        int loop = sc.nextInt();
        for (int i = 0; i < loop; i++) {

            System.out
                    .println(ConsoleColors.BLUE + "enter id,name, fatherName,dob(dd-mm-yyyy),standard (seperated by comma)"
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
                    PreparedStatement pst = conn.prepareStatement(sql);
            ) {
                for (int j = 0; j < 5;j++) {
                    if (j == 0) {
                        pst.setLong(j+1, Long.parseLong(details[j]));
                    } else {

                        pst.setString(j+1, details[j]);
                    }
                }
                {
                    for (int j = 0; j < 5;j++) {
                        pst.setLong(j+5+1, Long.parseLong(marks[j]));
                    }
                    int row = pst.executeUpdate();
                    System.out.println(""+ConsoleColors.GREEN_BOLD_BRIGHT+"rows affected in database: " + row);

                    if (row != 0) {
                        Candidate c1 = new Candidate(Long.parseLong(details[0]), details[1], details[2], details[3], details[4]);
                        Result r1 = new Result(Float.parseFloat(marks[0]),Float.parseFloat(marks[1]),Float.parseFloat(marks[2]),Float.parseFloat(marks[3]),Float.parseFloat(marks[4]));
                        System.out.println("_______________________________________________________________");
        
                        System.out.println(c1.showMyDetail(Long.parseLong(details[0])));
                        System.out.println(r1);
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Saving to database..."
                                + ConsoleColors.RESET);



                    }

                }
            } catch (SQLException e) {
                    System.out.println(""+ConsoleColors.RED_BOLD_BRIGHT+"SQL State: "+e.getSQLState()+e.getLocalizedMessage()+ConsoleColors.RESET);
                } catch (Exception e) {
                    e.printStackTrace();
                }


        }
        


        



    }
}
