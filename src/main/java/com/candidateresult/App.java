package com.candidateresult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    static int welcome() {
        log.info(ConsoleColors.CYAN_BOLD_BRIGHT + "welcome screen :\n" + ConsoleColors.GREEN_BOLD_BRIGHT);
        System.out.print("\n");
        System.out.print("\n");
        System.out.println(ConsoleColors.BLUE_BOLD + "############# CANDIDATE RESULT MANAGEMENT SYSTEM #############"
                + ConsoleColors.GREEN_BOLD_BRIGHT);
        System.out.println("                ____          ____   ____                  ____");
        System.out.println(" \\    /\\    /  |      |      /      /    \\    /\\    /\\    |");
        System.out.println("  \\  /  \\  /   |----  |      |      |    |   /  \\  /  \\   |----");
        System.out.println("   \\/    \\/    |____  |____  \\____  \\____/  /    \\/    \\  |____");
        System.out.print('\n');
        System.out.print('\n');
        System.out.print('\n');

        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "    SIGN ON or just CREATE A NEW ACCOUNT before you use it"
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

        sc.close();
        return answer;
    }

    public static void main(String[] args) {
        //creating jdbc










        Date date=null;

        log.info(ConsoleColors.BLUE_BOLD + "Main screen: " + ConsoleColors.WHITE_BOLD_BRIGHT);
        // Properties prop = new Properties();
        // prop.getProperty(null);
        int choice = welcome();
        if (choice == 0) {
            // 0 means termination is successful
            System.exit(choice);
        }
        if (choice == 1) {


            String pattern = "MM-dd-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            
            try {
                date = simpleDateFormat.parse("12-01-2018");
            } catch (ParseException e) {
               
                e.printStackTrace();
            }
            Candidate c1 = new Candidate((long) 100, "Ashit", "10", date, "shyam");
           
            System.out.println( c1.showMyDetail());
        }
        if (choice == 2) {

        }
        if (choice == 3) {

        }

    }

}
