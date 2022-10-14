package com.candidateresult;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
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

    static final int ROOTPIN = 20222022;
    
    static Candidate c1;
    static Result r1;
    static String line = "_______________________________________________________________";
    static String decarativeLineBar = "                       |                          | ";
    static Scanner sc = new Scanner(System.in);

    static int welcome() {
        if (count == 0) {
            log.info(ConsoleColors.CYAN_BOLD_BRIGHT + "welcome screen :\n" + ConsoleColors.GREEN);
         
           log.info(ConsoleColors.BLUE_BOLD + "############# CANDIDATE RESULT MANAGEMENT SYSTEM #############"
                            + ConsoleColors.GREEN_BOLD_BRIGHT);
            log.info("                ____          ____   ____                  ____");
            log.info(" \\    /\\    /  |      |      /      /    \\    /\\    /\\    |");
            log.info("  \\  /  \\  /   |----  |      |      |    |   /  \\  /  \\   |----");
            log.info("   \\/    \\/    |____  |____  \\____  \\____/  /    \\/    \\  |____");
           
        }
        count = 1;
        

        log.info(ConsoleColors.BLUE_BOLD_BRIGHT + "    enter you rollnumber to view result or login as admin"
                + ConsoleColors.GREEN_BOLD);
        log.info("                        ___ __ __ __ __ __ ____ __ ");
        log.info(decarativeLineBar);
       
        
        log.info("                       |   1: view result         |");
        log.info(decarativeLineBar);
        log.info("                       |   2: Admin Login         | ");
        log.info(decarativeLineBar);
        log.info("                       |   3: new admin register  |");
        log.info(decarativeLineBar);
        log.info("                       |   4: view Rank List      |");
        log.info(decarativeLineBar);
        log.info("                       |   5: Exit                |");
        log.info("                       |___ __ __ __ __ __ __ ____|");
        log.info(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n                      please input your choice"
                + ConsoleColors.RESET);
        
        
        int answer = Integer.parseInt(sc.nextLine());

        while (answer != 5 && answer != 1 && answer != 2 && answer != 3 && answer != 4) {
            log.info(ConsoleColors.GREEN_BOLD_BRIGHT + "you should input 5 || 1 || 2 || 3 || 4 to continue");
            answer = new Scanner(System.in).nextInt();
        }
    
        
         return answer;
        
    }
      
    static void viewResult() {
        log.info(ConsoleColors.PURPLE_BOLD + "Enter Your RollNumber: " + ConsoleColors.RESET);

        String sql = "select * from candidate where id=?";
        ResultSet rs = null;
        boolean empty = true;
        
        Long tmp=0l;
        
        
        
             tmp = Long.parseLong(sc.nextLine());
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pst = conn.prepareStatement(sql);) {
           
            
            
            
            Long rollnumber = tmp;
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
            
            
                log.info("try  once again....." + ConsoleColors.RESET);
               App.viewResult();

        }


        if (!empty) {
            c1 = new Candidate(id, name, standard, dob, fatherName);
            r1 = new Result(physics, chemistry, mathematics, computerScience, english);

            log.info(c1.showMyDetail(id));

            log.info(r1.toString());

            log.info("" + ConsoleColors.GREEN_BOLD + "Enter 0 to exit OR  1 to print Result to pdf:  "
                    + ConsoleColors.RESET);
            try {
                

                int printChoice = Integer.parseInt(sc.nextLine());
                
            
                while (printChoice != 0 && printChoice != 1) {
                    log.info(
                            ConsoleColors.GREEN_BOLD_BRIGHT + "you should input 0|1 to continue" + ConsoleColors.RESET);
                    printChoice = Integer.parseInt(sc.nextLine());
                }
          
                if (printChoice == 1) {
                    log.info(Print.printpdf(c1, r1, id));
                    //pdf file, should be opening in default text editor or web browser
                    File file = new File(String.format(".\\result%d.pdf", id));

                    Desktop desktop = Desktop.getDesktop();
                    if (file.exists())
                        desktop.open(file);

                }
            } catch (Exception e) {
                
                log.info("try  once again....." + ConsoleColors.RESET);
                App.viewResult();
            }
            

        } else {
            log.info("\n                          " + ConsoleColors.RED_BOLD_BRIGHT
                    + "Does Not Matches Any Result Try Again...\n" + ConsoleColors.RESET);
        }

    }

    static void adminLogin(String []args) throws SQLException {
        String message = Admin.login();
        if (message != null) {
           log.info("" + ConsoleColors.GREEN_BOLD + "                        __ __ __ __ __ __ ____ __ ");
            log.info(decarativeLineBar);
            
            log.info(decarativeLineBar);
            log.info("                       |   1: Create Results      | ");
            log.info(decarativeLineBar);
            log.info("                       |   2: view Pin            | ");
            log.info(decarativeLineBar);
            log.info("                       |   3: showMyDetail        | ");
            log.info(decarativeLineBar);
            log.info("                       |   4: Logout              | ");
            log.info("                       |___ __ __ __ __ __ __ ____| ");
            log.info(
                    ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n                      please input your choice"
                            + ConsoleColors.RESET);
            
            int answer1 = Integer.parseInt(sc.nextLine());
            while (answer1 != 4 && answer1 != 1 && answer1 != 2 && answer1 != 3) {
                log.info(ConsoleColors.GREEN_BOLD_BRIGHT + "you should input 0||1||2||3 to continue");
                answer1 = Integer.parseInt(sc.nextLine());
            }
           
            if (answer1 == 4) {
                main(args);
            }

            if (answer1 == 1) {

                try {
                    Admin.createResult();
                } catch (IOException e) {
                   
                    
                    log.info("try  once again....." + ConsoleColors.RESET);
                    App.adminLogin(args);
                }
                log.info(line);

            }

            if (answer1 == 2) {
                log.info("            " + ConsoleColors.BLUE_UNDERLINED
                        + "(ROOTPIN is secret & read only by admin level\tfor changing the ROOTPIN contact s/w developer)\n\t\tCurrent ROOTPIN : "
                        + "20222022" + ConsoleColors.RESET);
                        log.info(line);

            }

            if (answer1 == 3) {

                Admin admin = new Admin();
                log.info(admin.showMyDetail(Admin.id));
              

            }

          

        }

            }

    public static void main(String[] args) throws SQLException {
    	
        

        int choice = welcome();
        if (choice == 5) {
            // 0 means termination is successful
            System.exit(0);
        }
        if (choice == 1) {
          
            viewResult();
        }
        if (choice == 2) {

            adminLogin(args);
        }
        if (choice == 3) {
            
            log.info(Admin.register());
            log.info(line);

        }
        if(choice == 4){
           
            try {
                Print.rankList();
            } catch (IOException e) {
               
                
                log.info("try  once again....." + ConsoleColors.RESET);
                App.main(args);
            }
     
        }
        main(args);
        log.info("" + ConsoleColors.RESET);

      

    }
    

    

}
