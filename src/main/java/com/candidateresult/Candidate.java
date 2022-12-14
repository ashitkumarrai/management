package com.candidateresult;


import de.vandermeer.asciitable.AsciiTable;
import lombok.AllArgsConstructor;

import lombok.Setter;



@Setter
@AllArgsConstructor
public class Candidate extends Person{
    private Long id;
    private String name;
    private String standard;
    private String dob;
    private String fatherName;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Candidate other = (Candidate) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStandard() {
        return standard;
    }

    public String getDob() {
        return dob;
    }

    public String getFatherName() {
        return fatherName;
    }



    @Override
    public String toString() {
        String mssg = ""+ ConsoleColors.CYAN_BOLD_BRIGHT;
        AsciiTable at = new AsciiTable();
        
  
        at.addRule();
        at.addRow("ID", id);
        at.addRule();
        
        at.addRow("NAME", name);
        at.addRule();
        
        at.addRow("FATHER NAME", fatherName);
        at.addRule();
        
        at.addRow("STANDARD", standard);
        at.addRule();
        
        at.addRow("D.O.B", dob);
        at.addRule();
        
        String rend = "";
        rend = mssg+ at.render();
        return ConsoleColors.CYAN_BOLD_BRIGHT+"Candidate Details:\n\n"+ConsoleColors.GREEN_BOLD+rend;
    }

    public String printpdf() {
    	return ConsoleColors.CYAN_BOLD_BRIGHT+"Candidate Details:\n\nID =>          " + id + "\nNAME =>        "  + name + "\nFATHER NAME => " + fatherName +"\nstandard =>    "
                + standard + "\nD.O.B =>       " + dob +"\n"+ConsoleColors.RESET+"\n";
    }
  
    






   
    

 
    @Override
    public String showMyDetail(Long id) {
       
     
        return this.toString();
    }

}
