package bitlab.academyProject.SpringProject.repositories;


import bitlab.academyProject.SpringProject.models.AuthUser;
import bitlab.academyProject.SpringProject.models.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory,Long> {

List<PasswordHistory> findAllByUser(AuthUser user);
}
