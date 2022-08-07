package com.candidateresult;

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
        return ""+ConsoleColors.BLUE_BOLD_BRIGHT+"Candidate Result:\n\nPHYSICS   CHEMISTRY   MATHEMATICS   COMPUTER-SCIENCE   ENGLISH    \n"+ConsoleColors.CYAN_BOLD_BRIGHT+physics+"       "+chemistry+"         "+mathematics+"            "+computerScience+"              "+english+ ConsoleColors.GREEN_BOLD_BRIGHT+"\n\n\t MARKS OBTAINED     TOTAL MARKS    STATUS    PERCENTAGE"+"\n\t   "+ConsoleColors.CYAN_BOLD_BRIGHT+marksObtained+"            "+totalMarks+"            " + status+"        " + percentage+"\n"+ConsoleColors.RESET;
    }
}
