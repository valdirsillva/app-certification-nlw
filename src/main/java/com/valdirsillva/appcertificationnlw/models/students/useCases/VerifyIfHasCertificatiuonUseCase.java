package com.valdirsillva.appcertificationnlw.models.students.useCases;

import org.springframework.stereotype.Service;

import com.valdirsillva.appcertificationnlw.models.students.dto.StudentVerifyCertificationDTO;

@Service
public class VerifyIfHasCertificatiuonUseCase {

    public boolean execute(StudentVerifyCertificationDTO dto) {
        if (dto.getEmail().equals("valdirpiresba@gmail.com") && dto.getTechnology().equals("java")) {
            return true;
        }

        return false;
    }
}
