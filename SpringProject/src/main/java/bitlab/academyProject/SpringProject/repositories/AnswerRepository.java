package bitlab.academyProject.SpringProject.repositories;

import bitlab.academyProject.SpringProject.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AnswerRepository extends JpaRepository<Answer,Long> {

}
