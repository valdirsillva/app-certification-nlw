package com.valdirsillva.appcertificationnlw.modules.students.useCases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valdirsillva.appcertificationnlw.modules.questions.entities.QuestionEntity;
import com.valdirsillva.appcertificationnlw.modules.questions.repositories.QuestionRepository;
import com.valdirsillva.appcertificationnlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.valdirsillva.appcertificationnlw.modules.students.dto.StudentVerifyCertificationDTO;
import com.valdirsillva.appcertificationnlw.modules.students.entities.AnswersCertificationEntity;
import com.valdirsillva.appcertificationnlw.modules.students.entities.CertificationStudentEntity;
import com.valdirsillva.appcertificationnlw.modules.students.entities.StudentEntity;
import com.valdirsillva.appcertificationnlw.modules.students.repositories.CertificationStudentRepository;
import com.valdirsillva.appcertificationnlw.modules.students.repositories.StudentRepository;

@Service
public class StudentCertificationUseCase {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CertificationStudentRepository certificationsStudentRepository;

    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    public CertificationStudentEntity execute(StudentCertificationAnswerDTO dto) throws Exception {
        var hasCertification = this.verifyIfHasCertificationUseCase
                .execute(new StudentVerifyCertificationDTO(dto.getEmail(), dto.getTechnology()));

        if (hasCertification) {
            throw new Exception("Você já tirou sua certificação!");
        }

        // Buscar as alterenativas das perguntas
        // Correta ou Incorreta
        List<QuestionEntity> questionsEntity = questionRepository.findByTechnology(dto.getTechnology());
        List<AnswersCertificationEntity> anwsersCertifications = new ArrayList<>();

        AtomicInteger correctAnswers = new AtomicInteger(0);

        dto.getQuestionsAnswers().stream().forEach(questionAnswer -> {
            var question = questionsEntity.stream()
                    .filter(q -> q.getId()
                            .equals((questionAnswer.getQuestionID())))
                    .findFirst().get();

            var findCorrectAlternative = question.getAlternatives().stream()
                    .filter(alternative -> alternative.isCorrect())
                    .findFirst().get();

            if (findCorrectAlternative.getId().equals(questionAnswer.getAlternativeID())) {
                questionAnswer.setCorrect(true);
                correctAnswers.incrementAndGet();
            } else {
                questionAnswer.setCorrect(false);
            }

            var answersCertificationsEntity = AnswersCertificationEntity.builder()
                    .answerID(questionAnswer.getAlternativeID())
                    .questionID(questionAnswer.getQuestionID())
                    .isCorrect(questionAnswer.isCorrect()).build();

            anwsersCertifications.add(answersCertificationsEntity);
        });

        // Verifica se student existe pelo email
        var student = studentRepository.findByEmail(dto.getEmail());
        UUID studentID;
        if (student.isEmpty()) {
            var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
            studentCreated = studentRepository.save(studentCreated);
            studentID = studentCreated.getId();
        } else {
            studentID = student.get().getId();
        }

        CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity.builder()
                .technology(dto.getTechnology())
                .studentID(studentID)
                .grade(correctAnswers.get())
                .build();

        var certificationStudentCreated = certificationsStudentRepository.save(certificationStudentEntity);
        anwsersCertifications.stream().forEach(anwserCertification -> {
            anwserCertification.setCertificationID(certificationStudentEntity.getId());
            anwserCertification.setCertificationStudentEntity(certificationStudentEntity);
        });

        certificationStudentEntity.setAnswersCertificationsEntities(anwsersCertifications);

        certificationsStudentRepository.save(certificationStudentEntity);

        return certificationStudentCreated;

        // Salvar as informaçlões da certificação
    }
}
