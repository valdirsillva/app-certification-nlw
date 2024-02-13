package com.valdirsillva.appcertificationnlw.certifications.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valdirsillva.appcertificationnlw.modules.students.entities.CertificationStudentEntity;
import com.valdirsillva.appcertificationnlw.modules.students.repositories.CertificationStudentRepository;

@Service
public class Top10RankingUseCase {

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    public List<CertificationStudentEntity> execute() {
        var result = this.certificationStudentRepository.findTop10ByOrderBygradeDesc();
        return result;
    }
}
