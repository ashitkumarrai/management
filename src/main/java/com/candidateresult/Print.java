package com.candidateresult;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Print {
    

    public static String printpdf(Candidate c, Result r, Long id) {

        //printing pdf 
       

        

        Document document = new Document();
        try{
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(String.format(".\\result%d.pdf", id)));
            document.open();
            String text = c.showMyDetail(id);
           
            
            text = text.replaceAll("(\\[[1;96024]*m)", "");
           
            document.add(new Paragraph(text));
            PdfPTable table1 = new PdfPTable(5);
            
            float[] colWidth = {12f,12f,12f,12f,12f};
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
            
            float[] colWidth1 = {12f,12f,12f,12f};
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

        }
        catch(DocumentException | FileNotFoundException e){
            e.printStackTrace();
        }
      
        System.out.println("" + ConsoleColors.GREEN
                + " Success... pdf is opened to your default web browser open and do print by ctrl+p"
                + ConsoleColors.RESET);

        

        return "";

    }
    
 
    
}
