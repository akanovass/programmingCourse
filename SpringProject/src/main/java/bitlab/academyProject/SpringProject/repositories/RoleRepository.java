package bitlab.academyProject.SpringProject.repositories;

import bitlab.academyProject.SpringProject.models.AuthRole;
import bitlab.academyProject.SpringProject.models.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<AuthRole,Long> {


    AuthRole findByRole(String role);
//    List<AuthUser> findUserByRole(String role);


}
