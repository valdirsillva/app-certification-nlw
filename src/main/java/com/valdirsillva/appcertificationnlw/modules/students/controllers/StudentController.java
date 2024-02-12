package com.valdirsillva.appcertificationnlw.modules.students.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valdirsillva.appcertificationnlw.modules.students.dto.StudentVerifyCertificationDTO;
import com.valdirsillva.appcertificationnlw.modules.students.useCases.VerifyIfHasCertificatiuonUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/students")
public class StudentController {

    // Usar useCase
    @Autowired
    private VerifyIfHasCertificatiuonUseCase verifyIfHasCertificatiuonUseCase;

    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody StudentVerifyCertificationDTO studentVerifyCertificationDTO) {
        var result = this.verifyIfHasCertificatiuonUseCase.execute(studentVerifyCertificationDTO);
        if (result) {
            return "Usuário já fez a prova";
        }

        return "Usuario pode fazer a prova";
    }
}
