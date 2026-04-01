package de.mkn.training;

import de.mkn.training.model.Subject;
import de.mkn.training.model.SubjectCategory;
import de.mkn.training.repository.SubjectRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    private final SubjectRepository subjectRepository;

    public DataInitializer(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (subjectRepository.count() == 0) {
            subjectRepository.save(new Subject("Strength Training", SubjectCategory.PHYSICAL));
            subjectRepository.save(new Subject("Guitar", SubjectCategory.MUSICAL));
            subjectRepository.save(new Subject("Vocabulary", SubjectCategory.COGNITIVE));
        }
    }
}
