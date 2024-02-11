package com.valdirsillva.appcertificationnlw.models.students.entities;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswersCertificationEntity {
    private UUID id;
    private UUID certificationID;
    private UUID studentID;
    private UUID answerID;
    private boolean isCorrect;

}
