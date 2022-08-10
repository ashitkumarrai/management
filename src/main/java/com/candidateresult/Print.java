package com.candidateresult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.BinaryOperator;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.util.stream.Stream;
public class Print {
    

    public static String printpdf(Candidate c, Result r, Long id) {

        //printing pdf 

        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(String.format(".\\result%d.pdf", id)));
            document.open();
            String text = c.showMyDetail(id);

            text = text.replaceAll("(\\[[1;96024]*m)", "");

            document.add(new Paragraph(text));
            PdfPTable table1 = new PdfPTable(5);

            float[] colWidth = { 12f, 12f, 12f, 12f, 12f };
            table1.setWidths(colWidth);
            PdfPCell c1 = new PdfPCell(new Paragraph("PHYSICS"));
            PdfPCell c2 = new PdfPCell(new Paragraph("CHEMISTRY"));
            PdfPCell c3 = new PdfPCell(new Paragraph("MATHS"));
            PdfPCell c4 = new PdfPCell(new Paragraph("CS"));
            PdfPCell c5 = new PdfPCell(new Paragraph("ENGLISH"));

            table1.addCell(c1);
            table1.addCell(c2);
            table1.addCell(c3);
            table1.addCell(c4);
            table1.addCell(c5);

            document.add(table1);

            PdfPTable table2 = new PdfPTable(5);

            table2.setWidths(colWidth);
            c1 = new PdfPCell(new Paragraph(String.valueOf(r.getPhysics())));
            c2 = new PdfPCell(new Paragraph(String.valueOf(r.getChemistry())));
            c3 = new PdfPCell(new Paragraph(String.valueOf(r.getMathematics())));
            c4 = new PdfPCell(new Paragraph(String.valueOf(r.getComputerScience())));
            c5 = new PdfPCell(new Paragraph(String.valueOf(r.getEnglish())));

            table2.addCell(c1);
            table2.addCell(c2);
            table2.addCell(c3);
            table2.addCell(c4);
            table2.addCell(c5);

            document.add(table2);

            document.add(new Paragraph("\n\n"));
            PdfPTable table3 = new PdfPTable(4);

            float[] colWidth1 = { 12f, 12f, 12f, 12f };
            table3.setWidths(colWidth1);
            c1 = new PdfPCell(new Paragraph("MARKS OBTAINED"));
            c2 = new PdfPCell(new Paragraph("TOTAL MARKS"));
            c3 = new PdfPCell(new Paragraph("STATUS"));
            c4 = new PdfPCell(new Paragraph("PERCENTAGE"));

            table3.addCell(c1);
            table3.addCell(c2);
            table3.addCell(c3);
            table3.addCell(c4);
            table3.addCell(c5);

            document.add(table3);

            PdfPTable table4 = new PdfPTable(4);

            table4.setWidths(colWidth1);
            c1 = new PdfPCell(new Paragraph(String.valueOf(r.getMarksObtained())));
            c2 = new PdfPCell(new Paragraph(String.valueOf(r.getTotalMarks())));
            c3 = new PdfPCell(new Paragraph(String.valueOf(r.getStatus())));
            c4 = new PdfPCell(new Paragraph(String.valueOf(r.getPercentage())));

            table4.addCell(c1);
            table4.addCell(c2);
            table4.addCell(c3);
            table4.addCell(c4);

            document.add(table4);
            //--------------------------

            document.close();
            writer.close();

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

      

        return "" + ConsoleColors.GREEN
        + " Success... pdf is opened to your default web browser open and do print by ctrl+p"
        + ConsoleColors.RESET;

    }
    

    static void rankList() {
        // printing ranks of all candidates

        
        String sql = "select id, name, ((physics + chemistry + mathematics + computerScience + english)/500)*100 as percentage from candidate order by percentage desc";
        ResultSet rs = null;
      
        Long id=(long) 0;
        String name="";
        Float percentage = 0f;
    
        try (Connection conn = DriverManager.getConnection(App.DB_URL, App.USER, App.PASS);
                PreparedStatement pst = conn.prepareStatement(sql);) {
     
        

            rs = pst.executeQuery();
            List<String[ ] > rankList = new ArrayList<String[ ] >();
          
            System.out.println(""+ConsoleColors.GREEN_BOLD_BRIGHT +"Id:"+"\t"+"NAME:"+"\t\t"+"PERCENTAGE:"+"\n"+ConsoleColors.CYAN_BOLD);
            while (rs.next()) {

                id = rs.getLong("id");
                name = rs.getString("name");
                percentage = rs.getFloat("percentage");
                String row[] = { String.valueOf(id), String.valueOf(name), String.valueOf(percentage) };
                rankList.add(row);
                System.out.println(id + "  " + name + "   " +  percentage + "\n");

            }
            System.out.println(App.line);
            System.out.println(""+ConsoleColors.GREEN_BOLD+"Enter 0 to exit OR  1 to print Result to pdf:  "+ConsoleColors.RESET);
        try{
            Scanner sc5 = new Scanner(System.in);

        int printChoice = sc5.nextInt();
        while (printChoice != 0 && printChoice != 1) {
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "you should input 0|1 to continue"+ConsoleColors.RESET);
            printChoice = sc5.nextInt();
        }
        if (printChoice == 1) {
            System.out.println(Print.printpdf(rankList));
          //pdf file, should be opening in default text editor or web browser
            File file = new File(".\\rankList.pdf");
            
            Desktop desktop = Desktop.getDesktop();
            if(file.exists()) desktop.open(file);

        }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        } catch (SQLException e) {
            e.printStackTrace();

        }

  

    }


    private static String printpdf(List<String[]> rankList) {
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(".\\rankList.pdf"));
            document.open();
            document.add(new Paragraph("Rank List of All Candidates: \n\n"));
            PdfPTable table1 = new PdfPTable(3);

            float[] colWidth = { 12f, 12f, 12f };
            table1.setWidths(colWidth);
            PdfPCell c1 = new PdfPCell(new Paragraph("ID"));
            PdfPCell c2 = new PdfPCell(new Paragraph("NAME"));
            PdfPCell c3 = new PdfPCell(new Paragraph("PERCENTAGE"));

            table1.addCell(c1);
            table1.addCell(c2);
            table1.addCell(c3);

            document.add(table1);
           
         
            for (int i = 0; i < rankList.size(); i++) {

                String row[] = rankList.get(i);
                PdfPTable table = new PdfPTable(3);
                table.setWidths(colWidth);
                c1 = new PdfPCell(new Paragraph(row[0]));
                c2 = new PdfPCell(new Paragraph(row[1]));
                c3 = new PdfPCell(new Paragraph(row[2]));

                table.addCell(c1);
                table.addCell(c2);
                table.addCell(c3);

                document.add(table);
            }
            document.close();
            writer.close();

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }


        return "" + ConsoleColors.GREEN
        + " Success... pdf is opened to your default web browser open and do print by ctrl+p"
        + ConsoleColors.RESET;
    }
    
 
    
}
