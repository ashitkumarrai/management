package com.candidateresult;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;


@Setter
@ToString
@EqualsAndHashCode
public abstract class Person {
    private Long id;
    private Long name;
    private Long dob;

    public abstract  String showMyDetail();
    public abstract  String updateMyDetail();
}
