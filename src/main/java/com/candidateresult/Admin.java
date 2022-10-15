package com.candidateresult;


import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.extern.slf4j.Slf4j;
import jline.*;
import jline.console.ConsoleReader;


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
        
        log.info("" + ConsoleColors.CYAN_BOLD + "Enter your username: " + ConsoleColors.RESET);
        username =App.sc.nextLine();

        log.info("" + ConsoleColors.CYAN_BOLD + "Enter your password: " + ConsoleColors.RESET);
        // 1.password = App.sc.nextLine();
        
         Console cnsl = System.console(); 
         
       // char[] ch = cnsl.readPassword( "Enter password : ");
       //password = String.valueOf(ch);
        
         
        
        //*********************************************************
         password = PasswordField.readPassword("Enter password: ");
        
        
        
        
        
      

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
            log.info(ConsoleColors.RED+ e.getLocalizedMessage());
            log.info("try  once again....."+ConsoleColors.RESET);
            Admin.login();
            

        }
        if (em) {
            log.info(ConsoleColors.RED_BOLD_BRIGHT + "wrong credential try again .....");
        }
        return null;

    }
    


    private static Point getCenterPosition(int height, int width)
    //for center position of frame(file chooser dialog window
    {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int xCenter = (screen.width / 2) - (width / 2); // Center
        int yCenter = (screen.height / 2) - (height / 2); // Center
        Point res = new Point(xCenter, yCenter);
        return res;
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
                s = s.trim();
                
                

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
                    

                    if (row != 0) {
                        Candidate c1 = new Candidate(Long.parseLong(details[0]), details[1], details[2], details[3],
                                details[4]);
                        Result r1 = new Result(Float.parseFloat(marks[0]), Float.parseFloat(marks[1]),
                                Float.parseFloat(marks[2]), Float.parseFloat(marks[3]), Float.parseFloat(marks[4]));
                        log.info("_______________________________________________________________");

                        log.info(c1.showMyDetail(Long.parseLong(details[0])));
                        log.info(r1.toString());
                        

                    

                }
            } catch (SQLException e) {
                
                log.info("" + ConsoleColors.RED_BOLD_BRIGHT + "SQL State:" + e.getSQLState()
                        + e.getLocalizedMessage() + ConsoleColors.RESET);
                        Admin.createResult();
            } catch (Exception e) {
                
               
                Admin.createResult();
            }
        }
        log.info(ConsoleColors.GREEN_BOLD_BRIGHT + "Saved to database..."
                + ConsoleColors.RESET);
      
    	}
        
    	   if (ans == 1) {
             log.info("file chooser dialog box in opened...");
             //opening file chooser dialog window
             try
             {
                 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());    
             }
             catch (Exception e)
             {
                 
             }
              Frame frame = new JFrame();
             
             Point centerPoint = getCenterPosition(frame.getWidth(), frame.getHeight());
             FileDialog fd = new FileDialog(frame, "choose a excel file", FileDialog.LOAD);
             frame.setLocation(centerPoint);
             
            
             frame.setAlwaysOnTop(true);
             frame.setVisible(false);
             
             //fd.toFront();
            
             fd.setDirectory(System.getProperty("user.dir"));
            
               
             
     
             
             fd.setFilenameFilter((File dir, String name) -> name.endsWith(".xlsx"));
             fd.setFile("*.xlsx");

             
             
             fd.setVisible(true);
              String filename= fd.getFile();
              FileInputStream inputStream = null;
            
             if (filename == null)
              {log.info(ConsoleColors.RED_BOLD_BRIGHT+"You closed the file chooser dialog box\n try again,");
              
              log.info(""+ConsoleColors.RESET);
              Admin.createResult();
              try {
                App.adminLogin(null);
            } catch (SQLException e) {
               
                e.printStackTrace();
            }
              }
             else
               {
               
               File selectedFile = fd.getFiles()[0];
    	       
           /*
            * JFileChooser fileChooser = new JFileChooser();
            * 
            * fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            * fileChooser.setBackground(Color.black);
            * fileChooser.setForeground(Color.white);
            * 
            * int result = fileChooser.showOpenDialog(null);
            * 
            * 
            * FileInputStream inputStream = null;
            * try {
            * if (result == JFileChooser.APPROVE_OPTION) {
            * File selectedFile = fileChooser.getSelectedFile();
            */
    	       
    	       
    	       
    	       
    	       
                       
                       
               

               //String filePath = App.sc.nextLine();
             
            
              
             
   			try {
   				 inputStream = new FileInputStream(selectedFile);
   			   
   			} catch (FileNotFoundException e) {
   				
                
                Admin.createResult();
                
   			}}
    	      
   			XSSFWorkbook wb = null;
   			int totalRows = 0;
   			ArrayList<String> list = new ArrayList<>();
   				try{wb = new XSSFWorkbook(inputStream);
   			
   				
   			
   		
               
   			//creating a Sheet object to retrieve the object  
               XSSFSheet sheet = wb.getSheetAt(0);
               Iterator<Row> rowIterator = sheet.iterator();
               totalRows = 0;
               
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
   				}
   				catch(Exception e) {
                    log.info(ConsoleColors.RED + e.getLocalizedMessage());
                   
                   log.info("try  once again....."+ConsoleColors.RESET);
                   Admin.createResult();
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
                                 
                               } else {

                                   pst.setString(j+1, String.valueOf(list.get((k+j))));
                                   
                               }
                           }
                           
                               for (;j < 10; j++) {
                            	   pst.setLong(j + 1, Long.valueOf(list.get((k+j))));
                            	
                               }
                               int row = pst.executeUpdate();
                           

                               if (row != 0) {
                            
                                   log.info(ConsoleColors.GREEN_BOLD_BRIGHT + "Saved to database..."
                                           + ConsoleColors.RESET);

                               }

                           
                       } catch (SQLException e) {
                           log.info("" + ConsoleColors.RED_BOLD_BRIGHT + "SQL State: " + e.getSQLState()
                                   + e.getLocalizedMessage() + ConsoleColors.RESET);
                         
                           log.info("try  once again....."+ConsoleColors.RESET);
                                   Admin.createResult();
                       } catch (Exception e) {
                           log.info(ConsoleColors.RED+ e.getLocalizedMessage());
                           log.info("try  once again....."+ConsoleColors.RESET);
                           Admin.createResult();
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
                
            }
//---------------------------------------------------------------------------------------------------------





            return ConsoleColors.GREEN_BOLD_BRIGHT + "Admin registration done successful.";

        }
        else {
            return ConsoleColors.RED + "Incorrect or Invalid ROOTPIN...";
        }





        
        
    }}
    class PasswordField {

        public static String readPassword (String prompt) {
           EraserThread et = new EraserThread(prompt);
           Thread mask = new Thread(et);
           mask.start();

           BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
           String password = "";

           try {
               password = in.readLine();
           } catch (IOException ioe) {
               ioe.printStackTrace();
           }
           et.stopMasking();
           return password;
        }
     }   

     class EraserThread implements Runnable {
        private boolean stop;

        public EraserThread(String prompt) {
            System.out.print(prompt);
        }public void run () {
            while (!stop){
                System.out.print("\010*");
                try {
                   Thread.currentThread().sleep(1);
                } catch(InterruptedException ie) {
                   ie.printStackTrace();
                }
             }
          }

          public void stopMasking() {
             this.stop = true;
          }
     }

