package com.candidateresult;


import lombok.AllArgsConstructor;

import lombok.Setter;


@Setter
@AllArgsConstructor
public class Candidate extends Person implements Print{
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
        return ConsoleColors.CYAN_BOLD_BRIGHT+"Candidate Details:\n\ndob =>         " + dob + "\nfatherName =>  "  + fatherName + "\nid =>          " + id + "\nname =>        " + name + "\nstandard =>    "
                + standard + "\n"+ConsoleColors.RESET;
    }

    @Override
    public String updateMyDetail() {
        System.out.println("_______________________________________________________________");
        return ConsoleColors.RED_BOLD_BRIGHT + "contact your " + standard + "teacher for updation";
    }
    






    @Override
    public String printResult(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String viewResult(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String viewRank() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String printRankList() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String showMyDetail(Long id) {
        // TODO Auto-generated method stub
        System.out.println("_______________________________________________________________");
        return this.toString();
    }

}
