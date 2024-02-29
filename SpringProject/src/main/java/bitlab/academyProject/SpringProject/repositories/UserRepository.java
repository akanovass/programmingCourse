package bitlab.academyProject.SpringProject.repositories;

import bitlab.academyProject.SpringProject.models.AuthRole;
import bitlab.academyProject.SpringProject.models.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<AuthUser,Long> {


    AuthUser findByEmail(String email);
}
