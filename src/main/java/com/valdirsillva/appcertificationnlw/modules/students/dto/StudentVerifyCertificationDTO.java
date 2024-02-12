package com.valdirsillva.appcertificationnlw.modules.students.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentVerifyCertificationDTO {
    private String email;
    private String technology;
}
