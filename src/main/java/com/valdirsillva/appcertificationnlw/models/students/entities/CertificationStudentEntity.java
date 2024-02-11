package com.valdirsillva.appcertificationnlw.models.students.entities;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificationStudentEntity {

    private UUID id;
    private UUID studentID;
    private String tecnology;
    private int grate;

    List<AnswersCertificationEntity> anbAnswersCertificationEntities;

}
