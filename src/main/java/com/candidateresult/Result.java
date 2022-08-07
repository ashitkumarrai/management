package com.candidateresult;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    
    private Float physics;
    private Float chemistry;
    private Float mathematics;
    private Float computerScience;
    private Float english;

    private Float marksObtained;
    private Float totalMarks;
    private Float status;
    private Float rank;
    private Float percentage;
    @Override
    public String toString() {
        return ConsoleColors.GREEN_BOLD_BRIGHT+"Candidate Result:\n\nPHYSICS => " + physics + "\nCHEMISTRY =>  "  + chemistry + "\nMATHEMATICS =>          " + mathematics + "\nCOMPUTER-SCIENCE =>        " + computerScience + "\nENGLISH =>    "
                + english +  "\nMARKS OBTAINED =>        " +marksObtained+ "\nTOTAL MARKS =>        " + totalMarks +"\nSTATUS =>        " + status + "\nRANK =>        " + rank + "\nPERCENTAGE =>        " + percentage +"\n"+ConsoleColors.RESET;
    }
}
