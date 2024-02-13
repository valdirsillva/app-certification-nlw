package com.valdirsillva.appcertificationnlw.modules.students.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valdirsillva.appcertificationnlw.modules.students.dto.StudentVerifyCertificationDTO;
import com.valdirsillva.appcertificationnlw.modules.students.repositories.CertificationStudentRepository;

@Service
public class VerifyIfHasCertificationUseCase {

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    public boolean execute(StudentVerifyCertificationDTO dto) {
        var result = this.certificationStudentRepository
                .findByStudentEmailAndTechnology(dto.getEmail(), dto.getTechnology());

        if (!result.isEmpty()) {
            return true;
        }

        return false;
    }
}
