package com.candidateresult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
    static final String DB_URL = "jdbc:mysql://localhost/resultdb";
    static final String USER = "root";
    static final String PASS = "123456789";
    static Long id;
    static String name;
    static String fatherName;
    static String dob;
    static String standard;

    static Float physics;
    static Float chemistry;
    static Float mathematics;
    static Float computerScience;
    static Float english;

    static Float marksObtained;
    static Float totalMarks;
    static String status;
    static Integer rank;
    static Float percentage;
    static int count = 0;

    static final int  ROOTPIN=20222022; 

    static int welcome() {
        if (count == 0) {
            log.info(ConsoleColors.CYAN_BOLD_BRIGHT + "welcome screen :\n" + ConsoleColors.GREEN_BOLD_BRIGHT);
            System.out.print("\n");
            System.out.print("\n");
            System.out
                    .println(ConsoleColors.BLUE_BOLD + "############# CANDIDATE RESULT MANAGEMENT SYSTEM #############"
                            + ConsoleColors.GREEN_BOLD_BRIGHT);
            System.out.println("                ____          ____   ____                  ____");
            System.out.println(" \\    /\\    /  |      |      /      /    \\    /\\    /\\    |");
            System.out.println("  \\  /  \\  /   |----  |      |      |    |   /  \\  /  \\   |----");
            System.out.println("   \\/    \\/    |____  |____  \\____  \\____/  /    \\/    \\  |____");
            System.out.print('\n');
            System.out.print('\n');
            System.out.print('\n');
        }
        count=1;

        log.info(ConsoleColors.BLUE_BOLD_BRIGHT + "    enter you rollnumber to view result or login as admin"
                + ConsoleColors.GREEN_BOLD);
        System.out.println("                        ___ __ __ __ __ __ ____ __ ");
        System.out.println("                       |                          | ");
        System.out.println("                       |   0: Exit                |");
        System.out.println("                       |                          |");
        System.out.println("                       |   1: view result         |");
        System.out.println("                       |                          | ");
        System.out.println("                       |   2: Admin Login         | ");
        System.out.println("                       |                          | ");
        System.out.println("                       |   3: new admin register  |");
        System.out.println("                       |                          | ");
        System.out.println("                       |___ __ __ __ __ __ __ ____|");
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n                      please input your choice"
                + ConsoleColors.RESET);
        Scanner sc = new Scanner(System.in);
        int answer = Integer.parseInt(sc.nextLine());
        while (answer != 0 && answer != 1 && answer != 2 && answer != 3) {
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "you should input 0||1||2||3 to continue");
            answer = new Scanner(System.in).nextInt();
        }

        return answer;
    }

    public static void main(String[] args) throws SQLException {

       
        int choice = welcome();
        if (choice == 0) {
            // 0 means termination is successful
            System.exit(choice);
        }
        if (choice == 1) {
            // log.info(ConsoleColors.BLUE_BOLD + "View Result screen: " +
            // ConsoleColors.WHITE_BOLD_BRIGHT);
            System.out.println(ConsoleColors.PURPLE_BOLD + "Enter Your RollNumber: " + ConsoleColors.RESET);

            String sql = "select * from candidate where id=?";
            ResultSet rs = null;
            boolean empty = true;
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    PreparedStatement pst = conn.prepareStatement(sql);
            ) {
                Scanner sc1 = new Scanner(System.in);
                String tmp = sc1.nextLine();
                Long rollnumber = Long.parseLong(tmp);
                pst.setLong(1, rollnumber);

                rs = pst.executeQuery();
                
              
                while (rs.next()) {
                     empty = false;
                    id = rs.getLong("id");
                    name = rs.getString("name");
                    standard = rs.getString("standard");
                    dob = rs.getString("dob");
                    fatherName = rs.getString("fatherName");
                    physics = rs.getFloat("physics");
                    chemistry = rs.getFloat("chemistry");
                    mathematics = rs.getFloat("mathematics");
                    computerScience = rs.getFloat("computerScience");
                    english = rs.getFloat("english");
             



                }

            } catch (SQLException e) {
                e.printStackTrace();

            }


            if (!empty) {
                Candidate c1 = new Candidate(id, name, standard, dob, fatherName);
                Result r1 = new Result(physics, chemistry, mathematics, computerScience, english);
                System.out.println("_______________________________________________________________");

                System.out.println(c1.showMyDetail(id));
                System.out.println(r1);
            }
            else {
               System.out.println("\n                          "+ConsoleColors.RED_BOLD_BRIGHT+"Does Not Matches Any Result Try Again...\n"+ConsoleColors.RESET ); 
            }
            

        }
        if (choice == 2) {

            String message = Admin.login();
            if (message!=null) {
                System.out.println(""+ ConsoleColors.GREEN_BOLD+"                        __ __ __ __ __ __ ____ __ ");
                System.out.println("                       |                          | ");
                System.out.println("                       |   0: Logout              | ");
                System.out.println("                       |                          | ");
                System.out.println("                       |   1: Create Results      | ");
                System.out.println("                       |                          | ");
                System.out.println("                       |   2: view Pin            | ");
                System.out.println("                       |                          | ");
                System.out.println("                       |___ __ __ __ __ __ __ ____| ");
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n                      please input your choice"
                + ConsoleColors.RESET);
                Scanner sc1 = new Scanner(System.in);
                int answer1 = Integer.parseInt(sc1.nextLine());
                while (answer1 != 0 && answer1 != 1 && answer1 != 2) {
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "you should input 0||1||2 to continue");
                    answer1 = Integer.parseInt(sc1.nextLine());
                    if (answer1 == 0) {
                        main(args);
                    }

                    if (answer1 == 1) {

                        Admin.createResult();

                    }

                    if (answer1 == 2) {
                        System.out.println("            " + ConsoleColors.BLUE_UNDERLINED
                                + "(ROOTPIN is secret & read only by admin level\tfor changing the ROOTPIN contact s/w developer)\n\t\tCurrent ROOTPIN : "
                                + "20222022" + ConsoleColors.RESET);

                    }
                }
                
                
        
            }


                

            



        
        }
        if (choice == 3) {
            //new admin register
        
            
        }
        main(args);
        System.out.println(""+ConsoleColors.RESET);

    }

}
