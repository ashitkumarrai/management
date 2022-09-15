package com.candidateresult;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.extern.slf4j.Slf4j;




@Slf4j
public class Admin extends Person {

    static String username;
    static String password;
     static  Long id;


    @Override
    public String showMyDetail(Long id) {

        
        return ConsoleColors.CYAN_BOLD_BRIGHT+"\t\tAdmin Details:\n\nid =>         " +id + "\nuserName =>  "  + username + "\n"+ConsoleColors.RESET;
    }

    
    




    public static String login() {
        //for login admin
        
        log.info("" + ConsoleColors.CYAN_BOLD + "Enter you username: " + ConsoleColors.RESET);
        username =App.sc.nextLine();

        log.info("" + ConsoleColors.CYAN_BOLD + "Enter you password: " + ConsoleColors.RESET);
        password = App.sc.nextLine();
        

        String sql = "select * from admin where username LIKE BINARY ? and password LIKE BINARY ?";
    
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
            log.info(ConsoleColors.RED_BOLD_BRIGHT + "wrong credential try again .....");
        }
        return null;

    }
    




	public static void createResult() throws IOException {
    	
    	log.info("" + ConsoleColors.GREEN_BOLD + "Enter 0 to input details from command line or enter 1 to read input from excel sheet: ");
    	
    	 
         int ans = Integer.parseInt(App.sc.nextLine());
         while (ans != 0 && ans != 1) {
             log.info(ConsoleColors.GREEN_BOLD_BRIGHT + "you should input 0||1|| to continue");
             ans = Integer.parseInt(App.sc.nextLine());
         }
       
        //creating results of candidates
         
    	if(ans ==0) {
        log.info("" + ConsoleColors.GREEN_BOLD + "Enter total candidates numbers to createResult: ");
    
        int loop = Integer.parseInt(App.sc.nextLine());
        for (int i = 0; i < loop; i++) {

            log.info(ConsoleColors.BLUE
                            + "enter id,name, fatherName,dob(dd-mm-yyyy),standard (seperated by comma)"
                            + ConsoleColors.RESET);

            String[] details = App.sc.nextLine().split(",");
            for (String s : details)
                s=s.trim();

            log.info(ConsoleColors.BLUE
                    + "enter Marks:\n\n PHYSICS,CHEMISTRY,MATHEMATICS,COMPUTER-SCIENCE,ENGLISH (seperated by comma)"
                    + ConsoleColors.RESET);
            String reply2 = App.sc.nextLine();
            String[] marks = reply2.split(",");
            for (String s : marks)
                s=s.trim();

            String sql = "insert into candidate(id,name,fatherName,dob,standard,physics,chemistry,mathematics,computerScience,english) values(?,?,?,?,?,?,?,?,?,?)";
            try (Connection conn = DriverManager.getConnection(App.DB_URL, App.USER, App.PASS);
                    PreparedStatement pst = conn.prepareStatement(sql);) {
                for (int j = 0; j < 5; j++) {
                    if (j == 0) {
                        pst.setLong(j + 1, Long.parseLong(details[j]));
                    } else {

                        pst.setString(j + 1, details[j]);
                    }
                }
                
                    for (int j = 0; j < 5; j++) {
                        pst.setLong(j + 5 + 1, Long.parseLong(marks[j]));
                    }
                    int row = pst.executeUpdate();
                    log.info("" + ConsoleColors.GREEN_BOLD_BRIGHT + "rows affected in database " + row);

                    if (row != 0) {
                        Candidate c1 = new Candidate(Long.parseLong(details[0]), details[1], details[2], details[3],
                                details[4]);
                        Result r1 = new Result(Float.parseFloat(marks[0]), Float.parseFloat(marks[1]),
                                Float.parseFloat(marks[2]), Float.parseFloat(marks[3]), Float.parseFloat(marks[4]));
                        log.info("_______________________________________________________________");

                        log.info(c1.showMyDetail(Long.parseLong(details[0])));
                        log.info(r1.toString());
                        log.info(ConsoleColors.GREEN_BOLD_BRIGHT + "Saving to database..."
                                + ConsoleColors.RESET);

                    

                }
            } catch (SQLException e) {
                log.info("" + ConsoleColors.RED_BOLD_BRIGHT + "SQL State:" + e.getSQLState()
                        + e.getLocalizedMessage() + ConsoleColors.RESET);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
      
    	}
        
    	   if (ans == 1) {
               log.info(
                       "" + ConsoleColors.GREEN_BOLD + "Enter excel file path (absolute path): " + ConsoleColors.RESET);
               

               String filePath = App.sc.nextLine();
             
            

             FileInputStream inputStream = null;
   			try {
   				 inputStream = new FileInputStream(new File(String.format(filePath)));
   			} catch (FileNotFoundException e) {
   				
   				e.printStackTrace();
   			}
   			XSSFWorkbook wb = null;
   			
   				wb = new XSSFWorkbook(inputStream);
   			
   				
   			
   		
               
   			//creating a Sheet object to retrieve the object  
               XSSFSheet sheet = wb.getSheetAt(0);
               Iterator<Row> rowIterator = sheet.iterator();
               int totalRows = 0;
               ArrayList<String> list = new ArrayList<>();
               Row rows = rowIterator.next();
               //skipping first row
               while (rowIterator.hasNext()) 
               {
                   rows = rowIterator.next();
                   // For each row, iterate through each columns 
                   totalRows += 1;
                   Iterator<Cell> cellIterator = rows.cellIterator();
                   while (cellIterator.hasNext()) {
                       Cell cell = cellIterator.next();
                       switch (cell.getCellType()) {
                           case Cell.CELL_TYPE_STRING:
                               list.add(cell.getStringCellValue());
                               break;
                           case Cell.CELL_TYPE_NUMERIC:
                               list.add(String.valueOf((long) cell.getNumericCellValue()));
                               break;
                               default:

                       }
                   }
               }
               wb.close();
           
           
               
                   
                   for (int i = 0,k=0; i < totalRows; i++,k+=10)
                   {
                	   int j=0;

                       String sql = "insert into candidate(id,name,fatherName,dob,standard,physics,chemistry,mathematics,computerScience,english) values(?,?,?,?,?,?,?,?,?,?)";
                    
                       try (Connection conn = DriverManager.getConnection(App.DB_URL, App.USER, App.PASS);
                               PreparedStatement pst = conn.prepareStatement(sql);) {
                           for (; j < 5; j++) {
                               if (j == 0) {
                                   pst.setLong(j + 1, Long.valueOf(list.get((k+j))));
                                   log.info(list.get((k+j)));
                               } else {

                                   pst.setString(j+1, String.valueOf(list.get((k+j))));
                                   log.info(list.get((k+j)));
                               }
                           }
                           
                               for (;j < 10; j++) {
                            	   pst.setLong(j + 1, Long.valueOf(list.get((k+j))));
                            	   log.info("Hi"+list.get((k+j)));
                               }
                               int row = pst.executeUpdate();
                               log.info("" + ConsoleColors.GREEN_BOLD_BRIGHT + "rows affected in database: " + row);

                               if (row != 0) {
                            
                                   log.info(ConsoleColors.GREEN_BOLD_BRIGHT + "Saved to database..."
                                           + ConsoleColors.RESET);

                               }

                           
                       } catch (SQLException e) {
                           log.info("" + ConsoleColors.RED_BOLD_BRIGHT + "SQL State: " + e.getSQLState()
                                   + e.getLocalizedMessage() + ConsoleColors.RESET);
                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                   }
               	
               
           }
        	
        }
    
            
            

        

    
    






    public static String register() {
        log.info(ConsoleColors.BLUE + " ENTER ROOTPIN:  " + ConsoleColors.RESET);
       
        int pin = Integer.parseInt(App.sc.nextLine());
       
        
        
        if (pin == 20222022) {

            
            log.info("" + ConsoleColors.GREEN_BOLD_BRIGHT + "Enter your Admin Registration details..." + ConsoleColors.RESET);
            log.info("" + ConsoleColors.CYAN_BOLD + "create you username(username should have one word atleast 3 letters only alpha-numeric characters and undesrcore '_' is allowed, no any space is allowed): " + ConsoleColors.RESET);
            
            while (true) {

                username = App.sc.nextLine();
                if (!username.matches("[\\w]{3,}")) {
                    log.info(
                            "" + ConsoleColors.RED_BOLD_BRIGHT
                                    + "username should have one word atleast 3 letters only alpha-numeric characters and undesrcore '_' is allowed, no any space is allowed, try Again..."
                                    + ConsoleColors.RESET);
                                    log.info(username);

                } else {
                    break;
                }
            }
            
    
            log.info("" + ConsoleColors.CYAN_BOLD + "create your password:(Minimum eight characters, at least one letter and one number: )" + ConsoleColors.RESET);
         
            while (true) {

                password = App.sc.nextLine();
                if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                    log.info(
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
                    log.info("" + ConsoleColors.GREEN_BOLD_BRIGHT + "rows affected in database: " + row);

                    if (row != 0) {
                        
                        Admin admin = new Admin();
                        
                       
                         rs2 = pst2.executeQuery();
                        while (rs2.next()) {
                            id = rs2.getLong("adminId");
                            username = rs2.getString("userName");

                        }
                        log.info(admin.showMyDetail(id));
                       
                        log.info("_______________________________________________________________");
                      
                        log.info(ConsoleColors.GREEN_BOLD_BRIGHT + "Saving to database..."
                                + ConsoleColors.RESET);

                    }

                
            } catch (SQLException e) {
                log.info("" + ConsoleColors.RED_BOLD_BRIGHT + "SQL State: " + e.getSQLState()
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
