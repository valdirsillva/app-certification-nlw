package com.valdirsillva.appcertificationnlw.modules.students.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valdirsillva.appcertificationnlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.valdirsillva.appcertificationnlw.modules.students.dto.StudentVerifyCertificationDTO;
import com.valdirsillva.appcertificationnlw.modules.students.useCases.StudentCertificationUseCase;
import com.valdirsillva.appcertificationnlw.modules.students.useCases.VerifyIfHasCertificationUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentCertificationUseCase studentCertificationUseCase;

    // Usar useCase
    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody StudentVerifyCertificationDTO studentVerifyCertificationDTO) {
        var result = this.verifyIfHasCertificationUseCase.execute(studentVerifyCertificationDTO);
        if (result) {
            return "Usuário já fez a prova";
        }

        return "Usuario pode fazer a prova";
    }

    @PostMapping("/certification/answer")
    public ResponseEntity<Object> certificationAnswer(
            @RequestBody StudentCertificationAnswerDTO studentCertificationAnswerDTO) {

        try {
            var result = this.studentCertificationUseCase.execute(studentCertificationAnswerDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
