package bitlab.academyProject.SpringProject.repositories;

import bitlab.academyProject.SpringProject.models.Tasks;
import bitlab.academyProject.SpringProject.models.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TopicRepository extends JpaRepository<Topics,Long> {


    List<Topics> getTopicsById(Long id);
//    void deleteAllByTasks(List<Tasks> tasks);

}
