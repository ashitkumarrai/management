package com.candidateresult;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@ToString
@EqualsAndHashCode
public abstract class Person {
    private Long id;
    private Long name;
    private Long dob;

    public  abstract  String showMyDetail(Long id);
  
}
