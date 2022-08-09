package com.candidateresult;

import java.io.FileWriter;

public class Print {
    

    public static String printpdf(Candidate c, Result r, Long id) {

        //printing pdf 
        System.out.println(r);

        System.out.println(c.showMyDetail(id));
        try {
            FileWriter fw = new FileWriter(String.format(".\\result%d.pdf", id));
            fw.write("Welcome to javaTpoint.");
            fw.close();

            









        } catch (Exception e) {
            System.out.println(e);
        }
      
        System.out.println("" + ConsoleColors.GREEN
                + " Success... pdf is opened to your default web browser open and do print by ctrl+p"
                + ConsoleColors.RESET);

        return "";

    }
    
 
    
}
