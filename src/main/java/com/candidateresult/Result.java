package com.candidateresult;

import de.vandermeer.asciitable.AsciiTable;
import lombok.Data;


@Data

public class Result {
    
    private Float physics=0f;
    private Float chemistry=0f;
    private Float mathematics=0f;
    private Float computerScience=0f;
    private Float english=0f;
    private Float marksObtained = 0f;
    private Float totalMarks=0f;
    private String status= "not Available";
    private Float percentage= 0f;

    public Result(Float physics, Float chemistry, Float mathematics, Float computerScience, Float english) {
      
        this.physics = physics;
        this.chemistry = chemistry;
        this.mathematics = mathematics;
        this.computerScience = computerScience;
        this.english = english;
        this.marksObtained = physics + chemistry + mathematics + computerScience + english;
        this.totalMarks = 500f;
        this.status = marksObtained >= 250 ? "PASS" : "FAIL";
        this.percentage = (marksObtained / 500f) * 100;
    }

    @Override
    public String toString() {
        String mssg = ""+ ConsoleColors.BLUE_BOLD_BRIGHT;
        AsciiTable at = new AsciiTable();
        AsciiTable at1 = new AsciiTable();
        
        at.addRule();
        at.addRow("PHYSICS", "CHEMISTRY","MATHEMATICS","CS","ENGLISH");
        at.addRule();
        at1.addRule();
        
        at1.addRow(physics,chemistry,mathematics,computerScience,english);
        at1.addRule();
        

        String rend2 = at1.render();
        String rend = "";
        rend = mssg+ at.render();
        
        return "Candidate Result:\n\n"+rend+ConsoleColors.CYAN_BOLD_BRIGHT+"\n"+rend2+"\n\n"+"      "+" MARKS OBTAINED     TOTAL MARKS    STATUS    PERCENTAGE"+"\n        "+ConsoleColors.BLUE_BOLD+marksObtained+"                "+totalMarks+"        " + status+"        " + percentage+"\n"+ConsoleColors.RESET;
    }
}
