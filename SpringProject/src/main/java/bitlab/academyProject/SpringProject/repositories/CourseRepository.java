package bitlab.academyProject.SpringProject.repositories;

import bitlab.academyProject.SpringProject.models.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CourseRepository extends JpaRepository<Courses,Long> {

    Courses findByName(String name);
}
